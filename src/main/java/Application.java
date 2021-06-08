import constants.Movement;
import game.Player;
import game.Worker;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static constants.ApplicationConstants.DOWN;
import static constants.ApplicationConstants.INSTRUCTIONS;
import static constants.ApplicationConstants.INSTRUCTIONS2;
import static constants.ApplicationConstants.QUIT;
import static constants.ApplicationConstants.RIGHT;
import static constants.ApplicationConstants.START;
import static constants.ApplicationConstants.UP;
import static helper.JDAHelper.getBoardEmbed;
import static helper.JDAHelper.getExitEmbed;
import static helper.JDAHelper.getGameOverEmbed;
import static helper.JDAHelper.getInstructionEmbed;
import static helper.JDAHelper.getWelcomeEmbed;
import static helper.JDAHelper.getWinnerEmbed;

/**
 * @author Srijan
 */

//@Slf4j
public class Application extends ListenerAdapter {

  Logger log = LoggerFactory.getLogger(Application.class);

  private Map<String, String> players = new HashMap<>();
  private Map<String, Player> messageToBoard = new HashMap<>();

  private Worker worker = new Worker();

  private Set<String> inputs = Set.of(START, QUIT, INSTRUCTIONS, INSTRUCTIONS2);

  public static void main(String[] args) throws LoginException {

    String token = System.getenv().get("token");;

    JDABuilder.createLight(token)
      .addEventListeners(new Application())
      .setActivity(Activity.playing("Nine Game"))
      .build();


    System.out.println("hey");
  }

  private void onReaction(GenericMessageEvent event, MessageReaction.ReactionEmote emote, User user){
    try{
      String input = emote.getEmoji();
      System.out.println("Emote reaction received" + event.getMessageId()+ " ");
      System.out.println("Emote - " + input);
      MessageChannel channel = event.getChannel();
      if(event.isFromType(ChannelType.PRIVATE) && !user.isBot()) {
//        event.getReaction().removeReaction(event.getUser()).queue();
        System.out.println("emote 2");
        Movement movement = this.getMovement(input);
        log.error("MessageId {}", event.getMessageId());

        Player player = this.messageToBoard.get(event.getMessageId());
        String status = "error";
        try{
          status = worker.makeMovement(movement, player);
        }
        catch (Exception e){
          e.printStackTrace();
        }

        try{
          channel.editMessageById(event.getMessageId(), getBoardEmbed(player.toString()))
            .queue(response -> {
              log.info("{}", response);
              System.out.println(response);
            });

          System.out.println("Status : "+status);
          if(status.equalsIgnoreCase("error")){
            channel.sendMessage("Some Error occurred !!")
              .queue(response -> {
                log.info("{}", response);
              });

            String id = messageToBoard.remove(event.getMessageId()).getId();
            players.remove(id);
          }
          else if(status.equalsIgnoreCase("over")){
            channel.sendMessage(getGameOverEmbed())
              .queue(response -> {
                log.info("{}", response);
              });
            String id = messageToBoard.remove(event.getMessageId()).getId();
            players.remove(id);
          }
          else if(status.equalsIgnoreCase("win")){
            channel.sendMessage(getWinnerEmbed())
              .queue(response -> {
                log.info("{}", response);
              });
            String id = messageToBoard.remove(event.getMessageId()).getId();
            players.remove(id);
          }
        }
        catch (Exception e){
          e.printStackTrace();
        }
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent event) {

    this.onReaction(event, event.getReactionEmote(), event.getUser());
  }

  @Override
  public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {

    this.onReaction(event, event.getReactionEmote(), event.getUser());
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event)
  {
    MessageChannel channel = event.getChannel();
    Message msg = event.getMessage();

    System.out.println("ignore " + msg.getMember() + " "+ msg.getId());
    if(event.getAuthor().isBot() || !event.getAuthor().hasPrivateChannel())
      return;
    System.out.println("hey - "+ msg.getContentRaw());
    if(!inputs.contains(msg.getContentRaw().trim())) {
      channel.sendMessage("Please enter `"+INSTRUCTIONS+"` for instructions")
        .queue(response -> {
          log.info("{}", response);
        });
      return;
    }

    String input = msg.getContentRaw();

    if(INSTRUCTIONS.equalsIgnoreCase(input) || INSTRUCTIONS2.equalsIgnoreCase(input)){
      channel.sendMessage(getInstructionEmbed())
        .queue(response -> {
          log.info("{}", response);
        });
      return;
    }


    if(START.equalsIgnoreCase(input) ){
      if(players.containsKey(event.getAuthor().getAvatarId())) {
        return;
      }
        try{
          channel.sendMessage(getWelcomeEmbed())
            .queue(response -> {
              Player player = new Player(event.getAuthor().getAvatarId());
              channel.sendMessage(getBoardEmbed(player.toString()))
                .queue(response2 -> {

                  players.put(event.getAuthor().getAvatarId(), response2.getId());
                  messageToBoard.put(response2.getId(), player);

                  response2.addReaction("⬅️").queue();
                  response2.addReaction("➡️").queue();
                  response2.addReaction("⬇️").queue();
                  response2.addReaction("⬆️").queue();
                });
            });


        }
        catch (Exception e){
          e.printStackTrace();
        }

    }
    else if(QUIT.equalsIgnoreCase(input)){
      if(players.containsKey(event.getAuthor().getAvatarId())) {
        channel.sendMessage(getExitEmbed())
          .queue(response -> {
            log.info("{}", response);
          });
        players.remove(event.getAuthor().getAvatarId());
      }
    }
    else if(!players.containsKey(event.getAuthor().getAvatarId())) {
      channel.sendMessage("Please enter `"+INSTRUCTIONS+"` for instructions")
        .queue(response -> {
          log.info("{}", response);
        });
    }

  }

  private Movement getMovement(String movement){
    if(UP.equalsIgnoreCase(movement))
      return Movement.UP;
    else if(DOWN.equalsIgnoreCase(movement))
      return Movement.DOWN;
    else if(RIGHT.equalsIgnoreCase(movement))
      return Movement.RIGHT;
    else
      return Movement.LEFT;
  }



}

import constants.Movement;
import game.Player;
import game.Worker;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static constants.ApplicationConstants.DOWN;
import static constants.ApplicationConstants.DOWN2;
import static constants.ApplicationConstants.LEFT;
import static constants.ApplicationConstants.LEFT2;
import static constants.ApplicationConstants.QUIT;
import static constants.ApplicationConstants.RIGHT;
import static constants.ApplicationConstants.RIGHT2;
import static constants.ApplicationConstants.START;
import static constants.ApplicationConstants.UP;
import static constants.ApplicationConstants.UP2;
import static helper.JDAHelper.getBoardEmbed;
import static helper.JDAHelper.getExitEmbed;
import static helper.JDAHelper.getGameOverEmbed;
import static helper.JDAHelper.getWelcomeEmbed;
import static helper.JDAHelper.getWinnerEmbed;

/**
 * @author Srijan
 */

//@Slf4j
public class Application extends ListenerAdapter {

  Logger log = LoggerFactory.getLogger(Application.class);

  private Map<String, Player> players = new HashMap<>();

  private Worker worker = new Worker();

  private Set<String> inputs = Set.of(UP, DOWN, RIGHT, LEFT, START, QUIT, UP2, DOWN2, RIGHT2, LEFT2);

  public static void main(String[] args) throws LoginException {

    String token = System.getenv().get("token");;

    JDABuilder.createLight(token)
      .addEventListeners(new Application())
      .setActivity(Activity.playing("!9 - Game"))
      .build();


    System.out.println("hey");
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event)
  {
    System.out.println("hey");
    MessageChannel channel = event.getChannel();
    Message msg = event.getMessage();

    if(event.getAuthor().isBot())
      return;
    if(!inputs.contains(msg.getContentRaw().trim())) {
      channel.sendMessage("Please enter `"+START+"` to start the game")
        .queue(response -> {
          log.info("{}", response);
        });
      return;
    }
    String input = msg.getContentRaw();


    if(START.equalsIgnoreCase(input) ){
      if(players.containsKey(event.getAuthor().getAvatarId())) {
        return;
      }
        try{
          channel.sendMessage(getWelcomeEmbed())
            .queue(response -> {
              log.info("{}", response);
            });
        }
        catch (Exception e){
          e.printStackTrace();
        }
          players.put(event.getAuthor().getAvatarId(), new Player());

    }
    else if(QUIT.equalsIgnoreCase(input)){
      if(players.containsKey(event.getAuthor().getAvatarId())) {
        channel.sendMessage(getExitEmbed())
          .queue(response -> {
            log.info("{}", response);
          });
        players.remove(event.getAuthor().getAvatarId());
      }
      return;
    }
    else if(!players.containsKey(event.getAuthor().getAvatarId())) {
      channel.sendMessage("Please enter `"+START+"` to start the game")
        .queue(response -> {
          log.info("{}", response);
        });
      return;
    }

    Movement movement = this.getMovement(input);
    log.error("Author {}", event.getAuthor());

    Player player = this.players.get(event.getAuthor().getAvatarId());
    String status = "error";
    try{
      status = worker.makeMovement(movement, player);
    }
    catch (Exception e){
      e.printStackTrace();
    }

    try{
      channel.sendMessage(getBoardEmbed(player.toString()))
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
        players.remove(event.getAuthor().getAvatarId());
      }
      else if(status.equalsIgnoreCase("over")){
        channel.sendMessage(getGameOverEmbed())
          .queue(response -> {
            log.info("{}", response);
          });
        players.remove(event.getAuthor().getAvatarId());
      }
      else if(status.equalsIgnoreCase("win")){
        channel.sendMessage(getWinnerEmbed())
          .queue(response -> {
            log.info("{}", response);
          });
        players.remove(event.getAuthor().getAvatarId());
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  private Movement getMovement(String movement){
    if(UP.equalsIgnoreCase(movement) || UP2.equalsIgnoreCase(movement))
      return Movement.UP;
    else if(DOWN.equalsIgnoreCase(movement) || DOWN2.equalsIgnoreCase(movement))
      return Movement.DOWN;
    else if(RIGHT.equalsIgnoreCase(movement) || RIGHT2.equalsIgnoreCase(movement))
      return Movement.RIGHT;
    else
      return Movement.LEFT;
  }



}

package helper;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

/**
 * @author Srijan
 */
public class JDAHelper {
  public static MessageEmbed getWelcomeEmbed(){
    EmbedBuilder embedBuilder = new EmbedBuilder();

    embedBuilder.setTitle("Start Game");
    embedBuilder.setColor(Color.BLUE);
    embedBuilder.setDescription("Welcome !!!");

    return embedBuilder.build();
  }
  public static MessageEmbed getExitEmbed(){
    EmbedBuilder embedBuilder = new EmbedBuilder();

    embedBuilder.setTitle("Exit Game");
    embedBuilder.setColor(Color.GRAY);
    embedBuilder.setDescription("Thanks for playing");

    return embedBuilder.build();
  }
  public static MessageEmbed getBoardEmbed(String board){
    EmbedBuilder embedBuilder = new EmbedBuilder();

    embedBuilder.setTitle("Nine !!!");
    embedBuilder.setColor(Color.YELLOW);
    embedBuilder.setDescription(board);


    return embedBuilder.build();
  }
  public static MessageEmbed getWinnerEmbed(){
    EmbedBuilder embedBuilder = new EmbedBuilder();

    embedBuilder.setTitle("You Win !!!");
    embedBuilder.setColor(Color.GREEN);
    embedBuilder.setDescription("Congratulation...");

    return embedBuilder.build();
  }
  public static MessageEmbed getGameOverEmbed(){
    EmbedBuilder embedBuilder = new EmbedBuilder();

    embedBuilder.setTitle("Game Over !!!");
    embedBuilder.setColor(Color.RED);

    return embedBuilder.build();
  }
}

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

    embedBuilder.setTitle("Welcome !!!");
    embedBuilder.setColor(Color.BLUE);
    embedBuilder.setDescription("Please use the below commands to play the game-" +
      "\n`i` or `I` - For Instructions" +
      "\n`\u2B05\uFE0F` - Move all blocks to left" +
      "\n`\u27A1\uFE0F` - Move all blocks to right" +
      "\n`\u2B06\uFE0F` - Move all blocks to up" +
      "\n`\u2B07\uFE0F` - Move all blocks to down" +
      "\n`!quit` - To quit the game");

    return embedBuilder.build();
  }

  public static MessageEmbed getInstructionEmbed(){
    EmbedBuilder embedBuilder = new EmbedBuilder();

    embedBuilder.setTitle("Instructions");
    embedBuilder.setColor(Color.BLUE);
    embedBuilder.setDescription("Please use the below commands to play the game-" +
      "\n`i` or `I` - For Instructions" +
      "\n`!start` - To start the game" +
      "\n`\u2B05\uFE0F` - Move all blocks to left" +
      "\n`\u27A1\uFE0F` - Move all blocks to right" +
      "\n`\u2B06\uFE0F` - Move all blocks to up" +
      "\n`\u2B07\uFE0F` - Move all blocks to down" +
      "\n`!quit` - To quit the game");

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

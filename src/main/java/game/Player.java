package game;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author srijan
 */

@Getter
public class Player {

  private static Map<Integer, String> valueMapper = new HashMap<>();
  private static String smallSpacing = "  \t";
  private static String halfSmallSpacing = "   ";
  private static String bigSpacing = "            ";

  static {
    String spacing= smallSpacing;
    valueMapper.put(0, spacing + spacing + spacing + spacing);
    valueMapper.put(2, spacing + halfSmallSpacing +":two:" + halfSmallSpacing+ spacing);
    valueMapper.put(4, spacing + halfSmallSpacing +":four:"+ halfSmallSpacing+ spacing);
    valueMapper.put(8, spacing + halfSmallSpacing +":eight:"+ halfSmallSpacing+ spacing);
    valueMapper.put(16, spacing +":one::six:"+spacing);
    valueMapper.put(32, spacing  +":three::two:"+spacing);
    valueMapper.put(64, spacing +":six::four:"+spacing);
    valueMapper.put(128, halfSmallSpacing +":one::two::eight:"+halfSmallSpacing);
    valueMapper.put(256, halfSmallSpacing +":two::five::six:"+halfSmallSpacing);
    valueMapper.put(512, halfSmallSpacing +":five::one::two:"+halfSmallSpacing);
    valueMapper.put(1024, ":one::zero::two::four:");
  }

  private int[][] state = new int[4][4];

  public Player(){
//    state[1][0] = 512;
//    state[1][1] = 256;
//    state[1][2] = 256;
//    state[1][3] = 8;
  }


  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append(IntStream.range(0, 18).mapToObj(i -> ":white_large_square:").collect(Collectors.joining(""))).append("\n");
    int in=1;
    for(int[] arr : state){
//      builder.append(":white_large_square:").append(IntStream.range(0, 16).mapToObj(i -> smallSpacing).collect(Collectors.joining(""))).append(":white_large_square:").append("\n");
      builder.append(":white_large_square:");
      for (int value : arr) {
        builder.append(valueMapper.get(value));
      }
      builder.append(":white_large_square:").append("\n");
      if (in++<4)
      builder.append(":white_large_square:").append(IntStream.range(0, 16).mapToObj(i -> smallSpacing).collect(Collectors.joining(""))).append(":white_large_square:").append("\n");
    }

      builder.append(IntStream.range(0, 18).mapToObj(i -> ":white_large_square:").collect(Collectors.joining(""))).append("\n");
    return builder.toString();
  }

  public static String getAll(){
    StringBuilder builder = new StringBuilder();
    for(String s : valueMapper.values())
      builder.append(s).append("\n");
    System.out.println("builder : "+builder.toString());
    return builder.toString();
  }
}

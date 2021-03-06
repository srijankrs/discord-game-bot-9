package game;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author srijan
 */

@Getter
@Slf4j
public class Player {

  private static Map<Integer, String> valueMapper = new HashMap<>();
  private final String id;
  private static final String LARGE_WHITE_SQUARE_EMOJI = ":white_large_square:";

  static {
    valueMapper.put(-1, ":black_large_square:");
    valueMapper.put(0, ":zero:");
    valueMapper.put(1, ":one:");
    valueMapper.put(2, ":two:");
    valueMapper.put(3, ":three:");
    valueMapper.put(4, ":four:");
    valueMapper.put(5, ":five:");
    valueMapper.put(6, ":six:");
    valueMapper.put(7, ":seven:");
    valueMapper.put(8, ":eight:");
    valueMapper.put(9, ":nine:");
  }

  private int[][] state = new int[4][4];

  public Player(String id) {
    this.id = id;
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++) {
        state[i][j] = -1;
      }
    state[2][2] = 0;
  }


  @Override
  public String toString() {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(IntStream.range(0, 6).mapToObj(i -> LARGE_WHITE_SQUARE_EMOJI).collect(Collectors.joining(""))).append("\n");
    for (int[] arr : state) {
      stringBuilder.append(LARGE_WHITE_SQUARE_EMOJI);
      for (int value : arr) {
        stringBuilder.append(valueMapper.get(value));
      }
      stringBuilder.append(LARGE_WHITE_SQUARE_EMOJI).append("\n");
    }
    stringBuilder.append(IntStream.range(0, 6).mapToObj(i -> LARGE_WHITE_SQUARE_EMOJI).collect(Collectors.joining(""))).append("\n");

    return stringBuilder.toString();
  }

  public static String getAll() {
    StringBuilder builder = new StringBuilder();
    for (String s : valueMapper.values())
      builder.append(s).append("\n");
    log.debug("Builder : " + builder.toString());
    return builder.toString();
  }
}

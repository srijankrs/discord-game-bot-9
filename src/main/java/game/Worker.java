package game;

import constants.Movement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Srijan
 */
public class Worker {

  private static final int MAX = 4;

  public String makeMovement(Movement movement, Player player){
    int[][] state = player.getState();

    switch (movement){
      case RIGHT:
        for(int i=0;i<MAX;i++){
          int[] fArr = transform(new int[]{state[i][3],state[i][2],state[i][1],state[i][0]});
          int k=MAX-1;
          for(int j=0;j<MAX;j++){
            state[i][j] = fArr[k];
            k--;
          }
        }
        break;
      case LEFT:
        for(int i=0;i<MAX;i++){
          int[] fArr = transform(new int[]{state[i][0],state[i][1],state[i][2],state[i][3]});
          for(int j=0;j<MAX;j++){
            state[i][j] = fArr[j];
          }
        }
        break;
      case DOWN:
        for(int i=0;i<MAX;i++){
          int[] fArr = transform(new int[]{state[3][i],state[2][i],state[1][i],state[0][i]});
          int k=MAX-1;
          for(int j=0;j<MAX;j++){
            state[j][i] = fArr[k];
            k--;
          }
        }
        break;
      case UP:
        for(int i=0;i<MAX;i++){
          int[] fArr = transform(new int[]{state[0][i],state[1][i],state[2][i],state[3][i]});
          for(int j=0;j<MAX;j++){
            state[j][i] = fArr[j];
          }
        }
        break;
    }

    return checkWin(player) ? "win" : generateRandom(player);
  }

  private boolean checkWin(Player player){
    int[][] state = player.getState();
    for(int i=0;i<4;i++)
      for(int j=0;j<4;j++){
        if(state[i][j] == 9)
          return true;
      }
    return false;
  }

  private String generateRandom(Player player){
    int[][] state = player.getState();

    List<Pair> vacant = new ArrayList<>();
    for(int i=0;i<4;i++)
      for(int j=0;j<4;j++)
        if(state[i][j] == -1) {
          vacant.add(new Pair(i, j));
        }

    if(vacant.size()==0)
      return "over";
    int randomIndex = new Random().nextInt(vacant.size());
    state[vacant.get(randomIndex).x][vacant.get(randomIndex).y] = 0;
    return "continue";
  }

  private class Pair{
    int x;
    int y;
    Pair(int x, int y){
      this.x = x;
      this.y = y;
    }
  }


  private int[] transform(int[] list){
    int[] finalList = new int[4];
    Arrays.fill(finalList, -1);
    int index=0;
    for(int item : list) {
      if(item != -1){
        if(finalList[index]==item){
          finalList[index]+=1;
          index++;
        }
        else if(finalList[index]!=item && finalList[index]!=-1){
          index++;
          finalList[index]=item;
        }
        else{
          finalList[index]=item;
        }
      }
    }
    return finalList;
  }
}

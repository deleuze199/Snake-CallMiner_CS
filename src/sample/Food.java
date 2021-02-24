package sample;

import java.util.LinkedList;

public class Food {

  int rows;
  int columns;
  int x;
  int y;

  public Food(int numOfRows, int numOfColumns) {
    rows = numOfRows;
    columns = numOfColumns;
  }

  public void createNewLocation(LinkedList<int[]> snakeLocation) {
    x = (int) (Math.random() * rows);
    y = (int) (Math.random() * columns);
    int[] newFoodLocation = {x,y};
    if (isNewLocationInSnake(snakeLocation, newFoodLocation)) {
      createNewLocation(snakeLocation);
    }
  }

  public boolean isNewLocationInSnake(LinkedList<int[]> snakeLocation, int[] newFoodLocation) {
    boolean foodInSnake = false;
    for (int[] currentSnakeLocation : snakeLocation) {
      if (currentSnakeLocation[0] == newFoodLocation[0]
          && currentSnakeLocation[1] == newFoodLocation[1]) {
        foodInSnake = true;
        break;
      }
    }
    return foodInSnake;
  }

  public int[] getLocation() {
    return new int[]{x, y};
  }
}

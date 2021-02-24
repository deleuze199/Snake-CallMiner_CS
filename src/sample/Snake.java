package sample;

import java.util.LinkedList;

public class Snake {

  int snakeSize = 5;
  public LinkedList<int[]> location = new LinkedList();
  String currentDirection = "right";
  Boolean gameOver = false;

  Snake() {
    int[] startingPosition;
    for (int i = 0; i < snakeSize; i++) {
      startingPosition = new int[]{i, 10};
      location.addFirst(startingPosition);
    }
  }

  public void move(boolean isSnakeGrowing) {
    switch (currentDirection) {
      case "right":
        snakeMoveRight(isSnakeGrowing);
        break;
      case "left":
        snakeMoveLeft(isSnakeGrowing);
        break;
      case "up":
        snakeMoveUp(isSnakeGrowing);
        break;
      case "down":
        snakeMoveDown(isSnakeGrowing);
        break;
    }

  }

  public LinkedList<int[]> snakeMoveRight(boolean isSnakeGrowing) {
    currentDirection = "right";
    int[] oldLocationHead = location.peekFirst();
    int[] newLocationHead = new int[]{oldLocationHead[0] + 1, oldLocationHead[1]};
    if (newLocationHead[0] > 19) {
      newLocationHead[0] = 0;
    }
    isSpaceOccupied(location, newLocationHead);
    location.addFirst(newLocationHead);
    if (!isSnakeGrowing) {
      location.removeLast();
    }
    return location;
  }

  public LinkedList<int[]> snakeMoveLeft(boolean isSnakeGrowing) {
    currentDirection = "left";
    int[] oldLocationHead = location.peekFirst();
    int[] newLocationHead = new int[]{oldLocationHead[0] - 1, oldLocationHead[1]};
    if (newLocationHead[0] < 0) {
      newLocationHead[0] = 19;
    }
    isSpaceOccupied(location, newLocationHead);
    location.addFirst(newLocationHead);
    if (!isSnakeGrowing) {
      location.removeLast();
    }
    return location;
  }

  public LinkedList<int[]> snakeMoveDown(boolean isSnakeGrowing) {
    currentDirection = "down";
    int[] oldLocationHead = location.peekFirst();
    int[] newLocationHead = new int[]{oldLocationHead[0], oldLocationHead[1] + 1};
    if (newLocationHead[1] > 19) {
      newLocationHead[1] = 0;
    }
    isSpaceOccupied(location, newLocationHead);
    location.addFirst(newLocationHead);
    if (!isSnakeGrowing) {
      location.removeLast();
    }
    return location;
  }

  public LinkedList<int[]> snakeMoveUp(boolean isSnakeGrowing) {
    currentDirection = "up";
    int[] oldLocationHead = location.peekFirst();
    int[] newLocationHead = new int[]{oldLocationHead[0], oldLocationHead[1] - 1};
    if (newLocationHead[1] < 0) {
      newLocationHead[1] = 19;
    }
    isSpaceOccupied(location, newLocationHead);
    location.addFirst(newLocationHead);
    if (!isSnakeGrowing) {
      location.removeLast();
    }
    return location;
  }

  public void isSpaceOccupied(LinkedList<int[]> snakeLocation, int[] newLocationHead) {
    boolean isOccupied = false;
    for (int[] currentSnakeLocation : snakeLocation) {
      if (newLocationHead[0] == currentSnakeLocation[0]
          && newLocationHead[1] == currentSnakeLocation[1]) {
        isOccupied = true;
        break;
      }
    }
    if (isOccupied) {
      gameOver = true;
    }
  }

  public void increaseSnakeSize() {
    move(true);
    snakeSize++;
  }

  public String getCurrentDirection() {
    return currentDirection;
  }

  public Boolean getGameOver() {
    return gameOver;
  }

  public void setGameOver(boolean gameOver1) {
    gameOver = gameOver1;
  }

  public LinkedList<int[]> getLocationList() {
    return location;
  }

  public void setCurrentDirection(String currentDirection) {
    this.currentDirection = currentDirection;
  }
}

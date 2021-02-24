package sample;

import java.util.LinkedList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

  @FXML
  private Button buttonPlayAgain;
  private GraphicsContext gc;
  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;
  private static final int ROWS = 20;
  private static final int COLUMNS = ROWS;
  private static final double SQUARE_SIZE = WIDTH / ROWS;
  private Image foodImage = new Image("./img/apple.png");
  private Snake snake;
  private Food food;
  Stage stage;
  Group root;
  private Timeline timeline;
  private int score;


  @Override
  public void start(Stage primaryStage) {
//        Group root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
    stage = primaryStage;
    primaryStage.setTitle("Snake");
    root = new Group();
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    root.getChildren().add(canvas);
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    gc = canvas.getGraphicsContext2D();
    score = 0;
    snake = new Snake();
    food = new Food(ROWS, COLUMNS);
    food.createNewLocation(snake.getLocationList());

    scene.setOnKeyPressed(event -> {
      KeyCode code = event.getCode();
      if (code == KeyCode.RIGHT || code == KeyCode.D) {
        if (!snake.getCurrentDirection().equals("left")) {
          snake.setCurrentDirection("right");
        }
      } else if (code == KeyCode.LEFT || code == KeyCode.A) {
        if (!snake.getCurrentDirection().equals("right")) {
          snake.setCurrentDirection("left");
        }
      } else if (code == KeyCode.UP || code == KeyCode.W) {
        if (!snake.getCurrentDirection().equals("down")) {
          snake.setCurrentDirection("up");
        }
      } else if (code == KeyCode.DOWN || code == KeyCode.S) {
        if (!snake.getCurrentDirection().equals("up")) {
          snake.setCurrentDirection("down");
        }
      }
    });

    timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> run(gc)));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  private void run(GraphicsContext gc) {
    if (!snake.getGameOver()) {
      drawBackground(gc);
      drawSnake(gc);
      drawFood(gc);
      drawScore();
      if (isFoodEaten(snake.getLocationList(), food.getLocation())) {
        food.createNewLocation(snake.getLocationList());
        snake.increaseSnakeSize();
        score += 1;
      }
    } else {
      endGame();
    }
    snake.move(false);
  }


  private boolean isFoodEaten(LinkedList<int[]> snakeLocation, int[] foodLocation) {
    boolean foodEaten = false;
    int[] snakeHeadLocation = snakeLocation.peekFirst();
    if (snakeHeadLocation[0] == foodLocation[0] && snakeHeadLocation[1] == foodLocation[1]) {
      foodEaten = true;
    }
    return foodEaten;
  }

  private void drawBackground(GraphicsContext gc) {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS; j++) {
        if ((i + j) % 2 == 0) {
          gc.setFill(Color.web("AAD751"));
        } else {
          gc.setFill(Color.web("A2D149"));
        }
        gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
      }
    }
  }

  private void drawSnake(GraphicsContext gc) {
    gc.setFill(Color.web("4674E9"));
    //int[] temp = (int[]) snake.getLocationList().peekFirst();
    //gc.fillRoundRect(temp[0] * SQUARE_SIZE, temp[1] * SQUARE_SIZE,  SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);
    for (int[] currentSquare : snake.getLocationList()) {
      gc.fillRoundRect(currentSquare[0] * SQUARE_SIZE, currentSquare[1] * SQUARE_SIZE,
          SQUARE_SIZE - 1, SQUARE_SIZE - 1, 20, 20);
    }
  }

  public void drawFood(GraphicsContext gc) {
    int[] foodLocation = food.getLocation();
    gc.drawImage(foodImage, foodLocation[0] * SQUARE_SIZE, foodLocation[1] * SQUARE_SIZE,
        SQUARE_SIZE, SQUARE_SIZE);
  }

  public void drawScore() {
    gc.setFill(Color.WHITE);
    gc.setFont(new Font(30));
    gc.fillText("Score: " + score, 10, 35);
  }

  public void endGame() {
    timeline.stop();
    gc.setFill(Color.RED);
    gc.setFont(new Font(70));
    gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
    buttonPlayAgain = new Button("Play Again");
    root.getChildren().add(buttonPlayAgain);
    buttonPlayAgain.setLayoutX((WIDTH / 2) - 50);
    buttonPlayAgain.setLayoutY((HEIGHT / 2) + 15);
    buttonPlayAgain.setOnAction(e -> {
      try {
        snake.setGameOver(false);
        start(stage);
        //run(gc);
        root.getChildren().remove(buttonPlayAgain);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
  }

  public static void main(String[] args) {
    launch(args);
  }
}

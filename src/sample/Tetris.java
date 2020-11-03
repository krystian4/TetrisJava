package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Tetris extends Application {
    //Tetris settings
    public static final int MOVE = 25;
    public static final int BLOCKSIZE = 25;
    public static int WIDTH = BLOCKSIZE * 12;
    public static int HEIGHT = BLOCKSIZE * 24;
    public static int score = 0;
    //TEXTS
    private final Text scoreText = new Text("");
    private final Text level = new Text("");
    private final Text gameOver = new Text("");
    private static final Text pauseText = new Text("");

    //board array contains information if block is busy(1) empty(0)
    public static int [][] BOARD = new int[WIDTH/ BLOCKSIZE][HEIGHT/ BLOCKSIZE];
    public static Pane group;
    public static Figure figure;
    public static Figure nextFigure;
    public static Scene scene;
    private static boolean gameRunning = true;
    public static boolean gamePaused = false;
    public static int numOfLines = 0;
    private static int blockOnTop = 0;
    private Stage mainStage;
    //menu
    private Pane menuPane = new Pane();
    Scene menuScene = new Scene(menuPane, 300, 600);

    public static void main() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        //if window is closed
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        //menu scene
        addStartButtonToPane();
        addControlsButtonToPane();
        menuScene.setFill(Color.rgb(8,15,48));
        menuScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        mainStage.setTitle("M E N U");
        mainStage.setScene(menuScene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    private void addControlsButtonToPane() {
        Button controlsButton = new Button("CONTROLS");
        menuScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        controlsButton.setPrefWidth(150);
        controlsButton.setLayoutX(75);
        controlsButton.setLayoutY(150);
        menuPane.getChildren().add(controlsButton);
        group = new Pane();
        group.setId("controls");
        addBackButtonToPane(group);
        Scene controlsScene = new Scene(group, 300, 600);
        controlsScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        controlsButton.setOnAction(event -> mainStage.setScene(controlsScene));
    }

    private void addBackButtonToPane(Pane pane) {
        Button backButton = new Button("B A C K");
        backButton.setPrefWidth(150);
        backButton.setLayoutX(75);
        backButton.setLayoutY(450);
        backButton.setOnAction(event -> mainStage.setScene(menuScene));
        pane.getChildren().add(backButton);
    }


    private void addStartButtonToPane() {
        Button startButton = new Button("S T A R T");
        startButton.setPrefWidth(150);
        startButton.setLayoutX(75);
        startButton.setLayoutY(50);
        menuPane.setBackground(Background.EMPTY);
        menuPane.getChildren().add(startButton);
        startButton.setOnAction(event -> runGame());
    }
    //zwracanie sceny i uruchamianie nowej
    private void runGame() {
        newGame();
        //TIMER
        Timer fall = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if ((figure.a.getY() == 0 || figure.b.getY() == 0 ||
                                figure.c.getY() == 0 || figure.d.getY() == 0) && (gameRunning && !gamePaused)) {
                            blockOnTop++;
                        } else blockOnTop = 0;

                        //GAME OVER - block do not move down
                        if (blockOnTop == 2) {
                            displayGameOver(gameOver);
                            gameRunning = false;
                            displayRestartButton();
                        }
                        //Game is running
                        else if(gameRunning && !gamePaused){
                            Controller.MoveFigureDown(figure);
                            scoreText.setText("SCORE: " + score);
                            level.setText("Lines: " + numOfLines);
                        }
                        if(!gameRunning && blockOnTop >= 2){
                            System.out.println("app closed");
                            //fall.cancel();
                            //fall.purge();
                        }
                    }
                });
            }
        };
        fall.schedule(timerTask, 500, 100);
    }

    private void displayRestartButton() {
        Button restartButton = new Button("R E S T A R T");
        restartButton.setPrefWidth(250);
        restartButton.setLayoutX(100);
        restartButton.setLayoutY(310);
        group.getChildren().add(restartButton);
        restartButton.setOnAction(event -> {
            group.getChildren().remove(restartButton);
            newGame();
        });
    }

    private void newGame() {
        group = new Pane();
        scene = new Scene(group, WIDTH+150, HEIGHT);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        mainStage.setScene(scene);
        System.out.println("Restarting game");
        blockOnTop=0;
        score = 0;
        numOfLines = 0;
        figure = null;
        for(int[] a:BOARD){
            Arrays.fill(a, 0);
        }
        //Score side panel
        Line line = new Line(WIDTH, 0, WIDTH, HEIGHT);

        displayScore(scoreText);
        displayLines(level);

        setUpPauseText(pauseText);
        group.setBackground(Background.EMPTY);
        group.getChildren().addAll(line, scoreText, level, pauseText);
        setUpFigures();

        scene.setFill(Color.rgb(8,15,48));
        mainStage.setScene(scene);
        mainStage.setTitle("TETRIS GAME");
        mainStage.show();
        gameRunning=true;
    }

    private void setUpFigures(){
        nextFigure = Controller.makeFigure();
        gameRunning = true;
        Figure a = nextFigure;
        a.putOnBoard();
        group.getChildren().addAll(a.a, a.b, a.c, a.d);
        Controller.moveOnKeyPress(a);
        figure = a;
        nextFigure = Controller.makeFigure();
        Figure prev = Tetris.nextFigure;
        Tetris.group.getChildren().addAll(prev.a, prev.b, prev.c, prev.d);
    }

/*    private void cleanBoard() {
        ArrayList<Node> rects = new ArrayList<Node>();
        for(Node node : group.getChildren()){
            if(node instanceof Rectangle){
                Rectangle a = (Rectangle) node;
                rects.add(a);
            }
        }
        for(Node r : rects){
            group.getChildren().remove(r);
        }
        rects.clear();
        System.out.println("Board cleaned");
    }*/

    private void setUpPauseText(Text pauseText) {
        pauseText.setId("textOnScreen");
        pauseText.setY(300);
        pauseText.setX(20);
    }
    private void displayLines(Text level) {
        level.setText("Lines:");
        level.setY(160);
        level.setX(WIDTH + 5);
        level.setFont(Font.font("Comic Sans MS", 20));
        level.setFill(Color.LIME);
    }
    private void displayScore(Text scoreText) {
        scoreText.setText("SCORE: ");
        scoreText.setFont(Font.font("Comic Sans MS", 20));
        scoreText.setFill(Color.WHITE);
        scoreText.setY(125);
        scoreText.setX(WIDTH + 5);
    }
    private void displayGameOver(Text gameOver) {
        gameOver.setText("G A M E  O V E R ");
        gameOver.setId("textOnScreen");
        gameOver.setY(300);
        gameOver.setX(5);
        //gameOver.setFont(Font.font("Comic Sans MS", 55));
        group.getChildren().add(gameOver);
    }

    /*public static boolean gamePaused(){
        return !gameRunning;
    }*/

    public static void pauseGame(){
        if(!gamePaused){
            //gameRunning = false;
            gamePaused = true;
            pauseText.setText("P A U S E");
            pauseText.toFront();
        }
        else{
            //gameRunning = true;
            gamePaused = false;
            pauseText.setText("");
        }
    }

    public static boolean isGameOver(){
        return !gameRunning;
    }
}
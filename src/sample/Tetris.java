package sample;

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
import java.util.TimerTask;
import java.util.concurrent.*;

public class Tetris{
    //Tetris settings
    public static final int MOVE = 25;
    public static final int BLOCKSIZE = 25;
    public static int WIDTH = BLOCKSIZE * 12;
    public static int HEIGHT = BLOCKSIZE * 24;
    public static int score = 0;
    private int userHighScore = 0;
    //TEXTS
    private final Text scoreText = new Text("");
    private final Text level = new Text("");
    private final Text gameOver = new Text("");
    private static final Text pauseText = new Text("");
    //GAME VARIABLES
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
    private int gameSpeed = 1000;
    private int checkpointScoreforTime;
    private final Stage mainStage;
    //multitasking
    private ScheduledFuture<?> futureTask;
    private ScheduledExecutorService threadPoolExecutor;
    private TimerTask timerTask;
    private final Semaphore mutex = new Semaphore(1);

    public Tetris(Stage primaryStage) {
        mainStage = primaryStage;
        primaryStage.setOnCloseRequest(event -> {
            if(score > userHighScore){
                UserMenu.sendHighscoreToServer(score);
            }
            System.exit(0);
        });
    }

    //zwracanie sceny i uruchamianie nowej
    void runGame() throws InterruptedException {
        try {
            //System.out.println(mutex.toString());
            mutex.acquire();
            //System.out.println(mutex.toString());
            newGame();
        } finally {
            mutex.release();
        }


        //TIMER
        threadPoolExecutor = Executors.newScheduledThreadPool(2);
        TimerTask topCheckTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    gameRunning = !Controller.figureStuckOnTop(figure);
                    if(!gameRunning && blockOnTop == 0){
                        blockOnTop = 1;
                        System.out.println("Block on top: " + blockOnTop);
                    }
                });
            }
        };
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    //GAME OVER - block do not move down
                    if (!gameRunning && blockOnTop == 1) {
                        displayGameOver(gameOver);
                        blockOnTop++;
                        System.out.println("Block on top: " + blockOnTop);
                        scoreText.setText("SCORE: " + score);
                        displayRestartButton();
                        gameSpeed = 1000;
                        changeReadInterval(gameSpeed);
                    }
                    //Game is running
                    else if(gameRunning && !gamePaused){
                        Controller.MoveFigureDown(figure);
                        scoreText.setText("SCORE: " + score);
                        level.setText("Lines: " + numOfLines);
                    }
                    //change falling speed
                    if(gameRunning && (score >= checkpointScoreforTime)){
                        changeGameSpeed();
                    }
                });
            }
        };
        futureTask = threadPoolExecutor.scheduleAtFixedRate(timerTask, 500, gameSpeed, TimeUnit.MILLISECONDS);
        threadPoolExecutor.scheduleAtFixedRate(topCheckTask, 500, 10, TimeUnit.MILLISECONDS);
    }

    private void changeGameSpeed() {
        int fasterBy = 50;
        if(gameSpeed - fasterBy > 50){
            gameSpeed -= fasterBy;
            checkpointScoreforTime += 100;
            changeReadInterval(gameSpeed);
        }
    }

    public void changeReadInterval(int time) {
        if(time > 0) {
            if (futureTask != null) {
                futureTask.cancel(true);
            }
            futureTask = threadPoolExecutor.scheduleAtFixedRate(timerTask, 0, time, TimeUnit.MILLISECONDS);
        }
    }

    private void displayRestartButton() {
        Button restartButton = new Button("R E S T A R T");
        restartButton.setPrefWidth(250);
        restartButton.setLayoutX(100);
        restartButton.setLayoutY(310);
        group.getChildren().add(restartButton);
        restartButton.setOnAction(event -> {
            if(score > userHighScore){
                UserMenu.sendHighscoreToServer(score);
            }
            group.getChildren().remove(restartButton);
            newGame();
        });
    }

    private void displayMenuButton() {
        Button menuButton = new Button("M E N U");
        menuButton.setPrefWidth(125);
        menuButton.setLayoutX(WIDTH+10);
        menuButton.setLayoutY(HEIGHT - 100);
        group.getChildren().add(menuButton);
        menuButton.setOnAction(event -> {
            if(score > Integer.parseInt(UserMenu.highscore.getText())){
                UserMenu.sendHighscoreToServer(score);
            }
            threadPoolExecutor.shutdown();
            gameRunning = false;
            if(gamePaused){
                pauseGame();
            }
            mainStage.setScene(UserMenu.menuScene);
        });
    }

    private void newGame() {
        group = new Pane();
        scene = new Scene(group, WIDTH+150, HEIGHT);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        mainStage.setScene(scene);
        System.out.println("New game started");
        checkpointScoreforTime = 100;
        blockOnTop=0;
        userHighScore = Integer.parseInt(UserMenu.highscore.getText());

        if(score > userHighScore){
            UserMenu.sendHighscoreToServer(score);
        }

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
        displayMenuButton();
        setUpPauseText();
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

    private void setUpPauseText() {
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

    public static void pauseGame(){
        if(!gamePaused){
            gamePaused = true;
            pauseText.setText("P A U S E");
            pauseText.toFront();
        }
        else{
            gamePaused = false;
            pauseText.setText("");
        }
    }

    public static boolean isGameOver(){
        return !gameRunning;
    }
}

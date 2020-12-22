package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class UserMenu {
    private Stage mainStage;
    private static final Pane menuPane = new Pane();
    public static Scene menuScene = new Scene(menuPane, 300, 600);
    public static Text highscore = new Text("0");
    //server
    private static DataOutputStream send;
    private DataInputStream receive;

    public UserMenu(Stage primaryStage, String login, DataOutputStream send, DataInputStream receive, String highscore) {
        //Server
        this.send = send;
        this.receive = receive;
        this.highscore = new Text(highscore);
        //menu scene
        mainStage = primaryStage;
        addStartButtonToPane();
        addControlsButtonToPane();
        addRankingButton();
        menuScene.setFill(Color.rgb(8, 15, 48));
        menuScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        mainStage.setTitle("M E N U");
        mainStage.setScene(menuScene);
        mainStage.setResizable(false);

        Text sceneTitle = new Text("T E T R I S");
        sceneTitle.setId("textOnScreen");

        Text loginText = new Text(login);
        loginText.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 32));
        loginText.setStyle("-fx-fill: orange;");

        Text hsText = new Text("HIGHSCORE:");
        hsText.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 38));
        hsText.setStyle("-fx-fill: white;");


        UserMenu.highscore.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 30));
        UserMenu.highscore.setStyle("-fx-fill: white;");

        VBox vbox = new VBox(10, loginText, hsText, UserMenu.highscore);
        vbox.setMinWidth(300);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(120, 0, 0, 0));

        sceneTitle.setY(100);
        menuPane.getChildren().addAll(sceneTitle, vbox);
        mainStage.show();
    }

    public static void sendHighscoreToServer(int score) {

        try {
            highscore.setText(Integer.toString(score));
            send.writeUTF("updateHighScore");
            send.writeUTF(highscore.getText());
            send.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addStartButtonToPane() {
        Button startButton = new Button("S T A R T");
        startButton.setPrefWidth(150);
        startButton.setLayoutX(75);
        startButton.setLayoutY(300);
        menuPane.setBackground(Background.EMPTY);
        menuPane.getChildren().add(startButton);
        startButton.setOnAction(event -> {
            try {
                Tetris t = new Tetris(mainStage);
                t.runGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void addControlsButtonToPane() {
        Button controlsButton = new Button("CONTROLS");
        menuScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        controlsButton.setPrefWidth(150);
        controlsButton.setLayoutX(75);
        controlsButton.setLayoutY(375);
        menuPane.getChildren().add(controlsButton);
        Tetris.group = new Pane();
        Tetris.group.setId("controls");
        addBackButtonToPane(Tetris.group);
        Scene controlsScene = new Scene(Tetris.group, 300, 600);
        controlsScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        controlsButton.setOnAction(event -> mainStage.setScene(controlsScene));
    }

    private void addRankingButton() {
        Button rankingButton = new Button("RANKING");
        menuScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        rankingButton.setPrefWidth(150);
        rankingButton.setLayoutX(75);
        rankingButton.setLayoutY(450);
        menuPane.getChildren().add(rankingButton);

        Pane newPane = new Pane();
        newPane.setId("ranking");



        Scene rankingScene = new Scene(newPane, 300, 600);
        rankingScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());

        rankingButton.setOnAction(event -> {
            setUpRankingScene(rankingScene, newPane);
            mainStage.setScene(rankingScene);
        });
    }

    private void setUpRankingScene(Scene rankingScene, Pane newPane) {

        VBox rankingVBox = new VBox(10);
        rankingVBox.setPadding(new Insets(30,0,0,50));
        //rankingVBox.setAlignment(Pos.CENTER);
        Text t = new Text("");
        newPane.getChildren().clear();
        newPane.getChildren().add(rankingVBox);
        addBackButtonToPane(newPane);
        try {
            send.writeUTF("ranking");

            while(true){
                String oneRow = receive.readUTF();

                if(oneRow.equals("end")){
                    return;
                }

                System.out.println(oneRow);
                t = new Text(oneRow);
                t.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 20));
                t.setStyle("-fx-fill: white;");

                //ranking.add(oneRow);
                rankingVBox.getChildren().add(t);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void addBackButtonToPane(Pane pane) {
        Button backButton = new Button("B A C K");
        backButton.setPrefWidth(150);
        backButton.setLayoutX(75);
        backButton.setLayoutY(450);
        backButton.setOnAction(event -> mainStage.setScene(menuScene));
        pane.getChildren().add(backButton);
    }



}
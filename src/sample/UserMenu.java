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
import javafx.stage.Stage;

public class UserMenu{
    private Stage mainStage;
    private static final Pane menuPane = new Pane();
    public static Scene menuScene = new Scene(menuPane, 300, 600);

    public UserMenu(Stage primaryStage, String login){

        //menu scene
        mainStage = primaryStage;
        addStartButtonToPane();
        addControlsButtonToPane();
        menuScene.setFill(Color.rgb(8,15,48));
        menuScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        mainStage.setTitle("M E N U");
        mainStage.setScene(menuScene);
        mainStage.setResizable(false);

        Text sceneTitle = new Text("T E T R I S");
        sceneTitle.setId("textOnScreen");

        Text loginText = new Text(login);
        loginText.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 32));
        loginText.setStyle("-fx-fill: white;");

        VBox vbox = new VBox(10, loginText);
        vbox.setMinWidth(300);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(120, 0, 0 , 0));

        sceneTitle.setY(100);
        menuPane.getChildren().addAll(sceneTitle, vbox);
        mainStage.show();
    }

    private void addControlsButtonToPane() {
        Button controlsButton = new Button("CONTROLS");
        menuScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        controlsButton.setPrefWidth(150);
        controlsButton.setLayoutX(75);
        controlsButton.setLayoutY(450);
        menuPane.getChildren().add(controlsButton);
        Tetris.group = new Pane();
        Tetris.group.setId("controls");
        addBackButtonToPane(Tetris.group);
        Scene controlsScene = new Scene(Tetris.group, 300, 600);
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
        startButton.setLayoutY(350);
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
}

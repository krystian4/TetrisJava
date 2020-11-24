package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterMenu {

    private Stage mainStage;
    private static final GridPane loginPane = new GridPane();
    public static Scene menuScene = new Scene(loginPane, 300, 300);

    private final TextField emailTextField = new TextField();
    private final TextField loginTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();

    public RegisterMenu(Stage primaryStage) {
        mainStage = primaryStage;

        menuScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        loginPane.setBackground(Background.EMPTY);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(25, 25, 25, 25));
        menuScene.setFill(Color.rgb(8,15,48));

        mainStage.setScene(menuScene);

        Text sceneTitle = new Text("Register");
        sceneTitle.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 32));
        sceneTitle.setStyle("-fx-fill: red;");
        loginPane.add(sceneTitle, 0,0, 2, 1 );
        GridPane.setMargin(sceneTitle, new Insets(0, 0, 20, 0));

        Label emailLabel = new Label("Email:");
        emailLabel.setTextFill(Color.WHITE);
        loginPane.add(emailLabel, 0, 1);

        Label userName = new Label("Login:");
        userName.setTextFill(Color.WHITE);
        loginPane.add(userName, 0, 2);

        Label password = new Label("Password:");
        password.setTextFill(Color.WHITE);
        loginPane.add(password, 0, 3);

        loginPane.add(emailTextField, 1, 1);
        loginPane.add(loginTextField, 1, 2);
        loginPane.add(passwordField, 1, 3);

        createSignUpButtons();

        mainStage.show();

    }

    private void createSignUpButtons() {
        Button backButton = new Button("Back");
        Button registerButton = new Button("Register");

        backButton.setId("accountButton");
        registerButton.setId("accountButton");

        HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHBox.getChildren().addAll(backButton, registerButton);
        loginPane.add(buttonHBox, 1, 5);

        backButton.setOnAction(event -> {
                mainStage.setScene(LoginMenu.menuScene);
        });

        registerButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Register complete");
            alert.setHeaderText(null);
            alert.setContentText("Thanks for registration!");

            alert.showAndWait();
        });
    }
}

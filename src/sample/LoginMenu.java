package sample;

import javafx.application.Application;
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

import java.io.*;
import java.net.Socket;

public class LoginMenu extends Application {

    private Socket socket;
    private DataOutputStream send;
    private DataInputStream receive;

    private Stage mainStage;
    private static final GridPane loginPane = new GridPane();
    public static Scene menuScene = new Scene(loginPane, 300, 300);
    private final TextField loginTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private String highscore;

    public static void main() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Server connection

        //if window is closed
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        mainStage = primaryStage;
        menuScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        loginPane.setBackground(Background.EMPTY);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(25, 25, 25, 25));
        menuScene.setFill(Color.rgb(8,15,48));

        mainStage.setScene(menuScene);

        Text sceneTitle = new Text("LOGIN  MENU");
        sceneTitle.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 32));
        sceneTitle.setStyle("-fx-fill: red;");
        loginPane.add(sceneTitle, 0,0, 2, 1 );
        GridPane.setMargin(sceneTitle, new Insets(0, 0, 20, 0));

        Label userName = new Label("Login:");
        userName.setTextFill(Color.WHITE);
        loginPane.add(userName, 0, 1);

        Label password = new Label("Password:");
        password.setTextFill(Color.WHITE);
        loginPane.add(password, 0, 2);

        loginPane.add(loginTextField, 1, 1);
        loginPane.add(passwordField, 1, 2);

        createSignUpButtons();

        if(connectToServer() == false){
            System.exit(0);
        }

        mainStage.show();
    }

    private void createSignUpButtons() {
        Button loginButton = new Button("Log in");
        Button registerButton = new Button("Register");

        loginButton.setId("accountButton");
        registerButton.setId("accountButton");

        HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHBox.getChildren().addAll(registerButton, loginButton);
        loginPane.add(buttonHBox, 1, 4);

        loginButton.setOnAction(event -> {
            if(!loginTextField.getText().isEmpty() && !passwordField.getText().isEmpty()){
                if(loginByServer()){
                    new UserMenu(mainStage, loginTextField.getText().toString(), send, receive, highscore);
                }
            }
        });

        registerButton.setOnAction(event -> {
            new RegisterMenu(mainStage, socket, send, receive);
        });
    }

    private boolean loginByServer() {
        try {
            send.writeUTF("login");
            send.writeUTF(loginTextField.getText());
            send.writeUTF(passwordField.getText());
            String ret = receive.readUTF();
            if(ret.contentEquals("0")){
                System.out.println("Bad login or password!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No account");
                alert.setHeaderText(null);
                alert.setContentText("Wrong username or password!");
                alert.showAndWait();
                return false;
            }
            else if(ret.equals("1")){
                System.out.println("Login successful1");
                highscore = receive.readUTF();//highscore
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 4444);

            send = new DataOutputStream(socket.getOutputStream());

            receive = new DataInputStream(socket.getInputStream());
            System.out.println("Połączono z serwerem!");

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Connection Failed");
            alert.setHeaderText(null);
            alert.setContentText("Cannot connect to the server. Please, restart game.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}

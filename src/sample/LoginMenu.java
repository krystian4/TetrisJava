package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginMenu extends Application {

    private Stage mainStage;
    private static final GridPane loginPane = new GridPane();
    public static Scene menuScene = new Scene(loginPane, 300, 300);
    private final TextField loginTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();

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
            if(!loginTextField.getText().isEmpty()){
                new UserMenu(mainStage, loginTextField.getText().toString());
                connectToServer();
            }
        });

        registerButton.setOnAction(event -> {
            new RegisterMenu(mainStage);
        });
    }

    private void connectToServer() {
        try(
                Socket kkSocket = new Socket("127.0.0.1" , 4444);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ){
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            //System.out.println("echo: " + in.readLine());

            String userInput = loginTextField.getText();
            out.println(userInput);
            System.out.println("echo: " + in.readLine());
            userInput = passwordField.getText();
            out.println(userInput);
            System.out.println("echo: " + in.readLine());


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

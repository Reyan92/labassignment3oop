package com.example.labasssignment3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class HelloApplication extends Application {

    private static final String FILE_NAME = "users.txt";

    @Override
    public void start(Stage primaryStage) {
        // Ensure the users file exists and initialize it with default credentials
        initializeFile();

        // Main layout
        VBox mainLayout = new VBox(20);
        mainLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Set background image
        Image backgroundImage = new Image("file:images.jpeg"); // Replace with the actual image file path
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );
        mainLayout.setBackground(new Background(bgImage));

        // Banner with Title and Image
        VBox banner = new VBox(10);
        banner.setStyle("-fx-alignment: center; -fx-background-color: rgba(255, 255, 255, 0.8); -fx-padding: 10; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label title = new Label("Login Form");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ImageView bannerImage = new ImageView(new Image("file:jin.jpeg"));
        bannerImage.setFitHeight(80);
        bannerImage.setFitWidth(80);

        banner.getChildren().addAll(title, bannerImage);


        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        // Buttons
        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");

        // Notification Label
        Label notificationLabel = new Label();
        notificationLabel.setStyle("-fx-text-fill: red;");

        // Button Actions
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (validateCredentials(username, password)) {
                openUserForm(username);
                primaryStage.close();
            } else {
                notificationLabel.setText("Invalid username or password!");
            }
        });

        exitButton.setOnAction(event -> primaryStage.close());

        // Layout for form controls
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.add(usernameLabel, 0, 0);
        formGrid.add(usernameField, 1, 0);
        formGrid.add(passwordLabel, 0, 1);
        formGrid.add(passwordField, 1, 1);
        formGrid.add(loginButton, 0, 2);
        formGrid.add(exitButton, 1, 2);

        // Add components to main layout
        mainLayout.getChildren().addAll(banner, formGrid, notificationLabel);

        // Set up the scene and stage
        Scene scene = new Scene(mainLayout, 400, 500); // Increased height to 500
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void initializeFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("reyan:password\n");
                writer.write("Abis:password\n");
                writer.write("Umair:password\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean validateCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void openUserForm(String username) {
        Stage userStage = new Stage();
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #ffffff;");

        Label welcomeLabel = new Label("Welcome, " + username + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> userStage.close());

        layout.getChildren().addAll(welcomeLabel, closeButton);

        Scene scene = new Scene(layout, 300, 200);
        userStage.setTitle("User Dashboard");
        userStage.setScene(scene);
        userStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

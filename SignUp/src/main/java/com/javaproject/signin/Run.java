package com.javaproject.signin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Run extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Run.class.getResource("MainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);

        stage.setTitle("Bidding");
        stage.setScene(scene);
        stage.show();
    }
}

package com.javaproject.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SwitchSceneController {
    @FXML
    public static void goToScene(ActionEvent event, String fxmlFile) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 1. Save the current size
            double width = stage.getWidth();
            double height = stage.getHeight();

            Parent root = FXMLLoader.load(SwitchSceneController.class.getResource(fxmlFile));

            // 2. Create the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // 3. Re-apply the saved size
            stage.setWidth(width);
            stage.setHeight(height);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
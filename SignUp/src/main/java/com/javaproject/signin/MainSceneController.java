package com.javaproject.signin;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainSceneController {
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;

    @FXML
    public void handleSignIn(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        String query = "select * from users where username = ? and password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // User found! Get their role
                String userRole = rs.getString("role");
                System.out.println("Success! Logged in as: " + userRole);

                // Switch scene based on role
                SwitchSceneController.goToScene(event, "MainApp.fxml");
            } else {
                AlertBox.display("Tên đăng nhập hoặc mật khẩu không chính xác!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AlertBox.display("Lỗi kết nối cơ sở dữ liệu!");
        }
    }
    @FXML
    public void SwitchToSignUp(ActionEvent event) {
        SwitchSceneController.goToScene(event,"SignUp.fxml");
    }
}

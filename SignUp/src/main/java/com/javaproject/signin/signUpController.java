package com.javaproject.signin;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class signUpController {
    @FXML private TextField txtTenDangNhap;
    @FXML private PasswordField passMatKhau;
    @FXML private PasswordField passNhapLai;
    @FXML private TextField txtEmail;
    @FXML private ChoiceBox<String> role;
    @FXML private Label requirement;

    @FXML
    public void initialize() {
        requirement.setVisible(false);
        requirement.setManaged(false);
        requirement.setStyle("-fx-text-fill: #ff0000; -fx-font-size: 11px;");

        passMatKhau.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                requirement.setVisible(true);
                requirement.setManaged(true);
            }else{
                if (isValidPassword(passMatKhau.getText())){
                    requirement.setVisible(false);
                    requirement.setManaged(false);
                }
            }
        });
        passMatKhau.textProperty().addListener((obs, oldText, newText) -> {
            if (isValidPassword(newText)) {
                requirement.setStyle("-fx-text-fill: #008000;"); // Green
                requirement.setText("✔ Password meets requirements");
            } else {
                requirement.setStyle("-fx-text-fill: #ff0000;"); // Red
                requirement.setText("• At least 6 chars, 1 letter, 1 number");
            }
        });
    }

    @FXML
    public void MainSceneSwitch(ActionEvent event) throws IOException {
        SwitchSceneController.goToScene(event, "MainScene.fxml");
    }

    @FXML
    public void signUp(ActionEvent event) {
        String username = txtTenDangNhap.getText();
        String password = passMatKhau.getText();
        String nhapLai = passNhapLai.getText();
        String email = txtEmail.getText();
        String userRole = role.getValue();


        if (username.isEmpty()|| password.isEmpty() || nhapLai.isEmpty()|| email.isEmpty()) {
            AlertBox.display("Hãy nhập hết các ô trống");
            return;
        }

        if (!nhapLai.equalsIgnoreCase(password)) {
            AlertBox.display("Bạn nhập lại sai mật khẩu");
            return;
        }

        if (password.length() < 6) {
            AlertBox.display("Mật khẩu quá ngắn");
            return;
        }
        if (!containSymbol(email)){ // check xem email đã có "@" chưa
            AlertBox.display("SĐT hoặc email không hợp lệ");
            return;
        }

        boolean success = saveToMySQL(username, password, email, userRole);
        if (success) {
            SwitchSceneController.goToScene(event, "MainScene.fxml");
        }
    }

    private  boolean containSymbol(String msg){
        if (!msg.contains("@")) {
            return false;
        }
        return true;
    }
    private boolean isValidPassword(String password){
        return password.length() >= 6 &&
                password.matches(".*[a-zA-Z].*") &&
                password.matches(".*\\d.*");
    }
    private boolean saveToMySQL(String user, String pass, String email, String role) {
        String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            pstmt.setString(3, email);
            pstmt.setString(4, role);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // MySQL code for Duplicate Entry
                AlertBox.display("Tên đăng nhập đã tồn tại!");
            }
            return false;
        }
    }
}
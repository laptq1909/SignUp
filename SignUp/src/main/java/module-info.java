module com.javaproject.signin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires com.google.gson;


    opens com.javaproject.signin to javafx.fxml, com.google.gson;
    exports com.javaproject.signin;
}
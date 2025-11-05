module org.example.gestorjuegosfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires mysql.connector.j;
    requires java.naming;
    requires javafx.graphics;

    opens org.example.gestorjuegosfx to javafx.fxml;
    exports org.example.gestorjuegosfx;
    exports org.example.gestorjuegosfx.user;
    opens org.example.gestorjuegosfx.user to javafx.fxml;
    exports org.example.gestorjuegosfx.controllers;
    opens org.example.gestorjuegosfx.controllers to javafx.fxml;
    exports org.example.gestorjuegosfx.common;
    opens org.example.gestorjuegosfx.common to javafx.fxml;
    exports org.example.gestorjuegosfx.utils;
    opens org.example.gestorjuegosfx.utils to javafx.fxml;
}
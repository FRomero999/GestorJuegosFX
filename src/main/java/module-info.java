module org.example.gestorjuegosfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.gestorjuegosfx to javafx.fxml;
    exports org.example.gestorjuegosfx;
}
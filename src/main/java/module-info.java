module org.example.gestorjuegosfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens org.example.gestorjuegosfx to javafx.fxml;
    exports org.example.gestorjuegosfx;
}
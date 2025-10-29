package org.example.gestorjuegosfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @javafx.fxml.FXML
    private Button btnSalir;

    private Stage stage;
    @javafx.fxml.FXML
    private ChoiceBox<String> choice;

    private ObservableList<String> datos = FXCollections.observableArrayList();

    public void setStage(Stage stage){
        this.stage=stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        datos.addAll("Hola","Adios");
        choice.setItems(datos);

        //choice.getItems().addAll("A","B","C","D","E","F","G","H");
        choice.setValue( choice.getItems().getFirst() );
    }

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        datos.add("Saliendo");

/*    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();



    } catch (IOException e) {
        throw new RuntimeException(e);
    }
*/
    }
}

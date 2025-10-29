package org.example.gestorjuegosfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField txtContraseña;
    @FXML
    private Button btnContinuar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Label lblInfo;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void cancelar(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void continuar(ActionEvent actionEvent) {
        if( txtCorreo.getText().equals("F") && txtContraseña.getText().equals("F") ) {
            lblInfo.setText("Acceso correcto");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bienvenido");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenido");
            alert.showAndWait();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
                Parent root = loader.load();
                MainController controller = loader.getController();
                controller.setStage(stage);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.centerOnScreen();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } else{
            lblInfo.setText("Error en el acceso");
            txtCorreo.clear();
            txtContraseña.clear();
        }

    }


}

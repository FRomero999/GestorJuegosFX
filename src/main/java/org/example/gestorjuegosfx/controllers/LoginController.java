package org.example.gestorjuegosfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.gestorjuegosfx.utils.AuthService;
import org.example.gestorjuegosfx.utils.JavaFXUtil;
import org.example.gestorjuegosfx.common.DataProvider;
import org.example.gestorjuegosfx.user.UserDAO;

import javax.sql.DataSource;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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

    private UserDAO userDAO;
    private AuthService authService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSource ds = DataProvider.getDataSource();
        userDAO = new UserDAO(ds);
        authService = new AuthService(userDAO);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void cancelar(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void continuar(ActionEvent actionEvent) {

        if(authService.login(txtCorreo.getText(),txtContraseña.getText()).isPresent()){
            lblInfo.setText("Acceso correcto");
            JavaFXUtil.showModal(Alert.AlertType.INFORMATION,"Bienvenido",null,"Bienvenido a la aplicación");
            MainController mainController = JavaFXUtil.setScene("/org/example/gestorjuegosfx/main-view.fxml");
        } else{
            lblInfo.setText("Error en el acceso");
            txtCorreo.clear();
            txtContraseña.clear();
        }

    }


}

package org.example.gestorjuegosfx.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.gestorjuegosfx.game.Game;
import org.example.gestorjuegosfx.game.GameDAO;
import org.example.gestorjuegosfx.utils.AuthService;
import org.example.gestorjuegosfx.common.DataProvider;
import org.example.gestorjuegosfx.user.User;
import org.example.gestorjuegosfx.user.UserDAO;
import org.example.gestorjuegosfx.utils.JavaFXUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @javafx.fxml.FXML
    private Button btnSalir;

    private Stage stage;

    private GameDAO gameDAO;
    private UserDAO userDAO;
    private AuthService authService;
    private User currentUser;

    @javafx.fxml.FXML
    private TextArea txtDescription;
    @javafx.fxml.FXML
    private Spinner<Integer> spinUser;
    @javafx.fxml.FXML
    private TextField txtPlataforma;
    @javafx.fxml.FXML
    private Label lblId;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cTitle;
    @javafx.fxml.FXML
    private TextField txtImage;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cPlatform;
    @javafx.fxml.FXML
    private Spinner<Integer> spinYear;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private TableView<Game> table;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cId;
    @javafx.fxml.FXML
    private TextField txtTitle;

    private ObservableList<User> datos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataSource ds = DataProvider.getDataSource();
        gameDAO = new GameDAO(ds);
        userDAO = new UserDAO(ds);
        authService = new AuthService(userDAO);
        currentUser = authService.getCurrentUser().get();

        authService.getCurrentUser().ifPresent(System.out::println);

        cId.setCellValueFactory( (row)->{
            return new SimpleStringProperty(row.getValue().getId().toString());
        });
        cTitle.setCellValueFactory( (row)->{
            return new SimpleStringProperty(row.getValue().getTitle());
        });
        cPlatform.setCellValueFactory( (row)->{
            return new SimpleStringProperty(row.getValue().getPlatform());
        });

        spinUser.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3));
        spinYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 2025,2025,1));

        table.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
           if(newValue!=null){
               txtTitle.setText(newValue.getTitle());
               txtDescription.setText(newValue.getDescription());
               lblId.setText(newValue.getId().toString());
               txtPlataforma.setText(newValue.getPlatform());
               txtImage.setText(newValue.getImage_url());
               spinUser.getValueFactory().setValue(newValue.getUser_id());
               spinYear.getValueFactory().setValue(newValue.getYear());
           }
        });

        refreshTable();

    }

    private void refreshTable() {
        table.getItems().clear();
        table.getItems().addAll( gameDAO.findAllByUserId( currentUser.getId()) );
    }

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        authService.logout();
        JavaFXUtil.setScene("org/example/gestorjuegosfx/login-view.fxml");
    }

    @javafx.fxml.FXML
    public void añadir(ActionEvent actionEvent) {
        Game game = new Game();
        game.setTitle(txtTitle.getText());
        game.setDescription(txtDescription.getText());
        game.setYear(spinYear.getValue());
        game.setUser_id(spinUser.getValue());
        game.setPlatform(txtPlataforma.getText());

        var newGame = gameDAO.save(game);
        if(newGame.isPresent()){
            refreshTable();
            JavaFXUtil.showModal(Alert.AlertType.INFORMATION,"Añadido",null,"Se ha añadido "+game.getTitle());
        }
    }
}

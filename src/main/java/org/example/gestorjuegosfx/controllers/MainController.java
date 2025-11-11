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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    @javafx.fxml.FXML
    private ImageView cover;

    private AudioClip clapAudio;
    private AudioClip fondo = new AudioClip(getClass().getResource("/audio/chiptune-loop.wav").toString());
    private MediaPlayer  player;

    @javafx.fxml.FXML
    private ToggleButton btnMute;
    @javafx.fxml.FXML
    private Slider volumenSlider;
    @javafx.fxml.FXML
    private Slider sliderRate;
    @javafx.fxml.FXML
    private Label lblEcho;

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
           if(newValue!=null) showDataInForm(newValue);
        });

        btnMute.selectedProperty().addListener((observable,oldValue,newValue)->{
            if(btnMute.isSelected()){
                player.setMute(true);
            }else {
                player.setMute(false);
            }
        });

        volumenSlider.valueProperty().addListener((observable,oldValue,newValue)->{
            player.setVolume(volumenSlider.getValue()/100);
        });

        lblEcho.textProperty().bindBidirectional(txtTitle.textProperty());

        clapAudio = new AudioClip(getClass().getResource("/audio/clap13.wav").toString());

        Media m = new Media(getClass().getResource("/audio/chiptune-loop.wav").toString());
        player = new MediaPlayer(m);

        player.rateProperty().bindBidirectional(sliderRate.valueProperty());

        player.setVolume(0.5);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();

        //fondo.setCycleCount(AudioClip.INDEFINITE);
        //fondo.play(0.5);

        refreshTable();

    }

    private void showDataInForm(Game newValue) {
        txtTitle.setText(newValue.getTitle());
        txtDescription.setText(newValue.getDescription());
        lblId.setText(newValue.getId().toString());
        txtPlataforma.setText(newValue.getPlatform());
        txtImage.setText(newValue.getImage_url());
        spinUser.getValueFactory().setValue(newValue.getUser_id());
        spinYear.getValueFactory().setValue(newValue.getYear());

        Image im = new Image("file:covers/"+ newValue.getImage_url());
        if(im.isError()){
            im = new Image(getClass().getResource("/images/placeholder.png").toString());
        }
        cover.setImage(im);
        clapAudio.play();
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
        game.setImage_url(txtImage.getText());

        var newGame = gameDAO.save(game);
        if(newGame.isPresent()){
            refreshTable();
            JavaFXUtil.showModal(Alert.AlertType.INFORMATION,"Añadido",null,"Se ha añadido "+game.getTitle());
        }
    }
}

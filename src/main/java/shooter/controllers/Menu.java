package shooter.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Menu {

    @FXML
    private ImageView menuBcg,playBcg,highscoreBcg,exitBcg,afghanButton,syrianButton,muteIV;
    @FXML
    private VBox buttonsVBox;
    @FXML
    private Pane maps;

    MediaPlayer a;
    private boolean music=true;

    /**
     * In the {@code initialize} we load the images of the buttons and the background. Then we call the {@code music}
     * method.
     */
    @FXML
    public void initialize(){
        menuBcg.setImage(new Image(getClass().getResource("/images/menuS/menuBcg.png").toExternalForm()));
        playBcg.setImage(new Image(getClass().getResource("/images/menuS/playBcg.png").toExternalForm()));
        highscoreBcg.setImage(new Image(getClass().getResource("/images/menuS/highscoreBcg.png").toExternalForm()));
        exitBcg.setImage(new Image(getClass().getResource("/images/menuS/exitBcg.png").toExternalForm()));
        muteIV.setImage(new Image(getClass().getResource("/images/menuS/muteBcg.png").toExternalForm()));
        music();
    }

    /**
     * Start the background music, setting it's volume to 80% also making sure it keeps repeating if the sound file
     * comes to an end. [Also the MediaPlayer has to be declared in the class outside of this method else the
     * music will stop after few seconds!]
     */
    private void music(){
        Media med = new Media(getClass().getResource("/sounds/menuMusic.mp3").toExternalForm());
        a =new MediaPlayer(med);
        a.setVolume(0.8);
        a.setAutoPlay(true);
        a.setOnEndOfMedia(new Runnable() {
            public void run() {
                a.seek(Duration.ZERO);
            }
        });
        log.info("Starting music in menu");
    }

    /**
     * This method is connected to the play button, when pressed sets the VBox invisible which the buttons are in
     * and loads the map choosing buttons and images.
     * @param event Click on button.
     */
    public void Play(ActionEvent event) {
        log.info("Getting map choosing buttons on");
        buttonsVBox.setVisible(false);
        afghanButton.setImage(new Image(getClass().getResource("/images/street/afghanButton.png").toExternalForm()));
        syrianButton.setImage(new Image(getClass().getResource("/images/syria/syriaButton.png").toExternalForm()));
        maps.setVisible(true);
    }

    /**
     * If called loads the afghanistan map.
     * @param event Click on button
     * @throws IOException FXMLLoader
     */
    public void goAfghanistan(ActionEvent event) throws IOException {
        log.info("Changing scene to Afghanistan");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/playAfghanFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        a.pause();
    }

    /**
     * If called loads the afghanistan map.
     * @param event Click on button
     * @throws IOException FXMLLoader
     */
    public void goSyria(ActionEvent event) throws IOException{
        log.info("Changing scene to play Syria");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/playSyria.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        a.pause();
    }

    /**
     * This method is connected to HighScore button, when that pressed it loads the HighScore scene.
     * @param event Click on button.
     * @throws IOException by FXMLLoader
     */
    public void highscore(ActionEvent event) throws IOException{
        log.info("Changing scene to high score Scene");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/highscore.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        a.pause();
    }

    /**
     * This method is connected to the Exit button, when that pressed the program closes.
     * @param event Click on button.
     */
    public void exitApp(ActionEvent event) {
        log.info("Exiting app...");
        Platform.exit();
    }

    /**
     * This is a simple method connected to the Mute button. It mutes or unmutes the music and sets the image
     * according to that.
     * @param event Click on mute (aka muteB) button
     */
    public void mute(ActionEvent event){
        if (music){
            log.info("Music muted");
            music = false;
            a.pause();
            muteIV.setImage(new Image(getClass().getResource("/images/menuS/unmuteBcg.png").toExternalForm()));
        } else {
            log.info("Music unmuted");
            music = true;
            a.play();
            muteIV.setImage(new Image(getClass().getResource("/images/menuS/muteBcg.png").toExternalForm()));
        }
    }

}

package shooter.controllers;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import shooter.outerMethods.gameplayMethods;

import java.io.*;
import java.util.Random;

@Slf4j
public class street {

    @FXML
    private Pane one, two, three, four, five, six, seven, nameEnter, results, ingameScore;
    @FXML
    private Text scoreT, killedT, missedT, finalTimeT, goodJobT, finalScoreT;
    @FXML
    private Label timeL;
    @FXML
    private TextField nameTF;
    @FXML
    private Button exitB,muteB;
    @FXML
    private ImageView streetBcg,enemyOne,enemyTwo,enemyThree,enemyFour,enemyFive,enemySix,enemySeven;

    private int whichEnemy, missedShots;
    private int killed = 0, score = 0;
    private Pane thisEnemy;
    private long start, millisElapsed;
    private MediaPlayer a;
    private boolean music=true;

    /**
     * Here we load the background, also call the {@code music} method.
     */
    @FXML
    public void initialize(){
        streetBcg.setImage(new Image(getClass().getResource("/images/street/bcgStreet.png").toExternalForm()));;
        music();
    }

    /**
     * This method loads and starts the music at 60% volume, also making sure if the file ends it starts over.
     * [Also the MediaPlayer has to be declared in the class outside of this method else the
     * music will stop after few seconds!]
     */
    private void music() {
        Media med = new Media(getClass().getResource("/sounds/streetMusic.mp3").toExternalForm());
        a = new MediaPlayer(med);
        a.setVolume(0.6);
        a.setAutoPlay(true);
        a.setOnEndOfMedia(new Runnable() {
            public void run() {
                a.seek(Duration.ZERO);
            }
        });
        log.info("Afghan music started");
    }

    /**
     * This is connected to the play button, when pressed it vanishes the little starting box, setting the scoreT
     * to it's base value, calling {@code scoreCheck} method and starting the timer.
     * @param event Click on button.
     * @throws IOException because of {@code scoreCheck} method
     */
    public void start(ActionEvent event) throws IOException {
        nameEnter.setVisible(false);
        scoreT.setText("Score: " + score);
        missedShots=0;
        scoreCheck();
        timer();
    }

    /**
     * This timer counts the time for the program, it runs until the player wins.
     */
    private void timer() {
        log.info("timer started");
        start = System.currentTimeMillis();
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            millisElapsed = System.currentTimeMillis() - start;
            timeL.setText(DurationFormatUtils.formatDuration(millisElapsed, "mm:ss"));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Here is a simple if-else block, but this is the center of the game. This is called after every enemy shot.
     * It calls {@code enemy} method until the player reaches the 100 points.
     * @throws IOException because of {@code win} method
     */
    public void scoreCheck() throws IOException {
        if (score < 100) {
            enemy();
        } else {
            win();
            log.info("Game ended");
        }
    }

    /**
     * Here the program first creates a random number ({@code random}) through that it will spawn an enemy. Then based
     * on what was the number it spawns an enemy by calling that pane with the number generated.
     */
    public void enemy() {
        random();
        Pane[] enemies = {one, two, three, four, five, six, seven};
        ImageView[] terrorist = {enemyOne,enemyTwo,enemyThree,enemyFour,enemyFive,enemySix,enemySeven};
        thisEnemy = enemies[whichEnemy];
        terrorist[whichEnemy].setImage(new Image(getClass().getResource("/images/street/terrorist.png").toExternalForm()));
        thisEnemy.setVisible(true);
        log.info("{} spawned",terrorist[whichEnemy].getId());
    }

    /**
     * In this method it generates the random number for {@code enemy}. It makes sure that the game does not spawn
     * the same enemy twice in a row, by a simple if where it checks if the last enemy is the same as the new.
     * For the random number it uses {@code random.nextInt} the bound is 7 it means the number will be 0-6.
     */
    public  void random() {
        int lastEnemy = whichEnemy;
        Random random = new Random();
        whichEnemy = random.nextInt(7);
        log.info("the random number is {}", whichEnemy);
        if (whichEnemy == lastEnemy){
            random();
        }
    }

    /**
     * First it deletes the score field and the exit button and makes the result field visible.
     * Then it prints things (the name, the time, the kills, the missed
     * shots, and also the score) on it. At he end it calls method {@code storeScore} in {@code gameplayMethods}..
     * @throws IOException by storeScore's FileWriter
     */
    public void win() throws IOException{
        ingameScore.setVisible(false);
        exitB.setVisible(false);
        results.setVisible(true);
        String name = gameplayMethods.getName(nameTF);
        goodJobT.setText("Good job " + name + "!");
        finalTimeT.setText(DurationFormatUtils.formatDuration(millisElapsed, "mm:ss"));
        String finalTimeSecT = (DurationFormatUtils.formatDuration(millisElapsed, "ss"));
        killedT.setText("and killed " + killed + " terrorists,");
        missedT.setText(gameplayMethods.missedText(missedShots));
        int finalScore = gameplayMethods.calculateScore(finalTimeSecT,score,killed,missedShots);
        finalScoreT.setText("Your calculated score is: " + finalScore);
        gameplayMethods.storeScore("scoresSyria.txt", name, finalScore, finalTimeT.getText(), missedShots,killed);
    }

    /**
     * This method is connected to an invisible button place on the body of the enemies. As said the body shot
     * counts as one, so it raises the score by 1 like the number of kills. Also it changes the score text on the
     * right and deletes the enemy. It also calls the {@code shot} method, which plays the sound of a shot.
     * At the end calls the {@code scoreCheck}.
     * @param event Click on hitbox/button
     * @throws IOException because of {@code scoreCheck} method
     */
    public void body(ActionEvent event) throws IOException {
        log.info("body shot");
        shot();
        ++score;
        ++killed;
        scoreT.setText("Score: " + score);
        thisEnemy.setVisible(false);
        scoreCheck();
    }

    /**
     * This works like the {@code body} but it is attached to a button on the head and it gives 2 points.
     * @param event Click on hitbox/button
     * @throws IOException because of {@code scoreCheck} method
     */
    public void head(ActionEvent event) throws IOException {
        log.info("head shot");
        shot();
        score = score + 2;
        ++killed;
        scoreT.setText("Score: " + score);
        thisEnemy.setVisible(false);
        scoreCheck();
    }

    /**
     * Here it counts the miss shots, also playing the shot sound. It is on invisible buttons around the enemy objects
     * and a big one which cowers the playing field.
     * @param event Click on places where is no enemy or other button/pane.
     */
    public void missClick(ActionEvent event) {
        log.info("missed shot");
        ++missedShots;
        shot();
    }

    /**
     * The {@code shot} method as described earlier it loads and plays the sound of a shot. But here we need to
     * declare the MediaPlayer locally because we want to play the sound multiple times.
     */
    private void shot(){
        Media med = new Media(getClass().getResource("/sounds/shot.mp3").toExternalForm());
        MediaPlayer shot = new MediaPlayer(med);
        shot.play();
    }

    /**
     * The {@code toMenu} is connected to 2 buttons, one is the "Exit" at the lower right corner and the other one is
     * when the game ends, called "Menu". It just loads the Menu scene and stops the timer.
     * @param event Click on button (Exit and Menu)
     * @throws IOException by FXMLLoader
     */
    public void toMenu(ActionEvent event) throws IOException {
        log.info("Going to Menu..");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        a.pause();
    }

    /**
     * This is a simple method connected to the Mute button. It mutes or unmutes the music.
     * @param event Click on mute (aka muteB) button
     */
    public void mute(ActionEvent event){
        if (music){
            log.info("Music muted");
            music = false;
            a.pause();
            muteB.setText("Unmute");
        } else {
            log.info("Music unmuted");
            music = true;
            a.play();
            muteB.setText("Mute");
        }
    }
}


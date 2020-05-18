package shooter.streetMap;


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
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;
import java.util.Random;

public class street {

    @FXML
    private Pane one, two, three, four, five, six, seven, nameEnter, results, ingameScore;
    @FXML
    private Text scoreT, killedT, missedT, finalTimeT, goodJobT, nameT, finalScoreT, finalTimeSecT;
    @FXML
    private Label timeL;
    @FXML
    private TextField nameTF;
    @FXML
    private Button exitB;
    @FXML
    private ImageView streetBcg,enemyOne,enemyTwo,enemyThree,enemyFour,enemyFive,enemySix,enemySeven;

    private boolean startOn = true;
    private int whichEnemy, lastEnemy, missedShots;
    private int killed = 0, score = 0;
    private Pane thisEnemy;
    private long start, millisElapsed;
    private MediaPlayer a;
    private double finScore;

    @FXML
    public void initialize(){
        streetBcg.setImage(new Image(getClass().getResource("/images/bcgStreet.png").toExternalForm()));
        music();
    }

    public void start(ActionEvent event) {
        getName();
        deleteStart();
    }

    private void getName() {
        if (nameTF.getText() != null && !nameTF.getText().isEmpty()) {
            nameT.setText(nameTF.getText());
        } else {
            nameT.setText("player");
        }
    }
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
    }

    private void deleteStart() {
        if (startOn) {
            nameEnter.setVisible(false);
            startOn = false;
            deleteStart();
        } else {
            scoreT.setText("Score: " + score);
            scoreCheck();
            timer();
        }
    }

    private void timer() {
        start = System.currentTimeMillis();
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            millisElapsed = System.currentTimeMillis() - start;
            timeL.setText(DurationFormatUtils.formatDuration(millisElapsed, "mm:ss"));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void scoreCheck() {
        if (score < 100) {
            enemy();
        } else {
            win();
        }
    }

    private void enemy() {
        random();
        if (whichEnemy == lastEnemy) {
            enemy();
        } else {
            Pane[] enemies = {one, two, three, four, five, six, seven};
            ImageView[] terrorist = {enemyOne,enemyTwo,enemyThree,enemyFour,enemyFive,enemySix,enemySeven};
            thisEnemy = enemies[whichEnemy];
            terrorist[whichEnemy].setImage(new Image(getClass().getResource("/images/terrorist.png").toExternalForm()));
            thisEnemy.setVisible(true);
        }
    }

    private void random() {
        lastEnemy = whichEnemy;
        Random random = new Random();
        whichEnemy = random.nextInt(7);
    }

    private void win() {
        ingameScore.setVisible(false);
        exitB.setVisible(false);
        results.setVisible(true);
        goodJobT.setText("Good job " + nameT.getText() + "!");
        finalTimeT.setText(DurationFormatUtils.formatDuration(millisElapsed, "mm:ss"));
        finalTimeSecT.setText(DurationFormatUtils.formatDuration(millisElapsed, "ss"));
        killedT.setText("and killed " + killed + " terrorists,");
        if (missedShots == 0) {
            missedT.setText("and you missed 0 shots. Nice!");
        } else if (missedShots < 10) {
            missedT.setText("and missed only " + missedShots + " shots.");
        } else {
            missedT.setText("but missed " + missedShots + " shots.");
        }
        finalScoreT.setText("Your calculated score is: " + calculateScore());
    }

    private int calculateScore() {
        int finalTimeSecInt = Integer.parseInt(finalTimeSecT.getText());
        double scoreDub = score;
        double kmf = killed + missedShots + finalTimeSecInt;
        finScore = (scoreDub / kmf) * 1000;
        return (int)finScore;
    }

    public void body(ActionEvent event) {
        shot();
        ++score;
        ++killed;
        scoreT.setText("Score: " + score);
        thisEnemy.setVisible(false);
        scoreCheck();
    }

    public void head(ActionEvent event) {
        shot();
        score = score + 2;
        ++killed;
        scoreT.setText("Score: " + score);
        thisEnemy.setVisible(false);
        scoreCheck();
    }

    public void missClick(ActionEvent event) {
        ++missedShots;
        shot();
    }

    private void shot(){
        Media med = new Media(getClass().getResource("/sounds/shot.mp3").toExternalForm());
        MediaPlayer shot = new MediaPlayer(med);
        shot.play();
    }

    public void exit(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        a.pause();
    }
}


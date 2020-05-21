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
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.*;
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

    /**
     * Here we load the background, also call the {@code music} method.
     */
    @FXML
    public void initialize(){
        streetBcg.setImage(new Image(getClass().getResource("/images/street/bcgStreet.png").toExternalForm()));;
        music();
    }

    /**
     * This is connected to the play button, when pressed it calls two methods.
     * @param event Click on button.
     * @throws IOException
     */
    public void start(ActionEvent event) throws IOException {
        getName();
        deleteStart();
    }

    /**
     * This method called by {@code start} checks the field where the player puts his/her name, if the name
     * is 3 character long {@code nameT} will be that in uppercase. If the given name is longer the 3 characters
     * then it will cut the first 3 characters and store stat. Else the basic name is "AAA".
     */
    private void getName() {
        if (nameTF.getText() != null && !nameTF.getText().isEmpty() && nameTF.getText().length() == 3) {
            nameT.setText((nameTF.getText().toUpperCase()));
        } else if (nameTF.getText() != null && !nameTF.getText().isEmpty() && nameTF.getText().length() > 3){
            nameT.setText(nameTF.getText().toUpperCase().substring(0,3));
        } else {
            nameT.setText("AAA");
        }
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
    }

    /**
     * This deletes the little window where the user puts its name, it is called by the {@code start} method. It is an
     * if-else because it worked the best for me. In the if it deletes the window, and calls itself, where it goes
     * in the else, where it sets the score text, also starting the timer under it (shown on the side of the window)
     * and calls {@code scoreCheck}.
     * @throws IOException
     */
    private void deleteStart() throws IOException {
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

    /**
     * This timer counts the time for the program, it runs until the player wins.
     */
    private void timer() {
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
     * @throws IOException
     */
    private void scoreCheck() throws IOException {
        if (score < 100) {
            enemy();
        } else {
            win();
        }
    }

    /**
     * Here the program spawns the enemies on the map. Firs we call {@code random} where we get a random number between
     * 0 an 7 (7 not included). The it checks if the random number is different than the last one, this way the enemy
     * will spawn in different locations every time. If the random number (aka {@code whichEnemy}) is the same as the
     * {@code lastEnemy} the it calls itself, this way getting a new random number. It goes until the new number won't
     * be the same as the last.
     * In the else it makes an array of the Panes which represent the enemies and also an array of the
     * {@code ImageView}s. Then it loads the image on the {@code ImageView} of the chosen Pane and makes that active.
     */
    private void enemy() {
        random();
        if (whichEnemy == lastEnemy) {
            enemy();
        } else {
            Pane[] enemies = {one, two, three, four, five, six, seven};
            ImageView[] terrorist = {enemyOne,enemyTwo,enemyThree,enemyFour,enemyFive,enemySix,enemySeven};
            thisEnemy = enemies[whichEnemy];
            terrorist[whichEnemy].setImage(new Image(getClass().getResource("/images/street/terrorist.png").toExternalForm()));
            thisEnemy.setVisible(true);
        }
    }
    private void random() {
        lastEnemy = whichEnemy;
        Random random = new Random();
        whichEnemy = random.nextInt(7);
    }

    /**
     * This is the longest method so it makes a lot of things. First it deletes the score field and the exit button
     * and makes the result field visible. Then it prints things (the name, the time, the kills, the missed
     * shots, and also the score) on it. At he end it calls method {@code storeScore}.
     * @throws IOException
     */
    private void win() throws IOException {
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
        storeScore();
    }

    /**
     * Here it calculates a score for the player this way it is easier for them to now measure performance.
     * @return The calculated score as Integer.
     */
    private int calculateScore() {
        int finalTimeSecInt = Integer.parseInt(finalTimeSecT.getText());
        double scoreDub = score;
        double kmf = killed + missedShots + finalTimeSecInt;
        finScore = (scoreDub / kmf) * 1000;
        return (int)finScore;
    }

    /**
     * This as it's name says stores the score the player got. It writes the data in a text file, with a space
     * between each data of course keeping the old data too. [The text file could be called only this way. If it's
     * being set with classloader the FileWriter won't found it, and there will be an error. This error does not
     * occur only if the file is in the root directory, this way i put it there.]
     * @throws IOException
     */
    private void storeScore() throws IOException {
        FileWriter fw = new FileWriter("scores.txt",true);
        BufferedWriter writer = new BufferedWriter(fw);
            writer.write(nameT.getText());
            writer.write(" "+(int)finScore);
            writer.write(" "+finalTimeT.getText());
            writer.write(" "+missedShots);
            writer.write(" "+killed);
            writer.newLine();
        writer.close();
    }

    /**
     * This method is connected to an invisible button place on the body of the enemies. As said the body shot
     * counts as one, so it raises the score by 1 like the number of kills. Also it changes the score text on the
     * right and deletes the enemy. It also calls the {@code shot} method, which plays the sound of a shot.
     * At the end calls the {@code scoreCheck}.
     * @param event Click on hitbox/button
     * @throws IOException
     */
    public void body(ActionEvent event) throws IOException {
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
     * @throws IOException
     */
    public void head(ActionEvent event) throws IOException {
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
     * The {@code exit} is connected to 2 buttons, one is the "Exit" at the lower right corner and the other one is
     * when the game ends, called "Menu". It just loads the Menu scene and stops the timer.
     * @param event Click on button (Exit & Menu)
     * @throws IOException
     */
    public void exit(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        a.pause();
    }
}


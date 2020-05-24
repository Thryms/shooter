package shooter;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class gameplayMethods {

    /**
     * This method sets the player's name based on the input at the tart of the game. If the name is as requested it
     * will be then in uppercase, if the name is longer than 3 characters than the name will be the first 3 characters
     * of the input in uppercase. If the input name is wrong the name will be "AAA".
     * @param tf The TextField where the player puts his/her name.
     * @return The set name as String.
     */
    public static String getName(TextField tf) {
        Text nameT = new Text();
        if (tf.getText() != null && !tf.getText().isEmpty() && tf.getText().length() == 3) {
            nameT.setText((tf.getText().toUpperCase()));
        } else if (tf.getText() != null && !tf.getText().isEmpty() && tf.getText().length() > 3){
            nameT.setText(tf.getText().toUpperCase().substring(0,3));
        } else {
            nameT.setText("AAA");
        }
        log.info("The player name is {}", nameT.getText());
        return nameT.getText();
    }

    /**
     * Setting missedT aka missed text, based on the number of the missed shots.
     * @param missedShots The number of missed shots.
     * @return The correct missedT based on the number of missed shots.
     */
    public static String missedText(int missedShots){
        log.info("Setting missedT");
        String missesText = "";
        if (missedShots == 0) {
            missesText="and you missed 0 shots. Nice!";
        } else if (missedShots < 10) {
            missesText="and missed only " + missedShots + " shots.";
        } else {
            missesText="but missed " + missedShots + " shots.";
        }
        return missesText;
    }

    /**
     * Here it calculates a score for the player this way it is easier for them to now measure performance.
     * @param finalTime The time the game takes, as seconds
     * @param score The score the player reaches (100 or 101)
     * @param killed The number of enemies the player kills
     * @param missedShots The number of shots the player misses.
     * @return The calculated score as Integer.
     */
    public static int calculateScore(Text finalTime, int score, int killed, int missedShots) {
        log.info("Calculating score...");
        int finalTimeSecInt = Integer.parseInt(finalTime.getText());
        double kmf = killed + missedShots + finalTimeSecInt;
        double finScore = ((double) score / kmf) * 1000;
        return (int)finScore;
    }
}

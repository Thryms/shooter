package shooter.outerMethods;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

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
        String[] name = tf.getText().split(" ");
        if (name.length==0){
            nameT.setText("AAA");
        }else if (tf.getText() != null && !tf.getText().isEmpty() && name[0].length() == 3) {
            nameT.setText(name[0].toUpperCase());
        } else if (tf.getText() != null && !tf.getText().isEmpty() && name[0].length() > 3){
            nameT.setText(name[0].toUpperCase().substring(0,3));
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
    public static int calculateScore(String finalTime, int score, int killed, int missedShots) {
        log.info("Calculating score...");
        int finalTimeSecInt = Integer.parseInt(finalTime);
        double kmf = killed + missedShots + finalTimeSecInt;
        double finScore = ((double) score / kmf) * 1000;
        return (int)finScore;
    }

    /**
     * This as it's name says stores the score the player got. It writes the data in a text file, with a space
     * between each data of course keeping the old data too. [The text file could be called only this way. If it's
     * being set with classloader the FileWriter won't found it, and there will be an error. This error does not
     * occur only if the file is in the root directory, this way i put it there.]
     * @throws IOException by FileWriter, if the named file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason.
     * BufferedWriter- If an I/O error occurs.
     */
    public static void storeScore(String string, String name, int score, String time, int missed, int killed) throws IOException {
        log.info("storing data");
        FileWriter fw = new FileWriter("scores/"+string,true);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(name);
        writer.write(" "+score);
        writer.write(" "+time);
        writer.write(" "+missed);
        writer.write(" "+killed);
        writer.newLine();
        writer.close();
    }
}
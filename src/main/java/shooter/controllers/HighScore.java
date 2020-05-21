package shooter.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class HighScore {

    /**
     * [Little side note]
     * I know this is not the best looking code, but with this part i spent more then 24 hrs to make it work
     * and after multiple failed attempts and because i have a deadline i'll stick to this code. Also i have
     * to say that this works fine, it does what it needs to and i like how it shows in game.
     */

    @FXML
    private ImageView hsBcg;
    @FXML
    private TextArea textArea;
    @FXML
    private ImageView menuButtonIV;

    private  MediaPlayer a;
    private boolean isThereData = false;

    /**
     * In the {@code initialize} it loads the background image also the image of the button. Also calls
     * {@code sortScores} which sorts the scores, and {@code setTextArea} where it loads the data in the TextArea if
     * there is data in the file (technically there is always is a newline in in bat that is not what we need).
     * And finally it starts the background music.
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {
        hsBcg.setImage(new Image(getClass().getResource("/images/menuS/menuBcg.png").toExternalForm()));
        menuButtonIV.setImage(new Image(getClass().getResource("/images/menuS/menuButtonBcg.png").toExternalForm()));
        sortScores();
        if(isThereData) {
            setTextArea();
        }
        music();
    }

    /**
     * This class is for initializing the result which it will use later to sort the scores.
     */
    class result{
        int score,misses,kills;
        String name,time;
        public result( String name, int score, String time, int misses, int kills){
            this.name = name;
            this.score = score;
            this.time = time;
            this.misses = misses;
            this.kills = kills;
        }
    }

    /**
     * In here it sets the {@code compare} so it sorts by the scores descending.
     */
    class scoreCompare implements Comparator<result>{
        @Override
        public int compare(result s1, result s2){
            return s2.score - s1.score;
        }
    }

    /**
     * In this long method it creates the sorted file, it also overrides the content of it, by the new sorted list.
     * Also this is where it uses the past two classes. It uses {@code BufferedReader} and {@code FileReader} from
     * reading the scores text file and {@code BufferedWriter} and {@code FileWriter} from creating the scoresSorted
     * text file.
     * In the while it reads the records, it goes by lines, basically cuts the lines at the spaces and puts
     * each part into an array of their kind. Also it sets the {@code isThereData} so we can check to call the
     * {@code setTextArea} or not.
     * Then it sorts the data, the arrays and writes everything into the sorted file. And at the end closes the
     * writer and the reader.
     * @throws IOException
     */
    public void sortScores() throws IOException{
        FileReader fr = new FileReader("scores.txt");
        BufferedReader reader = new BufferedReader(fr);
        ArrayList<result> resultArrayList = new ArrayList<result>();
        String currentLine = reader.readLine();

        while (currentLine != null) {
            String[] resultDetail = currentLine.split(" ");
            String name = resultDetail[0];
            int score = Integer.parseInt(resultDetail[1]);
            String time = resultDetail[2];
            int misses = Integer.parseInt(resultDetail[3]);
            int kills = Integer.parseInt(resultDetail[4]);
            resultArrayList.add(new result(name,score,time,misses,kills));
            currentLine = reader.readLine();
            isThereData = true;
        }
        resultArrayList.sort(new scoreCompare());
        FileWriter fw = new FileWriter("scoresSorted.txt");
        BufferedWriter writer = new BufferedWriter(fw);
        for (result result : resultArrayList){
            writer.write(result.name+checkScore(result.score));
            writer.write(result.score+"     ");
            writer.write(result.time+checkMisses(result.misses));
            writer.write(result.misses+checkKills(result.kills));
            writer.write(result.kills+"\n");
        }
        reader.close();
        writer.close();
    }

    /**
     * Here as in the following 2 methods it just sets the scoresSorted text. It basically checks how long is
     * the score (in this case) and returns spaces. This is because this way in the scoresSorted text file the
     * same type of data will be under each other in a line so it is better for the eye.
     * [It might not be the most elegant way to do it but it works fine.]
     * @param score score
     * @return A string of spaces.
     */
    private String checkScore(int score){
        if (score > -1 && score < 10){
            return "        ";
        } else if(score > 9 && score < 100){
            return "       ";
        } else if(score > 99 && score < 1000){
            return "      ";
        } else {
            return "     ";
        }
    }

    /**
     * Here it checks the misses.
     * @param miss misses
     * @return A string of spaces
     */
    private String checkMisses(int miss) {
        if (miss > -1 && miss < 10){
            return "       ";
        } else if(miss > 9 && miss < 100){
            return "      ";
        } else {
            return "     ";
        }
    }

    /**
     * Here it checks the kills.
     * @param kills kills
     * @return A string of spaces.
     */
    private String checkKills(int kills){
        if(kills < 100){
            return "      ";
        } else {
            return "     ";
        }
    }

    /**
     * Here it simply sets the text of the TextArea. It goes thru the sorted file and writes each line in the
     * TextArea until the file ends or it wrote 10 lines because we need only the 10 best results.
     */
    public void setTextArea(){
        File file = new File("scoresSorted.txt");
        int c = 1;
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine() || c < 11 ) {
                textArea.appendText(input.nextLine());
                textArea.appendText("\n");
                ++c;
                if(!input.hasNextLine()){
                    c = 11;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This is the same as used before, it loads the menu. It is connected to the "Menu" button.
     * @param event Click on button
     * @throws IOException
     */
    public void menuGoGo(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        a.pause();
    }

    /**
     * Also works the same as in the other controllers. It loads the music, sets it's volume to 80% and
     * keeps it repeating. [Also the MediaPlayer has to be declared in the class outside of this method else the
     * music will stop after few seconds!]
     */
    private void music(){
        Media med = new Media(getClass().getResource("/sounds/hsMusic.mp3").toExternalForm());
        a =new MediaPlayer(med);
        a.setVolume(0.8);
        a.setAutoPlay(true);
        a.setOnEndOfMedia(new Runnable() {
            public void run() {
                a.seek(Duration.ZERO);
            }
        });
    }
}

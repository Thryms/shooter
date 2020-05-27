package shooter.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import shooter.outerMethods.highscoreMethods;

import java.io.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

@Slf4j
public class HighScore {

    /**
     * [Little side note]
     * I know this is not the best looking code, but with this part i spent more then 24 hrs to make it work
     * and after multiple failed attempts and because i have a deadline i'll stick to this code. Also i have
     * to say that this works fine, it does what it needs to and i like how it shows in game.
     * Created based on this site: https://javaconceptoftheday.com/how-to-sort-a-text-file-in-java/
     */

    @FXML
    private ImageView hsBcg,muteIV,loadIV,menuButtonIV,clearIV;
    @FXML
    private TextArea textArea,placements;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Text titles;
    @FXML
    private Pane confirmPane;

    private  MediaPlayer a;
    private boolean isThereData = false, music=true;
    private String whichMap;

    /**
     * In the {@code initialize} it loads the background image also the image of the button. Also calls
     * {@code sortScores} which sorts the scores, and {@code setTextArea} where it loads the data in the TextArea if
     * there is data in the file (technically there is always is a newline in in bat that is not what we need).
     * And finally it starts the background music.
     */
    @FXML
    public void initialize() {
        hsBcg.setImage(new Image(getClass().getResource("/images/menuS/menuBcg.png").toExternalForm()));
        menuButtonIV.setImage(new Image(getClass().getResource("/images/menuS/menuButtonBcg.png").toExternalForm()));
        muteIV.setImage(new Image(getClass().getResource("/images/menuS/muteBcg.png").toExternalForm()));
        loadIV.setImage(new Image(getClass().getResource("/images/menuS/loadBcg.png").toExternalForm()));
        clearIV.setImage(new Image(getClass().getResource("/images/menuS/clearBcg.png").toExternalForm()));
        music();
        choiceBox.getItems().addAll("Afghan","Syrian");
        getOS();
    }

    /**
     * According to the operating system it sets the fonts of the texts.
     */
    private void getOS(){
        if (SystemUtils.IS_OS_WINDOWS_10){
            textArea.setFont(Font.font("monospace"));
            titles.setFont(Font.font("monospace"));
            placements.setFont(Font.font("monospace"));
        } else if (SystemUtils.IS_OS_LINUX){
            textArea.setFont(Font.font("FreeMono"));
            titles.setFont(Font.font("FreeMono"));
            placements.setFont(Font.font("FreeMono"));
        }
    }

    /**
     * This class is for initializing the result which it will use later to sort the scores.
     */
    static class result{
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
    static class scoreCompare implements Comparator<result>{
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
     * @param map This is the file where the unsorted scores are.
     * @param sortedmap This is the file where the sorted scores being put.
     * @throws IOException by FileWriter/Reader or BufferReader/Writer, if the named file exists but is a directory
     * rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason.
     *  BufferedWriter- If an I/O error occurs.
     */
    public void sortScores(String map,String sortedmap) throws IOException{
        FileReader fr = new FileReader("scores/"+map);
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
            resultArrayList.add(new result(name, score, time, misses, kills));
            currentLine = reader.readLine();
            isThereData = true;
            log.info("Breaking down one line into arrays");
        }
        resultArrayList.sort(new scoreCompare());
        FileWriter fw = new FileWriter("scores/"+sortedmap);
        BufferedWriter writer = new BufferedWriter(fw);
        for (result result : resultArrayList){
            writer.write(result.name+highscoreMethods.checkScore(result.score));
            writer.write(result.score+"     ");
            writer.write(result.time+highscoreMethods.checkMisses(result.misses));
            writer.write(result.misses+highscoreMethods.checkKills(result.kills));
            writer.write(result.kills+"\n");
            log.info("Writing one sorted line");
        }
        reader.close();
        log.info("BufferReader closed");
        writer.close();
        log.info("BufferWriter closed");
    }

    /**
     * Here it gets the value of the choice box, and according to what did the user chose ot loads the scores of
     * that map and calls the sorting and writing methods with the correct strings.
     * @param event Click on button
     * @throws IOException If setTextArea() throws an exception
     */
    public void getChoice(ActionEvent event) throws IOException{
        whichMap = choiceBox.getValue();
        if (whichMap != null && whichMap.equals("Syrian")) {
            sortScores("scoresSyria.txt", "scoresSyriaSorted.txt");
            if(isThereData){
                textArea.clear();
                setTextArea("scoresSyriaSorted.txt");
            }
        } else if (whichMap != null && whichMap.equals("Afghan")){
            sortScores("scoresAfghan.txt","scoresAfghanSorted.txt");
            if(isThereData){
                textArea.clear();
                setTextArea("scoresAfghanSorted.txt");
            }
        }
    }

    /**
     * Here it simply sets the text of the TextArea. It goes thru the sorted file and writes each line in the
     * TextArea until the file ends or it wrote 10 lines because we need only the 10 best results.
     * @param sortedMap This is the sorted file whose lines it will set to the TextArea.
     */
    public void setTextArea(String sortedMap){
        File file = new File("scores/"+sortedMap);
        int c = 1;
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine() && c < 11 ) {
                textArea.appendText(input.nextLine());
                textArea.appendText("\n");
                ++c;
                if(!input.hasNextLine()){
                    c = 11;
                }
            }
            log.info("Setting Text Area");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This is the same as used before, it loads the menu. It is connected to the "Menu" button.
     * @param event Click on button
     * @throws IOException by FXMLLoader.load
     */
    public void menuGoGo(ActionEvent event) throws IOException {
        log.info("Going back to Menu from the High Score");
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
        log.info("Playing music in HighScore");
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

    /**
     * Sets the pane's visibility true.
     * @param event CLick on button "clear scores"
     */
    public void clearPress(ActionEvent event) {
        confirmPane.setVisible(true);
    }

    /**
     * Called by {@code clearPress}. Here it basically replaces the score file with empty, then clears the TextArea.
     * @param scores The file where the scores are
     * @param sorted The file where the sorted scores are
     * @throws IOException by FileWriter/Reader, if the named file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason.
     * BufferedWriter/Reader- If an I/O error occurs.
     */
    private void clear(String scores, String sorted) throws IOException{
        log.info("Deleting data from "+scores);
        FileWriter fr = new FileWriter("scores/"+scores);
        BufferedWriter writer = new BufferedWriter(fr);
        writer.write("");
        writer.close();
        sortScores(scores,sorted);
        textArea.clear();
    }

    /**
     * If yes button pressed it deletes the scores connected to the file in the chose bar.
     * @param event Click on button yes
     * @throws IOException by {@code clear} method
     */
    public void yes(ActionEvent event) throws IOException {
        confirmPane.setVisible(false);
        if(whichMap != null && whichMap.equals("Afghan")){
            clear("scoresAfghan.txt","scoresAfghanSorted.txt");
        } else if (whichMap != null && whichMap.equals("Syrian")){
            clear("scoresSyria.txt","scoresSyriaSorted.txt");
        }
    }

    /**
     * Sets the pane invisible.
     * @param event Click on button no.
     */
    public void no(ActionEvent event){
        confirmPane.setVisible(false);
        textArea.clear();
    }
}

package shooter.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class HighScore {


    @FXML
    private ImageView hsBcg;
    @FXML
    private TextArea textArea;

    private int scoreLength;

    @FXML
    public void initialize() throws IOException {
        hsBcg.setImage(new Image(getClass().getResource("/images/menuBcg.png").toExternalForm()));
        sortScores();
        setTextArea();
    }

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

    class scoreCompare implements Comparator<result>{
        @Override
        public int compare(result s1, result s2){
            return s2.score - s1.score;
        }
    }

    public void sortScores() throws IOException{
        //FileReader fr = new FileReader(getClass().getResource("/scores/scores.txt").toExternalForm());
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
        }
        Collections.sort(resultArrayList, new scoreCompare());
        //FileWriter fw = new FileWriter(getClass().getResource("/scores/scoresSorted.txt").toExternalForm());
        FileWriter fw = new FileWriter("scoresSorted.txt");
        BufferedWriter writer = new BufferedWriter(fw);
        for (result result : resultArrayList){
            writer.write(result.name+"     ");
            writer.write(result.score+checkScore(result.score));
            writer.write(result.time+"     ");
            writer.write(result.misses+checkMisses(result.misses));
            writer.write(result.kills+"\n");
            //writer.newLine();
        }
        reader.close();
        writer.close();
    }

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

    private String checkMisses(int miss) {
        if (miss > -1 && miss < 10){
            return "       ";
        } else if(miss > 9 && miss < 100){
            return "      ";
        } else {
            return "     ";
        }
    }

    public void setTextArea(){
        File file = new File("scoresSorted.txt");
        int c = 1;
        try (Scanner input = new Scanner(file)) {
            while (/*input.hasNextLine()*/ c < 11 ) {
                textArea.appendText(input.nextLine());
                textArea.appendText("\n");
                ++c;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void menuGoGo(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}

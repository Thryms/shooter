package shooter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import shooter.house.house;

import java.awt.event.ActionEvent;
import java.io.IOException;


public class Main extends Application {

    Scene scene1, playScene;

    @Override
    public void start(Stage stage){
        //Main menu
        Button exit =  new Button("Exit");
        exit.setOnAction((ActionEvent) -> {
            Platform.exit();});

        Button play = new Button("Play");
        play.setOnAction((ActionEvent) ->{
            Parent houseScript = null;
            try {
                houseScript = FXMLLoader.load(getClass().getResource("house.houseFXML.fxml"));
                playScene = new Scene(houseScript);
                stage.setScene(playScene);
                stage.setFullScreen(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        Button highScore = new Button("High Score");

        VBox menu = new VBox();
        menu.setPadding(new Insets(10));
        menu.setSpacing(8);
        menu.getChildren().addAll(play,highScore, exit);
        menu.setAlignment(Pos.CENTER);

        play.setPrefSize(500, 150);
        play.setStyle("-fx-font-size: 5em; -fx-background-color: #add8e6; -fx-border-color: #00ff00; -fx-border-width: 0.5em;");
        highScore.setPrefSize(500,150);
        highScore.setStyle("-fx-font-size: 5em; -fx-background-color: #add8e6; -fx-border-color: #00ff00; -fx-border-width: 0.5em;");
        exit.setPrefSize(500,150);
        exit.setStyle("-fx-font-size: 5em; -fx-background-color: #add8e6; -fx-border-color: #00ff00; -fx-border-width: 0.5em;");

        scene1 = new Scene(menu, 1000, 1000);

       /* //Play scene
        Button back = new Button("Back");
        back.setOnAction((ActionEvent) -> {
            stage.setScene(scene1);
        });

        HBox playBox = new HBox();
        playBox.getChildren().addAll(back);
        Parent houseScript = FXMLLoader.load(getClass().getResource("house.houseFXML.fxml"));
        playScene = new Scene(houseScript);*/


        stage.setTitle("Shooter");
        stage.setScene(scene1);
        stage.show();
        stage.setFullScreen(true);
    }


  /*  private void initUI(Stage stage){
        //Main menu
        Button exit =  new Button("Exit");
        exit.setOnAction((ActionEvent) -> {
            Platform.exit();});

        Button play = new Button("Play");
        play.setOnAction((ActionEvent) ->{
            stage.setScene(playScene);
            stage.setFullScreen(true);
        });

        Button highScore = new Button("High Score");

        VBox menu = new VBox();
        menu.setPadding(new Insets(10));
        menu.setSpacing(8);
        menu.getChildren().addAll(play,highScore, exit);
        menu.setAlignment(Pos.CENTER);

        play.setPrefSize(500, 150);
        play.setStyle("-fx-font-size: 5em; -fx-background-color: #add8e6; -fx-border-color: #00ff00; -fx-border-width: 0.5em;");
        highScore.setPrefSize(500,150);
        highScore.setStyle("-fx-font-size: 5em; -fx-background-color: #add8e6; -fx-border-color: #00ff00; -fx-border-width: 0.5em;");
        exit.setPrefSize(500,150);
        exit.setStyle("-fx-font-size: 5em; -fx-background-color: #add8e6; -fx-border-color: #00ff00; -fx-border-width: 0.5em;");

        scene1 = new Scene(menu, 1000, 1000);

       /* //Play scene
        Button back = new Button("Back");
        back.setOnAction((ActionEvent) -> {
            stage.setScene(scene1);
        });

        HBox playBox = new HBox();
        playBox.getChildren().addAll(back);
        Parent houseScript = FXMLLoader.load(getClass().getResource("house.houseFXML.fxml"));
        playScene = new Scene(houseScript);


        stage.setTitle("Shooter");
        stage.setScene(scene1);
        stage.show();
        stage.setFullScreen(true);
    }*/


    public static void main(String[] args) {
        launch(args);
    }


}

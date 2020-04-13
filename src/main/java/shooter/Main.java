package shooter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;


public class Main extends Application {

    Scene scene1, scene2;

    @Override
    public void start(Stage stage){
        initUI(stage);
    }

    private void initUI(Stage stage){
        Button exit =  new Button("Exit");
        exit.setOnAction((ActionEvent) -> {
            Platform.exit();});

        Button play = new Button("Play");
        //play.setOnAction((ActionEvent) ->{
        //    stage.setScene(scene2);
        //});

        Button highScore = new Button("High Score");

        VBox menu = new VBox();
        menu.setPadding(new Insets(10));
        menu.setSpacing(8);
        menu.getChildren().addAll(play,highScore, exit);
        menu.setAlignment(Pos.CENTER);

        play.setPrefSize(250, 75);
        play.setStyle("-fx-font-size: 2.5em; -fx-background-color: #add8e6;");
        highScore.setPrefSize(250,75);
        highScore.setStyle("-fx-font-size: 2.5em; -fx-background-color: #add8e6;");
        exit.setPrefSize(250,75);
        exit.setStyle("-fx-font-size: 2.5em; -fx-background-color: #add8e6;");

        scene1 = new Scene(menu, 600, 600);
        stage.setTitle("Shooter");
        stage.setScene(scene1);
        stage.show();

        stage.setFullScreen(true);
    }


    public static void main(String[] args) {
        launch(args);
    }

}

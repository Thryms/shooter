//package org.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;




public class Main extends Application {

    @Override
    public void start(Stage stage){
        initUI(stage);
    }

    private void initUI(Stage stage){
        var root = new StackPane();
        var scene = new Scene(root, 500, 500);
        var lbl = new Label("TODO");
        lbl.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
        root.getChildren().add(lbl);

        stage.setTitle("Shooter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

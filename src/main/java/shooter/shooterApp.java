package shooter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class shooterApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Starting application...");
        Parent root = FXMLLoader.load(shooterApp.class.getResource("/fxml/menuFXML.fxml"));
        primaryStage.setTitle("Shooter");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }
}

package shooter;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.AbstractModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import shooter.results.gameResultDao;
import util.guice.PersistenceModule;


import java.util.List;

@Slf4j
public class shooterApp extends Application {

    private GuiceContext context = new GuiceContext(this, () -> List.of(
            new AbstractModule() {
                @Override
                protected void configure() {
                    install(new PersistenceModule("shooter"));
                    bind(gameResultDao.class);
                }
            }
    ));

    @Override
    public void start(Stage primaryStage) throws Exception {
        context.init();
        Parent root = FXMLLoader.load(shooterApp.class.getResource("/fxml/menuFXML.fxml"));
        primaryStage.setTitle("Shooter");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }
}

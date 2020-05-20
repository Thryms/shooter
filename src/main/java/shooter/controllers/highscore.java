package shooter.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import shooter.results.gameResult;
import shooter.results.gameResultDao;

import javax.inject.Inject;
import java.util.List;

@Slf4j
public class highscore {

    @Inject
    private gameResultDao gameRD;
    @FXML
    private TableView<gameResult> highScoreTable;
    @FXML
    private TableColumn<gameResult,String> nameC,timeC;
    @FXML
    private TableColumn<gameResult,Integer> killsC,scoreC,missesC;

    @FXML
    private void initialize() throws NullPointerException{

            //List<gameResult> highscoreList = gameResultDao.findBest(10);
            List<gameResult> highscoreList = gameRD.findBest(10);

        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeC.setCellValueFactory(new PropertyValueFactory<>("time"));
        killsC.setCellValueFactory(new PropertyValueFactory<>("kills"));
        missesC.setCellValueFactory(new PropertyValueFactory<>("misses"));
        scoreC.setCellValueFactory(new PropertyValueFactory<>("score"));


        ObservableList<gameResult> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(highscoreList);

        highScoreTable.setItems(observableResult);
    }
}


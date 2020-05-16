package shooter.streetMap;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.Random;

public class street {

    @FXML
    public Pane one,two,three,four,five,six,seven;

    @FXML
    private Button startButton;

    private boolean death;
 /*   private BufferedImage houseBcg;

    public BufferedImage houseSetup() {
        houseBcg = Resources.getImage("/images/menuPanelBackground.png");
    }*/

    public void start(ActionEvent event) throws InterruptedException{
        //System.out.println("Start");
        startButton.setVisible(false);
        death = false;
        game();
    }

    public void body(ActionEvent event){
        System.out.println("Body");
        despawn(five);
    }

    public void head(ActionEvent event){
        System.out.println("Head");
        despawn(five);
    }

    public void despawn(Pane pane) {
        pane.setVisible(false);
    }

    public void spawn(Pane pane){
        pane.setVisible(true);
    }

    private void game() throws InterruptedException{
        while(!death){
            //randomEnemy re = new randomEnemy();
            //spawn(re.enemy());
            spawn(enemy());
            Thread.sleep(4000);
            death=true;
        }

    }

    //EZT KI KELL JAVITANI, NEM LEHET IGY, MEG KELL CSINALNI randomEnemy.java-ba!!!!!
    private int[] nums= {1,2,3,4,5,6,7};
    private int whichEnemy,random;
    private Pane whichPane;

    public Pane enemy() {
        random = new Random().nextInt(nums.length);
        whichEnemy = nums[random];

        if(whichEnemy==1){
            whichPane = one;
        } else if(whichEnemy==2){
            whichPane = two;
        } else if(whichEnemy==3){
            whichPane = three;
        } else if(whichEnemy==4){
            whichPane = four;
        } else if(whichEnemy==5){
            whichPane = five;
        } else if(whichEnemy==6){
            whichPane = six;
        } else if(whichEnemy==7){
            whichPane = seven;
        }

        return whichPane;
    }

}


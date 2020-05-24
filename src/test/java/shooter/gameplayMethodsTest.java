package shooter;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shooter.gameplayMethods;

import static org.junit.jupiter.api.Assertions.*;

class gameplayMethodsTest {

    private JFXPanel panel = new JFXPanel();
    @BeforeEach
    void setUp() {
    }

    @Test
    void getNameTest() {
        assertEquals("JUH", gameplayMethods.getName(new TextField("juhte")));
        assertEquals("AAA",gameplayMethods.getName(new TextField()));
        assertEquals("LOK",gameplayMethods.getName(new TextField("LOK")));
        assertEquals("AAA",gameplayMethods.getName(new TextField("s")));
        assertEquals("   ",gameplayMethods.getName(new TextField("   ")));
        assertEquals("K73",gameplayMethods.getName(new TextField("k73")));
        assertEquals("+-/",gameplayMethods.getName(new TextField("+-/")));
        assertEquals("AAA",gameplayMethods.getName(new TextField("te")));
    }

    @Test
    void missedTextTest() {
        assertEquals("and you missed 0 shots. Nice!", gameplayMethods.missedText(0));
        assertEquals("and missed only 3 shots.", gameplayMethods.missedText(3));
        assertEquals("but missed 14 shots.", gameplayMethods.missedText(14));
    }

    @Test
    void calculateScoreText() {
        assertEquals(1063, gameplayMethods.calculateScore(new Text("40"),100,50,4));
        assertEquals(737, gameplayMethods.calculateScore(new Text("60"),101,64,13));
    }
}
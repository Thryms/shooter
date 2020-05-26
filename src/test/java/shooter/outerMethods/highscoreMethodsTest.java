package shooter.outerMethods;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class highscoreMethodsTest {

    @Test
    void checkScoreTest() {
        assertEquals("        ", highscoreMethods.checkScore(0));
        assertEquals("        ", highscoreMethods.checkScore(5));
        assertEquals("       ", highscoreMethods.checkScore(13));
        assertEquals("       ", highscoreMethods.checkScore(67));
        assertEquals("      ", highscoreMethods.checkScore(145));
        assertEquals("     ", highscoreMethods.checkScore(1303));
    }

    @Test
    void checkMissesTest() {
        assertEquals("       ", highscoreMethods.checkMisses(5));
        assertEquals("      ", highscoreMethods.checkMisses(12));
        assertEquals("      ", highscoreMethods.checkMisses(99));
        assertEquals("     ", highscoreMethods.checkMisses(123));
    }

    @Test
    void checkKillsTest() {
        assertEquals("      ", highscoreMethods.checkKills(50));
        assertEquals("      ", highscoreMethods.checkKills(65));
        assertEquals("      ", highscoreMethods.checkKills(99));
        assertEquals("     ", highscoreMethods.checkKills(101));
    }
}
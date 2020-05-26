package shooter.outerMethods;

public class highscoreMethods {

    /**
     * Here as in the following 2 methods it just sets the scoresSorted text. It basically checks how long is
     * the score (in this case) and returns spaces. This is because this way in the scoresSorted text file the
     * same type of data will be under each other in a line so it is better for the eye.
     * [It might not be the most elegant way to do it but it works fine.]
     * @param score score
     * @return A string of spaces.
     */
    public static String checkScore(int score){
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

    /**
     * Here it checks the misses.
     * @param miss misses
     * @return A string of spaces
     */
    public static String checkMisses(int miss) {
        if (miss > -1 && miss < 10){
            return "       ";
        } else if(miss > 9 && miss < 100){
            return "      ";
        } else {
            return "     ";
        }
    }

    /**
     * Here it checks the kills.
     * @param kills kills
     * @return A string of spaces.
     */
    public static String checkKills(int kills){
        if(kills < 100){
            return "      ";
        } else {
            return "     ";
        }
    }
}

import java.util.*;

class ScoreComparator implements Comparator<Score> {
    public int compare(Score a, Score b) {
        int sc1 = a.getTime4Compare();
        int sc2 = b.getTime4Compare();

        if (sc1 > sc2) {
            return 1;
        } else if (sc1 == sc2) {
            return 0;
        } else {
            return -1;
        }
    }
}
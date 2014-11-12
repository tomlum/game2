package imperial.squadron;
import java.util.Random;

public class Tester {
    
    static Random rand = new Random();
    public static Integer randomInt( int min, int max ) {
        return rand.nextInt((max - min) + 1) + min; }
    
    
}

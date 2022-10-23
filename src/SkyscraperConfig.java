import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Represents a single configuration in the skyscraper puzzle.
 *
 * @author RIT CS
 * @author YOUR NAME HERE
 */
public class SkyscraperConfig implements Configuration {
    /** empty cell value */
    public final static int EMPTY = 0;

    /** empty cell value display */
    public final static char EMPTY_CELL = '.';

    private static int DIM;
    private static int[] lookNS;
    private static int[] lookEW;
    private static int[] lookSN;
    private static int[] lookWE;
    private int[][]grid;
    /**
     * Constructor
     *
     * @param filename the filename
     *  <p>
     *  Read the board file.  It is organized as follows:
     *  DIM     # square DIMension of board (1-9)
     *  lookNS   # DIM values (1-DIM) left to right
     *  lookEW   # DIM values (1-DIM) top to bottom
     *  lookSN   # DIM values (1-DIM) left to right
     *  lookWE   # DIM values (1-DIM) top to bottom
     *  row 1 values    # 0 for empty, (1-DIM) otherwise
     *  row 2 values    # 0 for empty, (1-DIM) otherwise
     *  ...
     *
     *  @throws FileNotFoundException if file not found
     */
    SkyscraperConfig(String filename) throws FileNotFoundException {
        Scanner f = new Scanner(new File(filename));

        // TO DO
        DIM = f.nextInt();
        lookNS = new int[DIM];
        for(int i = 0; i < DIM; i++){
            lookNS[i] = f.nextInt();
        }
        // do for other 3 look arrays
        lookEW = new int[DIM];
        for(int i = 0; i < DIM; i++){
            lookEW[i] = f.nextInt();
        }

        lookSN = new int[DIM];
        for(int i = 0; i < DIM; i++){
            lookSN[i] = f.nextInt();
        }

        lookWE = new int[DIM];
        for(int i = 0; i < DIM; i++){
            lookWE[i] = f.nextInt();
        }

        // initialize grid
        this.grid = new int[DIM][DIM];
        for(int r = 0; r < DIM; r++){
            for(int c = 0; c < DIM; c++){
                this.grid[r][c] = f.nextInt();
            }
        }

        // close the input file
        f.close();
    }

    /**
     * Copy constructor
     *
     * @param copy SkyscraperConfig instance
     */
    public SkyscraperConfig(SkyscraperConfig copy) {

        // TODO

    }

    @Override
    public boolean isGoal() {

        // TODO

        return false; // remove after implementing
    }

    /**
     * getSuccessors
     *
     * @returns Collection of Configurations
     */
    @Override
    public Collection<Configuration> getSuccessors() {

        // TODO

        return new ArrayList<>();   // remove after implementing
    }

    /**
     * isValid() - checks if current config is valid
     *
     * @returns true if config is valid, false otherwise
     */
    @Override
    public boolean isValid() {

        // TODO

        return false;  // remove after implementing
    }

    /**
     * toString() method
     *
     * @return String representing configuration board & grid w/ look values.
     * The format of the output for the problem solving initial config is:
     *
     *   1 2 4 2
     *   --------
     * 1|. . . .|3
     * 2|. . . .|3
     * 3|. . . .|1
     * 3|. . . .|2
     *   --------
     *   4 2 1 2
     */
    @Override
    public String toString() {

        // TODO
        String stringNS = "N: [";
        String concatNS = "";
        String stringEW = "E: [";
        String concatEW = "";
        String stringSN = "S: [";
        String concatSN = "";
        String stringWE = "W: [";
        String concatWE = "";
        for(int i = 0; i < DIM - 1; i++){
            concatNS = concatNS + lookNS[i] + ", ";
            concatEW = concatEW + lookEW[i] + ", ";
            concatSN = concatSN + lookSN[i] + ", ";
            concatWE = concatWE + lookWE[i] + ", ";
        }
        System.out.println(stringNS + concatNS +lookNS[DIM - 1] +"]");
        System.out.println(stringEW + concatEW +lookEW[DIM - 1] +"]");
        System.out.println(stringSN + concatSN +lookSN[DIM - 1] +"]");
        System.out.println(stringWE + concatWE +lookWE[DIM - 1] +"]");

        // prints top header of config
        System.out.print(" ");
        for(int header = 0; header < DIM; header++){
            System.out.print(" " + lookNS[header]);
        }
        System.out.println("");

        System.out.print("  ");
        for(int header_dash = 0; header_dash < DIM; header_dash++){
            System.out.print("--");
        }
        System.out.println("");

        for(int r = 0; r < DIM; r++){
            System.out.print(lookWE[r] + "|");
            for(int c = 0; c < DIM; c++){
                System.out.print(EMPTY_CELL + " ");
            }
            System.out.println("|" + lookEW[r]);
        }

        System.out.print("  ");
        for(int header_dash = 0; header_dash < DIM; header_dash++){
            System.out.print("--");
        }
        System.out.println();

        System.out.print(" ");
        for(int header = 0; header < DIM; header++){
            System.out.print(" " + lookSN[header]);
        }
        System.out.println("");

        return "SkyscraperConfig::toString() not implemented";  // remove
    }
}

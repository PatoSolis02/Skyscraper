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

    /** dimension of board */
    private static int DIM;

    /** desired value of number of towers seen from NS for each column */
    private static int[] lookNS;

    /** desired value of number of towers seen from EW for each row */
    private static int[] lookEW;

    /** desired value of number of towers seen from SN for each column */
    private static int[] lookSN;

    /** desired value of number of towers seen from WE for each row */
    private static int[] lookWE;

    /** grid to be populated */
    private int[][]grid;

    /** current column */
    private int col = -1;

    /** current row */
    private int row = 0;

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
    public SkyscraperConfig(SkyscraperConfig copy, int num) {

        // TODO
        this.row = copy.row;
        this.col = copy.col;
        this.grid = new int[DIM][DIM];

        this.col += 1;
        if(this.col == DIM) {
            this.col = 0;
            this.row++;
        }

        for(int r = 0; r < DIM; r++){
            System.arraycopy(copy.grid[r], 0, this.grid[r], 0, DIM);
        }
        this.grid[row][col] = num;
    }

    /**
     * checks if board is a solution.
     *
     * @return true if board is filled, false otherwise
     */
    @Override
    public boolean isGoal() {

        // TODO
        return col == DIM-1 && row == DIM-1;
    }

    /**
     * getSuccessors
     *
     * @return Collection of Configurations
     */
    @Override
    public Collection<Configuration> getSuccessors() {

        // TODO
        List<Configuration> successors = new LinkedList<>();
        for(int i = 1; i <= DIM; i++){
            successors.add(new SkyscraperConfig(this, i));
        }

        return successors;
    }

    /**
     * isValid() - checks if current config is valid
     *
     * @return true if config is valid, false otherwise
     */
    @Override
    public boolean isValid() {

        // TODO
        boolean flag = true;
        int countEW = 0; // num of towers visible looking EW
        int countWE = 0; // num of towers visible looking WE
        int countNS = 0; // num of towers visible looking NS
        int countSN = 0; // num of towers visible looking SN
        int maxSeen = 0; // value of the biggest tower seen

        // checks for duplicates
        for(int c = 0; c < DIM; c++){
            if(grid[row][col] == grid[row][c] && col != c){
                flag = false;
                break;
            }
        }
        for(int r = 0; r < DIM; r++ ){
            if(grid[row][col] == grid[r][col] && row != r){
                flag = false;
                break;
            }
        }

        // checks if configuration is a possible solution for the row each time a cell is populated (WE direction)
        for(int c = 0; c <= col; c++){
            if(grid[row][c] > maxSeen){
                maxSeen = grid[row][c];
                countWE++;
                if(countWE > lookWE[row]){
                    return false;
                }
            }
        }
        // checks if configuration is a possible solution when row is filled (EW direction)
        maxSeen = 0;
        if(col == DIM -1){
            for(int c = col; c >= 0; c--){
                if(grid[row][c] > maxSeen){
                    maxSeen = grid[row][c];
                    countEW++;
                    if(countEW > lookEW[row]){
                        return false;
                    }
                }
            }
        }

        // checks if configuration is a possible solution for the row each time a cell is populated (NS direction)
        maxSeen = 0;
        for(int r = 0; r < row; r++){
            if(grid[r][col] > maxSeen){
                maxSeen = grid[r][col];
                countNS++;
                if(countNS > lookNS[col]){
                    return false;
                }
            }
        }
        // checks if configuration is a possible solution for the row each time a cell is populated (SN direction)
        if(row == DIM - 1){
            maxSeen = 0;
            for(int r = DIM - 1; r >= 0; r--){
                if(grid[r][col] > maxSeen){
                    maxSeen = grid[r][col];
                    countSN++;
                    if(countSN > lookSN[col]){
                        return false;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * toString() method
     *
     * @return String representing configuration board & grid w/ look values.
     * The format of the output for the problem-solving initial config is:
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
        // prints top header of config
        System.out.print(" ");
        for(int header = 0; header < DIM; header++){
            System.out.print(" " + lookNS[header]);
        }
        System.out.println();

        System.out.print("  ");
        for(int header_dash = 0; header_dash < DIM; header_dash++){
            System.out.print("--");
        }
        System.out.println();

        for(int r = 0; r < DIM; r++){
            System.out.print(lookWE[r] + "|");
            for(int c = 0; c < DIM; c++){
                if(this.grid[r][c] == 0){
                    System.out.print(EMPTY_CELL + " ");
                }else{
                    System.out.print(grid[r][c] + " ");
                }
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
        System.out.println();

        return "";  // remove
    }
}

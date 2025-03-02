/**
 * Generates a sudoku of given dimension
 * - dimension must be a perfect square
 * - generates a sudoku with numbers from 1 to dimension
 * - shuffles the sudoku
 * - shuffles the rows of the same subgrid
 * - shuffles the columns of the same subgrid
 * - prints the generated sudoku
 */
public class SudokuGenerator {
    public static void main(String[] args) {
        int[][] sudoku = generateSudoku(9);
        printSudoku(sudoku);
    }

    /**
     * Generates a sudoku of given dimension
     * 
     * @param dimension dimension of the sudoku
     * @return generated sudoku
     */
    public static int[][] generateSudoku(int dimension) {
        int singleGridSize = (int) Math.sqrt(dimension);
        if (singleGridSize * singleGridSize != dimension) {
            throw new IllegalArgumentException("dimension must be a perfect square");
        }

        int[][] sudoku = new int[dimension][dimension];

        initiateSudoku(sudoku, dimension, singleGridSize);

        suffleSudoku(sudoku, dimension, singleGridSize);

        return sudoku;
    }

    /**
     * Initiates the sudoku with numbers
     * 
     * @param sudoku         sudoku to be initiated
     * @param dimension      dimension of the sudoku
     * @param singleGridSize size of the subgrid
     */
    private static void initiateSudoku(int[][] sudoku, int dimension, int singleGridSize) {

        int[] row = new int[dimension];
        for (int i = 0; i < dimension; i++) {
            row[i] = i + 1;
        }

        for (int subgridRow = 0; subgridRow < singleGridSize; subgridRow++) {
            for (int subgrid = 0; subgrid < dimension / singleGridSize; subgrid++) {
                int rowNumber = subgrid * singleGridSize + subgridRow;
                System.arraycopy(row, 0, sudoku[rowNumber], 0, dimension);
                circularRotate(row);
            }
        }
    }

    /**
     * Shuffles the sudoku
     * 
     * @param sudoku         sudoku to be shuffled
     * @param dimension      dimension of the sudoku
     * @param singleGridSize size of the subgrid
     */
    private static void suffleSudoku(int[][] sudoku, int dimension, int singleGridSize) {
        int numberOfShuffles = 10 + (int) (Math.random() * 10);

        for (int i = 0; i < numberOfShuffles; i++) {
            int shuffleType = (int) (Math.random() * 2);
            if (shuffleType == 0) {
                shuffleSameGridRows(sudoku, dimension, singleGridSize);
            } else if (shuffleType == 1) {
                shuffleSameGridColumns(sudoku, dimension, singleGridSize);
            }
        }
    }

    /**
     * Shuffles the rows of the same subgrid
     * 
     * @param sudoku         sudoku to be shuffled
     * @param dimension      dimension of the sudoku
     * @param singleGridSize size of the subgrid
     */
    private static void shuffleSameGridRows(int[][] sudoku, int dimension, int singleGridSize) {
        int subgrid = (int) (Math.random() * singleGridSize);
        int subgridRow1 = (int) (Math.random() * singleGridSize);
        int subgridRow2 = (int) (Math.random() * singleGridSize);

        int actualRow1 = subgrid * singleGridSize + subgridRow1;
        int actualRow2 = subgrid * singleGridSize + subgridRow2;

        int[] temp = sudoku[actualRow1];
        sudoku[actualRow1] = sudoku[actualRow2];
        sudoku[actualRow2] = temp;
    }

    /**
     * Shuffles the columns of the same subgrid
     * 
     * @param sudoku         sudoku to be shuffled
     * @param dimension      dimension of the sudoku
     * @param singleGridSize size of the subgrid
     */
    private static void shuffleSameGridColumns(int[][] sudoku, int dimension, int singleGridSize) {
        int subgrid = (int) (Math.random() * singleGridSize);
        int subgridColumn1 = (int) (Math.random() * singleGridSize);
        int subgridColumn2 = (int) (Math.random() * singleGridSize);

        int actualColumn1 = subgrid * singleGridSize + subgridColumn1;
        int actualColumn2 = subgrid * singleGridSize + subgridColumn2;

        for (int i = 0; i < dimension; i++) {
            int temp = sudoku[i][actualColumn1];
            sudoku[i][actualColumn1] = sudoku[i][actualColumn2];
            sudoku[i][actualColumn2] = temp;
        }
    }

    /**
     * Rotates the row by one
     * 
     * @param row row to be rotated
     */
    private static void circularRotate(int[] row) {
        int temp = row[0];
        for (int i = 0; i < row.length - 1; i++) {
            row[i] = row[i + 1];
        }
        row[row.length - 1] = temp;
    }

    /**
     * Prints the sudoku
     * 
     * @param sudoku sudoku to be printed
     */
    public static void printSudoku(int[][] sudoku) {
        int singleGridSize = (int) Math.sqrt(sudoku.length);

        for (int i = 0; i < sudoku.length; i++) {
            if (i % singleGridSize == 0) {
                System.out.println((" -" + ("----".repeat(singleGridSize))).repeat(singleGridSize));
            }
            for (int j = 0; j < sudoku[0].length; j++) {
                if (j % singleGridSize == 0) {
                    System.out.print("| ");
                }
                System.out.print(String.format(" %-2s", sudoku[i][j] == 0 ? " " : Integer.toString(sudoku[i][j])));

                System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println((" -" + ("----".repeat(singleGridSize))).repeat(singleGridSize));
    }
}

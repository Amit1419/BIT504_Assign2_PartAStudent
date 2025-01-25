import java.awt.*;

public class Board {
    // Grid line width in pixels
    public static final int GRID_WIDTH = 8;
    // Half the grid line width for alignment purposes
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

    // 2D array representing the grid of cells
    Cell[][] cells;

    /**
     * Constructor to create the game board by initializing all cells.
     */
    public Board() {
        // Initialize the cells array using ROWS and COLS constants from GameMain
        cells = new Cell[GameMain.ROWS][GameMain.COLS];

        // Create and initialize each cell in the grid
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col] = new Cell(row, col); // Create a new Cell object for each position
            }
        }
    }

    /**
     * Checks if the game has ended in a draw (i.e., no more EMPTY cells).
     * @return true if all cells are filled and no moves are left, otherwise false.
     */
    public boolean isDraw() {
        // Iterate through each cell in the grid
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                // If any cell is empty, the game is not a draw
                if (cells[row][col].content == Player.Empty) {
                    return false;
                }
            }
        }
        // All cells are filled, so it is a draw
        return true;
    }

    /**
     * Checks if the current player has won the game after making their move.
     * @param thePlayer The player to check for a win condition.
     * @param playerRow The row index of the player's most recent move.
     * @param playerCol The column index of the player's most recent move.
     * @return true if the player has won, otherwise false.
     */
    public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
        // Check if the player has 3 in the current row
        if (cells[playerRow][0].content == thePlayer &&
            cells[playerRow][1].content == thePlayer &&
            cells[playerRow][2].content == thePlayer) {
            return true; // Player wins by completing a row
        }

        // Check if the player has 3 in the current column
        if (cells[0][playerCol].content == thePlayer &&
            cells[1][playerCol].content == thePlayer &&
            cells[2][playerCol].content == thePlayer) {
            return true; // Player wins by completing a column
        }

        // Check if the player has 3 in the main diagonal (top-left to bottom-right)
        if (cells[0][0].content == thePlayer &&
            cells[1][1].content == thePlayer &&
            cells[2][2].content == thePlayer) {
            return true; // Player wins by completing the main diagonal
        }
        // Check if the player has 3 in the anti-diagonal (top-right to bottom-left)
        // No winning condition met, game continues

        return cells[0][2].content == thePlayer &&
                cells[1][1].content == thePlayer &&
                cells[2][0].content == thePlayer;
    }

    /**
     * Draws the game board grid and each cell's content.
     * @param g The Graphics object for drawing the board.
     */
    public void paint(Graphics g) {
        // Set the grid color to gray
        g.setColor(Color.gray);

        // Draw horizontal grid lines
        for (int row = 1; row < GameMain.ROWS; ++row) {
            g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDHT_HALF,
                    GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH); // Horizontal grid line
        }

        // Draw vertical grid lines
        for (int col = 1; col < GameMain.COLS; ++col) {
            g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                    GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
                    GRID_WIDTH, GRID_WIDTH); // Vertical grid line
        }

        // Draw each cell in the grid
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col].paint(g); // Delegate cell drawing to the Cell's paint method
            }
        }
    }
}

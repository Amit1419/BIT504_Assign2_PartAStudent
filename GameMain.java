import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {
    // Constants for game configuration
    public static final int ROWS = 3; // Number of rows in the grid
    public static final int COLS = 3; // Number of columns in the grid
    public static final String TITLE = "Tic Tac Toe"; // Title of the game

    // Constants for drawing dimensions
    public static final int CELL_SIZE = 100; // Size of each cell in pixels
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS; // Canvas width
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS; // Canvas height
    public static final int CELL_PADDING = CELL_SIZE / 6; // Padding within each cell
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // Size of the symbols (X or O)
    public static final int SYMBOL_STROKE_WIDTH = 8; // Stroke width of the symbols

    // Game components
    private Board board; // The game board object

    // Enumeration for game states
    private enum GameState {
        Playing, Draw, Cross_won, Nought_won
    }

    private GameState currentState; // Current state of the game
    private Player currentPlayer;  // Current player (X or O)
    private JLabel statusBar;      // Status bar to display messages to the user

    /**
     * Constructor to set up the UI and initialize game components
     */
    public GameMain() {
        // Add mouse listener to detect user clicks
        addMouseListener(this);

        // Set up the status bar to display messages
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        // Use BorderLayout for the panel and add the status bar to the bottom
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);

        // Adjust the preferred size of the panel to account for the status bar
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        // Initialize the game board
        board = new Board();

        // Initialize the game state and player
        initGame();
    }

    public static void main(String[] args) {
        // Run GUI code in the Event Dispatch thread for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create the main application window
                JFrame frame = new JFrame(TITLE);

                // Add the GameMain panel to the frame
                frame.add(new GameMain());

                // Set the default close operation
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.pack(); // Adjust the frame size to fit content
                frame.setLocationRelativeTo(null); // Center the frame on the screen
                frame.setVisible(true); // Make the frame visible
            }
        });
    }

    /**
     * Custom painting logic for the game panel
     */
    public void paintComponent(Graphics g) {
        // Clear the panel and set the background color to white
        super.paintComponent(g);
        setBackground(Color.WHITE);

        // Ask the game board to draw itself
        board.paint(g);

        // Update the status bar with the current game state
        if (currentState == GameState.Playing) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == Player.Cross) {
                statusBar.setText("X's Turn"); // Display message for X's turn
            } else {
                statusBar.setText("O's Turn"); // Display message for O's turn
            }
        } else if (currentState == GameState.Draw) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.Nought_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    /**
     * Initialize the game board and reset the game state
     */
    public void initGame() {
        // Clear the board by setting all cells to empty
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board.cells[row][col].content = Player.Empty;
            }
        }
        currentState = GameState.Playing; // Set the game state to playing
        currentPlayer = Player.Cross;    // X starts first
    }

    /**
     * Update the game state after a move
     * @param thePlayer The player making the move
     * @param row The row of the move
     * @param col The column of the move
     */
    public void updateGame(Player thePlayer, int row, int col) {
        // Check if the current player has won
        if (board.hasWon(thePlayer, row, col)) {
            if (thePlayer == Player.Cross) {
                currentState = GameState.Cross_won; // X wins
            } else {
                currentState = GameState.Nought_won; // O wins
            }
        } else if (board.isDraw()) {
            currentState = GameState.Draw; // Check for a draw
        }
        // Otherwise, the game continues
    }

    /**
     * Handle mouse click events to interact with the game
     */
    public void mouseClicked(MouseEvent e) {
        // Get the coordinates of the click event
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Determine the row and column clicked
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;

        if (currentState == GameState.Playing) {
            // Check if the clicked cell is within bounds and empty
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS &&
                board.cells[rowSelected][colSelected].content == Player.Empty) {
                // Place the current player's symbol
                board.cells[rowSelected][colSelected].content = currentPlayer;

                // Update the game state based on the move
                updateGame(currentPlayer, rowSelected, colSelected);

                // Switch to the other player
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
            }
        } else {
            // Game is over; restart the game
            initGame();
        }

        // Refresh the panel to reflect the new state
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Event not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Event not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Event not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Event not used
    }
}

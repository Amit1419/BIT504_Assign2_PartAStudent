import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cell {
    // Content of this cell (can be empty, cross, or nought)
    Player content;
    // Row and column indices of this cell
    int row, col;

    /** Constructor to initialize this cell with the specified row and col */
    public Cell(int row, int col) {
        // Initialize the row and column variables
        this.row = row;
        this.col = col;

        // Set the cell's content to EMPTY by default
        clear();
    }

    /** Paint itself on the graphics canvas, given the Graphics context g */
    public void paint(Graphics g) {
        // Graphics2D allows setting of pen's stroke size
        Graphics2D graphic2D = (Graphics2D) g;
        graphic2D.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Calculate the top-left position of the symbol to be drawn
        int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
        int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;

        // Draw the symbol based on the cell's content
        if (content == Player.Cross) {
            // Draw a red cross
            graphic2D.setColor(Color.RED);
            int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            graphic2D.drawLine(x1, y1, x2, y2); // Draw first diagonal line
            graphic2D.drawLine(x2, y1, x1, y2); // Draw second diagonal line
        } else if (content == Player.Nought) {
            // Draw a blue circle
            graphic2D.setColor(Color.BLUE);
            graphic2D.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);
        }
    }

    /** Set this cell's content to EMPTY */
    public void clear() {
        // Set the cell content to Player.Empty, indicating it's unoccupied
        content = Player.Empty;
    }
}

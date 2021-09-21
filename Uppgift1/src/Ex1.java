import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import java.util.Random;


public class Ex1 {
    private static final int WIDTH = 800;  // Size of the window in pixels
    private static final int HEIGHT = 800;
    
    static int cells=20;    // The size of the maze is cells*cells (default is 20*20)
    
    public static void main(String[] args) {
	
	// Get the size of the maze from the command line
	if (args.length > 0) {
	    try {
		cells = Integer.parseInt(args[0]);  // The maze is of size cells*cells
	    } catch (NumberFormatException e) {
		System.err.println("Argument " + args[0] + " should be an integer");
		System.exit(-1);
	    }
	}
	// Check that the size is valid
	if ( (cells <= 1) || (cells > 100) ) {
	    System.err.println("Invalid size, must be between 2 and 100 ");
	    System.exit(-1);
	}
        Runnable r = new Runnable() {
		public void run() {
		    // Create a JComponent for the maze
		    MazeComponent mazeComponent = new MazeComponent(WIDTH, HEIGHT, cells);
		    // Change the text of the OK button to "Close"
		    UIManager.put("OptionPane.okButtonText", "Close");
		    JOptionPane.showMessageDialog(null, mazeComponent, "Maze " + cells + " by " + cells,
						  JOptionPane.INFORMATION_MESSAGE);
		}
	    };
        SwingUtilities.invokeLater(r);
    }   
}

class MazeComponent extends JComponent {
    protected int width;
    protected int height;
    protected int cells;
    protected int cellWidth;
    protected int cellHeight;
    Random random;

    // Draw a maze of size w*h with c*c cells
    MazeComponent(int w, int h, int c) {
        super();
        cells = c;                // Number of cells
	cellWidth = w/cells;      // Width of a cell
	cellHeight = h/cells;     // Height of a cell
	width =  c*cellWidth;     // Calculate exact dimensions of the component
	height = c*cellHeight;
	setPreferredSize(new Dimension(width+1,height+1));  // Add 1 pixel for the border
    }
    
    public void paintComponent(Graphics g) {
	g.setColor(Color.yellow);                    // Yellow background
	g.fillRect(0, 0, width, height);
	
	// Draw a grid of cells
	g.setColor(Color.blue);                 // Blue lines
	for (int i = 0; i<=cells; i++) {        // Draw horizontal grid lines
	    g.drawLine (0, i*cellHeight, cells*cellWidth, i*cellHeight);
	}
	for (int j = 0; j<=cells; j++) {       // Draw verical grid lines
	    g.drawLine (j*cellWidth, 0, j*cellWidth, cells*cellHeight);
	}

	// Mark entry and exit cells
	paintCell(0,0,Color.green, g);               // Mark entry cell
	drawWall(-1, 0, 2, g);                       // Open up entry cell
	paintCell(cells-1, cells-1,Color.pink, g);   // Mark exit cell
	drawWall(cells-1, cells-1, 2, g);            // Open up exit cell
	
	g.setColor(Color.yellow);                 // Use yellow lines to remove existing walls
	createMaze(cells, g);
    }

    private void createMaze (int cells, Graphics g) {
	
	// This is what you write
	
    }


    // Paints the interior of the cell at postion x,y with colour c
    private void paintCell(int x, int y, Color c, Graphics g) {
	int xpos = x*cellWidth;    // Position in pixel coordinates
	int ypos = y*cellHeight;
	g.setColor(c);
	g.fillRect(xpos+1, ypos+1, cellWidth-1, cellHeight-1);
    }

    
    // Draw the wall w in cell (x,y) (0=left, 1=up, 2=right, 3=down)
    private void drawWall(int x, int y, int w, Graphics g) {
	int xpos = x*cellWidth;    // Position in pixel coordinates
	int ypos = y*cellHeight;
	
	switch(w){
	case (0):       // Wall to the left
	    g.drawLine(xpos, ypos+1, xpos, ypos+cellHeight-1);
	    break;
	case (1):       // Wall at top
	    g.drawLine(xpos+1, ypos, xpos+cellWidth-1, ypos);
	    break;
	case (2):      // Wall to the right
	    g.drawLine(xpos+cellWidth, ypos+1, xpos+cellWidth, ypos+cellHeight-1);
	    break;
	case (3):      // Wall at bottom
	    g.drawLine(xpos+1, ypos+cellHeight, xpos+cellWidth-1, ypos+cellHeight);
	    break;
	}
    }
}

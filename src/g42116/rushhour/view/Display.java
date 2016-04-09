package g42116.rushhour.view;

import g42116.rushhour.model.Board;
import g42116.rushhour.model.Car;
import g42116.rushhour.model.Position;
import static g42116.rushhour.model.Orientation.*;
import static g42116.rushhour.view.Colour.*;

/**
 * This class serves the purpose of displaying the game board.
 * 
 * @author john
 */
public class Display {
    
    /**
     * Main method to test the display.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        Board board = new Board();
        Car redCar = new Car('1', 2, HORIZONTAL, new Position(2,0));
        Car car1 = new Car('2', 3, VERTICAL, new Position(2,2));
        Car car2 = new Car('3', 3, VERTICAL, new Position(2,4));
        
        board.put(redCar);
        if (board.canPut(car1)) board.put(car1);
        if (board.canPut(car2)) board.put(car2);
        
        displayBoard(board);
    }
    
    /**
     * Displays lines of characters representing the game board in the terminal.
     * 
     * @param   board   the board to display
     */
    public static void displayBoard(Board board) {
        printLineOfDashes(board.width());
        
        for (int i = 0; i < board.height()  ; i++) {
            printRow(board, i);
        }
        printLineOfDashes(board.width());
        System.out.print("\n");
    }
    
    /**
     * Prints a carriage return followed by a line of dashes.
     * 
     * @param   number  the number of dashes printed
     */
    private static void printLineOfDashes(int number) {
        String line = "\n\u0020";
        for (int i = 0; i < number; i++) {
            line += "-";
        }
        line += "\u0020";
        System.out.print(ColourString.to(line, GREEN, WHITE));
    }
    
    /**
     * Prints a carriage return followed by a game board row in the terminal as 
     * follows:
     *  - first a pipe character (or a cross if row's first box is the exit) ;
     *  - followed by dots for empty boxes or the car's id if the box is
     *    occupied ;
     *  - finally a pipe character (or a cross if row's last box  the exit).
     * 
     * @param   board       the board being printed
     * @param   currentRow  the row being printed
     */
    private static void printRow(Board board, int currentRow) {
        String row = "\n";
        Position boardSquare;
        int exitColumn = board.getExit().getColumn();
        int exitRow = board.getExit().getRow();
        
        if((exitColumn == 0) && (exitRow == currentRow)) {
            row += ColourString.to("x", GREEN, WHITE);
        } else {
            row += ColourString.to("|", GREEN, WHITE);
        }

        for (int i = 0; i < board.width(); i++) {
            boardSquare = new Position(currentRow, i);
            
            if (board.getCarAt(boardSquare) == null) {
                row += ColourString.to("\u0020", YELLOW, BLACK);
            } else {
                row += ColourString.to(""+board.getCarAt(boardSquare).getId(), RED, WHITE);
            }
        }
        
        if((exitColumn == board.width()-1) && (exitRow == currentRow)) {
            row += ColourString.to("x", GREEN, RED);
        } else {
            row += ColourString.to("|", GREEN, WHITE);
        }
        
        System.out.print(row);
    }
}

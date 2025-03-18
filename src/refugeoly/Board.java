package refugeoly;

import java.util.ArrayList;

public class Board extends Square {
    
    private static ArrayList<Square> squares = new ArrayList<Square>();
    private Square square = new Square();
    
    public Square getSquare(int number) {
        return squares.get(number);
    }
    
    public void setSquare(int number, String text, String description, Action action) {
        square.setNumber(number);
        square.setText(text);
        square.setDescription(description);
        square.addAction(action);
        
        squares.add(square);
    }
    
    public void addSquare(Square square) {
        this.square = square;
        squares.add(square);
    }
    
    public void printBoard(int j) {
        Square square = squares.get(j);
        System.out.println(square.getText());
        System.out.println(square.getDescription());
    }
}
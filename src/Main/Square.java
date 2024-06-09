package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Square {
    public final JPanel component;
    public final Board board;
    public Piece piece = null;

    public final int row;
    public final int column;
    public final boolean isEven;

    private static final Color SQUARE_EVEN_COLOR = new Color(242, 225, 195);
    private static final Color SQUARE_SELECTED_EVEN_COLOR = new Color(249, 240, 123);
    private static final Color SQUARE_ODD_COLOR = new Color(195, 160, 130);
    private static final Color SQUARE_SELECTED_ODD_COLOR = new Color(226, 207, 89);

    public Square(Board board, int row, int column) {
        this.board = board;
        this.row = row;
        this.column = column;
        isEven = (row + column) % 2 == 0;

        component = new JPanel(new BorderLayout());
        component.setPreferredSize(new Dimension(board.squareSize, board.squareSize));
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                board.select(Square.this);
            }
        });
        board.component.add(component);
    }

    public void update() {
        boolean selected = board.selectedSquare == this;
        component.setBackground(isEven
                ? selected ? SQUARE_SELECTED_EVEN_COLOR : SQUARE_EVEN_COLOR
                : selected ? SQUARE_SELECTED_ODD_COLOR : SQUARE_ODD_COLOR);
    }
}
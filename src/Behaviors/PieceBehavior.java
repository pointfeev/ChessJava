package Behaviors;

import Main.Board;
import Main.Piece;
import Main.Square;

import javax.swing.*;
import java.util.HashSet;
import java.util.Iterator;

public abstract class PieceBehavior {
    public final Piece piece;
    public final Icon icon;

    public PieceBehavior(Piece piece) {
        this.piece = piece;

        icon = piece.square.board.window.getIcon(piece.color.name() + "-" + piece.type.name(), piece.square.board.squareSize);
    }

    public abstract HashSet<Square> getValidSquares();

    public static Iterator<Square> iterateSquaresInDirection(Square fromSquare, int rowDirection, int columnDirection, int maxSquares) {
        Board board = fromSquare.board;
        return new Iterator<>() {
            private int index = 0;

            private int getRow() {
                return fromSquare.row + index * rowDirection;
            }

            private int getColumn() {
                return fromSquare.column + index * columnDirection;
            }

            @Override
            public boolean hasNext() {
                if (maxSquares > 0 && index >= maxSquares) {
                    return false;
                }
                index++;
                if (board.getSquare(getRow(), getColumn()) == null) {
                    index--;
                    return false;
                }
                index--;
                return true;
            }

            @Override
            public Square next() {
                index++;
                return board.getSquare(getRow(), getColumn());
            }
        };
    }
}
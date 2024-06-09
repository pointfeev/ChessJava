package Behaviors;

import Enums.PieceColor;
import Main.Board;
import Main.Piece;
import Main.Square;

import java.util.HashSet;
import java.util.Iterator;

public class PawnBehavior extends PieceBehavior {
    public PawnBehavior(Piece piece) {
        super(piece);
    }

    private static final int[] distances = {-1, 1};

    @Override
    public HashSet<Square> getValidSquares() {
        HashSet<Square> validSquares = new HashSet<>();

        Square square = piece.square;
        Board board = square.board;
        boolean increasing = piece.color == PieceColor.BLACK;

        Iterator<Square> squaresInDirection = iterateSquaresInDirection(square,
                increasing ? 1 : -1, 0, piece.hasMoved ? 1 : 2);
        while (squaresInDirection.hasNext()) {
            Square toSquare = squaresInDirection.next();
            Piece toPiece = toSquare.piece;
            if (toPiece != null) {
                break;
            }
            validSquares.add(toSquare);
        }

        int row = square.row + (increasing ? 1 : -1);
        for (int distance : distances) {
            Square toSquare = board.getSquare(row, square.column + distance);
            if (toSquare != null) {
                Piece toPiece = toSquare.piece;
                if (toPiece != null && toPiece.color != piece.color) {
                    validSquares.add(toSquare);
                }
            }
        }

        return validSquares;
    }
}
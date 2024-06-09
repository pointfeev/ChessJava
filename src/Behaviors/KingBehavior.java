package Behaviors;

import Main.Board;
import Main.Piece;
import Main.Square;

import java.util.HashSet;

public class KingBehavior extends PieceBehavior {
    public KingBehavior(Piece piece) {
        super(piece);
    }

    private static final int[][] distances = {
            {1, 0}, {0, 1}, {-1, 0}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    @Override
    public HashSet<Square> getValidSquares() {
        HashSet<Square> validSquares = new HashSet<>();

        Square square = piece.square;
        Board board = square.board;

        for (int[] distance : distances) {
            Square toSquare = board.getSquare(square.row + distance[0], square.column + distance[1]);
            if (toSquare != null) {
                Piece toPiece = toSquare.piece;
                if (toPiece == null || toPiece.color != piece.color) {
                    validSquares.add(toSquare); // TODO: do not let the king move into a spot where it would be in check
                }
            }
        }

        return validSquares;
    }
}
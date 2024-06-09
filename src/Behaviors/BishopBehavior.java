package Behaviors;

import Main.Piece;
import Main.Square;

import java.util.HashSet;
import java.util.Iterator;

public class BishopBehavior extends PieceBehavior {
    public BishopBehavior(Piece piece) {
        super(piece);
    }

    private static final int[][] directions = {
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    @Override
    public HashSet<Square> getValidSquares() {
        HashSet<Square> validSquares = new HashSet<>();

        Square square = piece.square;
        for (int[] direction : directions) {
            Iterator<Square> squaresInDirection = iterateSquaresInDirection(square,
                    direction[0], direction[1], -1);
            while (squaresInDirection.hasNext()) {
                Square toSquare = squaresInDirection.next();
                Piece toPiece = toSquare.piece;
                if (toPiece != null) {
                    if (toPiece.color != piece.color) {
                        validSquares.add(toSquare);
                    }
                    break;
                }
                validSquares.add(toSquare);
            }
        }

        return validSquares;
    }
}
package Main;

import Enums.PieceColor;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

import static Enums.PieceColor.BLACK;
import static Enums.PieceColor.WHITE;
import static Enums.PieceType.*;

public class Board {
    public final JPanel component;
    public final Window window;
    private final Square[][] squares;

    public int turn;

    public final int squareSize;

    private final HashMap<Square, HashSet<Square>> validSquaresCache;

    public Square selectedSquare = null;
    private final HashMap<Square, HashSet<Component>> hintComponents;
    private final Icon hintMoveIcon;
    private final Icon hintCaptureIcon;


    public Board(Window window) {
        this.window = window;

        component = new JPanel(new GridLayout(8, 8));
        window.component.getContentPane().add(component);

        squareSize = Math.min(150, window.getMaxBoardSize() / 8);
        squares = new Square[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c] = new Square(this, r, c);
            }
        }

        validSquaresCache = new HashMap<>();

        hintComponents = new HashMap<>();
        hintMoveIcon = window.getIcon("HINT-MOVE", squareSize);
        hintCaptureIcon = window.getIcon("HINT-CAPTURE", squareSize);

        reset();
    }

    public void reset() {
        for (int i = 0; i < 8; i += 7) {
            PieceColor color = i == 0 ? BLACK : WHITE;
            squares[i][0].piece = new Piece(squares[i][0], color, ROOK);
            squares[i][1].piece = new Piece(squares[i][1], color, KNIGHT);
            squares[i][2].piece = new Piece(squares[i][2], color, BISHOP);
            squares[i][3].piece = new Piece(squares[i][3], color, QUEEN);
            squares[i][4].piece = new Piece(squares[i][4], color, KING);
            squares[i][5].piece = new Piece(squares[i][5], color, BISHOP);
            squares[i][6].piece = new Piece(squares[i][6], color, KNIGHT);
            squares[i][7].piece = new Piece(squares[i][7], color, ROOK);
        }
        for (int i = 0; i < 8; i++) {
            squares[1][i].piece = new Piece(squares[1][i], BLACK, PAWN);
            squares[6][i].piece = new Piece(squares[6][i], WHITE, PAWN);
        }
        for (int r = 2; r < 6; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c].piece = null;
            }
        }
        turn = 0;
        hint(null);
        update();
    }

    public void update() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c].update();
            }
        }
    }

    public Square getSquare(int row, int column) {
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            return null;
        }
        return squares[row][column];
    }

    private boolean canSelect(Square square) {
        Piece piece = square.piece;
        return piece != null && (turn % 2 == 0 ? piece.color == WHITE : piece.color == BLACK);
    }

    public void select(Square square) {
        Square currentSquare = selectedSquare;
        if (currentSquare != null) {
            if (currentSquare == square || move(currentSquare, square) || !canSelect(square)) {
                selectedSquare = null;
            } else {
                selectedSquare = square;
            }
            currentSquare.update();
        } else if (canSelect(square)) {
            selectedSquare = square;
        }
        square.update();
        hint(selectedSquare);
    }

    public HashSet<Square> getCachedValidSquares(Square fromSquare) {
        HashSet<Square> validSquares = validSquaresCache.get(fromSquare);
        if (validSquares != null) {
            return validSquares;
        }
        validSquares = fromSquare.piece.behavior.getValidSquares();
        validSquaresCache.put(fromSquare, validSquares);
        return validSquares;
    }

    public void hint(Square fromSquare) {
        hintComponents.forEach((square, components) -> {
            components.forEach(component -> {
                square.component.remove(component);
                square.component.repaint();
            });
            components.clear();
        });
        hintComponents.clear();
        if (fromSquare == null) {
            return;
        }
        Piece piece = fromSquare.piece;
        if (piece == null) {
            return;
        }
        HashSet<Square> validSquares = getCachedValidSquares(fromSquare);
        for (Square toSquare : validSquares) {
            JLabel hintComponent = new JLabel(toSquare.piece != null ? hintCaptureIcon : hintMoveIcon);
            hintComponents.computeIfAbsent(toSquare, _ -> new HashSet<>()).add(hintComponent);
            toSquare.component.add(hintComponent);
            toSquare.component.validate();
        }
    }

    public boolean move(Square fromSquare, Square toSquare) {
        Piece piece = fromSquare.piece;
        if (piece != null && getCachedValidSquares(fromSquare).contains(toSquare)) {
            piece.move(toSquare);
            validSquaresCache.clear();
            turn++;
            return true;
        }
        return false;
    }
}
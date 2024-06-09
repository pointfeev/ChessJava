package Main;

import Behaviors.*;
import Enums.PieceColor;
import Enums.PieceType;

import javax.swing.*;

public class Piece {
    public JLabel component;
    public Square square;

    public final PieceColor color;
    public PieceType type;

    public PieceBehavior behavior;
    public boolean hasMoved = false;

    public Piece(Square square, PieceColor color, PieceType type) {
        this.square = square;
        this.color = color;

        setType(type);

        component = new JLabel(behavior.icon);
        square.component.add(component);
    }

    public void setType(PieceType type) {
        this.type = type;

        behavior = switch (type) {
            case KING -> new KingBehavior(this);
            case QUEEN -> new QueenBehavior(this);
            case ROOK -> new RookBehavior(this);
            case BISHOP -> new BishopBehavior(this);
            case KNIGHT -> new KnightBehavior(this);
            case PAWN -> new PawnBehavior(this);
        };
    }

    public void move(Square toSquare) {
        square.piece = null;
        square.component.remove(component);

        square = toSquare;
        Piece toPiece = square.piece;
        if (toPiece != null) {
            toSquare.component.remove(toPiece.component);
        }

        square.piece = this;
        square.component.add(component);

        hasMoved = true;
    }
}
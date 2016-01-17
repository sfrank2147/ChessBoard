package com.samuel_frank.chessboard;

/**
 * Created by smf2147 on 1/2/16.
 */
public class Piece {
    public Piece(PlayerColor color, Type type) {
        this.setColor(color);
        this.setType(type);
        this.setHasMoved(false);
    }

    // Copy constructor.
    public Piece(Piece otherPiece) {
        this.setColor(otherPiece.getColor());
        this.setType(otherPiece.getType());
        this.setHasMoved(otherPiece.hasMoved());
    }

    public enum Type {ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN};

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    private PlayerColor color;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private Type type;

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    private boolean hasMoved;
}

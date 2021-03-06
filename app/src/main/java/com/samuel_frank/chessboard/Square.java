package com.samuel_frank.chessboard;

/**
 * Created by smf2147 on 1/5/16.
 */
public class Square {
    public Square(char col, int row) {
        setRow(row);
        setCol(col);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private void initializePrivateMembers() {
        this.piece = null;
        this.board = null;
    }

    private Piece piece;
    private Board board;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public char getCol() {
        return col;
    }

    public void setCol(char col) {
        this.col = col;
    }

    private int row;
    private char col;
}

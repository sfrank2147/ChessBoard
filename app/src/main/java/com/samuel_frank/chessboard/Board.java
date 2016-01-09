package com.samuel_frank.chessboard;

import android.content.Context;

/**
 * Created by smf2147 on 1/5/16.
 */
public class Board {
    public Board() {
        initializeSquares();
        this.currentPlayer = PlayerColor.WHITE;
    }

    private PlayerColor currentPlayer;

    private static final String TAG = "Board";

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;

    private Square[][] squares;

    private void initializeSquares() {
        this.squares = new Square[8][8];
        for (char row = 'a'; row < 'i'; row++) {
            for (int col = 1; col < 9; col++) {
                Square curSquare = new Square(row, col);
                curSquare.setBoard(this);
                this.squares[row - 'a'][col - 1] = curSquare;
            }
        }
        getSquare('a', 1).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.ROOK));
        getSquare('b', 1).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.KNIGHT));
        getSquare('c', 1).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.BISHOP));
        getSquare('d', 1).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.QUEEN));
        getSquare('e', 1).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.KING));
        getSquare('f', 1).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.BISHOP));
        getSquare('g', 1).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.KNIGHT));
        getSquare('h', 1).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.ROOK));

        getSquare('a', 2).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.PAWN));
        getSquare('b', 2).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.PAWN));
        getSquare('c', 2).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.PAWN));
        getSquare('d', 2).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.PAWN));
        getSquare('e', 2).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.PAWN));
        getSquare('f', 2).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.PAWN));
        getSquare('g', 2).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.PAWN));
        getSquare('h', 2).setPiece(new Piece(PlayerColor.WHITE, Piece.Type.PAWN));

        getSquare('a', 8).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.ROOK));
        getSquare('b', 8).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.KNIGHT));
        getSquare('c', 8).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.BISHOP));
        getSquare('d', 8).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.QUEEN));
        getSquare('e', 8).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.KING));
        getSquare('f', 8).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.BISHOP));
        getSquare('g', 8).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.KNIGHT));
        getSquare('h', 8).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.ROOK));

        getSquare('a', 7).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.PAWN));
        getSquare('b', 7).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.PAWN));
        getSquare('c', 7).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.PAWN));
        getSquare('d', 7).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.PAWN));
        getSquare('e', 7).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.PAWN));
        getSquare('f', 7).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.PAWN));
        getSquare('g', 7).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.PAWN));
        getSquare('h', 7).setPiece(new Piece(PlayerColor.BLACK, Piece.Type.PAWN));

    }

    Square getSquare(char col, int row) {
        return this.squares[row - 1][col - 'a'];
    }

    public void move(Square origin, Square destination) {
        destination.setPiece(origin.getPiece());
        origin.setPiece(null);
    }
}

package com.samuel_frank.chessboard;

import android.content.Context;

import java.util.Set;

/**
 * Created by smf2147 on 1/5/16.
 */
public class Board {

    private class Coordinates {
        Coordinates(char col, int row) {
            this.col = col;
            this.row = row;
        }

        char col;
        int row;
    }

    private Coordinates getCoordinates(Square sq) {
        return new Coordinates(sq.getCol(), sq.getRow());
    }

    public Board() {
        initializeSquares();
        this.currentPlayer = PlayerColor.WHITE;
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerColor currentPlayer) {
        this.currentPlayer = currentPlayer;
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
        for (char col = 'a'; col < 'i'; col++) {
            for (int row = 1; row < 9; row++) {
                Square curSquare = new Square(col, row);
                curSquare.setBoard(this);
                this.squares[row - 1][col - 'a'] = curSquare;
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

    private boolean isValidMoveForQueen(Square origin, Square destination) {
        Coordinates originCoords = getCoordinates(origin);
        Coordinates destCoords = getCoordinates(destination);
        // If origin and destination in the same row, make sure no pieces in between.
        if (sameRow(originCoords, destCoords)) {
            return rowClear(originCoords, destCoords);
        }
        if (sameCol(originCoords, destCoords)) {
            return colClear(originCoords, destCoords);
        }
        if (sameDiag(originCoords, destCoords)) {
            return diagClear(originCoords, destCoords);
        }
        return false;
    }

    private boolean sameRow(Coordinates a, Coordinates b) {
        return a.row == b.row;
    }

    private boolean rowClear(Coordinates a, Coordinates b) {
        int row = a.row;  // Same for both
        char leftCol = a.col < b.col ? a.col : b.col;
        char rightCol = a.col < b.col ? b.col : a.col;
        for (char col = (char) (leftCol + 1); col < rightCol; col++) {
            if (getSquare(col, row).getPiece() != null) {
                return false;
            }
        }
        return true;
    }

    private boolean sameCol(Coordinates a, Coordinates b) {
        return a.col == b.col;
    }

    private boolean colClear(Coordinates a, Coordinates b) {
        char col = a.col;
        int bottomRow = a.row < b.row ? a.row : b.row;
        int topRow = a.row < b.row ? b.row : a.row;
        for (int row = bottomRow + 1; row < topRow; row++) {
            if (getSquare(col, row).getPiece() != null) {
                return false;
            }
        }
        return true;
    }

    private boolean sameDiag(Coordinates a, Coordinates b) {
        return Math.abs(a.row - b.row) == Math.abs(a.col - b.col);
    }

    private boolean diagClear(Coordinates a, Coordinates b) {
        // Diagonal could go up-right or up-down, WLOG
        // Pick the left point, determine if it goes up or down.
        Coordinates leftCoordinates = a.col < b.col ? a : b;
        Coordinates rightCoordinates = a.col < b.col ? b : a;
        boolean upRight = leftCoordinates.row < rightCoordinates.row;
        char startingCol = leftCoordinates.col;
        int rowIncrement = upRight ? 1 : -1;
        Coordinates coords = leftCoordinates;
        // Don't start with the initial point.
        coords.col++;
        coords.row += rowIncrement;
        while (coords.col < rightCoordinates.col) {
            if (getSquare(coords.col, coords.row).getPiece() != null) {
                return false;
            }
            coords.col++;
            coords.row += rowIncrement;
        }
        return true;
    }

    private boolean isValidMove(Square origin, Square destination) {
        Piece piece = origin.getPiece();
        if (piece == null) {
            return false;
        }
        Coordinates startCoords = getCoordinates(origin);
        Coordinates destCoords = getCoordinates(destination);
        // Can't start and end at the same place.
        if (startCoords.row == destCoords.row && startCoords.col == destCoords.col) {
            return false;
        }
        if (destCoords.col < 'a' || destCoords.col > 'h'
                || destCoords.row < 1 || destCoords.row > 8) {
            return false;
        }
        switch (piece.getType()) {
            case QUEEN:
                return isValidMoveForQueen(origin, destination);
            default:
                return true;
        }
    }

    Square getSquare(char col, int row) {
        return this.squares[row - 1][col - 'a'];
    }

    public boolean move(Square origin, Square destination) {
        if (origin.getPiece() == null || origin.getPiece().getColor() != this.getCurrentPlayer()) {
            return false;  // Have to move your own piece.
        }
        if (destination.getPiece() != null &&
                destination.getPiece().getColor() == this.getCurrentPlayer()) {
            return false;  // Can't take your own piece.
        }
        if (isValidMove(origin, destination)) {
            destination.setPiece(origin.getPiece());
            this.setCurrentPlayer(
                    currentPlayer == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE);
            origin.setPiece(null);
            return true;
        } else {
            return false;
        }
    }
}

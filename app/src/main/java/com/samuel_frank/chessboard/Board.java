package com.samuel_frank.chessboard;

import android.content.Context;

import java.util.HashSet;
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

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (getClass() != other.getClass()) {
                return false;
            }
            Coordinates otherCoords = (Coordinates) other;
            return this.col == otherCoords.col && this.row == otherCoords.row;
        }

        @Override
        public int hashCode() {
            return 8*row + (int) (col - 'a');
        }
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
        Coordinates destCoords = getCoordinates(destination);
        return getRowAndColumnCoordinates(origin).contains(destCoords) ||
                getDiagonalCoordinates(origin).contains(destCoords);
    }

    private HashSet<Coordinates> getRowAndColumnCoordinates(Square origin) {
        Coordinates startCoords = getCoordinates(origin);
        HashSet<Coordinates> coordSet = new HashSet<Coordinates>();
        // Need to be able to compare color to the moving piece
        if (origin.getPiece() == null) {
            return coordSet;
        }
        // Get coords on the same row to the right.
        for (char col = (char) (startCoords.col + 1); col < 'i'; col++) {
            Piece piece  = getSquare(col, startCoords.row).getPiece();
            if (piece == null) {
                coordSet.add(new Coordinates(col, startCoords.row));
            } else {
                if (piece.getColor() != origin.getPiece().getColor()) {
                    coordSet.add(new Coordinates(col, startCoords.row));
                }
                break;
            }
        }
        // Get coords on the same row to the left.
        for (char col = (char) (startCoords.col - 1); col > (char) ('a' - 1); col--) {
            Piece piece = getSquare(col, startCoords.row).getPiece();
            if (piece == null) {
                coordSet.add(new Coordinates(col, startCoords.row));
            } else {
                if (piece.getColor() != origin.getPiece().getColor()) {
                    coordSet.add(new Coordinates(col, startCoords.row));
                }
                break;
            }
        }
        // Get coords on the same column above.
        for (int row = startCoords.row + 1; row < 9; row++) {
            Piece piece  = getSquare(startCoords.col, row).getPiece();
            if (piece == null) {
                coordSet.add(new Coordinates(startCoords.col, row));
            } else {
                if (piece.getColor() != origin.getPiece().getColor()) {
                    coordSet.add(new Coordinates(startCoords.col, row));
                }
                break;
            }
        }
        // Get coords on the same column below.
        for (int row = startCoords.row - 1; row > 0; row--) {
            Piece piece  = getSquare(startCoords.col, row).getPiece();
            if (piece == null) {
                coordSet.add(new Coordinates(startCoords.col, row));
            } else {
                if (piece.getColor() != origin.getPiece().getColor()) {
                    coordSet.add(new Coordinates(startCoords.col, row));
                }
                break;
            }
        }
        return coordSet;
    }

    private HashSet<Coordinates> getDiagonalCoordinates(Square origin) {
        Coordinates startCoords = getCoordinates(origin);
        HashSet<Coordinates> coordSet = new HashSet<Coordinates>();
        // Need to be able to compare color to the moving piece
        if (origin.getPiece() == null) {
            return coordSet;
        }
        // Diagonal to the upper right.
        for (int offset = 1;
             (char) (startCoords.col + offset) < 'i' && startCoords.row + offset < 9;
                offset++) {
            char col = (char) (startCoords.col + offset);
            int row = startCoords.row + offset;
            Piece piece = getSquare(col, row).getPiece();
            if (piece == null) {
                coordSet.add(new Coordinates(col, row));
            } else {
                if (piece.getColor() != origin.getPiece().getColor()) {
                    coordSet.add(new Coordinates(col, row));
                }
                break;
            }
        }
        // Diagonal to the lower left.
        for (int offset = 1;
             (char) (startCoords.col - offset) > (char) ('a' - 1) && startCoords.row - offset > 0;
             offset++) {
            char col = (char) (startCoords.col - offset);
            int row = startCoords.row - offset;
            Piece piece = getSquare(col, row).getPiece();
            if (piece == null) {
                coordSet.add(new Coordinates(col, row));
            } else {
                if (piece.getColor() != origin.getPiece().getColor()) {
                    coordSet.add(new Coordinates(col, row));
                }
                break;
            }
        }
        // Diagonal to the upper left.
        for (int offset = 1;
             (char) (startCoords.col - offset) > (char) ('a' - 1) && startCoords.row + offset < 9;
             offset++) {
            char col = (char) (startCoords.col - offset);
            int row = startCoords.row + offset;
            Piece piece = getSquare(col, row).getPiece();
            if (piece == null) {
                coordSet.add(new Coordinates(col, row));
            } else {
                if (piece.getColor() != origin.getPiece().getColor()) {
                    coordSet.add(new Coordinates(col, row));
                }
                break;
            }
        }
        // Diagonal to the lower right.
        for (int offset = 1;
             (char) (startCoords.col + offset) < 'i' && startCoords.row - offset > 0;
             offset++) {
            char col = (char) (startCoords.col + offset);
            int row = startCoords.row - offset;
            Piece piece = getSquare(col, row).getPiece();
            if (piece == null) {
                coordSet.add(new Coordinates(col, row));
            } else {
                if (piece.getColor() != origin.getPiece().getColor()) {
                    coordSet.add(new Coordinates(col, row));
                }
                break;
            }
        }


        return coordSet;
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

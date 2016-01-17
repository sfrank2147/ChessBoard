package com.samuel_frank.chessboard;

import android.content.Context;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

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

    private class Move {
        public Move(Square origin, Square destination, boolean isCastle, boolean isEnPassant) {
            if (origin.getPiece() != null) {
                setMovingPiece(new Piece(origin.getPiece()));
            } else {
                setMovingPiece(null);
            }

            if (destination.getPiece() != null) {
                setTakenPiece(new Piece(destination.getPiece()));
            } else {
                setTakenPiece(null);
            }
            setStartCoordinates(getCoordinates(origin));
            setEndCoordinates(getCoordinates(destination));
            setIsCastle(isCastle);
            setIsEnPassant(isEnPassant);
        }

        public Piece getMovingPiece() {
            return movingPiece;
        }

        public void setMovingPiece(Piece movingPiece) {
            this.movingPiece = movingPiece;
        }

        public Piece getTakenPiece() {
            return takenPiece;
        }

        public void setTakenPiece(Piece takenPiece) {
            this.takenPiece = takenPiece;
        }

        public Coordinates getStartCoordinates() {
            return startCoordinates;
        }

        public void setStartCoordinates(Coordinates startCoordinates) {
            this.startCoordinates = startCoordinates;
        }

        public Coordinates getEndCoordinates() {
            return endCoordinates;
        }

        public void setEndCoordinates(Coordinates endCoordinates) {
            this.endCoordinates = endCoordinates;
        }

        public boolean isCastle() {
            return isCastle;
        }

        public void setIsCastle(boolean isCastle) {
            this.isCastle = isCastle;
        }

        public boolean isEnPassant() {
            return isEnPassant;
        }

        public void setIsEnPassant(boolean isEnPassant) {
            this.isEnPassant = isEnPassant;
        }

        Piece movingPiece;
        Piece takenPiece;
        Coordinates startCoordinates;
        Coordinates endCoordinates;
        boolean isCastle;
        boolean isEnPassant;
    }

    private Coordinates getCoordinates(Square sq) {
        return new Coordinates(sq.getCol(), sq.getRow());
    }

    public Board() {
        initializeSquares();
        this.currentPlayer = PlayerColor.WHITE;
        this.moveStack = new Stack<>();
    }

    // Copy constructor.
    public Board(Board otherBoard) {
        this();
        // Copy the current player and pieces.
        this.currentPlayer = otherBoard.currentPlayer;
        for (char col = 'a'; col < 'i'; col++) {
            for (int row = 1; row < 8; row++) {
                Piece otherPiece = otherBoard.getSquare(col, row).getPiece();
                if (otherPiece != null) {
                    Piece thisPiece = new Piece(otherPiece);
                    getSquare(col, row).setPiece(thisPiece);
                } else {
                    getSquare(col, row).setPiece(null);
                }
            }
        }
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerColor currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    private PlayerColor currentPlayer;

    private static final String TAG = "Board";

    private Square[][] squares;

    private Stack<Move> moveStack;

    public void recordMove(Move move) {
        moveStack.push(move);
    }

    public Move peekLastMove() {
        if (moveStack.size() == 0) {
            return null;
        }
        return moveStack.peek();
    }

    public Move popMove() {
        return moveStack.pop();
    }

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

    private HashSet<Coordinates> getValidCoordinatesForMove(Square origin, boolean includeCastle) {
        Piece piece = origin.getPiece();
        if (piece == null) {
            return new HashSet<Coordinates>();
        }
        switch (piece.getType()) {
            case QUEEN:
                return getQueenCoordinates(origin);
            case BISHOP:
                return getDiagonalCoordinates(origin);
            case ROOK:
                return getRowAndColumnCoordinates(origin);
            case KNIGHT:
                return getKnightCoordinates(origin);
            case KING:
                return getKingCoordinates(origin, includeCastle);
            case PAWN:
                return getPawnCoordinates(origin);
            default:
                return new HashSet<>();
        }
    }

    private HashSet<Coordinates> getPawnCoordinates(Square origin) {
        HashSet<Coordinates> coords = new HashSet<Coordinates>();
        if (origin.getPiece() == null) {
            return coords;
        }
        // Pawns should never be in rows 1 or 8.
        if (origin.getRow() == 1 || origin.getRow() == 8) {
            return coords;
        }
        int direction = origin.getPiece().getColor() == PlayerColor.WHITE ? 1 : -1;
        Coordinates startCoords = getCoordinates(origin);
        // First, check if the pawn can go one or two steps forward.
        Coordinates oneStepForward = new Coordinates(startCoords.col, startCoords.row + direction);
        if (oneStepForward.row >= 1 &&
                oneStepForward.row <= 8 &&
                getSquare(oneStepForward.col, oneStepForward.row).getPiece() == null) {
            coords.add(oneStepForward);
            // If the pawn can go one step forward, can it go two steps forward?
            Coordinates twoStepsForward = new Coordinates(
                    startCoords.col, startCoords.row + 2 * direction);
            if (twoStepsForward.row >= 1 &&
                    twoStepsForward.row <= 8 &&
                    getSquare(twoStepsForward.col, twoStepsForward.row).getPiece() == null &&
                    // The pawn can only move two steps forward if it's in its starting position.
                    (startCoords.row ==
                            (origin.getPiece().getColor() == PlayerColor.WHITE ? 2 : 7))) {
                coords.add(twoStepsForward);
            }
        }
        // Can the pawn attack diagonally?
        if (startCoords.col != 'a') {
            Coordinates leftDiagonalCoordinates = new Coordinates(
                    (char) (startCoords.col - 1), startCoords.row + direction);
            Piece attackedPiece =
                    getSquare(leftDiagonalCoordinates.col, leftDiagonalCoordinates.row).getPiece();
            if (attackedPiece != null && attackedPiece.getColor() != origin.getPiece().getColor()) {
                coords.add(leftDiagonalCoordinates);
            }
        }
        if (startCoords.col != 'h') {
            Coordinates rightDiagonalCoordinates = new Coordinates(
                    (char) (startCoords.col + 1), startCoords.row + direction);
            Piece attackedPiece = getSquare(
                    rightDiagonalCoordinates.col, rightDiagonalCoordinates.row).getPiece();
            if (attackedPiece != null && attackedPiece.getColor() != origin.getPiece().getColor()) {
                coords.add(rightDiagonalCoordinates);
            }
        }

        // Can the pawn en passant?
        Move lastMove = peekLastMove();
        // Can only en passant if the last move was a pawn moving two spaces
        if (lastMove != null &&
                lastMove.getMovingPiece().getType() == Piece.Type.PAWN &&
                Math.abs(lastMove.getEndCoordinates().row - lastMove.startCoordinates.row) == 2) {
            if (lastMove.getEndCoordinates().col == startCoords.col - 1) {
                coords.add(new Coordinates(
                        (char) (startCoords.col - 1), startCoords.row + direction));
            }
            if (lastMove.getEndCoordinates().col == startCoords.col + 1) {
                coords.add(new Coordinates(
                        (char) (startCoords.col + 1), startCoords.row + direction));
            }
        }

        return coords;
    }

    private HashSet<Coordinates> getKnightCoordinates(Square origin) {
        Coordinates startCoords = getCoordinates(origin);
        HashSet<Coordinates> coordSet = new HashSet<Coordinates>();
        if (origin.getPiece() == null) {
            return coordSet;
        }
        Coordinates[] newCoords = {
                new Coordinates((char) (startCoords.col + 2), startCoords.row + 1),
                new Coordinates((char) (startCoords.col + 2), startCoords.row - 1),
                new Coordinates((char) (startCoords.col - 2), startCoords.row + 1),
                new Coordinates((char) (startCoords.col - 2), startCoords.row - 1),
                new Coordinates((char) (startCoords.col + 1), startCoords.row + 2),
                new Coordinates((char) (startCoords.col + 1), startCoords.row - 2),
                new Coordinates((char) (startCoords.col - 1), startCoords.row + 2),
                new Coordinates((char) (startCoords.col - 1), startCoords.row - 2),
        };
        for (Coordinates coords : newCoords) {
            if (coords.col >= 'a' && coords.col <= 'h'
                    && coords.row >= 1 && coords.row <= 8) {
                Piece attacked = getSquare(coords.col, coords.row).getPiece();
                if (attacked == null || attacked.getColor() != origin.getPiece().getColor()) {
                    coordSet.add(coords);
                }
            }
        }
        return coordSet;
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

    private HashSet<Coordinates> getQueenCoordinates(Square origin) {
        HashSet<Coordinates> result = getRowAndColumnCoordinates(origin);
        result.addAll(getDiagonalCoordinates(origin));
        return result;
    }

    private HashSet<Coordinates> getKingCoordinates(Square origin, boolean includeCastle) {
        Coordinates startCoords = getCoordinates(origin);
        HashSet<Coordinates> coordSet = new HashSet<Coordinates>();
        if (origin.getPiece() == null) {
            return coordSet;
        }
        Coordinates[] newCoords = {
                new Coordinates((char) (startCoords.col + 1), startCoords.row + 1),
                new Coordinates((char) (startCoords.col + 1), startCoords.row),
                new Coordinates((char) (startCoords.col + 1), startCoords.row - 1),
                new Coordinates((char) (startCoords.col), startCoords.row + 1),
                new Coordinates((char) (startCoords.col), startCoords.row - 1),
                new Coordinates((char) (startCoords.col - 1), startCoords.row + 1),
                new Coordinates((char) (startCoords.col - 1), startCoords.row),
                new Coordinates((char) (startCoords.col - 1), startCoords.row - 1),
        };
        for (Coordinates coords : newCoords) {
            if (coords.col >= 'a' && coords.col <= 'h'
                    && coords.row >= 1 && coords.row <= 8) {
                Piece attacked = getSquare(coords.col, coords.row).getPiece();
                if (attacked == null || attacked.getColor() != origin.getPiece().getColor()) {
                    coordSet.add(coords);
                }
            }
        }
        // This if statement prevents infinite recursion when determining threatened pieces.
        if (includeCastle) {
            if (canCastleLeft(origin)) {
                coordSet.add(new Coordinates((char) (startCoords.col - 2), startCoords.row));
            }
            if (canCastleRight(origin)) {
                coordSet.add(new Coordinates((char) (startCoords.col + 2), startCoords.row));
            }
        }
        return coordSet;
    }

    private boolean canCastleLeft(Square sq) {
        if (sq.getPiece() == null || sq.getPiece().getType() != Piece.Type.KING) {
            return false;
        }
        PlayerColor kingColor = sq.getPiece().getColor();
        PlayerColor opponentColor =
                kingColor == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
        Square leftCornerSquare = getSquare('a', kingColor == PlayerColor.WHITE ? 1 : 8);
        if (leftCornerSquare.getPiece() == null ||
                leftCornerSquare.getPiece().getType() != Piece.Type.ROOK) {
            return false;
        }
        boolean isThreatened = false;
        for (char col = 'a'; col < 'f'; col++) {
            if(isThreatenedByColor(getSquare(col, sq.getRow()), opponentColor)) {
                isThreatened = true;
                break;
            }
        }
        return !sq.getPiece().hasMoved() &&
                !leftCornerSquare.getPiece().hasMoved() &&
                !isThreatened;

    }

    private boolean canCastleRight(Square sq) {
        if (sq.getPiece() == null || sq.getPiece().getType() != Piece.Type.KING) {
            return false;
        }
        PlayerColor kingColor = sq.getPiece().getColor();
        PlayerColor opponentColor =
                kingColor == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
        Square rightCornerSquare = getSquare('h', kingColor == PlayerColor.WHITE ? 1 : 8);
        if (rightCornerSquare.getPiece() == null ||
                rightCornerSquare.getPiece().getType() != Piece.Type.ROOK) {
            return false;
        }
        boolean isThreatened = false;
        for (char col = 'e'; col < 'i'; col++) {
            if(isThreatenedByColor(getSquare(col, sq.getRow()), opponentColor)) {
                isThreatened = true;
                break;
            }
        }
        return !sq.getPiece().hasMoved() &&
                !rightCornerSquare.getPiece().hasMoved() &&
                !isThreatened;

    }

    private boolean isThreatenedByColor(Square sq, PlayerColor color) {
        Coordinates targetCoords = getCoordinates(sq);
        for (char col = 'a'; col < 'i'; col++) {
            for (int row = 1; row < 9; row++) {
                Square attackingSquare = getSquare(col, row);
                Piece piece = attackingSquare.getPiece();
                if (piece != null && piece.getColor() == color) {
                    // Don't worry about the opponent's castle interfering with your castle.
                    // If the opponent's king is close enough for this to matter, they can't castle.
                    HashSet<Coordinates> threatenedCoords =
                            getValidCoordinatesForMove(attackingSquare, false);
                    if (threatenedCoords.contains(targetCoords)) {
                        return true;
                    }
                }
            }
        }
        return false;
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
        boolean isValidMove = getValidCoordinatesForMove(origin, true).contains(destCoords);

        return isValidMove;
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
            Piece movingPiece = origin.getPiece();
            // Handle castling before recording move.
            boolean isCastle = false;
            Square rookSquare = new Square('a', 1);
            Square rookDestination = new Square('a', 1);
            // Castle left
            if (movingPiece.getType() == Piece.Type.KING &&
                    destination.getCol() == origin.getCol() - 2) {
                rookSquare = getSquare('a', origin.getRow());
                rookDestination = getSquare('d', origin.getRow());
                isCastle = true;
            }
            if (movingPiece.getType() == Piece.Type.KING &&
                    destination.getCol() == origin.getCol() + 2) {
                rookSquare = getSquare('h', origin.getRow());
                rookDestination = getSquare('f', origin.getRow());
                isCastle = true;
            }

            // Handle en passant before recording move.
            // En passant occurs if the pawn is attacking diagonally to an empty space.
            boolean isEnPassant = (origin.getPiece() != null &&
                    origin.getPiece().getType() == Piece.Type.PAWN &&
                    origin.getCol() != destination.getCol() &&
                    destination.getPiece() == null);
            recordMove(new Move(origin, destination, isCastle, isEnPassant));

            movingPiece.setHasMoved(true);
            destination.setPiece(movingPiece);
            if (isCastle) {
                Piece castlingRook = rookSquare.getPiece();
                if (castlingRook == null || castlingRook.getType() != Piece.Type.ROOK) {
                    // Something went horribly wrong.
                    return false;
                }
                castlingRook.setHasMoved(true);
                rookDestination.setPiece(castlingRook);
                rookSquare.setPiece(null);
            }
            if (isEnPassant) {
                // Delete the pawn that was attacked.
                int startingRow = origin.getRow();
                char endingCol = destination.getCol();
                getSquare(endingCol, startingRow).setPiece(null);
            }
            origin.setPiece(null);
            this.setCurrentPlayer(
                    currentPlayer == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE);
            return true;
        } else {
            return false;
        }
    }
}

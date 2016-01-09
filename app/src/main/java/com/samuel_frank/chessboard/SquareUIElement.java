package com.samuel_frank.chessboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;

/**
 * Created by smf2147 on 1/2/16.
 */
public class SquareUIElement extends ImageButton {
    private static final String TAG = "Square";

    public SquareUIElement(Context context) {
        super(context);
        this.initializePrivateMembers();
        Log.d(TAG, "Initialized!");
    }

    public SquareUIElement(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initializePrivateMembers();
        Log.d(TAG, "Initialized!");
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public BoardLayout getBoard() {
        return board;
    }

    public void setBoard(BoardLayout board) {
        this.board = board;
    }

    private void initializePrivateMembers() {
        this.piece = null;
        this.board = null;
    }

    private Piece piece;
    private BoardLayout board;
}

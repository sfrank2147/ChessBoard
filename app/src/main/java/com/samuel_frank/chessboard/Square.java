package com.samuel_frank.chessboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;

/**
 * Created by smf2147 on 1/2/16.
 */
public class Square extends ImageButton {
    private static final String TAG = "Square";

    public Square(Context context) {
        super(context);
        this.initializePrivateMembers();
        Log.d(TAG, "Initialized!");
    }

    public Square(Context context, AttributeSet attrs) {
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
}

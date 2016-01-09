package com.samuel_frank.chessboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;

/**
 * Created by smf2147 on 1/2/16.
 */
public class SquareUIElement extends ImageButton {
    private static final String TAG = "SquareUIElement";

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    private Square square;

    public SquareUIElement(Context context) {
        super(context);
        this.square = null;
    }

    public SquareUIElement(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.square = null;
    }
}

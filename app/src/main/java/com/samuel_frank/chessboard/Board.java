package com.samuel_frank.chessboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by smf2147 on 1/2/16.
 */
public class Board extends LinearLayout {
    private static final String TAG = "Board";

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;

    private Square[][] squares;

    public Board(Context context) {
        super(context);
        initializeSquares(context);
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeSquares(context);
    }

    private void initializeSquares(Context context) {
        this.squares = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square curSquare = new Square(context);
                curSquare.setBoard(this);
                this.squares[i][j] = curSquare;
            }
        }
    }

    Square getSquare(char col, int row) {
        return this.squares[row - 1][col - 'a'];
    }

    @Override
    public void onMeasure(int width, int height) {
        int newSize = (width < height) ? width : height;
        super.onMeasure(newSize, newSize);
    }
}

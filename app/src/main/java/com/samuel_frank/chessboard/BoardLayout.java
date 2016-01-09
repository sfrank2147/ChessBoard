package com.samuel_frank.chessboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by smf2147 on 1/2/16.
 */
public class BoardLayout extends LinearLayout {

    public BoardLayout(Context context) {
        super(context);
    }

    public BoardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int width, int height) {
        int newSize = (width < height) ? width : height;
        super.onMeasure(newSize, newSize);
    }
}

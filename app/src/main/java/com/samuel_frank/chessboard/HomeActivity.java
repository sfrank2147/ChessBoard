package com.samuel_frank.chessboard;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeActivity extends Activity {

    private Square getSquareUIElement(char col, int row) {
        LinearLayout r;
        Square sq;
        switch (row) {
            case 1:
                r = (LinearLayout) findViewById(R.id.row1);
                break;
            case 2:
                r = (LinearLayout) findViewById(R.id.row2);
                break;
            case 3:
                r = (LinearLayout) findViewById(R.id.row3);
                break;
            case 4:
                r = (LinearLayout) findViewById(R.id.row4);
                break;
            case 5:
                r = (LinearLayout) findViewById(R.id.row5);
                break;
            case 6:
                r = (LinearLayout) findViewById(R.id.row6);
                break;
            case 7:
                r = (LinearLayout) findViewById(R.id.row7);
                break;
            case 8:
            default:
                r = (LinearLayout) findViewById(R.id.row8);
                break;
        }

        switch (col) {
            case 'a':
                return (Square) r.findViewById(R.id.cola);
            case 'b':
                return (Square) r.findViewById(R.id.colb);
            case 'c':
                return (Square) r.findViewById(R.id.colc);
            case 'd':
                return (Square) r.findViewById(R.id.cold);
            case 'e':
                return (Square) r.findViewById(R.id.cole);
            case 'f':
                return (Square) r.findViewById(R.id.colf);
            case 'g':
                return (Square) r.findViewById(R.id.colg);
            case 'h':
            default:
                return (Square) r.findViewById(R.id.colh);
        }
    }

    protected void setSquarePiece(char col, int row, int imageResource) {
        Square sq = getSquareUIElement(col, row);
        sq.setScaleType(Square.ScaleType.FIT_CENTER);
        sq.setImageResource(imageResource);
        sq.setPadding(0, 0, 0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        for (char col = 'a'; col < 'i'; col++) {
            for (int row = 1; row < 9; row++) {
                boolean white = ((int) col + row) % 2 == 1;
                getSquareUIElement(col, row).setBackgroundColor(
                        white ? Color.parseColor("#F5DEB3") : Color.parseColor("#8B4513"));
            }
        }
        //Set initial piece images.
//        Square e8 = getSquareUIElement('e', 8);
//        e8.setScaleType(Square.ScaleType.FIT_CENTER);
//        e8.setImageResource(R.drawable.black_king);
//        e8.setPadding(0, 0, 0, 0);
        setSquarePiece('a', 1, R.drawable.white_rook);
        setSquarePiece('b', 1, R.drawable.white_knight);
        setSquarePiece('c', 1, R.drawable.white_bishop);
        setSquarePiece('d', 1, R.drawable.white_queen);
        setSquarePiece('e', 1, R.drawable.white_king);
        setSquarePiece('f', 1, R.drawable.white_bishop);
        setSquarePiece('g', 1, R.drawable.white_knight);
        setSquarePiece('h', 1, R.drawable.white_rook);

        for (char col = 'a'; col < 'i'; col++) {
            setSquarePiece(col, 2, R.drawable.white_pawn);
        }

        setSquarePiece('a', 8, R.drawable.black_rook);
        setSquarePiece('b', 8, R.drawable.black_knight);
        setSquarePiece('c', 8, R.drawable.black_bishop);
        setSquarePiece('d', 8, R.drawable.black_queen);
        setSquarePiece('e', 8, R.drawable.black_king);
        setSquarePiece('f', 8, R.drawable.black_bishop);
        setSquarePiece('g', 8, R.drawable.black_knight);
        setSquarePiece('h', 8, R.drawable.black_rook);

        for (char col = 'a'; col < 'i'; col++) {
            setSquarePiece(col, 7, R.drawable.black_pawn);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

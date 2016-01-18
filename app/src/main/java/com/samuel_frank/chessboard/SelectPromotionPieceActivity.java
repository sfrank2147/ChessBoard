package com.samuel_frank.chessboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by smf2147 on 1/18/16.
 */
public class SelectPromotionPieceActivity extends Activity {
    public static final String TAG = "SelectPromotionPieceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_promotion_piece_activity);
    }

    public void selectQueen(View view) {
        Intent data = new Intent();
        data.putExtra("selectedPiece", "Queen");
        setResult(RESULT_OK, data);
        finish();
    }

    public void selectRook(View view) {
        Intent data = new Intent();
        data.putExtra("selectedPiece", "Rook");
        setResult(RESULT_OK, data);
        finish();
    }

    public void selectBishop(View view) {
        Intent data = new Intent();
        data.putExtra("selectedPiece", "Bishop");
        setResult(RESULT_OK, data);
        finish();
    }

    public void selectKnight(View view) {
        Intent data = new Intent();
        data.putExtra("selectedPiece", "Knight");
        setResult(RESULT_OK, data);
        finish();
    }
}

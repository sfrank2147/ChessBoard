package com.samuel_frank.chessboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomeActivity extends Activity {

    private static final String TAG = "HomeActivity";

    public static final int PROMOTION_REQUEST = 1;

    private Board board;

    public SquareUIElement getSelectedSquare() {
        return selectedSquare;
    }

    public void setSelectedSquare(SquareUIElement selectedSquare) {
        this.selectedSquare = selectedSquare;
    }

    private SquareUIElement selectedSquare;

    public Board getBoard() {
        if (this.board == null) {
            this.board = new Board();
        }
        return this.board;
    }

    private boolean isWhiteSquare(SquareUIElement squareUIElement) {
        Square sq = squareUIElement.getSquare();
        return ((int) sq.getCol() + sq.getRow()) % 2 == 1;
    }

    private SquareUIElement getSquareUIElement(char col, int row) {
        LinearLayout r;
        SquareUIElement sq;
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
                return (SquareUIElement) r.findViewById(R.id.cola);
            case 'b':
                return (SquareUIElement) r.findViewById(R.id.colb);
            case 'c':
                return (SquareUIElement) r.findViewById(R.id.colc);
            case 'd':
                return (SquareUIElement) r.findViewById(R.id.cold);
            case 'e':
                return (SquareUIElement) r.findViewById(R.id.cole);
            case 'f':
                return (SquareUIElement) r.findViewById(R.id.colf);
            case 'g':
                return (SquareUIElement) r.findViewById(R.id.colg);
            case 'h':
            default:
                return (SquareUIElement) r.findViewById(R.id.colh);
        }
    }

    private int getImageResource(Piece piece) {
        if (piece == null) {
            return android.R.color.transparent;
        }
        return getImageResource(piece.getColor(), piece.getType());
    }

    private int getImageResource(PlayerColor color, Piece.Type type) {
        if (color == PlayerColor.BLACK) {
            switch(type) {
                case ROOK:
                    return R.drawable.black_rook;
                case KNIGHT:
                    return R.drawable.black_knight;
                case BISHOP:
                    return R.drawable.black_bishop;
                case KING:
                    return R.drawable.black_king;
                case QUEEN:
                    return R.drawable.black_queen;
                case PAWN:
                    return R.drawable.black_pawn;
            }

        } else {
            switch(type) {
                case ROOK:
                    return R.drawable.white_rook;
                case KNIGHT:
                    return R.drawable.white_knight;
                case BISHOP:
                    return R.drawable.white_bishop;
                case KING:
                    return R.drawable.white_king;
                case QUEEN:
                    return R.drawable.white_queen;
                case PAWN:
                    return R.drawable.white_pawn;
            }
        }
        // Should never reach here.
        return 0;
    }

    private void setSquareImage(char col, int row, int imageResource) {
        SquareUIElement sq = getSquareUIElement(col, row);
        sq.setScaleType(SquareUIElement.ScaleType.FIT_CENTER);
        sq.setImageResource(imageResource);
        sq.setPadding(0, 0, 0, 0);
    }

    private void setUIFromBoard(Board board) {
        for (char col = 'a'; col < 'i'; col++) {
            for (int row = 1; row < 9; row++) {
                Piece piece = board.getSquare(col, row).getPiece();
                setSquareImage(col, row, getImageResource(piece));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_activity);
        for (char col = 'a'; col < 'i'; col++) {
            for (int row = 1; row < 9; row++) {
                SquareUIElement sq = getSquareUIElement(col, row);
                sq.setSquare(this.getBoard().getSquare(col, row));
                boolean white = isWhiteSquare(sq);
                getSquareUIElement(col, row).setBackgroundColor(
                        white ? Color.parseColor("#F5DEB3") : Color.parseColor("#8B4513"));
            }
        }
        this.selectedSquare = null;
        setUIFromBoard(getBoard());
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

    public void removeSelectedSquare() {
        removeSelectedSquareHighlight();
        setSelectedSquare(null);
    }

    public void removeSelectedSquareHighlight() {
        SquareUIElement sq = getSelectedSquare();
        if (sq != null) {
            sq.setBackgroundColor(
                    isWhiteSquare(sq)
                            ? Color.parseColor("#F5DEB3")
                            : Color.parseColor("#8B4513")
            );
        }
    }

    public void squareClicked(View view) {
        SquareUIElement squareUIElement = (SquareUIElement) view;
        SquareUIElement previousSelectedSquare = getSelectedSquare();
        if (previousSelectedSquare == null) {
            Piece selectedPiece = squareUIElement.getSquare().getPiece();
            if (selectedPiece == null ||
                    selectedPiece.getColor() != this.board.getCurrentPlayer()) {
                return;
            } else {
                squareUIElement.setBackgroundColor(Color.parseColor("#00FF00"));
                setSelectedSquare(squareUIElement);
            }
        } else {
            this.board.move(previousSelectedSquare.getSquare(), squareUIElement.getSquare());
            if (this.board.isPromotablePawn()) {
                startActivityForResult(
                        new Intent(this, SelectPromotionPieceActivity.class),
                        PROMOTION_REQUEST);
            } else {
                // If the pawn is promoted, set the UI and check for checkmate
                // after the piece is chosen.
                setUIFromBoard(this.board);
                checkForCheckmate();
            }
            removeSelectedSquare();
        }
    }

    public void checkForCheckmate() {
        if (board.isCheckmate()) {
            Context context = getApplicationContext();
            CharSequence text = "Checkmate!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        return;
    }

    public void resetBoard() {
        Log.d(TAG, "Resetting board.");
        this.board.reset();
        removeSelectedSquare();
        setUIFromBoard(this.board);
    }

    public void resetBoard(View view) {
        this.resetBoard();
    }

    public void undoMove() {
        this.board.undoMove();
        removeSelectedSquare();
        setUIFromBoard(this.board);
    }

    public void undoMove(View view) {
        this.undoMove();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PROMOTION_REQUEST) {
            if (resultCode == RESULT_OK) {
                String chosenPiece = data.getExtras().getString("selectedPiece");
                switch (chosenPiece) {
                    case "Queen":
                        board.promotePawn(Piece.Type.QUEEN);
                        break;
                    case "Rook":
                        board.promotePawn(Piece.Type.ROOK);
                        break;
                    case "Bishop":
                        board.promotePawn(Piece.Type.BISHOP);
                        break;
                    case "Knight":
                        board.promotePawn(Piece.Type.KNIGHT);
                        break;
                    default:
                        break;
                }
            }
            setUIFromBoard(getBoard());
            checkForCheckmate();
        }
    }
}

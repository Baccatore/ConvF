package baccatore.convf;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Baccatore on 11/6/15.
 */
public class Game extends Activity {
    private static final String TAG = "数独";

    public static final String KEY_DIFFICULTY = "org.example.convf.difficulty";
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;

    private int puzzle[] = new int[9 * 9];//オブジェクト渡しなあたり参照渡しとか使うのかな

    private PuzzleView puzzleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
        puzzle = getPuzzle(diff);
        calculateUsedTiles();

        puzzleView = new PuzzleView(this);
        setContentView(puzzleView);
        puzzleView.requestFocus();
    }

    protected void showKeypadOrError(int x, int y) {
        int tiles[] = {1,2,3,4,5,6,7,8,9}/*getUsedTiles(x, y)*/;
        if (tiles.length == 9) {
            Toast toast = Toast.makeText(this, R.string.no_moves_label,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else{
            Log.d(TAG,"showKeypad: used="/*+toPuzzleString(tiles)*/);
            Dialog v= new Keypad(this, tiles, puzzleView);
            v.show();
        }
    }
    public boolean setTileIfValid(int x, int y, int value){
        return true;
    }

}

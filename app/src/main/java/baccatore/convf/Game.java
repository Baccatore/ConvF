package baccatore.convf;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

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

//        int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
//        puzzle = getPuzzle(diff);
//        calculateUsedTiles();

        puzzleView = new PuzzleView(this);
        setContentView(puzzleView);
        puzzleView.requestFocus();
    }

}

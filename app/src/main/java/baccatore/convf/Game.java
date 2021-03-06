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
    public static final String KEY_DIFFICULTY = "org.example.convf.difficulty";
    public static final int DIFFICULTY_BLANK = 0;
    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_MEDIUM = 2;
    public static final int DIFFICULTY_HARD = 3;
    private static final String TAG = "数独";
    private final String blankPuzzle =
            "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
    private final String easyPuzzle =
            "360000000004230800000004200070460003820000014500013020001900000007048300000000045";
    private final String midiumPuzzle =
            "650000070000506000014000005005009000002314700000700800500000630000201000030000097";
    private final String hardPuzzle =
            "009000000080605020501078000000000700706040102004000000000720930903010800000000600";
    //配列をキャッシュにしておいて必要な時に呼び出す。一から計算するにはコストがかかりすぎる
    private final int used[][][] = new int[9][9][];
    private int puzzle[] = new int[9 * 9];//オブジェクト渡しなあたり参照渡しとか使うのかな
    private PuzzleView puzzleView;

    static private String toPuzzleString(int[] puz) {
        StringBuilder buf = new StringBuilder();
        for (int element : puz) {
            buf.append(element);
        }
        return buf.toString();
    }

    static protected int[] fromPuzzleString(String string) {
        int[] puz = new int[string.length()];
        for (int i = 0; i < puz.length; i++) {
            puz[i] = string.charAt(i) - '0';
        }
        return puz;
    }

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
        int tiles[] = getUsedTiles(x, y);
        if (tiles.length == 9) {
            Toast toast = Toast.makeText(this, R.string.no_moves_label, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Log.d(TAG, "showKeypad: used="/*+toPuzzleString(tiles)*/);
            Dialog v = new Keypad(this, tiles, puzzleView);
            v.show();
        }
    }

    public boolean setTileIfValid(int x, int y, int value) {
        int tiles[] = getUsedTiles(x, y);
        if (value != 0) {
            for (int tile : tiles) {
                if (tile == value) return false;
            }
        }
        setTile(x, y, value);
        calculateUsedTiles();
        return true;
    }

    protected int[] getUsedTiles(int x, int y) {
        return used[x][y];
    }

    private void calculateUsedTiles() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                used[x][y] = calculateUsedTiles(x, y);
            }
        }
    }

    private int[] calculateUsedTiles(int x, int y) {
        int[] c = new int[9];
        //横のチェック
        for (int i = 0; i < 9; i++) {
            if (i == y) continue; //今チェック中のマスに関しては飛ばす
            int t = getTile(x, i);
            if (t != 0) c[t - 1] = t;
        }
        //縦のチェック
        for (int i = 0; i < 9; i++) {
            if (i == x) continue; //今チェック中のマスに関しては飛ばす
            int t = getTile(i, y);
            if (t != 0) c[t - 1] = t;
        }
        //ブロックのチェック
        int startx = (x / 3) * 3;
        int starty = (y / 3) * 3;
        for (int i = startx; i < startx + 3; i++) {
            for (int j = starty; j < starty + 3; j++) {
                if (i == x && j == y) continue; //チェック中のマスは飛ばす
                int t = getTile(i, j);
                if (t != 0) c[t - 1] = t;
            }
        }
        //圧縮 - 使用済みのタイルの配列の中から0を取り除く
        int nused = 0;
        for (int t : c)
            if (t != 0) nused++;
        int c1[] = new int[nused];
        nused = 0;
        for (int t : c) {
            if (t != 0) c1[nused++] = t;
        }
        return c1;
    }

    private int[] getPuzzle(int diff) {
        String puz;
        //前回の保存データには触れていない
        switch (diff) {
            case DIFFICULTY_BLANK:
                puz = blankPuzzle;
                break;
            case DIFFICULTY_EASY:
                puz = easyPuzzle;
                break;
            case DIFFICULTY_MEDIUM:
                puz = midiumPuzzle;
                break;
            case DIFFICULTY_HARD:
                puz = hardPuzzle;
                break;
            default:
                puz = easyPuzzle;
                break;
        }
        return fromPuzzleString(puz);
    }

    private int getTile(int x, int y) {
        return puzzle[y * 9 + x];
    }

    private void setTile(int x, int y, int value) {
        puzzle[9 * y + x] = value;
    }

    protected String getTileString(int x, int y) {
        int v = getTile(x, y);
        if (v == 0) {
            return "";
        } else {
            return String.valueOf(v);
        }
    }
}

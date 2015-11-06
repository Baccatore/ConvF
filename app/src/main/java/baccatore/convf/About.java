package baccatore.convf;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Baccatore on 11/6/15.
 */
public class About extends Activity {
    @Override
    protected void onCreate (Bundle savedInstanceState){
        //状態の保存とレイアウトの設定
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }
}

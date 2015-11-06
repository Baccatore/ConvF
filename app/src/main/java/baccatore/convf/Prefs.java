package baccatore.convf;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Baccatore on 11/6/15.
 */
public class Prefs extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //XMLファイルから設定の定義を読み出しインフレート
        addPreferencesFromResource(R.xml.settings);
    }
}

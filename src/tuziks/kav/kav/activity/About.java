package tuziks.kav.kav.activity;

import android.app.Activity;
import android.os.Bundle;
import tuziks.kav.R;

/**
 * Created with IntelliJ IDEA.
 * Date: 12.22.12
 * Time: 22:13
 */
public class About extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
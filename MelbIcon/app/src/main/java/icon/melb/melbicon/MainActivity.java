package icon.melb.melbicon;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();

        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterMenu();
            }
        });
    }

    private void enterMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}

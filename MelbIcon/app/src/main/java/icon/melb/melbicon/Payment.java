package icon.melb.melbicon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class Payment extends Activity {

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // Hide the status bar.
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_payment);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent navigate = new Intent(Payment.this,WaitScreen.class);
                startActivity(navigate);
            }
        });
    }
}

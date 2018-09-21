package icon.melb.melbicon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class StatusView extends AppCompatActivity {

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_view);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent navigate = new Intent(StatusView.this, WaitScreen.class);
                startActivity(navigate);
            }
        });
    }
}

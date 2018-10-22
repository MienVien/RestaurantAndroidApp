package icon.melb.melbicon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class WaitScreen extends Activity {

    private ImageButton orderMoreButton;
    private ImageButton checkStatusButton;
    private ImageButton requestAssistanceButton;
    private ImageButton finaliseBillButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wait_screen);

        orderMoreButton = findViewById(R.id.ordermore);
        checkStatusButton = findViewById(R.id.checkstatus);
        requestAssistanceButton = findViewById(R.id.reqassistance);
        finaliseBillButton = findViewById(R.id.finalisebill);

        //ORDER MORE BUTTON
        orderMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //navigate to 'Home (TEMP)' screen --EXPLICIT INTENT
                Intent navigate = new Intent(WaitScreen.this, MainMenu.class);
                startActivity(navigate);
            }
        });

        //CHECK STATUS BUTTON
        checkStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //navigate to 'Check Status' screen --EXPLICIT INTENT
                Intent navigate = new Intent(WaitScreen.this, Status.class);
                startActivity(navigate);
            }
        });

        //REQUEST ASSISTANCE BUTTON
        requestAssistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(WaitScreen.this);
                View mView = getLayoutInflater().inflate(R.layout.request_assistance_overlay, null);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                dialog.show();

                stopButton = mView.findViewById(R.id.stopButton);

                stopButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });


        //FINALISE BILL BUTTON
        finaliseBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //navigate to 'Order Status' screen --EXPLICIT INTENT
                Intent navigate = new Intent(WaitScreen.this, Payment.class);
                startActivity(navigate);
            }
        });

    }
}
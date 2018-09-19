package icon.melb.melbicon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class WaitScreen extends Activity {

    ImageButton orderMoreButton;
    ImageButton checkStatusButton;
    ImageButton requestAssistanceButton;
    ImageButton finaliseBillButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_wait_screen);

        orderMoreButton = findViewById(R.id.ordermore);
        checkStatusButton = findViewById(R.id.checkstatus);
        requestAssistanceButton = findViewById(R.id.reqassistance);
        finaliseBillButton = findViewById(R.id.finalisebill);


        requestAssistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(WaitScreen.this);
                View mView = getLayoutInflater().inflate(R.layout.request_assistance_overlay, null);

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });



    }

//    private void showMyDialog(Context context) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.request_assistance_overlay);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(true);
//
//        TextView textView = (TextView) dialog.findViewById(R.id.txtTitle);
//        ListView listView = (ListView) dialog.findViewById(R.id.listView);
//        Button btnBtmLeft = (Button) dialog.findViewById(R.id.btnBtmLeft);
//        Button btnBtmRight = (Button) dialog.findViewById(R.id.btnBtmRight);
//
//        btnBtmLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        btnBtmRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // do whatever you want here
//            }
//        });
//
//        /**
//         * if you want the dialog to be specific size, do the following
//         * this will cover 85% of the screen (85% width and 85% height)
//         */
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        int dialogWidth = (int)(displayMetrics.widthPixels * 0.85);
//        int dialogHeight = (int)(displayMetrics.heightPixels * 0.85);
//        dialog.getWindow().setLayout(dialogWidth, dialogHeight);
//
//        dialog.show();
//    }
}

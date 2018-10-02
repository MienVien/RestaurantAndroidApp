package icon.melb.melbicon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import icon.melb.melbicon.Config.Config;

import static android.content.ContentValues.TAG;

public class Payment extends Activity {

    private ImageButton backButton;
    //private ArrayList<MenuItem> mItemList = new ArrayList<>(); //TESTING
    private EditText grandTotalField;
    private List<OrderItem> list = new ArrayList<>();

    private String amount = "";
    private ImageButton paypal;

   public static final int PAYPAL_REQUEST_CODE = 7171;

   private static PayPalConfiguration config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(Config.PAYPAL_CLIENT_ID);

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        backButton = findViewById(R.id.backButton);
        //pullData = findViewById(R.id.loadData);
        grandTotalField = findViewById(R.id.editText);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigate = new Intent(Payment.this, WaitScreen.class);
                startActivity(navigate);
            }
        });

        paypal = findViewById(R.id.paypal);
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayPalService();
            }
        });

        initText();
        displayTotal();
    }

    private void startPayPalService() {
            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startService(intent);
            processPayment();
        }

        private void processPayment() {
           PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "Pay for MelbIcon", PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
            startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        }

    private void displayTotal( ){
        Double grandTotal = 0.0;

        for (OrderItem orderItem : list) {
            grandTotal += orderItem.getTotal();
        }
       amount = Double.toString(grandTotal);
       grandTotalField.setText("$ "+ amount);
    }

    private void initText( ){
        Log.d(TAG, "initText: preparing text");
        initPaymentList();
    }

    private void initPaymentList( ){
        RecyclerView recyclerView = findViewById(R.id.paymentRecyclerView);

        for (Order order : MainMenu.orders){
            for (OrderItem orderItem : order.getOrderItemList()) {
                list.add(orderItem);
            }
        }
        PaymentAdapter adapter = new PaymentAdapter(list, this);

        Log.d(TAG, "initPaymentList: " + MainMenu.currentOrder.getOrderItemList().size());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this, PaymentDetails.class)
                               .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amount ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }
}

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

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Payment extends Activity {

    private ImageButton backButton;
    //private ArrayList<MenuItem> mItemList = new ArrayList<>(); //TESTING
    private EditText grandTotalField;
    private List<OrderItem> list = new ArrayList<>();


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

        initText();
        displayTotal();
    }

    private void displayTotal( ){
        Double grandTotal = 0.0;

        for (OrderItem orderItem : list) {
            grandTotal += orderItem.getTotal();
        }
        grandTotalField.setText("$ "+ Double.toString(grandTotal));
    }

    private void initText( ){
        Log.d(TAG, "initText: preparing text");

        ///////////TESTING//////////////
//        MenuItem order = new MenuItem("Test1","description",51.00,false,false,"https://www.healthymummy.com/wp-content/uploads/2017/05/Chicken-and-Corn-Pot-Pie-.jpg",true);
//        MenuItem order2 = new MenuItem("Test1","description",51.00,false,false,"https://www.healthymummy.com/wp-content/uploads/2017/05/Chicken-and-Corn-Pot-Pie-.jpg",true);
//        MenuItem order3 = new MenuItem("Test1","description",51.00,false,false,"https://www.healthymummy.com/wp-content/uploads/2017/05/Chicken-and-Corn-Pot-Pie-.jpg",true);
//
//        mItemList.add(order);
//        mItemList.add(order2);
//        mItemList.add(order3);
        ////////////TESTING//////////////

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
}

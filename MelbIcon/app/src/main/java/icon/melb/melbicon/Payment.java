package icon.melb.melbicon;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Payment extends Activity {

    private ImageButton backButton;
//    private Button pullData;
    private ArrayList<PaymentOrders> mPaymentList = new ArrayList<>();
    private EditText grandTotalField;

//    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference mConditionRef = mRootRef.child("menu");


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

        for(PaymentOrders po:mPaymentList) {
            grandTotal += po.getTotal();
        }
        grandTotalField.setText("$ "+ Double.toString(grandTotal));
    }

    private void initText( ){
        Log.d(TAG, "initText: preparing text");

        PaymentOrders order = new PaymentOrders("Test1",51.00,2);
        PaymentOrders order2 = new PaymentOrders("Test2OIOEIORUJOUR",21.00,50);
        PaymentOrders order3 = new PaymentOrders("Test3OIOEIORUJOURjhsjdhsd",21.00,200);

        mPaymentList.add(order);
        mPaymentList.add(order2);
        mPaymentList.add(order3);

        initPaymentList();
    }

    private void initPaymentList( ){
        RecyclerView recyclerView = findViewById(R.id.paymentRecyclerView);
        PaymentAdapter adapter  = new PaymentAdapter(mPaymentList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//
//        mConditionRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                PaymentOrders order = dataSnapshot.getValue(PaymentOrders.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//    }
}

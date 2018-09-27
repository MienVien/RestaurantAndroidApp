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

import static android.content.ContentValues.TAG;

public class Payment extends Activity {

    private ImageButton backButton;
    private ArrayList<Item> mItemList = new ArrayList<>(); //TESTING
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

        for(Item po:mItemList) {
            grandTotal += po.getTotal();
        }
        grandTotalField.setText("$ "+ Double.toString(grandTotal));
    }

    private void initText( ){
        Log.d(TAG, "initText: preparing text");

        ///////////TESTING//////////////
        Item order = new Item("Test1",51.00,2);
        Item order2 = new Item("Test2OIOEIORUJOUR",21.00,50);
        Item order3 = new Item("Test3OIOEIORUJOURjhsjdhsd",21.00,200);

        mItemList.add(order);
        mItemList.add(order2);
        mItemList.add(order3);
        ////////////TESTING//////////////
        initPaymentList();
    }

    private void initPaymentList( ){
        RecyclerView recyclerView = findViewById(R.id.paymentRecyclerView);
        PaymentAdapter adapter  = new PaymentAdapter(mItemList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public ArrayList<Item> getmItemList(){
        return mItemList;
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

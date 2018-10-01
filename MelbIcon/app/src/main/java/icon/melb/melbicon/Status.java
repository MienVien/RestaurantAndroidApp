package icon.melb.melbicon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Status extends Activity {

    private ImageButton backButton;
//    private List<Order> mOrderList = new ArrayList<>();
//    private List<OrderItem> mItemList = new ArrayList<>(); //TESTING
    private List<Object> mObjectList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_view);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent navigate = new Intent(Status.this, WaitScreen.class);
                startActivity(navigate);
            }
        });
        initText();
    }

    private void initText( ){
        Log.d(TAG, "initText: preparing text");
        ///////////TESTING//////////////
//        Payment payment = new Payment();
//
//        MenuItem item = new MenuItem("Test1","description",51.00,2,false,false,"https://www.healthymummy.com/wp-content/uploads/2017/05/Chicken-and-Corn-Pot-Pie-.jpg",true);
//        MenuItem item2 = new MenuItem("Test1","description",51.00,2,false,false,"https://www.healthymummy.com/wp-content/uploads/2017/05/Chicken-and-Corn-Pot-Pie-.jpg",true);
//        MenuItem item3 = new MenuItem("Test1","description",51.00,2,false,false,"https://www.healthymummy.com/wp-content/uploads/2017/05/Chicken-and-Corn-Pot-Pie-.jpg",true);
//
//        mItemList.add(item);
//        mItemList.add(item2);
//        mItemList.add(item3);
//
//        Order order = new Order(mItemList.size(), mItemList);
//        Order order2 = new Order(mItemList.size(), mItemList);;
//        Order order3 = new Order(mItemList.size(), mItemList);
//
//        mOrderList.add(order);
//        mOrderList.add(order2);
//        mOrderList.add(order3);
        ///////////TESTING//////////////

        initStatusList();
    }

    private void initStatusList( ){
        RecyclerView recyclerView = findViewById(R.id.statusRecyclerView);

        for (Order order : MainMenu.orders) {
            mObjectList.add(order);
        }
        for (Order order : MainMenu.orders){
            for (OrderItem orderItem : order.getOrderItemList()) {
                mObjectList.add(orderItem);
            }
        }

        //StatusPaymentAdapter adapter  = new StatusPaymentAdapter(mOrderList, mItemList, this);
        //PaymentAdapter adapter2 = new PaymentAdapter(mItemList, this);

        StatusPaymentAdapter adapter = new StatusPaymentAdapter(mObjectList, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}

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

import static android.content.ContentValues.TAG;

public class Status extends Activity {

    private ImageButton backButton;
    private ArrayList<Order> mOrderList = new ArrayList<>();
    private ArrayList<Item> mItemList = new ArrayList<>(); //TESTING

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
        Payment payment = new Payment();

        Item item = new Item("Test1",51.00,2);
        Item item2 = new Item("Test2OIOEIORUJOUR",21.00,50);
        Item item3 = new Item("Test3OIOEIORUJOURjhsjdhsd",21.00,200);

        mItemList.add(item);
        mItemList.add(item2);
        mItemList.add(item3);

        Order order = new Order(mItemList.size(), mItemList);
        Order order2 = new Order(mItemList.size(), mItemList);;
        Order order3 = new Order(mItemList.size(), mItemList);

        mOrderList.add(order);
        mOrderList.add(order2);
        mOrderList.add(order3);
        ///////////TESTING//////////////

        initStatusList();
    }

    private void initStatusList( ){
        RecyclerView recyclerView = findViewById(R.id.statusRecyclerView);
        StatusAdapter adapter  = new StatusAdapter(mItemList, mOrderList,this);///WILL ONLY BE ONE LIST ONCE MERGED WITH VIEN
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public ArrayList<Order> getmItemList(){
        return mOrderList;
    }
}

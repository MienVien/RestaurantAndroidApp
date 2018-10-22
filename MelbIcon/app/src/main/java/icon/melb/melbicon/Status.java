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
        initStatusList();
    }

    private void initStatusList( ){
        RecyclerView recyclerView = findViewById(R.id.statusRecyclerView);

        for (Order order : MainMenu.orders){
            mObjectList.add(order);
            for (OrderItem orderItem : order.getOrderItemList()) {
                mObjectList.add(orderItem);
            }
        }

        StatusPaymentAdapter adapter = new StatusPaymentAdapter(mObjectList,  this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

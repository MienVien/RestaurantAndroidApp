package icon.melb.melbicon.kitchen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

public class changeStatus extends AppCompatActivity {

    private static final String TAG = "changeStatus.java ";
    private TextView textView;
    private ImageButton cooking;
    private ImageButton completed;
    private List<OrderItem> items;
    private KitchenOrder ko;
    private List<Item> cookingItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        textView = findViewById( R.id.textView );
        cooking = findViewById( R.id.cooking );
        completed = findViewById( R.id.complete );
        ko = new KitchenOrder();
        ko = (KitchenOrder) getIntent( ).getSerializableExtra( "order_placed" );

        textView.setText( "All Orders shown for Table: 1" +
                "\nTotal Dishes: " + ko.getDishQty( ) +
                "\t\tStatus: " + ko.getKitchenStatus( ) );

        cooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ko.changeKitchenStatus( );
                ko.changeCustomerStatus( );
            }
        });

        initText( );
    }

    private void initText( ){
        items = new ArrayList<OrderItem>( ko.getOrderItemList() );
       // Log.d( TAG, "" + items.size() );
        Log.d( TAG, items.toString() );
        initOrderList( );
    }

    private void initOrderList( ){
        RecyclerView recyclerView = findViewById( R.id.allItems);
        statusChangeAdapter adapter = new statusChangeAdapter( items, this );
        recyclerView.setAdapter( adapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( this) );
    }
}

package icon.melb.melbicon.Kitchen;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.util.Log;

import java.util.ArrayList;

import icon.melb.melbicon.R;

import static android.content.ContentValues.TAG;

public class Kitchen_page extends Activity {

    private ImageButton allMenu;
    private ImageButton completedOrder;
    private ImageButton backBT;
    private ImageButton addRemoveBT;
    private ImageButton refreshBT;
    private ArrayList<KitchenOrder> mKitchenOrderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_page);

        initText( );
    }

    private void initText( ){
        Log.d( TAG, "initText: preparing text" );
        KitchenOrder order1 = new KitchenOrder( 0, 4, "21/9/18 @ 5:35 PM", "RECEIVED" );
        KitchenOrder order2 = new KitchenOrder( 4, 2, "21/9/18 @ 5:41 PM", "RECEIVED" );
        KitchenOrder order3 = new KitchenOrder( 1, 2, "21/9/18 @ 5:11 PM", "COOKING" );

        mKitchenOrderList.add( order1 );
        mKitchenOrderList.add( order2 );
        mKitchenOrderList.add( order3 );

        mKitchenOrderList.size();

        initOrderList( );
    }
    private void initOrderList( ){
        RecyclerView recyclerView = findViewById( R.id.recyclerView );
        OrderAdapter adapter = new OrderAdapter( mKitchenOrderList, this );
        recyclerView.setAdapter( adapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( this) );
    }
}

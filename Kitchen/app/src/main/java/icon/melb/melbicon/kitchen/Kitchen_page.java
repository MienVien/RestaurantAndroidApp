package icon.melb.melbicon.kitchen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Kitchen_page extends Activity {

    private ImageButton allMenu;
    private ImageButton completedOrder;
    private ImageButton addRemoveBT;
    private List<KitchenOrder> mKitchenOrderList = new ArrayList<>();
    private List<KitchenOrder> mCompletedOrderList = new ArrayList<>();

    private static List<OrderItem> items = new ArrayList<>();
    private static List<OrderItem> orderItems = new ArrayList<>();

    private static List<String> itemNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_page);

        operateDatabase();

        allMenu = findViewById( R.id.allMenu );
        completedOrder = findViewById( R.id.completedOrder );
        addRemoveBT = findViewById( R.id.addRemoveBT );

        items.add( new OrderItem( "Burger", 12 ) );
        items.add( new OrderItem( "Chips", 5 ) );
        items.add( new OrderItem( "HSP", 21.5, 2 ) );
        items.add( new OrderItem( "Spicy Chicken Wing", 3, 5, "mOre ChiLLy Plz") );

        orderItems.add( new OrderItem( "Burger", 12 ) );
        orderItems.add( new OrderItem( "Chips", 5 ) );
        orderItems.add( new OrderItem( "HSP", 21.5, 2 ) );
        orderItems.add( new OrderItem( "Spicy Chicken Wing", 3, 5, "More Chilly plz") );

        allMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initText( );
            }
        });

        completedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completedInitText( );
            }
        });

        addRemoveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Context context = v.getContext( );
                    Intent intent = new Intent( context, search.class );
                    // Bundle bundle = new Bundle( );
                    // bundle.putSerializable( "all_items", orderItems );
                    intent.putExtra( "all_items", (Serializable) orderItems);
                    context.startActivity( intent );

                Bundle bundle = intent.getExtras( );
                List<OrderItem> checkedItems = ( List<OrderItem>) bundle.getSerializable( "checked_items" );

                Intent intent1 = new Intent( context, statusChangeAdapter.class );
                intent1.putExtras( bundle );
            }
        });
        initText( );

        Log.d("Order Item", " " + itemNames);
    }
    private void operateDatabase() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("order");
        //DatabaseReference myRef = database.child("order");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String date = (String) child.child("date").getValue();
                    long dishQty = (long) child.child("dishQty").getValue();
                    long tableNo = (long) child.child("tableNo").getValue();
                    String kitchenStatus = (String) child.child("kitchenStatus").getValue();
                    DatabaseReference items = child.child("items").getRef();
                    getItemNames(items);
                    Log.d("Retrieved_Data", "date: " + date + "\ndishQty: " + dishQty + "\ntableNo: " + tableNo + "\nitems: " + itemNames);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //DatabaseReference items = myRef.child("1").child("items");

        //getDatabase(items);
    }

    private void getItemNames(DatabaseReference dataRef) {

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String itemName = (String) child.child("name").getValue();
                    itemNames.add(itemName);
                    Log.d("ITEM", itemName);
                    Log.d("ItemList1", "" + itemNames);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void initText( ){
        Log.d( TAG, "initText: preparing text" );

        KitchenOrder ko = ( KitchenOrder) getIntent().getSerializableExtra( "status_changed" );

        if( ko != null ){
            for( KitchenOrder k : mKitchenOrderList ){
                if( k.getOrderTimeDate() == ko.getOrderTimeDate() && k.getOrderItemList() == ko.getOrderItemList() )
                    mKitchenOrderList.remove( k );
            }
            mKitchenOrderList.add( ko );
        }
        KitchenOrder order1 = new KitchenOrder( 4, items );
        KitchenOrder order2 = new KitchenOrder( 3, items );
        KitchenOrder order3 = new KitchenOrder( 4, items );

        KitchenOrder order4 = new KitchenOrder( 1, "More Chilli Plz", items );

        add( order1, mKitchenOrderList );
        add( order2, mKitchenOrderList );
        add( order3, mKitchenOrderList );
        add( order4, mKitchenOrderList );

        mKitchenOrderList.size();

        initOrderList( );
    }

    private void completedInitText( ){
        Log.d( TAG, "completedInitText: preparing text" );

        KitchenOrder ko = (KitchenOrder) getIntent().getSerializableExtra( "order_completed" );

        KitchenOrder order1 = new KitchenOrder( 4, items );
        KitchenOrder order2 = new KitchenOrder( 3, items );

        add( order1, mCompletedOrderList );
        add( order2, mCompletedOrderList );

        if( ko != null ){
            Log.d( TAG, "Inside CompletedInit: " + ko.toString( ) );
            mKitchenOrderList.remove( ko );
            mCompletedOrderList.add( ko );
        }

        mCompletedOrderList.size();

        completedInitOrderList( );
    }

    private void initOrderList( ){
        RecyclerView recyclerView = findViewById( R.id.recyclerView );

        Log.d( TAG, " " + mKitchenOrderList.get(0).getOrderItemList() );
        OrderAdapter adapter = new OrderAdapter( mKitchenOrderList, this );

        recyclerView.setAdapter( adapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( this) );
    }

    private void completedInitOrderList( ){
        RecyclerView recyclerView = findViewById( R.id.recyclerView );
        OrderAdapter adapter = new OrderAdapter( mCompletedOrderList, this );
        recyclerView.setAdapter( adapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( this) );
    }

    private void add( KitchenOrder order, List<KitchenOrder> list ){
        boolean recorded = false;
        for( KitchenOrder ko : list ){
            if( ko.getOrderItemList() == order.getOrderItemList() && ko.getOrderTimeDate() == order.getOrderTimeDate() )
                recorded = true;
        }

        if( !recorded ){
            list.add( order );
        }
    }
}

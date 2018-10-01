package icon.melb.melbicon.kitchen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {

    private addRemoveAdapter adapter;
    private SearchView searchView;
    private static final String TAG = "from Search ";
    private List<OrderItem> itemList;
    private List<KitchenOrder> mKitchenItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        itemList = new ArrayList<>();
        itemList = (ArrayList<OrderItem>)getIntent().getSerializableExtra("all_items" );
        //mKitchenItems = getIntent().getParcelableArrayListExtra("All items");
        Log.d( TAG, "" + mKitchenItems.size() );
        searchView = findViewById( R.id.searchView );
        searchView.setImeOptions( EditorInfo.IME_ACTION_DONE );
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        initText( );
    }

    private void initText( ){
        for( KitchenOrder ko : mKitchenItems ){
            for( OrderItem item : ko.getOrderItemList() ){
                itemList.add( item );
            }
        }
        initOrderList( );
    }

    private void initOrderList( ){
        RecyclerView recyclerView = findViewById( R.id.allItems);
        adapter = new addRemoveAdapter( itemList, this );
        recyclerView.setAdapter( adapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( this) );
    }
}

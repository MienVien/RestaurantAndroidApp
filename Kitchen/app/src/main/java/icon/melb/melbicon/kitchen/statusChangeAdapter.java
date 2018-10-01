package icon.melb.melbicon.kitchen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class statusChangeAdapter extends RecyclerView.Adapter<statusChangeAdapter.statusViewHolder>{

    private static final String TAG = "statusChangeAdapter";
    private List<KitchenOrder> mKitchenItem = new ArrayList<>();
    private List<OrderItem> mMenuItem = new ArrayList<>();
    private Context mContext;
    private List<Item> selectedItems = new ArrayList<>();

    public statusChangeAdapter ( List<OrderItem> mMenuItem, Context mContext ){
        this.mMenuItem = mMenuItem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public statusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i ){
        Log.d( TAG, "onCreateViewHolder: entered" );

        View view = LayoutInflater.from( viewGroup.getContext() ).inflate(R.layout.activity_add_remove, viewGroup, false );

        return new statusViewHolder( view ) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final statusViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");
        Log.d(TAG, String.format("getItemCount: %d", mMenuItem.size()));

       // viewHolder.itemList.setText( mMenuItem.get(i).getTitle( ) );
        viewHolder.bind( i );
    }

    @Override
    public int getItemCount( ){
        return mMenuItem.size( );
    }

    public class statusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout screen;
       CheckedTextView itemList;
        SparseBooleanArray itemStateArray = new SparseBooleanArray( );
        public statusViewHolder(@NonNull View itemView) {
            super(itemView);
            screen = itemView.findViewById( R.id.addRemove_list );
           itemList = itemView.findViewById( R.id.checked_text_view);
           itemList.setOnClickListener( this );
        }

        public void bind( int position ){
            if( !itemStateArray.get( position, false ) )
                itemList.setChecked(false);
            else
                itemList.setChecked(true);

            itemList.setText( String.valueOf(mMenuItem.get(position).getTitle( )));
        }

        @Override
        public void onClick( View view ){
            int adapterPosition = getAdapterPosition( );
            if( !itemStateArray.get( adapterPosition, false) ) {
                itemList.setChecked(true);
                itemStateArray.put(adapterPosition, true);
            }else{
                itemList.setChecked( false );
                itemStateArray.put( adapterPosition, false );
            }
        }
    }
}


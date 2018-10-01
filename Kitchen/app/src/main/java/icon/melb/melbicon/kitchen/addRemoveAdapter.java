package icon.melb.melbicon.kitchen;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Filter;

import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class addRemoveAdapter extends RecyclerView.Adapter<addRemoveAdapter.addRemoveViewHolder>
    implements Filterable{
    private static final String TAG = "addRemoveAdapter";
    private List<KitchenOrder> mKitchenItem = new ArrayList<>();
    private List<OrderItem> mMenuItem = new ArrayList<>();
    private List<OrderItem> mItemCopy;
    private Context mContext;

    private SparseBooleanArray itemStateArray = new SparseBooleanArray( );

    public addRemoveAdapter (List<OrderItem> mMenuItem, Context mContext ){
        this.mMenuItem = mMenuItem;
        Log.d( TAG, "" + mMenuItem );
        this.mContext = mContext;

        for( KitchenOrder ko : mKitchenItem ){
            for( int i = 0; i < ko.getDishQty(); ++i ){
                mMenuItem.add( ko.getOrderItemList().get(i) );
            }
        }

        mItemCopy = new ArrayList<>( mMenuItem );
    }

    @NonNull
    @Override
    public addRemoveAdapter.addRemoveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i ){
        Log.d( TAG, "onCreateViewHolder: entered" );

        View view = LayoutInflater.from( viewGroup.getContext() ).inflate(R.layout.activity_add_remove, viewGroup, false );

        addRemoveAdapter.addRemoveViewHolder holder = new addRemoveAdapter.addRemoveViewHolder( view );
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull final addRemoveAdapter.addRemoveViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");
        Log.d(TAG, String.format("getItemCount: %d", mMenuItem.size()));

       // viewHolder.itemList.setText( mMenuItem.get(i).getTitle() );
        viewHolder.bind( i );
    }

    @Override
    public int getItemCount( ){
        return mMenuItem.size( );
    }

    public class addRemoveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout screen;
        CheckedTextView itemList;
        List<OrderItem> checkedItems = new ArrayList<>();

        public addRemoveViewHolder(@NonNull View itemView) {
            super(itemView);
            screen = itemView.findViewById( R.id.addRemove_list );
            itemList = itemView.findViewById( R.id.checked_text_view);
            for( OrderItem item : checkedItems ){
                if( itemList.getText().toString().equalsIgnoreCase(item.getTitle()) )
                    itemList.setChecked( true );
            }
            itemList.setOnClickListener( this );
        }

        public void bind( int position ){
            if( !itemStateArray.get( position, false ) )
                itemList.setChecked(false);
            else
                itemList.setChecked(true);

            itemList.setText( String.valueOf(mItemCopy.get(position).getTitle( )));
        }

        @Override
        public void onClick( View v ){
            Toast.makeText( mContext, "Clicked " + itemList.getText().toString(), Toast.LENGTH_LONG ).show( );
            int adapterPosition = getAdapterPosition( );
            if( !itemStateArray.get( adapterPosition, false) ) {
                itemList.setChecked(true);
                itemStateArray.put(adapterPosition, true);
                checkedItems.add( mMenuItem.get(adapterPosition) );
            }else{
                itemList.setChecked( false );
                itemStateArray.put( adapterPosition, false );
                checkedItems.remove( mMenuItem.get(adapterPosition) );
            }
        }
    }

    @Override
    public Filter getFilter( ){
        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<OrderItem> filteredList = new ArrayList<>();

            if( constraint == null || constraint.length( ) == 0 ){
                filteredList.addAll( mItemCopy );
            }else{
                String filterPattern = constraint.toString( ).toLowerCase().trim( );
                Log.d( TAG, filterPattern );
                for( OrderItem item : mItemCopy ){
                    if( item.getTitle().toLowerCase().contains( filterPattern )){
                     //   Log.d( TAG, "searching inside for loop for: " + filterPattern );
                        Log.d( TAG, item + " contains " + filterPattern );
                        filteredList.add( item );
                    }
                }
            }
            FilterResults results = new FilterResults( );
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMenuItem.clear();
            mMenuItem.addAll( (List<OrderItem>) results.values );
            notifyDataSetChanged();
        }
    };
}

package icon.melb.melbicon.kitchen;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private static final String TAG = "OrderAdapter";
    private List<KitchenOrder> mKitchenItem = new ArrayList<>();
    private Context mContext;

    public OrderAdapter(List<KitchenOrder> mKitchenItem, Context mContext ){
        this.mKitchenItem = mKitchenItem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i ){
        Log.d( TAG, "onCreateViewHolder: entered" );

        View view = LayoutInflater.from( viewGroup.getContext() ).inflate(R.layout.activity_order_list, viewGroup, false );

        return new ViewHolder( view );
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");
        Log.d(TAG, String.format("getItemCount: %d", mKitchenItem.size()));

        Log.d( TAG, "" + mKitchenItem.get(i).getOrderItemList() );

        viewHolder.tableNum.setText( "\tTable: 1" );//+ mKitchenItem.get(i).getTableNum( ) );
        viewHolder.NumOfDish.setText( "\t\t\tDishes: " + mKitchenItem.get(i).getDishQty( ) );
        viewHolder.OrderReceived.setText( "\t\t\tOrder Received: " + mKitchenItem.get(i).getOrderTimeDate() );
        viewHolder.Status.setText("\t\t\tStatus " + mKitchenItem.get(i).getKitchenStatus().toUpperCase( ) );

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int id = mKitchenItem.get(i).getTableNum( );
//                Log.d( TAG, " " + id );
                Log.d( TAG, "You clicked " );
                Toast.makeText( mContext,"You clicked", Toast.LENGTH_SHORT ).show( );

                KitchenOrder ko = mKitchenItem.get(i);

                Context context = v.getContext( );
                Intent intent = new Intent( context, changeStatus.class );
                intent.putExtra("order_placed", ko );
                context.startActivity( intent );
//
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder( context );
//                //View mView = getLayoutInflater( ).inflate( R.layout.activity_change_status, ko );
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View mView = inflater.inflate(R.layout.activity_change_status, null);
//
//                mBuilder.setView( mView );
//                final AlertDialog dialog = mBuilder.create( );
//                dialog.show( );
            }
        });
    }

    @Override
    public int getItemCount( ){
        return mKitchenItem.size( );
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener{
        LinearLayout item;
        TextView tableNum;
        TextView NumOfDish;
        TextView OrderReceived;
        TextView Status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.Order_list);
            tableNum = itemView.findViewById(R.id.tableNum);
            NumOfDish = itemView.findViewById(R.id.numOfDishes);
            OrderReceived = itemView.findViewById(R.id.orderReceived);
            Status = itemView.findViewById(R.id.status);
        }

//        @Override
//        public void onClick( View view ){
//            Log.d( TAG, "You clicked" );
//            Toast.makeText(mContext,"You clicked", Toast.LENGTH_SHORT).show();
//        }
    }
}

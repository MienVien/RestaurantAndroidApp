package icon.melb.melbicon.Kitchen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import icon.melb.melbicon.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private static final String TAG = "OrderAdapter";
    private ArrayList<Kitchen_page.KitchenOrder> mKitchenItem = new ArrayList<>();
    private Context mContext;

    public OrderAdapter(ArrayList<Kitchen_page.KitchenOrder> mKitchenItem, Context mContext ){
        this.mKitchenItem = mKitchenItem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i ){
        Log.d( TAG, "onCreateViewHolder: entered" );

        View view = LayoutInflater.from( viewGroup.getContext() ).inflate(R.layout.activity_order_list, viewGroup, false );

        ViewHolder holder = new ViewHolder( view );
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");
        Log.d(TAG, String.format("getItemCount: %d", mKitchenItem.size()));

        viewHolder.tableNum.setText( "Table " + mKitchenItem.get(i).getTableNum( ) );
        viewHolder.NumOfDish.setText( "Dishes: " + mKitchenItem.get(i).getNumOfDish( ) );
        viewHolder.OrderReceived.setText( "Order Received: " + mKitchenItem.get(i).getOrderReceiveTime() );
        viewHolder.Status.setText("Status " + mKitchenItem.get(i).getStatus().toUpperCase( ) );
    }

    @Override
    public int getItemCount( ){
        return mKitchenItem.size( );
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout item;
        TextView tableNum;
        TextView NumOfDish;
        TextView OrderReceived;
        TextView Status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.Order_list);
            tableNum = itemView.findViewById(R.id.tableNum);
            NumOfDish = itemView.findViewById(R.id.NumOfDishes);
            OrderReceived = itemView.findViewById(R.id.OrderReceived);
            Status = itemView.findViewById(R.id.Status);
        }
    }
}

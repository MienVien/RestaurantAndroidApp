package icon.melb.melbicon;

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
import java.util.Date;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder>{
    private static final String TAG = "StatusAdapter";
    private List<OrderItem> mItem = new ArrayList<>(); ///TEST
    private List<Order> mOrder = new ArrayList<>(); //TEST
    private Context mContext;

    public StatusAdapter(List<Order> mOrder, List<OrderItem> mItem, Context mContext) {
        this.mItem = mItem;
        this.mOrder = mOrder;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: entered");
        //RESPONSIBLE FOR INFLATING THE 'item_listl' VIEW
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.status_list_header, viewGroup, false);

        //Custom Class outlined below
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");
        Log.d(TAG, String.format("getItemCount: %d", mOrder.size()));

        viewHolder.orderID.setText("ORDER# 00000");
        viewHolder.dishQty.setText("DISHES "+Integer.toString(mOrder.get(i).getDishQty()));
        viewHolder.orderDateTime.setText("Ordered "+ mOrder.get(i).getOrderTimeDate().toString());
        viewHolder.orderStatus.setText("STATUS "+ mOrder.get(i).getCustomerStatus());
    }

    @Override
    public int getItemCount() {
        return mItem.size( );
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout item;
        TextView orderID;
        TextView dishQty;
        TextView orderDateTime;
        TextView orderStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.itemContainer);
            orderID = itemView.findViewById(R.id.orderID);
            dishQty = itemView.findViewById(R.id.dishQty);
            orderDateTime = itemView.findViewById(R.id.orderTime);
            orderStatus = itemView.findViewById(R.id.orderStatus);
        }
    }
}
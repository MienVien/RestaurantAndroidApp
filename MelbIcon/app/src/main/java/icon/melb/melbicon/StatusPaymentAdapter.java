package icon.melb.melbicon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StatusPaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "StatusPaymentAdapter";
    private static final int ITEM_TYPE_ORDER = 0;
    private static final int ITEM_TYPE_ORDER_ITEM = 1;
    private final Context context;
    private List<Object> items = new ArrayList<>();
    private int index = 0;

    public StatusPaymentAdapter(List<Object> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Order) {
            return ITEM_TYPE_ORDER;
        } else if (items.get(position) instanceof OrderItem) {
            return ITEM_TYPE_ORDER_ITEM;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ITEM_TYPE_ORDER:
                View v1 = inflater.inflate(R.layout.status_list_header, viewGroup, false);
                return new OrderViewHolder(v1);
            case ITEM_TYPE_ORDER_ITEM:
                View v2 = inflater.inflate(R.layout.item_list, viewGroup, false);
                return new OrderItemViewHolder(v2);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case ITEM_TYPE_ORDER:
                OrderViewHolder vh1 = (OrderViewHolder) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case ITEM_TYPE_ORDER_ITEM:
                OrderItemViewHolder vh2 = (OrderItemViewHolder) viewHolder;
                configureViewHolder2(vh2, position);
                break;
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("d.list.data", adapter.getState());
//    }

    private void configureViewHolder1(OrderViewHolder vh1, int positionH) {
    Order order = (Order) items.get(positionH);

        if (order != null) {
            //++index;
            vh1.orderID.setText("ORDER#"+ items.indexOf(order));
            vh1.dishQty.setText("DISHES: "+Integer.toString(((Order) items.get(positionH)).getOrderItemList().size()));
            vh1.orderDateTime.setText("Ordered: "+ order.getOrderTimeDate().toString());
            vh1.orderStatus.setText("STATUS: "+ order.getCustomerStatus());
        }
    }

    private void configureViewHolder2(OrderItemViewHolder vh2, int positionB) {
        OrderItem orderItem = (OrderItem) items.get(positionB);
        if (orderItem != null) {
            vh2.title.setText(orderItem.getTitle());
            vh2.price.setText("$"+Double.toString(orderItem.getPrice()));
            vh2.qty.setText("QTY: "+Integer.toString(orderItem.getQty()));
            vh2.total.setText("$"+Double.toString(orderItem.getTotal()));
        }
    }

//    private void bindDataToAdapter() {
//        // Bind adapter to recycler view object
//        recyclerView.setAdapter(new StatusPaymentAdapter(getSampleArrayList()));
//    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder{

        LinearLayout item;
        TextView title;
        TextView price;
        TextView qty;
        TextView total;

        OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.itemContainer);
            title = itemView.findViewById(R.id.itemTitle);
            price = itemView.findViewById(R.id.itemPrice);
            qty = itemView.findViewById(R.id.itemQty);
            total = itemView.findViewById(R.id.itemTotal);
        }
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        LinearLayout item;
        TextView orderID;
        TextView dishQty;
        TextView orderDateTime;
        TextView orderStatus;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.itemContainer);
            orderID = itemView.findViewById(R.id.orderID);
            dishQty = itemView.findViewById(R.id.dishQty);
            orderDateTime = itemView.findViewById(R.id.orderTime);
            orderStatus = itemView.findViewById(R.id.orderStatus);
        }
    }
}


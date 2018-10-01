package icon.melb.melbicon;

import android.content.Context;
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

    private static final int ITEM_TYPE_ORDER = 0;
    private static final int ITEM_TYPE_ORDER_ITEM = 1;
    private final Context context;
    private List<Object> items = new ArrayList<>();
    private int orderSize;

    public StatusPaymentAdapter(List<Object> items, int orderSize, Context context) {
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
        } else if (items.get(position) instanceof String) {
            return ITEM_TYPE_ORDER_ITEM;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ITEM_TYPE_ORDER:
                View v1 = inflater.inflate(R.layout.status_list_header, viewGroup, false);
                viewHolder = new OrderViewHolder(v1);
                break;
            case ITEM_TYPE_ORDER_ITEM:
                View v2 = inflater.inflate(R.layout.item_list, viewGroup, false);
                viewHolder = new OrderItemViewHolder(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
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

    private void configureViewHolder1(OrderViewHolder vh1, int position) {
    Order order = (Order) items.get(position);
        if (order != null) {
            vh1.orderID.setText("ORDER# 00000");
            vh1.dishQty.setText("DISHES "+Integer.toString(orderSize));
            vh1.orderDateTime.setText("Ordered "+ order.getOrderTimeDate().toString());
            vh1.orderStatus.setText("STATUS "+ order.getCustomerStatus());
        }
    }

    private void configureViewHolder2(OrderItemViewHolder vh2, int position) {
        OrderItem orderItem = (OrderItem) items.get(position);
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

        public OrderItemViewHolder(@NonNull View itemView) {
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

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.itemContainer);
            orderID = itemView.findViewById(R.id.orderID);
            dishQty = itemView.findViewById(R.id.dishQty);
            orderDateTime = itemView.findViewById(R.id.orderTime);
            orderStatus = itemView.findViewById(R.id.orderStatus);
        }
    }
}


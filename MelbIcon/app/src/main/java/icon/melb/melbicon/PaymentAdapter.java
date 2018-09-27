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

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder>{

    private static final String TAG = "PaymentAdapter";
    private ArrayList<Item> mPaymentItem = new ArrayList<>();
    private Context mContext;

    public PaymentAdapter(ArrayList<Item> mPaymentItem, Context mContext) {
        this.mPaymentItem = mPaymentItem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: entered");
        //RESPONSIBLE FOR INFLATING THE 'item_list.xmlIEW
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);

        //Custom Class outlined below
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");
        Log.d(TAG, String.format("getItemCount: %d", mPaymentItem.size()));

        viewHolder.title.setText(mPaymentItem.get(i).getTitle());
        viewHolder.price.setText("$"+Double.toString(mPaymentItem.get(i).getPrice()));
        viewHolder.qty.setText("QTY: "+Integer.toString(mPaymentItem.get(i).getQty()));
        viewHolder.total.setText("$"+Double.toString(mPaymentItem.get(i).getTotal()));

//        viewHolder.item.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Log.d(TAG, "onClick: clicked on: " + mPaymentItem.get(i) );
//                Toast.makeText(mContext, (CharSequence) mPaymentItem.get(i), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mPaymentItem.size( );
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout item;
        TextView title;
        TextView price;
        TextView qty;
        TextView total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.itemContainer);
            title = itemView.findViewById(R.id.itemTitle);
            price = itemView.findViewById(R.id.itemPrice);
            qty = itemView.findViewById(R.id.itemQty);
            total = itemView.findViewById(R.id.itemTotal);
        }
    }
}

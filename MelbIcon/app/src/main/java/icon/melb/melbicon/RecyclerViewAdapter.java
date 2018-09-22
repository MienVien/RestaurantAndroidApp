package icon.melb.melbicon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Item> mData;

    public RecyclerViewAdapter(Context mContext, List<Item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void delete(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_item, viewGroup, false);
        Log.d("onCreateViewHolder","CreateViewHolder");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Item current = mData.get(i);
        myViewHolder.itemName.setText(current.getName());
        myViewHolder.itemDescription.setText(current.getDescription());
        myViewHolder.itemPrice.setText("" + current.getPrice());
        myViewHolder.itemImage.setImageResource(current.getThumbnail());
        Log.d("onBindViewHolder","BindViewHolder " + i);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView itemName, itemDescription, itemPrice;
        ImageView itemImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemDescription = (TextView) itemView.findViewById(R.id.itemDescription);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            itemImage = (ImageView) itemView.findViewById(R.id.imageView);

            itemImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //delete(getLayoutPosition());
            Toast.makeText(mContext, "Item clicked at " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}

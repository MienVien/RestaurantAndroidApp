package icon.melb.melbicon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Item> mData;
    private RetrieveMenuTask retriever;
    private Bitmap imageBitmap;

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
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Item current = mData.get(i);
        myViewHolder.itemName.setText(current.getTitle());
        myViewHolder.itemDescription.setText(current.getDescription());
        myViewHolder.itemPrice.setText("" + current.getPrice());
        try {
            imageBitmap = getImageFromUrl(current.getImg_src());
            myViewHolder.itemImage.setImageBitmap(imageBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);

            itemImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //delete(getLayoutPosition());
            Toast.makeText(mContext, "Item clicked at " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getImageFromUrl(String img_src) throws Exception {
        retriever = new RetrieveMenuTask();
        return retriever.execute(img_src).get();
    }

    public class RetrieveMenuTask extends AsyncTask<String, Void, Bitmap> {
        private Exception exception;
        private Bitmap img;

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myimg = BitmapFactory.decodeStream(input);
                return myimg;
            } catch (Exception e) {
                this.exception = e;;
            }
            return null;
        }
    }
}

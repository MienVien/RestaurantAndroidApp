package icon.melb.melbicon;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
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
            final Item item = mData.get(getLayoutPosition());
            View view;

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);

            LayoutInflater mInflater = LayoutInflater.from(mContext);

            view = mInflater.inflate(R.layout.item_onclick_dialog, null);

            TextView title = (TextView) view.findViewById(R.id.titleTextView);
            title.setText(item.getTitle());

            ImageButton backBtn = (ImageButton) view.findViewById(R.id.backBtn);

            final TextView amount = (TextView) view.findViewById(R.id.amountTextView);

            Button decreaseBtn = (Button) view.findViewById(R.id.decreaseBtn);
            decreaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantityOrdered = item.getQuantityOrdered();
                    if (quantityOrdered > 1) {
                        quantityOrdered--;
                        item.setQuantityOrdered(quantityOrdered);
                        amount.setText(Integer.toString(quantityOrdered));
                    }
                }
            });
            Button increaseBtn = (Button) view.findViewById(R.id.increaseBtn);
            increaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantityOrdered = item.getQuantityOrdered();
                    quantityOrdered++;
                    item.setQuantityOrdered(quantityOrdered);
                    amount.setText(Integer.toString(quantityOrdered));
                }
            });

            TextView priceTextView = (TextView) view.findViewById(R.id.priceTextView);
            priceTextView.setText("$" + item.getPrice() + " * " + amount.getText() + " = " + item.getPrice()*Integer.parseInt(amount.getText().toString()));

            EditText note = (EditText) view.findViewById(R.id.addNotes);

            Button addBtn = (Button) view.findViewById(R.id.addBtn);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });

            mBuilder.setView(view);

            final AlertDialog dialog = mBuilder.create();

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

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

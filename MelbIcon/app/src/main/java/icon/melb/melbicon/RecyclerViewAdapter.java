package icon.melb.melbicon;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<MenuItem> mData;
    private transient Bitmap imageBitmap;
    private Order order = MainMenu.currentOrder;
    private RetrieveImageTask imageRetriever;
    //private List<OrderItem> orderItemList = new ArrayList<>();

    public RecyclerViewAdapter(Context mContext, List<MenuItem> mData) {
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
        MenuItem current = mData.get(i);
        myViewHolder.itemName.setText(current.getTitle());
        myViewHolder.itemDescription.setText(current.getDescription());
        myViewHolder.itemPrice.setText("$" + current.getPrice());
        try {
            current.setImageBitmap(getImageFromUrl(Objects.requireNonNull(current).getImg_src()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        myViewHolder.itemImage.setImageBitmap(current.getImageBitmap());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView itemName, itemDescription, itemPrice;
        ImageView itemImage;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemDescription = (TextView) itemView.findViewById(R.id.itemDescription);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final MenuItem menuItem = mData.get(getLayoutPosition());
            View view;

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.item_onclick_dialog, null);

            final TextView title = (TextView) view.findViewById(R.id.titleTextView);
            title.setText(menuItem.getTitle());

            final List<String> selectedIngredients = new ArrayList<>();
            final String[] ingredients = itemDescription.getText().toString().split(",");

            for (String s:ingredients) {
                selectedIngredients.add(s.trim());
            }

            final ListView ingredientsListView = (ListView) view.findViewById(R.id.ingredientListView);
            ingredientsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.ingredient, R.id.checkText, ingredients);
            ingredientsListView.setAdapter(adapter);
            ingredientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected = ((TextView)view.findViewById(R.id.checkText)).getText().toString().trim();
                    if (selectedIngredients.contains(selected)) {
                        selectedIngredients.remove(selected);
                    }
                    else {
                        selectedIngredients.add(selected);
                    }
                }
            });

            ImageButton backBtn = (ImageButton) view.findViewById(R.id.backBtn);

            final TextView quantity = (TextView) view.findViewById(R.id.amountTextView);
            final TextView priceTextView = (TextView) view.findViewById(R.id.priceTextView);
            priceTextView.setText("$" + menuItem.getPrice() + " * " + quantity.getText() + " = $" + menuItem.getPrice()*Integer.parseInt(quantity.getText().toString()));

            quantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    priceTextView.setText("$" + menuItem.getPrice() + " * " + quantity.getText() + " = $" + menuItem.getPrice()*Integer.parseInt(quantity.getText().toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            Button decreaseBtn = (Button) view.findViewById(R.id.decreaseBtn);
            decreaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qty = Integer.parseInt(quantity.getText().toString());
                    if (qty > 1) {
                        qty--;
                        quantity.setText(Integer.toString(qty));
                    }
                }
            });
            Button increaseBtn = (Button) view.findViewById(R.id.increaseBtn);
            increaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qty = Integer.parseInt(quantity.getText().toString());
                        qty++;
                        quantity.setText(Integer.toString(qty));
                        Log.d("count",Integer.toString(qty));
                }
            });

            final EditText note = (EditText) view.findViewById(R.id.addNotes);

            Button addToOrderBtn = (Button) view.findViewById(R.id.addBtn);

            mBuilder.setView(view);

            final AlertDialog dialog = mBuilder.create();

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            addToOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qty = Integer.parseInt(quantity.getText().toString());
                    String notes = note.getText().toString();

                    if (notes.isEmpty()) {
                        OrderItem orderItem = new OrderItem(menuItem.getTitle(), menuItem.getPrice(), qty);
                        order.addToOrderItemList(orderItem);
                    } else{
                        OrderItem orderItem = new OrderItem(menuItem.getTitle(), menuItem.getPrice(), qty, notes);
                        order.addToOrderItemList(orderItem);
                    };

                    dialog.dismiss();
                    //Log.d("Current Order", String.valueOf(orderItemList.getOrderItemList().size()));
                }
            });
            dialog.show();
            //Toast.makeText(mContext, "MenuItem clicked at " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getImageFromUrl(String img_src) throws Exception {
        imageRetriever = new RetrieveImageTask();
        return imageRetriever.execute(img_src).get();
    }



    public class RetrieveImageTask extends AsyncTask<String, Void, Bitmap> {
        private Exception exception;
        private transient Bitmap img;

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
            }
            catch (Exception e) {
                this.exception = e;;
            }
            return null;
        }
    }
}

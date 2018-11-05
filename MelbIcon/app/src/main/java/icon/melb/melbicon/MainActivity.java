package icon.melb.melbicon;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    private RetrieveImageTask imageRetriever;
    private DatabaseReference mRef;
    public static List<MenuItem> lstSpecial, lstStarter, lstMain, lstSide, lstDessert, lstDrinks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();

        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout);

        FireBaseUtils.getDatabase();

        loadData();
        saveData();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterMenu();
            }
        });
    }

    private void enterMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    //////////////////////////////////DATABASE///////////////////////////////////////
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonlstSpecial = gson.toJson(lstSpecial);
        String jsonlstStarter = gson.toJson(lstStarter);
        String jsonlstMain = gson.toJson(lstMain);
        String jsonlstSide = gson.toJson(lstSide);
        String jsonlstDessert = gson.toJson(lstDessert);
        String jsonlstDrinks = gson.toJson(lstDrinks);

        editor.putString("lstSpecial", jsonlstSpecial);
        editor.putString("lstStarter", jsonlstStarter);
        editor.putString("lstMain", jsonlstMain);
        editor.putString("lstSide", jsonlstSide);
        editor.putString("lstDessert", jsonlstDessert);
        editor.putString("lstDrinks", jsonlstDrinks);

        editor.apply();
        Log.i("SaveData", "Saving Data");
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonlstSpecial = sharedPreferences.getString("lstSpecial", null);
        String jsonlstStarter = sharedPreferences.getString("lstStarter", null);
        String jsonlstMain = sharedPreferences.getString("lstMain", null);
        String jsonlstSide = sharedPreferences.getString("lstSide", null);
        String jsonlstDessert = sharedPreferences.getString("lstDessert", null);
        String jsonlstDrinks = sharedPreferences.getString("lstDrinks", null);
        Type type = new TypeToken<ArrayList<MenuItem>>() {}.getType();
        lstSpecial = gson.fromJson(jsonlstSpecial, type);
        lstStarter = gson.fromJson(jsonlstStarter, type);
        lstMain = gson.fromJson(jsonlstMain, type);
        lstSide = gson.fromJson(jsonlstSide, type);
        lstDessert = gson.fromJson(jsonlstDessert, type);
        lstDrinks = gson.fromJson(jsonlstDrinks, type);
        if (lstSpecial == null || lstSpecial.size() == 0) {
            operateDatabase();
            Log.i("LoadData","Loading data");
        }
    }
    private void operateDatabase() {
        lstSpecial = new ArrayList<>();
        lstStarter = new ArrayList<>();
        lstMain = new ArrayList<>();
        lstSide = new ArrayList<>();
        lstDessert = new ArrayList<>();
        lstDrinks = new ArrayList<>();

        mRef = FirebaseDatabase.getInstance().getReference("menu");
        mRef.keepSynced(true);

        DatabaseReference menu = mRef;
        DatabaseReference special = menu.child("0").child("special");
        DatabaseReference starter = menu.child("1").child("starter");
        DatabaseReference main = menu.child("2").child("main");
        DatabaseReference side = menu.child("3").child("side");
        DatabaseReference dessert = menu.child("4").child("dessert");
        //DatabaseReference drinks = menu.child("5").child("drinks");

        lstSpecial = getDatabase(special);
        lstStarter = getDatabase(starter);
        lstMain = getDatabase(main);
        lstSide = getDatabase(side);
        lstDessert = getDatabase(dessert);
        //lstDrinks = getDatabase(drinks);
    }

    private List<MenuItem> getDatabase(final DatabaseReference dataRef) {
        final List<MenuItem> dataList = new ArrayList<>();

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    if (child.child("available").getValue().equals(true)){

                        MenuItem menuItem = child.getValue(MenuItem.class);

                        try {
                            //menuItem.setImageBitmap(getImageFromUrl(Objects.requireNonNull(menuItem).getImg_src()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dataList.add(menuItem);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
        return dataList;
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

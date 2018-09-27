package icon.melb.melbicon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.fabric.sdk.android.Fabric;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainMenu extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private List<MenuItem> lstSpecial, lstStarter, lstMain, lstSide, lstDessert, lstDrinks;
    private DatabaseReference mRef;
    private RetrieveMenuTask retriever;

    public static List<Order> orders = new ArrayList<>();
    public static Order currentOrder = new Order();
    public static int currentOrderPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main_menu);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        lstSpecial = new ArrayList<>();
        lstStarter = new ArrayList<>();
        lstMain = new ArrayList<>();
        lstSide = new ArrayList<>();
        lstDessert = new ArrayList<>();
        lstDrinks = new ArrayList<>();

        operateDatabse();

        //Pass Order to other activities for use
        /*passOrderToActivity(RecyclerViewAdapter.class);
        passOrderToActivity(Tab1_Specials.class);*/

        // Create the adapter that will return a fragment for each of the six
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.customtab, null, false);

        setupTabIcons();

    }

    public static void newOrder() {
        currentOrder = new Order();
    }

    private void setupTabIcons() {
        View view0 = getLayoutInflater().inflate(R.layout.customtab, null);
        view0.setBackgroundResource(R.mipmap.specialsicons);
        tabLayout.getTabAt(0).setCustomView(view0);

        View view1 = getLayoutInflater().inflate(R.layout.customtab, null);
        view1.setBackgroundResource(R.mipmap.specialsicons); //starters
        tabLayout.getTabAt(1).setCustomView(view1);

        View view2 = getLayoutInflater().inflate(R.layout.customtab, null);
        view2.setBackgroundResource(R.mipmap.mainsicon2);
        tabLayout.getTabAt(2).setCustomView(view2);

        View view3 = getLayoutInflater().inflate(R.layout.customtab, null);
        view3.setBackgroundResource(R.mipmap.sidesicon2);
        tabLayout.getTabAt(3).setCustomView(view3);

        View view4 = getLayoutInflater().inflate(R.layout.customtab, null);
        view4.setBackgroundResource(R.mipmap.specialsicons); //dessert
        tabLayout.getTabAt(4).setCustomView(view4);

        View view5 = getLayoutInflater().inflate(R.layout.customtab, null);
        view5.setBackgroundResource(R.mipmap.drinksicon);
        tabLayout.getTabAt(5).setCustomView(view5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem menuItem) {
        // Handle action bar menuItem clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = menuItem.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            Bundle args;
            switch (position) {
                case 0:
                    Tab1_Specials tab1 = new Tab1_Specials();
                    args = new Bundle();
                    args.putSerializable("SpecialsList", (Serializable) lstSpecial);
                    tab1.setArguments(args);
                    return tab1;
                case 1:
                    Tab2_Starters tab2 = new Tab2_Starters();
                    args = new Bundle();
                    args.putSerializable("StartersList", (Serializable) lstStarter);
                    tab2.setArguments(args);
                    return tab2;
                case 2:
                    Tab3_Mains tab3 = new Tab3_Mains();
                    args = new Bundle();
                    args.putSerializable("MainsList", (Serializable) lstMain);
                    tab3.setArguments(args);
                    return tab3;
                case 3:
                    Tab4_Sides tab4 = new Tab4_Sides();
                    args = new Bundle();
                    args.putSerializable("SidesList", (Serializable) lstSide);
                    tab4.setArguments(args);
                    return tab4;
                case 4:
                    Tab5_Dessert tab5 = new Tab5_Dessert();
                    args = new Bundle();
                    args.putSerializable("DessertList", (Serializable) lstDessert);
                    tab5.setArguments(args);
                    return tab5;
                case 5:
                    Tab6_Drinks tab6 = new Tab6_Drinks();
                    args = new Bundle();
                    args.putSerializable("DrinksList", (Serializable) lstDrinks);
                    tab6.setArguments(args);
                    return tab6;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return 6;
        }
    }

    private void operateDatabse() {
        mRef = FirebaseDatabase.getInstance().getReference("menu");
        mRef.keepSynced(true);

        DatabaseReference menu = mRef.child("menu");
        DatabaseReference special = menu.child("0").child("special");
        DatabaseReference starter = menu.child("1").child("starter");
        DatabaseReference main = menu.child("2").child("main");
        DatabaseReference side = menu.child("3").child("side");
        DatabaseReference dessert = menu.child("4").child("dessert");
        DatabaseReference drinks = menu.child("5").child("drinks");

        lstSpecial = getDatabase(special);
        lstStarter = getDatabase(starter);
        lstMain = getDatabase(main);
        lstSide = getDatabase(side);
        lstDessert = getDatabase(dessert);
        lstDrinks = getDatabase(drinks);
    }

    private List<MenuItem> getDatabase(DatabaseReference dataRef) {
        final List<MenuItem> dataList = new ArrayList<>();

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    MenuItem menuItem = child.getValue(MenuItem.class);
                    try {
                        menuItem.setImageBitmap(getImageFromUrl(menuItem.getImg_src()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dataList.add(menuItem);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        return dataList;
    }

    public void passOrderToActivity(Class targetActivity) {
        Intent intent = new Intent(MainMenu.this, targetActivity);
        intent.putExtra("orders", (Serializable) orders);
        //getIntent().getSerializableExtra("Order");
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

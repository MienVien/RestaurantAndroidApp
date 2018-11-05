package icon.melb.melbicon;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class MainMenu extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private List<MenuItem> lstSpecial, lstStarter, lstMain, lstSide, lstDessert, lstDrinks;
    private DatabaseReference mRef;
    private DatabaseReference oRef;

    private RetrieveImageTask imageRetriever;
    //private RetrieveMenuTask menuRetriever;
    private ImageButton homeBtn, viewOrderBtn, assistantBtn;
    private Button stopButton;

    public static List<Order> orders = new ArrayList<>();
    public static Order currentOrder = new Order();
    public static int currentOrderPos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main_menu);

        lstSpecial = MainActivity.lstSpecial;
        lstStarter = MainActivity.lstStarter;
        lstMain = MainActivity.lstMain;
        lstSide = MainActivity.lstSide;
        lstDessert = MainActivity.lstDessert;
        lstDrinks = MainActivity.lstDrinks;

        // Create the adapter that will return a fragment for each of the six
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        View headerView = ((LayoutInflater) Objects.requireNonNull(getSystemService(Context.LAYOUT_INFLATER_SERVICE)))
                .inflate(R.layout.customtab, null, false);

        setupTabIcons();
        setButtonAction();

        /*if (lstSpecial.size() == 0)
            operateDatabase();
        */
    }

    private void setButtonAction() {

        homeBtn = (ImageButton) findViewById(R.id.homeButton);
        viewOrderBtn = (ImageButton) findViewById(R.id.viewOrderButton);
        assistantBtn = (ImageButton) findViewById(R.id.assistantButton);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Go back home", Toast.LENGTH_SHORT).show();
                Intent navigate = new Intent(MainMenu.this, WaitScreen.class);
                startActivity(navigate);
            }
        });

        viewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Toast.makeText(getContext(), "Go to view order", Toast.LENGTH_SHORT).show();
                viewOrder();
            }
        });

        assistantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainMenu.this);
                View mView = getLayoutInflater().inflate(R.layout.request_assistance_overlay, null);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                dialog.show();

                stopButton = mView.findViewById(R.id.stopButton);

                stopButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                // Toast.makeText(getContext(), "Ask for assistance", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewOrder() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainMenu.this);
        View mView = getLayoutInflater().inflate(R.layout.view_order_dialog, null);
        ImageButton backBtn = (ImageButton) mView.findViewById(R.id.backBtn);
        ImageButton submitBtn = (ImageButton) mView.findViewById(R.id.submitBtn);
        final SwipeMenuListView listView = (SwipeMenuListView) mView.findViewById(R.id.listView);
        final TextView totalPrice = (TextView) mView.findViewById(R.id.totalPrice);

        final ArrayAdapter adapter = new ArrayAdapter(MainMenu.this, android.R.layout.simple_list_item_1,  currentOrder.getOrderItemList());

        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        MainMenu.this);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        MainMenu.this);
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_action_name);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        MainMenu.currentOrder.getOrderItemList().remove(index);
                        listView.setAdapter(adapter);
                        totalPrice.setText("$" + MainMenu.currentOrder.getTotalPriceOrder());
                        Log.d("Delete", "Delete");
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        totalPrice.setText("$" + MainMenu.currentOrder.getTotalPriceOrder());

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentOrder.getOrderItemList().size() != 0) {
                    MainMenu.orders.add(MainMenu.currentOrder);

                    Intent navigate = new Intent(MainMenu.this, WaitScreen.class);

                    dialog.dismiss();
                    //////////////HERE....!!!!&&&&.//////
                    oRef = FirebaseDatabase.getInstance().getReference("order");
                    DatabaseReference ds = oRef.child("order").child("0");
                    DatabaseReference oi = ds.child("1");
                    oi.child("dishQty").setValue(currentOrder.getDishQty());
                    oi.child("date").setValue(currentOrder.getOrderTimeDate().toString());
                    DatabaseReference oi2 = oi.child("items");
                    oi2.setValue("test");
                    Integer count = 0;

                    Log.d(TAG, "orderItemSize: " + currentOrder.getOrderItemList().size());
                    for(OrderItem orderItem : currentOrder.getOrderItemList()) {
                        DatabaseReference oi3;
                        oi3 = oi2.child(count.toString());
                        ++count;
                        oi3.setValue("test");
                        oi3.child("name").setValue(orderItem.getTitle());

                        Log.d(TAG, "orderItemName " + orderItem.getTitle());
                    }
                    MainMenu.newOrder();


                    //oRef.child("order").setValue(student);
                    //////////////////////////////////////
                    startActivity(navigate);
                    Toast.makeText(MainMenu.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
                }
                else { Toast.makeText(MainMenu.this, "Please add a Menu Item, before submitting", Toast.LENGTH_SHORT).show(); }
            }
        });
        dialog.show();
    }

    public static void newOrder() {
        currentOrder = new Order();
    }

//////////////////////////////GENERATING MENU/////////////////////////////////////
    private void setupTabIcons() {

        View view0 = getLayoutInflater().inflate(R.layout.customtab, null);
        view0.setBackgroundResource(R.mipmap.specialsicons);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(view0);

        View view1 = getLayoutInflater().inflate(R.layout.customtab, null);
        view1.setBackgroundResource(R.mipmap.startersicon); //starters
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(view1);

        View view2 = getLayoutInflater().inflate(R.layout.customtab, null);
        view2.setBackgroundResource(R.mipmap.mainsicon2);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(view2);

        View view3 = getLayoutInflater().inflate(R.layout.customtab, null);
        view3.setBackgroundResource(R.mipmap.sidesicon2);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setCustomView(view3);

        View view4 = getLayoutInflater().inflate(R.layout.customtab, null);
        view4.setBackgroundResource(R.mipmap.dessertsicon2); //dessert
        Objects.requireNonNull(tabLayout.getTabAt(4)).setCustomView(view4);

        View view5 = getLayoutInflater().inflate(R.layout.customtab, null);
        view5.setBackgroundResource(R.mipmap.drinksicon);
        Objects.requireNonNull(tabLayout.getTabAt(5)).setCustomView(view5);
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

    //////////////////////////////////DATABASE///////////////////////////////////////

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
        DatabaseReference drinks = menu.child("5").child("drinks");

        lstSpecial = getDatabase(special);
        lstStarter = getDatabase(starter);
        lstMain = getDatabase(main);
        lstSide = getDatabase(side);
        lstDessert = getDatabase(dessert);
        lstDrinks = getDatabase(drinks);
    }

    private List<MenuItem> getDatabase(final DatabaseReference dataRef) {
        final List<MenuItem> dataList = new ArrayList<>();

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {

//                  Log.d(TAG, "getTitle: "+ dataSnapshot.child(Integer.toString(i)).child("title").getValue());
//                  Log.d(TAG, "getAvailable: " + dataSnapshot.child(Integer.toString(i)).child("available").getValue());

                    if (child.child("available").getValue().equals(true)){
                        Log.d(TAG, "DISPLAYED");

                        //Log.d(TAG, "Index: " +i);

                        MenuItem menuItem = child.getValue(MenuItem.class);

                        try {
                            menuItem.setImageBitmap(getImageFromUrl(Objects.requireNonNull(menuItem).getImg_src()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dataList.add(menuItem);
                    }
                    ++index;
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

    public class RetrieveImageTask extends icon.melb.melbicon.RetrieveImageTask {
        private Exception exception;
        private transient Bitmap img;
        ProgressDialog dialog;
        Integer index;

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


//    public class RetrieveMenuTask extends AsyncTask<String, Void, Bitmap> {
//        private Exception exception;
//        private transient Bitmap img;
//
//        @Override
//        protected Bitmap doInBackground(String... urls) {
//            try {
//                URL url = new URL(urls[0]);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                //connection.setDoInput(true);
//                connection.connect();
//                InputStream input = connection.getInputStream();
//                Bitmap myimg = BitmapFactory.decodeStream(input);
//
//                return myimg;
//            }
//            catch (Exception e) {
//                this.exception = e;;
//            }
//            return null;
//        }
//    }

}

package icon.melb.melbicon;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Tab2_Starters extends Fragment {
    List<MenuItem> lstMenuItem;

    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayout;
    RecyclerViewAdapter mAdapter;

    ImageButton homeBtn, viewOrderBtn, assistantBtn;

    private DatabaseReference mRef;

    public Tab2_Starters() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab2__starters, container, false  );

        Bundle args = getArguments();

        lstMenuItem = (List<MenuItem>) args.getSerializable("StartersList");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        mGridLayout = new GridLayoutManager(getActivity(), 4);

        mRecyclerView.setLayoutManager(mGridLayout);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewAdapter(getActivity(), lstMenuItem);

        mRecyclerView.setAdapter(mAdapter);

        homeBtn = (ImageButton) view.findViewById(R.id.homeButton);
        viewOrderBtn = (ImageButton) view.findViewById(R.id.viewOrderButton);
        assistantBtn = (ImageButton) view.findViewById(R.id.assistantButton);

        setButtonAction();

        return view;
    }

    private void setButtonAction() {
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Go back home", Toast.LENGTH_SHORT).show();
            }
        });

        viewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Go to view order", Toast.LENGTH_SHORT).show();
                viewOrder();
            }
        });

        assistantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ask for assistance", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewOrder() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.view_order_dialog, null);
        ImageButton backBtn = (ImageButton) mView.findViewById(R.id.backBtn);
        ImageButton submitBtn = (ImageButton) mView.findViewById(R.id.submitBtn);
        final SwipeMenuListView listView = (SwipeMenuListView) mView.findViewById(R.id.listView);
        final TextView totalPrice = (TextView) mView.findViewById(R.id.totalPrice);

        final ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,  MainMenu.currentOrder.getOrderItemList());
        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
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
                MainMenu.orders.add(MainMenu.currentOrder);
                MainMenu.newOrder();
                dialog.dismiss();
                Log.d("Submit Order", "Submitted Order");
                Log.d("Size", Integer.toString(MainMenu.orders.size()));
                Log.d("Test Details", MainMenu.orders.get(0).getOrderItemList().get(0).getTitle());
            }
        });

        dialog.show();
    }
}

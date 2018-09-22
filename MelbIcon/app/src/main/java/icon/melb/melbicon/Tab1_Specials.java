package icon.melb.melbicon;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Tab1_Specials extends Fragment {
    List<Item> lstItem;

    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayout;
    RecyclerViewAdapter mAdapter;

    ImageButton homeBtn, viewOrderBtn, assistantBtn;

    public Tab1_Specials() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab1__specials, container, false  );

        lstItem = getData();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        mAdapter = new RecyclerViewAdapter(getActivity(), lstItem);

        mRecyclerView.setAdapter(mAdapter);

        mGridLayout = new GridLayoutManager(getActivity(), 4);

        mRecyclerView.setLayoutManager(mGridLayout);

        homeBtn = (ImageButton) view.findViewById(R.id.homeButton);
        viewOrderBtn = (ImageButton) view.findViewById(R.id.viewOrderButton);
        assistantBtn = (ImageButton) view.findViewById(R.id.assistantButton);

        setButtonAction();
        return view;
    }

    public static List<Item> getData() {
        List<Item> data = new ArrayList<>();
        String[] names = {"Chicken Parma", "Broken Rice", "Noodle Soup"};
        String[] descriptions = {"This is made by Chicken", "This is made by rice", "This is made by noodle"};
        int[] prices = {11, 15, 14};
        Boolean[] isVegetarians = {false, true, false};
        int[] images = {R.mipmap.drinksicon, R.mipmap.specialsicons, R.mipmap.cookingstatusicon};

        for (int i=0; i<100; i++) {
            int k = i % names.length;
            Item item = new Item(names[k], descriptions[k], prices[k], isVegetarians[k], images[k]);
            data.add(item);
        }

        return data;
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Go back", Toast.LENGTH_SHORT).show();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Submit Order", Toast.LENGTH_SHORT).show();
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}

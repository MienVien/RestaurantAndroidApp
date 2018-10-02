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

import java.util.List;
import java.util.Objects;

public class Tab5_Dessert extends Fragment {
    List<MenuItem> lstMenuItem;

    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayout;
    RecyclerViewAdapter mAdapter;

    public Tab5_Dessert() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_order, container, false  );

        Bundle args = getArguments();

        lstMenuItem = (List<MenuItem>) Objects.requireNonNull(args).getSerializable("DessertList");

        Log.d("Passing Object", String.valueOf(lstMenuItem));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        mGridLayout = new GridLayoutManager(getActivity(), 4);

        mRecyclerView.setLayoutManager(mGridLayout);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewAdapter(getActivity(), lstMenuItem);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}

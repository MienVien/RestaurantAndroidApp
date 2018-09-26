package icon.melb.melbicon;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Tab4_Sides extends Fragment {
    List<Item> lstItem;

    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayout;
    RecyclerViewAdapter mAdapter;

    ImageButton homeBtn, viewOrderBtn, assistantBtn;

    public Tab4_Sides() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab4__sides, container, false  );

        Bundle args = getArguments();

        lstItem = (List<Item>) args.getSerializable("SidesList");

        Log.d("Passing Object", String.valueOf(lstItem));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        mGridLayout = new GridLayoutManager(getActivity(), 4);

        mRecyclerView.setLayoutManager(mGridLayout);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewAdapter(getActivity(), lstItem);

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

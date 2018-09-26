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

public class Tab1_Specials extends Fragment {
    List<Item> lstItem;

    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayout;
    RecyclerViewAdapter mAdapter;

    ImageButton homeBtn, viewOrderBtn, assistantBtn;

    private DatabaseReference mRef;

    public Tab1_Specials() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab1__specials, container, false  );

        operateDatabse();

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

    private void operateDatabse() {
        mRef = FirebaseDatabase.getInstance().getReference("menu");
        DatabaseReference menu = mRef.child("menu");
        DatabaseReference special = menu.child("0").child("special");

        lstItem = getDatabase(special);

    }

    private List<Item> getDatabase(DatabaseReference dataRef) {
        final List<Item> dataList = new ArrayList<>();

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Item item = child.getValue(Item.class);
                    dataList.add(item);
                    /*Toast.makeText(getContext(), item.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("KEY", child.getKey());
                    Log.d("Item", item.toString());*/
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        return dataList;
    }
}

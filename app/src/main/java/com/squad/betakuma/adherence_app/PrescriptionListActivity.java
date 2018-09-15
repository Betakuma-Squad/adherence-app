package com.squad.betakuma.adherence_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squad.betakuma.adherence_app.ocr.OcrCaptureActivity;

public class PrescriptionListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_list);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mFab = findViewById(R.id.fab);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an cardview_medication (see also next example)

        mAdapter = new PrescriptionListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OcrCaptureActivityIntent = new Intent(view.getContext(), OcrCaptureActivity.class);
                startActivity(OcrCaptureActivityIntent);
            }
        });
    }
}

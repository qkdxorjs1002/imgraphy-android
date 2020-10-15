package com.teamig.imgraphy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView graphyListView;
    private GraphyListAdapter graphyListAdapter;
    private LinearLayoutManager graphyListLayoutManager;

    private Imgraphy imgraphy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgraphy = new Imgraphy(new Imgraphy.Options(20, 0));
        imgraphy.refreshList();

        graphyListView = (RecyclerView) findViewById(R.id.GraphyListView);
        graphyListAdapter = new GraphyListAdapter(imgraphy);
        graphyListLayoutManager = new LinearLayoutManager(this);

        graphyListView.setHasFixedSize(true);
        graphyListView.setAdapter(graphyListAdapter);
        graphyListView.setLayoutManager(graphyListLayoutManager);

    }
}
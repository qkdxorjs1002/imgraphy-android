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


        graphyListView = (RecyclerView) findViewById(R.id.GraphyListView);
        graphyListAdapter = new GraphyListAdapter();
        graphyListLayoutManager = new LinearLayoutManager(this);

        graphyListView.setHasFixedSize(true);
        graphyListView.setAdapter(graphyListAdapter);
        graphyListView.setLayoutManager(graphyListLayoutManager);

        imgraphy = new Imgraphy(new ImgraphyType.Options(50, 0));
        imgraphy.refreshList(graphyListAdapter);
    }
}
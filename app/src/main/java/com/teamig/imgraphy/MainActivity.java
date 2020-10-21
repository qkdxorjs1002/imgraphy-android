package com.teamig.imgraphy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button graphyListRefresh;
    private EditText graphySearchInput;
    private Button graphyClearInput;

    private RecyclerView graphyListView;
    private GraphyListAdapter graphyListAdapter;
    private LinearLayoutManager graphyListLayoutManager;

    private Imgraphy imgraphy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphyListRefresh = (Button) findViewById(R.id.GraphyListRefresh);
        graphySearchInput = (EditText) findViewById(R.id.GraphySearchInput);
        graphyClearInput = (Button) findViewById(R.id.GraphyClearInput);

        graphyListView = (RecyclerView) findViewById(R.id.GraphyListView);
        graphyListAdapter = new GraphyListAdapter();
        graphyListLayoutManager = new LinearLayoutManager(this);

        graphyListView.setHasFixedSize(true);
        graphyListView.setAdapter(graphyListAdapter);
        graphyListView.setLayoutManager(graphyListLayoutManager);

        graphyListRefresh.setOnClickListener(v -> {
            imgraphy.setOptions(new ImgraphyType.Options(50, 0, graphySearchInput.getText().toString()));
            imgraphy.refreshList(graphyListAdapter);
        });

        graphyClearInput.setOnClickListener(v -> {
            graphySearchInput.setText("");
        });

        imgraphy = new Imgraphy(new ImgraphyType.Options(50, 0));
        imgraphy.refreshList(graphyListAdapter);
    }
}
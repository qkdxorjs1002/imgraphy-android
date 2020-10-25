package com.teamig.imgraphy.ui.graphy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.teamig.imgraphy.adapter.GraphyListAdapter;
import com.teamig.imgraphy.service.ImgraphyType;
import com.teamig.imgraphy.R;

public class GraphyFragment extends Fragment {

    private GraphyViewModel viewModel;

    private Button graphyListRefresh;
    private EditText graphySearchInput;
    private Button graphyClearInput;

    private RecyclerView graphyListView;
    private GraphyListAdapter graphyListAdapter;
    private StaggeredGridLayoutManager graphyListLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(GraphyViewModel.class);

        View root = inflater.inflate(R.layout.fragment_graphy, container, false);

        graphyListRefresh = (Button) root.findViewById(R.id.GraphyListRefresh);
        graphySearchInput = (EditText) root.findViewById(R.id.GraphySearchInput);
        graphyClearInput = (Button) root.findViewById(R.id.GraphyClearInput);

        graphyListView = (RecyclerView) root.findViewById(R.id.GraphyListView);
        graphyListAdapter = new GraphyListAdapter();
        graphyListLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        graphyListView.setHasFixedSize(true);
        graphyListView.setAdapter(graphyListAdapter);
        graphyListView.setLayoutManager(graphyListLayoutManager);

        viewModel.getGraphy(new ImgraphyType.Options.List(50, 0, graphySearchInput.getText().toString()))
                .observe(getViewLifecycleOwner(), graphy -> {
                    graphyListAdapter.updateList(graphy);
                    graphyListAdapter.notifyDataSetChanged();
                    graphyListView.scrollToPosition(0);
                });

        graphyListRefresh.setOnClickListener(v -> {
            viewModel.getGraphy(new ImgraphyType.Options.List(50, 0, graphySearchInput.getText().toString()))
                    .observe(getViewLifecycleOwner(), graphy -> {
                        graphyListAdapter.updateList(graphy);
                        graphyListAdapter.notifyDataSetChanged();
                        graphyListView.scrollToPosition(0);
                    });
        });

        graphyClearInput.setOnClickListener(v -> {
            graphySearchInput.setText("");
        });

        return root;
    }
}
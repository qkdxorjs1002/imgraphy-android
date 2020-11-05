package com.teamig.imgraphy.ui.viewer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.teamig.imgraphy.R;
import com.teamig.imgraphy.adapter.TagListAdapter;
import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

public class ViewerFragment extends Fragment {

    private String userID, uuid, tags;

    private ViewerViewModel viewModel;
    private View root;

    private ImageView ViewerImage;

    private RecyclerView tagListView;
    private TagListAdapter tagListAdapter;
    private FlowLayoutManager tagListLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ViewerViewModel.class);
        root = inflater.inflate(R.layout.fragment_viewer, container, false);

        initReferences();
        initEvents();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userID = ViewerFragmentArgs.fromBundle(getArguments()).getUserID();
        uuid = ViewerFragmentArgs.fromBundle(getArguments()).getUuid();
        tags = ViewerFragmentArgs.fromBundle(getArguments()).getTags();

        Glide.with(root)
                .load("https://api.novang.tk/imgraphy/files/img/" + uuid + "/" + uuid)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL)
                .into(ViewerImage);

        viewModel.parseTag(tags).observe(getViewLifecycleOwner(), strings -> {
            tagListAdapter.updateList(strings);
        });
    }

    private void initReferences() {
        ViewerImage = (ImageView) root.findViewById(R.id.ViewerImage);

        tagListView = (RecyclerView) root.findViewById(R.id.TagListView);
        tagListAdapter = new TagListAdapter();
        tagListLayoutManager = new FlowLayoutManager();
        tagListLayoutManager.setAutoMeasureEnabled(true);
        tagListLayoutManager.setAlignment(Alignment.LEFT);

        tagListView.setHasFixedSize(true);
        tagListView.setAdapter(tagListAdapter);
        tagListView.setLayoutManager(tagListLayoutManager);
    }

    private void initEvents() {

    }
}
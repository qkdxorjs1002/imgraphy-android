package com.teamig.imgraphy;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GraphyListAdapter extends RecyclerView.Adapter<GraphyListAdapter.ViewHolder> {
    private Imgraphy imgraphy;

    public GraphyListAdapter(Imgraphy imgraphy) {
        this.imgraphy = imgraphy;
    }

    @NonNull
    @Override
    public GraphyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView listItemImage = (ImageView) holder.view.findViewById(R.id.ListItemImage);
        TextView listItemShareCount = (TextView) holder.view.findViewById(R.id.ListItemShareCount);
        TextView listItemFavCount = (TextView) holder.view.findViewById(R.id.ListItemFavCount);

        Imgraphy.Graphy graphy = imgraphy.getList()[position];

        listItemShareCount.setText(String.valueOf(graphy.shrcnt));
        listItemFavCount.setText(String.valueOf(graphy.favcnt));
    }

    @Override
    public int getItemCount() {
        if (imgraphy.getList() == null) {

            return 0;
        }
        return imgraphy.getList().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }
}

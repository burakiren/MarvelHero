package com.burakiren.marveldemo.ui.marvel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.burakiren.domain.model.Hero;
import com.burakiren.marveldemo.BR;
import com.burakiren.marveldemo.R;
import com.burakiren.marveldemo.databinding.ComicItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {

    private List<Hero> dataModelList;
    private Context context;

    public ComicsAdapter(ArrayList<Hero> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @Override
    public ComicsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        ComicItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.comic_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hero dataModel = dataModelList.get(position);
        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ComicItemBinding itemRowBinding;

        public ViewHolder(ComicItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.data, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    public void setItems(List<Hero> heroes) {
        this.dataModelList.clear();
        this.dataModelList.addAll(heroes);
        notifyDataSetChanged();
    }


}
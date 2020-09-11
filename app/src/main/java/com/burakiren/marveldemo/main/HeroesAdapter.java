package com.burakiren.marveldemo.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.burakiren.domain.model.Hero;
import com.burakiren.marveldemo.BR;
import com.burakiren.marveldemo.R;
import com.burakiren.marveldemo.databinding.HorizontalHeroesItemBinding;

import java.util.List;

public class HeroesAdapter extends RecyclerView.Adapter<HeroesAdapter.ViewHolder> {

    private List<Hero> dataModelList;
    private Context context;

    public HeroesAdapter(List<Hero> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @Override
    public HeroesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        HorizontalHeroesItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.horizontal_heroes_item, parent, false);

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
        public HorizontalHeroesItemBinding itemRowBinding;

        public ViewHolder(HorizontalHeroesItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.data, obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
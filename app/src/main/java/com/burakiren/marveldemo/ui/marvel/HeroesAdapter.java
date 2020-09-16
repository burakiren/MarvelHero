package com.burakiren.marveldemo.ui.marvel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.burakiren.domain.model.Hero;
import com.burakiren.marveldemo.BR;
import com.burakiren.marveldemo.R;
import com.burakiren.marveldemo.databinding.HorizontalHeroesItemBinding;
import com.burakiren.marveldemo.helper.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class HeroesAdapter extends RecyclerView.Adapter<HeroesAdapter.ViewHolder> {


    private List<Hero> dataModelList;
    private Context context;
    private final OnItemClickListener listener;


    public HeroesAdapter(ArrayList<Hero> dataModelList, Context ctx, OnItemClickListener listener) {
        this.dataModelList = dataModelList;
        context = ctx;
        this.listener = listener;
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
        holder.bind(dataModel, listener);
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

        public void bind(Object obj, final OnItemClickListener listener) {
            itemRowBinding.setVariable(BR.data, obj);
            itemRowBinding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick((Hero) obj);
                }
            });

        }
    }

    public void setItems(List<Hero> heroes) {
        this.dataModelList.addAll(heroes);
        notifyDataSetChanged();
    }
}
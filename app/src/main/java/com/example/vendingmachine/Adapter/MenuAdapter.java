package com.example.vendingmachine.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendingmachine.Interface.RecyclerViewClick;
import com.example.vendingmachine.Model.MenuModel;
import com.example.vendingmachine.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    Context context;
    private List<MenuModel> list;
    RecyclerViewClick listener;

    public MenuAdapter(Context context, List<MenuModel> boardingList, RecyclerViewClick listener) {
        this.context = context;
        this.list = boardingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent, false);
        final MenuAdapter.ViewHolder viewHolder = new MenuAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuModel menuModel = list.get(holder.getAdapterPosition());
        holder.tvmenu.setText(menuModel.getMenu());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmenu = itemView.findViewById(R.id.tvmenu);
        }
    }
}

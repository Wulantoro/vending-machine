package com.example.vendingmachine.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendingmachine.Activity.TransaksiActivity;
import com.example.vendingmachine.Model.Barang;
import com.example.vendingmachine.R;

import java.util.ArrayList;
import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    private List<Barang> list;
    private Context context;

    public BarangAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public BarangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_barang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BarangAdapter.ViewHolder holder, int position) {
        final Barang barang = list.get(holder.getAdapterPosition());
        holder.tvnama.setText(list.get(position).getNama());
        holder.tvharga.setText(list.get(position).getHarga());
        holder.tvstock1.setText(list.get(position).getStock());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Integer.parseInt(barang.getStock()) == 0)) {
                        Toast.makeText(context, "Stock kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(context, TransaksiActivity.class);
                        intent.putExtra("key", barang);
                        context.startActivity(intent);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Barang barang) {
        list.add(barang);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<Barang> moveResult) {
        for (Barang result : moveResult) {
            add(result);
        }
    }

    private void remove(Barang barang) {
        int position = list.indexOf(barang);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void clearAll() {
        if (list.isEmpty()) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    private Barang getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return  null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvnama, tvharga, tvstock1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnama = itemView.findViewById(R.id.tvnama);
            tvharga = itemView.findViewById(R.id.tvharga);
            tvstock1 = itemView.findViewById(R.id.tvstock1);
        }
    }
}

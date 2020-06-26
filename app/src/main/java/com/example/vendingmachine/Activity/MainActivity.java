package com.example.vendingmachine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.vendingmachine.Adapter.MenuAdapter;
import com.example.vendingmachine.Interface.RecyclerViewClick;
import com.example.vendingmachine.Model.MenuModel;
import com.example.vendingmachine.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<MenuModel> list = new ArrayList<>();

    private RecyclerView rvmenu;
    private MenuAdapter menuAdapter;
    private MenuModel menuModel;
    private LinearLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvmenu = findViewById(R.id.rvmenu);
        parentLayout = findViewById(R.id.parentLayout);

        listMenu();
    }

    private void listMenu() {
//        0
        menuModel = new MenuModel(R.string.beli);
        list.add(menuModel);
//        1
        menuModel = new MenuModel(R.string.update_stock);
        list.add(menuModel);

        menuAdapter = new MenuAdapter(this, list, new RecyclerViewClick() {
            @Override
            public void onItemClick(View v, int position) {
                if (position == 0) {
                    intenToBelanja();
                } else if (position == 1) {
                    intentToUpdate();
                }
            }
        });

        rvmenu.setHasFixedSize(true);
        rvmenu.setAdapter(menuAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvmenu.setLayoutManager(linearLayoutManager);
        rvmenu.setItemAnimator(new DefaultItemAnimator());
    }

    private void intenToBelanja() {
        Intent intent = new Intent(getApplicationContext(), BelanjaActivity.class);
        startActivity(intent);
    }

    private void intentToUpdate() {
        Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
        startActivity(intent);
    }
}
package com.example.vendingmachine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.vendingmachine.Adapter.BarangAdapter;
import com.example.vendingmachine.Model.Barang;
import com.example.vendingmachine.R;
import com.example.vendingmachine.Utils.GlobalVars;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BelanjaActivity extends AppCompatActivity {

    private RecyclerView rvbarang;
    private BarangAdapter barangAdapter;
    private Barang barang;
    private Gson gson;

    private static final String TAG = BelanjaActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belanja);

        List<Barang> result = new ArrayList<>();
        result.add(barang);

        gson = new Gson();
        rvbarang = findViewById(R.id.rvbarang);
        barangAdapter = new BarangAdapter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BelanjaActivity.this);
        rvbarang.setLayoutManager(layoutManager);
        loadbarang();
        rvbarang.setAdapter(barangAdapter);
    }

    private void loadbarang() {
        if (barangAdapter != null)
            barangAdapter.clearAll();

        AndroidNetworking.get(GlobalVars.BASE_IP + "barang")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Barang> result = new ArrayList<>();
                        try {
                            Log.e(TAG,"Tampil barang = " + response.toString(1));

                            if (result != null)
                                result.clear();

                            String message = response.getString("message");
                            if (message.equals("Barang ditemukan")) {
                                String records = response.getString("data");
                                JSONArray jsonArray = new JSONArray(records);

                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Barang barang = gson.fromJson(jsonArray.getJSONObject(i).toString(), Barang.class);
                                        result.add(barang);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        barangAdapter.addAll(result);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
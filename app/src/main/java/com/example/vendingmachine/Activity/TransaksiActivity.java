package com.example.vendingmachine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.vendingmachine.Model.Barang;
import com.example.vendingmachine.R;
import com.example.vendingmachine.Utils.GlobalVars;
import com.travijuu.numberpicker.library.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TransaksiActivity extends AppCompatActivity {

    private NumberPicker numberPicker;
    private TextView tvnama;
    private TextView tvharga;
    private TextView tvtot1;
    private TextView tvsisa1;
    private EditText etbayar;
    private Button btnbayar;

    private Barang barang;
    private String id_brg;

    int harga, totbarang, hasil,  kembali;
    String sharga, stotbarang ;
    private Handler mHandler;

    private final static String TAG = TransaksiActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        tvnama = findViewById(R.id.tvnama);
        tvharga = findViewById(R.id.tvharga);
        tvtot1 = findViewById(R.id.tvtot1);
        tvsisa1 = findViewById(R.id.tvsisa1);
        etbayar = findViewById(R.id.etbayar);
        btnbayar = findViewById(R.id.btnbayar);

        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setDisplayFocusable(true);
        numberPicker.setMax(10000);
        numberPicker.setMin(0);
        numberPicker.setUnit(1);
        numberPicker.setValue(0);

        barang = getIntent().getParcelableExtra("key");
        tvnama.setText(barang.getNama());
        tvharga.setText(barang.getHarga());
        id_brg = barang.getId();
        Log.e(TAG, "id_brg = " + id_brg);

        this.mHandler = new Handler();
        m_Runnable.run();

        btnbayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanBayar();

            }
        });
    }

    private final Runnable m_Runnable = new Runnable() {
        @Override
        public void run() {
            totalBayar();
            hitungKembali();
            TransaksiActivity.this.mHandler.postDelayed(m_Runnable, 2000);
        }
    };

    public void totalBayar() {
        sharga = tvharga.getText().toString();
        harga = Integer.parseInt(sharga);
        totbarang = Integer.parseInt(String.valueOf(numberPicker.getValue()));
        hasil = harga * totbarang;
        tvtot1.setText(hasil + "");
    }

    public void hitungKembali() {

        final  String sbayar = etbayar.getText().toString();
        final int bayar = !sbayar.equals("")?Integer.parseInt(sbayar) : 0;

        sharga = tvharga.getText().toString();
        harga = Integer.parseInt(sharga);
        totbarang = Integer.parseInt(String.valueOf(numberPicker.getValue()));
        hasil = harga * totbarang;
        kembali = bayar - hasil;
        tvsisa1.setText(kembali + "");
    }

    private void simpanBayar() {
        final  String sbayar = etbayar.getText().toString();
        final int bayar = !sbayar.equals("")?Integer.parseInt(sbayar) : 0;

        if (bayar == 2000 || bayar == 5000 || bayar == 10000 || bayar == 20000 || bayar == 50000 ) {
            JSONObject jsonObject = new JSONObject();

            try {
                JSONArray jsonArray = new JSONArray();
                jsonObject.put("id_brg", id_brg);
                jsonObject.put("qty", String.valueOf(numberPicker.getValue()));

                jsonArray.put(jsonObject);
                Log.e("coba input p = ", jsonArray.toString(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AndroidNetworking.post(GlobalVars.BASE_IP + "barang")
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String message = response.getString("message");
                                Toast.makeText(TransaksiActivity.this, message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), BelanjaActivity.class));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(TransaksiActivity.this, "Transaksi gagal", Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            Toast.makeText(this, "Kami hanya menerima uang pecahan 2000, 5000, 10000, 20000, 50000", Toast.LENGTH_SHORT).show();
        }
    }
}
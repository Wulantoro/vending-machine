package com.example.vendingmachine.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Barang implements Parcelable
{

    private String id;
    private String nama;
    private String harga;
    private String stock;
    public final static Parcelable.Creator<Barang> CREATOR = new Creator<Barang>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Barang createFromParcel(Parcel in) {
            return new Barang(in);
        }

        public Barang[] newArray(int size) {
            return (new Barang[size]);
        }

    }
            ;

    protected Barang(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.nama = ((String) in.readValue((String.class.getClassLoader())));
        this.harga = ((String) in.readValue((String.class.getClassLoader())));
        this.stock = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Barang() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(nama);
        dest.writeValue(harga);
        dest.writeValue(stock);
    }

    public int describeContents() {
        return 0;
    }

}

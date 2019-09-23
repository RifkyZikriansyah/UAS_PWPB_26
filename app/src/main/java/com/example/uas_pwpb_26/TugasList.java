package com.example.uas_pwpb_26;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class TugasList extends ArrayAdapter<Tugas> {
    private Activity context;
    private List<Tugas> tugasList;

    public TugasList(Activity context, List<Tugas> tugasList){
        super(context, R.layout.activity_list, tugasList);
        this.context = context;
        this.tugasList = tugasList;

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_list, null, true);

        TextView textViewTugas = (TextView) listViewItem.findViewById(R.id.textViewTugas);
        TextView textViewDaftar = (TextView) listViewItem.findViewById(R.id.textViewDaftar);

        Tugas tugas = tugasList.get(position);

        textViewTugas.setText(tugas.getTugasNama());
        textViewDaftar.setText(tugas.getTugasDaftar());

        return listViewItem;
    }
}

package com.example.uas_pwpb_26;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends AppCompatActivity {

    ImageButton btn_tambah;

    ListView listViewTugas;
    List<Tugas> tugasList;

    DatabaseReference databaseTugas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        databaseTugas = FirebaseDatabase.getInstance().getReference("Tugas");

        listViewTugas = (ListView) findViewById(R.id.listViewTugas);
        tugasList = new ArrayList<>();

        listViewTugas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tugas tugas = tugasList.get(i);

                showUpdateDialog(tugas.getTugasId(), tugas.getTugasNama(), tugas.getTugasDaftar());

                return false;
            }
        });

        btn_tambah = (ImageButton) findViewById(R.id.btn_tambah);
        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Main2Activity = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(Main2Activity);
                finish();

            }
        });

    }

    private void showUpdateDialog(final String tugasId, String tugasNama, String tugasDaftar){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextName2 = (EditText) findViewById(R.id.editTextName2);
        final Button buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Updating Tugas  "+ tugasNama);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        

    }



    private boolean updateTugas(String id, String nama, String daftar){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tugas").child(id);
        Tugas tugas = new Tugas(id, nama, daftar);
        databaseReference.setValue(tugas);
        Toast.makeText(this, "MataPelajaran Berhasil diEdit", Toast.LENGTH_LONG).show();
        return true;
    }

    private void deleteTugas(String Id) {
        DatabaseReference drTugas = FirebaseDatabase.getInstance().getReference("tugas").child(Id);
        drTugas.removeValue();

        Toast.makeText(getApplicationContext(), "Artist Deleted", Toast.LENGTH_LONG).show();

    }

    protected void onStart(){
        super.onStart();

        databaseTugas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tugasList.clear();
                for (DataSnapshot tugasSnapShot : dataSnapshot.getChildren()) {


                    Tugas tugas = tugasSnapShot.getValue(Tugas.class);
                    tugasList.add(tugas);


                }

                TugasList adapter = new TugasList(Main2Activity.this, tugasList);
                listViewTugas.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

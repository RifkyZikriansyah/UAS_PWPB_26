package com.example.uas_pwpb_26;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main3Activity extends AppCompatActivity {

    EditText editTextName, editTextName2;
    Button buttonAddTugas;
    DatabaseReference databaseTugas;

    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        databaseTugas = FirebaseDatabase.getInstance().getReference("Tugas");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName2 = (EditText) findViewById(R.id.editTextName2);






        buttonAddTugas = (Button) findViewById(R.id.buttonAddTugas);
        buttonAddTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Main3Activity = new Intent(Main3Activity.this, Main2Activity.class);
                startActivity(Main3Activity);
                finish();
                AddTugas();

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

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = editTextName.getText().toString().trim();
                String daftar = editTextName2.getText().toString().trim();

                if (TextUtils.isEmpty(nama)){

                    editTextName.setError("MataPelajaran Harus Diisi");
                    return;

                }

                updateTugas(tugasId, nama, daftar);
                alertDialog.dismiss();


            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTugas(tugasId);
            }
        });


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








    private void AddTugas(){
        String mapel = editTextName.getText().toString().trim();
        String daftar = editTextName2.getText().toString().trim();

        if (!TextUtils.isEmpty(mapel)){

            String id = databaseTugas.push().getKey();

            Tugas tugas = new Tugas(id, mapel, daftar);

            databaseTugas.child(id).setValue(tugas);

            Toast.makeText(this, "Tugas Telah Dimasukkan", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "Mapel Harus Diisi", Toast.LENGTH_LONG ).show();
        }
    }

}

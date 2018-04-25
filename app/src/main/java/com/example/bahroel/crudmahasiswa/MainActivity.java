package com.example.bahroel.crudmahasiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.bahroel.crudmahasiswa.Model.Mahasiswa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    EditText edtNrp, edtNama, edtJurusan, edtKelas, edtTelp, edtAlamat;
    ProgressBar progressBar;
    ListView listView;
    Button btnAdd;

    List<Mahasiswa> mahasiswaList;
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNrp = (EditText)findViewById(R.id.edtNrp);
        edtNama = (EditText)findViewById(R.id.edtNama);
        edtJurusan = (EditText)findViewById(R.id.edtJurusan);
        edtKelas = (EditText)findViewById(R.id.edtKelas);
        edtTelp = (EditText)findViewById(R.id.edtTelp);
        edtAlamat = (EditText)findViewById(R.id.edtAlamat);
        btnAdd = (Button) findViewById(R.id.btnTambahkan);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        listView = (ListView)findViewById(R.id.listViewMahasiswa);

        mahasiswaList = new ArrayList<Mahasiswa>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isUpdating){
                    createMahasiswa();
                }
            }
        });

    }

    private void createMahasiswa() {
        int nrp         = Integer.valueOf(edtNrp.getText().toString().trim());
        String nama     = edtNama.getText().toString().trim();
        String jurusan  = edtJurusan.getText().toString().trim();
        String kelas    = edtKelas.getText().toString().trim();
        String telp     = edtTelp.getText().toString().trim();
        String alamat   = edtAlamat.getText().toString().trim();

        if(TextUtils.isEmpty(edtNrp.getText().toString().trim())){
            edtNrp.setError("Please enter nrp");
            edtNrp.requestFocus();
            return;
        }else if(TextUtils.isEmpty(nama)){
            edtNama.setError("Please enter nama");
            edtNama.requestFocus();
        }else if(TextUtils.isEmpty(jurusan)){
            edtJurusan.setError("Please enter jurusan");
            edtJurusan.requestFocus();
        }else if(TextUtils.isEmpty(kelas)){
            edtKelas.setError("Please enter kelas");
            edtKelas.requestFocus();
        }else if(TextUtils.isEmpty(telp)){
            edtTelp.setError("Please enter telp");
            edtTelp.requestFocus();
        }else if(TextUtils.isEmpty(alamat)){
            edtAlamat.setError("Please enter alamat");
            edtAlamat.requestFocus();
        }
        HashMap<Integer,String> params = new HashMap<>();
        params.put(nrp, "nrp");


    }
}

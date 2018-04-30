package com.example.bahroel.crudmahasiswa;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bahroel.crudmahasiswa.Api.ApiMahasiswa;
import com.example.bahroel.crudmahasiswa.Handler.RequestHandler;
import com.example.bahroel.crudmahasiswa.Model.Mahasiswa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    // TAG for error tracing
    private static final String TAG = MainActivity.class.getSimpleName();
    // code of request
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    // initialize component
    EditText edtNrp, edtNama, edtTelp, edtAlamat;
    AutoCompleteTextView edtKelas, edtJurusan;
    String[] jurusan = {"Elka","Elin","Telkom", "Informatika","Mekatronika","MMB", "Tekkom","Game Tech", "SPE","PLN","GMF"};
    String[] kelas = {"D3 A","D3 B", "D4 A", "D4 B"};
    // Adapter for AutocompeteTextView
    ArrayAdapter<String> jurusanAdapter, kelasAdapter;
    ProgressBar progressBar;
    ListView listView;
    Button btnAdd;

    List<Mahasiswa> mahasiswaList;
    // term condition if form in update or add data
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNrp = (EditText)findViewById(R.id.edtNrp);
        edtNama = (EditText)findViewById(R.id.edtNama);
        edtJurusan = (AutoCompleteTextView) findViewById(R.id.edtJurusan);
        jurusanAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, jurusan);
        edtJurusan.setAdapter(jurusanAdapter);
        edtKelas = (AutoCompleteTextView) findViewById(R.id.edtKelas);
        kelasAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, kelas);
        edtKelas.setAdapter(kelasAdapter);
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
                    // create new data when condition of form is not update
                    createMahasiswa();
                }else{
                    // get value from data form.
                    String nama=edtNama.getText().toString().trim();
                    String nrp=edtNrp.getText().toString().trim();
                    String jurusan=edtJurusan.getText().toString().trim();
                    String kelas=edtKelas.getText().toString().trim();
                    String telp=edtTelp.getText().toString().trim();
                    String alamat=edtAlamat.getText().toString().trim();
                    updateDataMahasiswa(nrp,nama,jurusan,kelas,telp,alamat);
                }
            }
        });
        // get data mahasiswa in database
        readMahasiswa();
    }


    private void createMahasiswa() {
        // get value from a form
        String nama=edtNama.getText().toString().trim();
        String nrp=edtNrp.getText().toString().trim();
        String jurusan=edtJurusan.getText().toString().trim();
        String kelas=edtKelas.getText().toString().trim();
        String telp=edtTelp.getText().toString().trim();
        String alamat=edtAlamat.getText().toString().trim();
        // exception when there is form that empty
        if (TextUtils.isEmpty(nrp)) {
            edtNrp.setError("Please enter NRP");
            edtNrp.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nama)) {
            edtNama.setError("Please enter Nama");
            edtNama.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(jurusan)) {
            edtJurusan.setError("Please enter Jurusan");
            edtJurusan.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(kelas)) {
            edtKelas.setError("Please enter Kelas");
            edtKelas.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(telp)) {
            edtTelp.setError("Please enter Telp");
            edtTelp.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(alamat)) {
            edtAlamat.setError("Please enter Alamat");
            edtAlamat.requestFocus();
            return;
        }
        // make a struct of data using Hashmap and send it to web server
        HashMap<String, String> params = new HashMap<>();
        params.put("nrp", nrp);
        params.put("nama", nama);
        params.put("jurusan", jurusan);
        params.put("kelas", kelas);
        params.put("telp", telp);
        params.put("alamat", alamat);

        //Memangil create Mahasiswa API
        PerformNetworkRequest request = new PerformNetworkRequest(ApiMahasiswa.URL_C_MHS, params,CODE_POST_REQUEST);
        request.execute();
    }

    private void readMahasiswa() {
        PerformNetworkRequest request = new PerformNetworkRequest(ApiMahasiswa.URL_R_MHS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void deleteMahasiswa(String nrp) {
        PerformNetworkRequest request = new PerformNetworkRequest(ApiMahasiswa.URL_D_MHS + nrp, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateDataMahasiswa(String nrp,String nama,String jurusan,String kelas,String telp,String alamat){

        HashMap<String, String> params = new HashMap<>();
        params.put("nrp", nrp);
        params.put("nama", nama);
        params.put("jurusan", jurusan);
        params.put("kelas", kelas);
        params.put("telp", telp);
        params.put("alamat", alamat);

        //Memangil update Mahasiswa API
        PerformNetworkRequest request = new PerformNetworkRequest(ApiMahasiswa.URL_U_MHS, params, CODE_POST_REQUEST);
        request.execute();
    }

    public void refreshMahasiswaList(JSONArray mahasiswa) throws JSONException{
        // clear an existing array
        mahasiswaList.clear();
        for (int i = 0; i < mahasiswa.length(); i++) {

            JSONObject obj = mahasiswa.getJSONObject(i);
            mahasiswaList.add(new Mahasiswa(
                    obj.getString("nrp"),
                    obj.getString("nama"),
                    obj.getString("jurusan"),
                    obj.getString("kelas"),
                    obj.getString("telp"),
                    obj.getString("alamat")
            ));
        }
        MahasiswaAdapter adapter = new MahasiswaAdapter(mahasiswaList);
        listView.setAdapter(adapter);
    }


    private class PerformNetworkRequest extends AsyncTask<Void,Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;
        PerformNetworkRequest(String url, HashMap<String, String> params, int
                requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                     android.util.Log.d(TAG, "Nilai s: " + s);
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG);
                    refreshMahasiswaList(object.getJSONArray("mahasiswa"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);
            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);
            return null;
        }
    }

    public class MahasiswaAdapter extends ArrayAdapter<Mahasiswa> {
        List<Mahasiswa> mahasiswaList;
        public MahasiswaAdapter(List<Mahasiswa> mahasiswaList) {
            super(MainActivity.this, R.layout.layout_mahasiswa_list,
                    mahasiswaList);
            this.mahasiswaList=mahasiswaList;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_mahasiswa_list,null, true);

            TextView textViewNama = listViewItem.findViewById(R.id.textViewNama);
            TextView textViewNRp = listViewItem.findViewById(R.id.textViewNrp);
            TextView textViewTelp = listViewItem.findViewById(R.id.textViewTelp);
            TextView textViewAlamat = listViewItem.findViewById(R.id.textViewAlamat);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Mahasiswa mahasiswa = mahasiswaList.get(position);
            textViewNama.setText(mahasiswa.getNama());
            textViewNRp.setText(mahasiswa.getNrp());
            textViewTelp.setText(mahasiswa.getTelp());
            textViewAlamat.setText(mahasiswa.getAlamat());
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating=true;
                    edtNrp.setText(mahasiswa.getNrp());
                    edtNama.setText(mahasiswa.getNama());
                    edtJurusan.setText(mahasiswa.getJurusan());
                    edtKelas.setText(mahasiswa.getKelas());
                    edtTelp.setText(mahasiswa.getTelp());
                    edtAlamat.setText(mahasiswa.getAlamat());
                    btnAdd.setText("Update");

                }
            });
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new
                            AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Delete " + mahasiswa.getNrp())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new
                                    DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int
                                                which) {
                                            deleteMahasiswa(mahasiswa.getNrp());
                                        }
                                    })
                            .setNegativeButton(android.R.string.no, new
                                    DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int
                                                which) {}
                                    })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
            return listViewItem;
        }
    }

}

package com.example.bahroel.crudmahasiswa;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bahroel.crudmahasiswa.Api.ApiMahasiswa;
import com.example.bahroel.crudmahasiswa.Helper.RequestHandler;
import com.example.bahroel.crudmahasiswa.Model.Mahasiswa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    EditText edtNrp, edtNama, edtAngkatan, edtKelas, edtTelp, edtAlamat;
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
        edtAngkatan = (EditText)findViewById(R.id.edtAngkatan);
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
        readMahasiswa();
    }

    private void readMahasiswa() {
        PerformNetworkRequest request = new PerformNetworkRequest(ApiMahasiswa.URL_R_MHS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void createMahasiswa() {
        String nrp      = edtNrp.getText().toString().trim();
        String nama     = edtNama.getText().toString().trim();
        String angkatan  = edtAngkatan.getText().toString().trim();
        String kelas    = edtKelas.getText().toString().trim();
        String telp     = edtTelp.getText().toString().trim();
        String alamat   = edtAlamat.getText().toString().trim();

//        if(TextUtils.isEmpty(nrp)){
//            edtNrp.setError("Please enter nrp");
//            edtNrp.requestFocus();
//            return;
//        }else if(TextUtils.isEmpty(nama)){
//            edtNama.setError("Please enter nama");
//            edtNama.requestFocus();
//        }else if(TextUtils.isEmpty(jurusan)){
//            edtJurusan.setError("Please enter jurusan");
//            edtJurusan.requestFocus();
//        }else if(TextUtils.isEmpty(kelas)){
//            edtKelas.setError("Please enter kelas");
//            edtKelas.requestFocus();
//        }else if(TextUtils.isEmpty(telp)){
//            edtTelp.setError("Please enter telp");
//            edtTelp.requestFocus();
//        }else if(TextUtils.isEmpty(alamat)){
//            edtAlamat.setError("Please enter alamat");
//            edtAlamat.requestFocus();
//        }
        HashMap<String,String> params = new HashMap<>();
        params.put("nrp",nrp);
        params.put("nama",nama);
        params.put("angkatan",angkatan);
        params.put("kelas",kelas);
        params.put("telp",telp);
        params.put("alamat",alamat);




        Log.d(TAG, "Nilai params : " + params.toString());

        PerformNetworkRequest request = new PerformNetworkRequest(ApiMahasiswa.URL_C_MHS, params, CODE_POST_REQUEST);
        request.execute();




    }

    private void deleteMahasiswa(String nrp) {
        PerformNetworkRequest request = new PerformNetworkRequest(ApiMahasiswa.URL_D_MHS + nrp, null,CODE_GET_REQUEST);
        request.execute();
    }

    public void refreshMahasiswaList(JSONArray mahasiswa) throws JSONException{
        mahasiswaList.clear();
        for (int i = 0; i < mahasiswa.length(); i++) {

            JSONObject obj = mahasiswa.getJSONObject(i);
            mahasiswaList.add(new Mahasiswa(
                    obj.getString("nrp"),
                    obj.getString("nama"),
                    obj.getString("angkatan"),
                    obj.getString("kelas"),
                    obj.getString("telp"),
                    obj.getString("alamat")
            ));
        }
        MahasiswaAdapter adapter = new MahasiswaAdapter(mahasiswaList);
        listView.setAdapter(adapter);
    }

    private class PerformNetworkRequest extends AsyncTask<Void,Void,String>{
        String url;
        HashMap<String,String> params;
        int requestCode;
        PerformNetworkRequest(String url, HashMap<String,String> params, int requestCode) {
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
            Log.d(TAG, "Nilai error : " + s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                boolean error = object.getBoolean("error");
//                Log.d(TAG, "Nilai jurusan : " + object.toString());

                if (!error) {
                    // toast masih error dan tidak muncul
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
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
            super(MainActivity.this, R.layout.layout_mahasiswa_list, mahasiswaList);
            this.mahasiswaList=mahasiswaList;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_mahasiswa_list,null, true);
            TextView textViewNama = listViewItem.findViewById(R.id.textViewNama);
            TextView textViewUpdate =listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete =listViewItem.findViewById(R.id.textViewDelete);

            final Mahasiswa mahasiswa = mahasiswaList.get(position);

            textViewNama.setText(mahasiswa.getNama());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating=true;
                    edtNrp.setText(String.valueOf(mahasiswa.getNrp()));
                    edtNama.setText(mahasiswa.getNama());
                    edtAngkatan.setText(mahasiswa.getAlamat());
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
                    builder.setTitle("Delete " + mahasiswa.getNama())
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

package com.sistelgroup.itadmin.project_registerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    Button btn_check;
    TextView CompanyVisited;
    EditText Name, Motiu;
    AutoCompleteTextView Visited;

    //TODO: WS new user: "http://192.168.4.13:8090/phpfiles/newuser.php?MAC=aaaa&ID=bbb"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();

        CompanyVisited = findViewById(R.id.CompanyVisited);
        Visited = findViewById(R.id.Visited);
        btn_check = findViewById(R.id.btn_check);
        Name = findViewById(R.id.Name);
        Motiu = findViewById(R.id.Motiu);

        //Captura els paràmetres
        Intent myIntent = getIntent(); // gets the previously created intent
        CompanyVisited.setText(myIntent.getStringExtra("CompanyVisited"));

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        //Llista persones a visitar
        ArrayAdapter<String> adapterV = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.List_Visited));
        Visited.setAdapter(adapterV);

        //Llista empreses
        /*@SuppressLint("ResourceType") ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.List_CompanyVisited));
        CompanyVisited.setAdapter(adapter);*/
    }

    private void register() {
        Toast.makeText(this,"Success", Toast.LENGTH_LONG).show();
        new CarregarDades().execute("http://192.168.4.13:8090/phpfiles/newuser.php?MAC="+Name.getText().toString()+"&ID="+Motiu.getText().toString());
    }

    //Inici funcions WS
    public class CarregarDades extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                return downloadUrl(strings[0]);
            } catch (IOException e) {
                return "URL incorrecta";
            }
        }

        private String downloadUrl(String myurl) throws IOException {
            myurl = myurl.replace(" ", "%20");
            InputStream stream = null;
            int len = 500;
            try {
                URL url = new URL(myurl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                // Open communications link (network traffic occurs here).
                connection.connect();
                int responseCode = connection.getResponseCode();
                Log.d("reposta", "La resposta es: " + responseCode);
                // Retrieve the response body as an InputStream.
                stream = connection.getInputStream();

                //Convertir el InputString a String
                String ContentAsString = readIt(stream, len);
                return ContentAsString;

            } finally {
                // Close Stream and disconnect HTTPS connection.
                if (stream != null) {
                    stream.close();
                }
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
    //Final funcions WS
}

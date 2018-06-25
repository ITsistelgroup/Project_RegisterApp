package com.sistelgroup.itadmin.project_registerapp;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;
import static java.lang.Integer.parseInt;



public class MainActivity extends AppCompatActivity {

    Button btn_check;
    TextView CompanyVisited;
    EditText Name, Empresa, DNI;
    AutoCompleteTextView Visited;
    private String company;
    private int IDcompany;
    JSONObject data;
    Spinner Motiu;
    private String missatge;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.SixTLEngineeringTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent myIntent = getIntent(); // gets the previously created intent
        company=myIntent.getStringExtra("CompanyVisited");
        IDcompany= myIntent.getIntExtra("IDCompanyVisited",0);


        /*switch (company){
            case "S.A.Sistel":
                //myIntent.putExtra("CompanyVisited","S.A.Sistel");
                themeUtils.changeToTheme(this,"S.A.Sistel");
                //this.setTheme(R.style.SASistelTheme);
                //getApplication().setTheme(R.style.SASistelTheme);
                break;
            case "DigiProces S.A.":
                //myIntent.putExtra("CompanyVisited","DigiProces S.A.");
                themeUtils.changeToTheme(this,"DigiProces S.A.");
                //this.setTheme(R.style.DigiProcesTheme);
                //getApplication().setTheme(R.style.DigiProcesTheme);
                break;
            case "SmartLift S.L.":
                //myIntent.putExtra("CompanyVisited","SmartLift S.L.");
                themeUtils.changeToTheme(this,"SmartLift S.L.");
                //this.setTheme(R.style.SmartLiftTheme);
                //getApplication().setTheme(R.style.SmartLiftTheme);
                break;
            case "6TL Engineering":
                //myIntent.putExtra("CompanyVisited","6TL Engineering");
                themeUtils.changeToTheme(this,"6TL Engineering");
                //this.setTheme(R.style.SixTLEngineeringTheme);
                //getApplication().setTheme(R.style.SixTLEngineeringTheme);
                break;
            default:
                break;
        }*/

        Resources res = getResources();

        CompanyVisited = findViewById(R.id.CompanyVisited);
        Visited = findViewById(R.id.Visited);
        btn_check = findViewById(R.id.btn_check);
        Name = findViewById(R.id.Name);
        Motiu = findViewById(R.id.Motiu);
        Empresa = findViewById(R.id.Company);
        DNI = findViewById(R.id.DNI);

        //tot en majúscules
        Visited.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s=editable.toString();
                if(!s.equals(s.toUpperCase()))
                {
                    s=s.toUpperCase();
                    Visited.setText(s);
                }
                Visited.setSelection(Visited.getText().length());
            }
        });
        Empresa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s=editable.toString();
                if(!s.equals(s.toUpperCase()))
                {
                    s=s.toUpperCase();
                    Empresa.setText(s);
                }
                Empresa.setSelection(Empresa.getText().length());
            }
        });
        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s=editable.toString();
                if(!s.equals(s.toUpperCase()))
                {
                    s=s.toUpperCase();
                    Name.setText(s);
                }
                Name.setSelection(Name.getText().length());
            }
        });
        DNI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s=editable.toString();
                if(!s.equals(s.toUpperCase()))
                {
                    s=s.toUpperCase();
                    DNI.setText(s);
                }
                DNI.setSelection(DNI.getText().length());
            }
        });

        //Captura els paràmetres

        CompanyVisited.setText(company);
        CompanyVisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CompanySelection.class));
            }
        });

        //Omplir Spinner Motiu
        ArrayAdapter<CharSequence> adapterS = ArrayAdapter.createFromResource(this, R.array.Motius, android.R.layout.simple_spinner_item);
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Motiu.setAdapter(adapterS);

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

    //TODO: control error WS (si no hi ha internet o falla ha de retornar-ho)
    private void register() {

        String a = "http://192.168.4.13:8090/phpfiles/sp_Registre.php?DNI="+DNI.getText().toString()
                +"&Emp_Vis="+Empresa.getText().toString()
                +"&Nom_Visitant="+Name.getText().toString()
                +"&ID_EmpresaVisitada="+IDcompany
                +"&ID_PersonaCitada="+Visited.getText().toString()
                +"&Motiu="+Motiu.getSelectedItem().toString();
        //new CarregarDades().execute(a);
        new CarregarDades().execute(a);

        //Toast.makeText(this, missatge, LENGTH_LONG).show();
        //Toast.makeText(this,ret.toString(), Toast.LENGTH_LONG).show();


        startActivity(new Intent(this, CompanySelection.class));
    }

    //Inici funcions WS
    public class CarregarDades extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                missatge = getResources().getString(R.string.Success);
                return downloadUrl(strings[0]);
            } catch (IOException e) {
                missatge = getResources().getString(R.string.URLerror);
                return getString(R.string.URLerror);
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
        StringBuffer json = new StringBuffer(2048);
        try {
            data = new JSONObject(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
    //Final funcions WS


}

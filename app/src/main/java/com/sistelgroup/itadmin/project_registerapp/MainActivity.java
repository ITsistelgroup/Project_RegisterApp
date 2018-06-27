package com.sistelgroup.itadmin.project_registerapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;



public class MainActivity extends AppCompatActivity {

    Button btn_check;
    ImageView CompanyVisited;
    EditText Name, LletraCIF, NumCIF, DNI;
    AutoCompleteTextView Visited;
    private String company;
    private int IDcompany;
    JSONObject data;
    Spinner Motiu;
    private String missatge;
    String[] entrades;
    ListView EntryList;
    ArrayList<DropBoxManager.Entry> list;
    ArrayAdapter<DropBoxManager.Entry> adapter;
    TextView NameCompany;



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
        LletraCIF = findViewById(R.id.LletraCIF);
        NumCIF = findViewById(R.id.NumCIF);
        DNI = findViewById(R.id.DNI);
        NameCompany = findViewById(R.id.NameCompany);

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
        LletraCIF.addTextChangedListener(new TextWatcher() {
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
                    LletraCIF.setText(s);
                }
                LletraCIF.setSelection(LletraCIF.getText().length());
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

        NumCIF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus && !LletraCIF.getText().toString().matches("") && !NumCIF.getText().toString().matches("")){
                    new ConsultarDades().execute("http://192.168.4.13:8090/phpfiles/ConsultaEmpresa.php?CIF="+LletraCIF.getText().toString()+NumCIF.getText().toString());
                }
            }
        });

        //Captura els paràmetres

        //assigna imatge
        switch (company){
            case "S.A.Sistel":
                CompanyVisited.setImageResource(R.mipmap.sasistel);
                break;
            case "DigiProces S.A.":
                CompanyVisited.setImageResource(R.mipmap.digi);
                break;
            case "SmartLift S.L.":
                CompanyVisited.setImageResource(R.mipmap.smartlift);
                break;
            case "6TL Engineering":
                CompanyVisited.setImageResource(R.mipmap.sixtl);
                break;
            default:

        }
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
                +"&Emp_Vis="+ LletraCIF.getText().toString()+NumCIF.getText().toString()
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
    private class ConsultarDades extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                return downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "URL incorrecta";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            if(result.indexOf("}")>0) {
                entrades=result.split("\\}");
                entrades = Arrays.copyOfRange(entrades, 0, entrades.length - 1);
                Log.i("nvertstr", "[" + result + "]");


                NameCompany.setText(entrades[0].substring(entrades[0].indexOf(":") + 2, entrades[0].length() - 1));
                NameCompany.setEnabled(false);
            } else {
                NameCompany.setText("");
                NameCompany.setEnabled(true);
            }
            /*
            for(int i=0; i<entrades.length;i++){
                list.add(new DropBoxManager.Entry(IDpersona, entrades[i]));
                //liststrings.add((new Entry(IDpersona, entrades[i])).getIDJob());
                Log.i("Element:", entrades[i]);
            }

            adapter = new EntryAdapter(HoursList.this,list);
            Log.i("Adapter", "adapter creat");
            EntryList.setAdapter(adapter);
            Log.i("Adapter", "adapter assignat");
            */

        }
    }


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
    }

    private String downloadUrl(String myurl) throws IOException {
        myurl = myurl.replace(" ", "%20");
        InputStream stream = null;
        int len = 1000;
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

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
    //Final funcions WS

    //Controla "Back button"
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            startActivity(new Intent(MainActivity.this, CompanySelection.class));
            return true;
        }
        return false;
    }


}

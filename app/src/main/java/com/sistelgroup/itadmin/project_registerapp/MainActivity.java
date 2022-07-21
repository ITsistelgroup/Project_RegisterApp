package com.sistelgroup.itadmin.project_registerapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.widget.Toast;

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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Integer.parseInt;



public class MainActivity extends AppCompatActivity {

    Button btn_check;
    ImageView CompanyVisited;
    EditText Name/*, LletraCIF*/, NumCIF, DNI;
    AutoCompleteTextView Visited;
    private String company;
    private int IDcompany;
    JSONObject data;
    Spinner Motiu;
    private String missatge;
    String[] entrades;
    ListView EntryList;
    TextView TitleCIF;
    String Wifi_voucher="";


    TextView NameCompany;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.SixTLEngineeringTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent myIntent = getIntent(); // gets the previously created intent
        company=myIntent.getStringExtra("CompanyVisited");
        IDcompany= myIntent.getIntExtra("IDCompanyVisited",0);

        new ConsultarEmpleatsaVisitar().execute("http://192.168.4.13:8090/phpfiles/llistaPersonesaVisitar.php");

        /*Timer myTimer = new Timer();

        MyTimerTask myTask = new MyTimerTask();

        myTimer.schedule(myTask, 5000);*/


        Resources res = getResources();

        CompanyVisited = findViewById(R.id.CompanyVisited);
        Visited = findViewById(R.id.Visited);
        btn_check = findViewById(R.id.btn_check);
        Name = findViewById(R.id.Name);
        Motiu = findViewById(R.id.Motiu);
        //LletraCIF = findViewById(R.id.LletraCIF);
        NumCIF = findViewById(R.id.NumCIF);
        DNI = findViewById(R.id.DNI);
        NameCompany = findViewById(R.id.NameCompany);
        NameCompany.setEnabled(false);
        //TitleCIF = findViewById(R.id.TitleCIF);
        new Consultar_WiFiVoucher().execute("http://192.168.4.13:8090/phpfiles/llistaWiFiVouchers.php");

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

        DNI.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus /*&& !LletraCIF.getText().toString().matches("")*/ && !DNI.getText().toString().matches("") && DNI.getText().toString().length()>=9){
                    new ConsultarEmpresaambDNI().execute("http://192.168.4.13:8090/phpfiles/ConsultaEmpresaambDNI.php?DNI="/*+LletraCIF.getText().toString()*/+DNI.getText().toString());

                }

                //if(!hasFocus /*&& !LletraCIF.getText().toString().matches("")*/ && (NumCIF.getText().toString().matches("") || NumCIF.getText().toString().length()<9)){
                //    NameCompany.setEnabled(false);
                //}
            }
        });

        NumCIF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus /*&& !LletraCIF.getText().toString().matches("")*/ && !NumCIF.getText().toString().matches("") && NumCIF.getText().toString().length()>=9){
                    new ConsultarEmpresa().execute("http://192.168.4.13:8090/phpfiles/ConsultaEmpresa.php?CIF="/*+LletraCIF.getText().toString()*/+NumCIF.getText().toString());
                }

                if(!hasFocus /*&& !LletraCIF.getText().toString().matches("")*/ && (NumCIF.getText().toString().matches("") || NumCIF.getText().toString().length()<9)){
                    NameCompany.setEnabled(false);
                }
            }
        });



        //assigna imatge
        switch (company){
            case "S.A.Sistel":
                CompanyVisited.setImageResource(R.mipmap.sasistel);
                break;
            case "DigiProces S.A.":
                CompanyVisited.setImageResource(R.mipmap.digi2);
                break;
            case "SmartLift S.L.":
                CompanyVisited.setImageResource(R.mipmap.smartlift);
                break;
            case "Kfew Systems S.L.":
                CompanyVisited.setImageResource(R.mipmap.kfew);
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

        /*btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });*/

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NumCIF.getText().toString().matches("") || NameCompany.getText().toString().matches("") || Name.getText().toString().matches("") || DNI.getText().toString().matches("")
                        || Visited.getText().toString().matches("")){

                    if(NumCIF.getText().toString().matches("")){
                        TitleCIF.setTextColor(Color.parseColor("#FF0000"));
                        TitleCIF.setText("CIF:*");
                    }
                    if(NameCompany.getText().toString().matches("")){
                        NameCompany.setHintTextColor(Color.parseColor("#FF0000"));
                        NameCompany.setHint(getString(R.string.NameCompany) + "*");
                    }
                    if(Name.getText().toString().matches("")){
                        Name.setHintTextColor(Color.parseColor("#FF0000"));
                        Name.setHint(getString(R.string.Name) + "*");
                    }
                    if(DNI.getText().toString().matches("")){
                        DNI.setHintTextColor(Color.parseColor("#FF0000"));
                        DNI.setHint(getString(R.string.dni) + "*");
                    }
                    if(Visited.getText().toString().matches("")){
                        Visited.setHintTextColor(Color.parseColor("#FF0000"));
                        Visited.setHint(getString(R.string.whosvisiting) + "*");
                    }
                    Toast.makeText(MainActivity.this, R.string.Rellenar, Toast.LENGTH_LONG).show();
                }
                else{

                Intent i = new Intent(MainActivity.this, CaptureSignature.class);
                i.putExtra("CIF", NumCIF.getText().toString());
                i.putExtra("NomEmpresa", NameCompany.getText().toString());
                i.putExtra("NomPersona", Name.getText().toString());
                i.putExtra("QuiVisita", Visited.getText().toString());
                i.putExtra("Motiu", Motiu.getSelectedItem().toString());
                i.putExtra("DNI", DNI.getText().toString());
                i.putExtra("company", company);
                i.putExtra("IDcompany", IDcompany);
                i.putExtra("WiFi", Wifi_voucher);
                startActivity(i);
                }
            }
        });

        //Omplir llista persones a visitar
        ArrayAdapter<String> adapterV = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.List_Visited));
        Visited.setAdapter(adapterV);

        //Llista empreses
        /*@SuppressLint("ResourceType") ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.List_CompanyVisited));
        CompanyVisited.setAdapter(adapter);*/
    }

    /*class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            //startActivity(new Intent(MainActivity.this, CompanySelection.class));
            Toast.makeText(MainActivity.this, "El tiempo ha expirado", Toast.LENGTH_LONG).show();
        }
    }*/

    //TODO: control error WS (si no hi ha internet o falla ha de retornar-ho)
    private void register() {

        String a = "http://192.168.4.13:8090/phpfiles/sp_Registre.php?DNI="+DNI.getText().toString()
                +"&Emp_Vis="/*+ LletraCIF.getText().toString()*/+NumCIF.getText().toString()
                +"&NOM_Emp_Vis="+NameCompany.getText().toString()
                +"&Nom_Visitant="+Name.getText().toString()
                +"&ID_EmpresaVisitada="+IDcompany
                +"&ID_PersonaCitada="+Visited.getText().toString()
                +"&Motiu="+Motiu.getSelectedItem().toString();
        //new CarregarDades().execute(a);
        new CarregarDades().execute(a);
        Toast.makeText(MainActivity.this, R.string.Success, Toast.LENGTH_LONG).show();


        //Toast.makeText(this, missatge, LENGTH_LONG).show();
        //Toast.makeText(this,ret.toString(), Toast.LENGTH_LONG).show();


        startActivity(new Intent(this, CompanySelection.class));
    }

    //Inici funcions WS
    private class ConsultarEmpresa extends AsyncTask<String, Void, String> {
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

    private class Consultar_WiFiVoucher extends AsyncTask<String, Void, String> {
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


                Wifi_voucher=entrades[0].substring(entrades[0].indexOf(":") + 2, entrades[0].length() - 1);
            } else {
                Wifi_voucher="";
            }

        }
    }

    private class ConsultarNomambDNI extends AsyncTask<String, Void, String> {
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


                Name.setText(entrades[0].substring(entrades[0].indexOf(":") + 2, entrades[0].length() - 1));
            } //else {
              //  NameCompany.setText("");
              //  NameCompany.setEnabled(true);
            //}
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

    private class ConsultarEmpresaambDNI extends AsyncTask<String, Void, String> {
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


                NumCIF.setText(entrades[0].substring(entrades[0].indexOf(":") + 2, entrades[0].length() - 1));
                new ConsultarNomambDNI().execute("http://192.168.4.13:8090/phpfiles/ConsultaNomambDNI.php?DNI="/*+LletraCIF.getText().toString()*/+DNI.getText().toString());
                new ConsultarEmpresa().execute("http://192.168.4.13:8090/phpfiles/ConsultaEmpresa.php?CIF="/*+LletraCIF.getText().toString()*/+NumCIF.getText().toString());
                //NumCIF.setEnabled(false);
            } /*else {
                NameCompany.setText("");
                NameCompany.setEnabled(true);
            }*/
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

    private class ConsultarEmpleatsaVisitar extends AsyncTask<String, Void, String> {
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

            if (result.indexOf("}") > 0) {
                List<String> list=new ArrayList<String>();
                entrades = result.split("\\}");
                entrades = Arrays.copyOfRange(entrades, 0, entrades.length - 1);
                Log.i("nvertstr", "[" + result + "]");


                for (int i = 0; i < entrades.length; i++) {

                    list.add(entrades[i].substring(entrades[i].indexOf(":") + 2, entrades[i].length() - 1));
                    //liststrings.add((new Entry(IDpersona, entrades[i])).getIDJob());
                    Log.i("Element:", entrades[i]);
                }

            /*adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
            Log.i("Adapter", "adapter creat");
            Visited.setAdapter(adapter);
            Log.i("Adapter", "adapter assignat");*/
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        list );
                Visited.setAdapter(arrayAdapter);



            }
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
        int len = 10000;
        try {
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(4000);
            connection.setConnectTimeout(4000);
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

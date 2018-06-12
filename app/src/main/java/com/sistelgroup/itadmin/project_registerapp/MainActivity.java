package com.sistelgroup.itadmin.project_registerapp;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_check;
    Spinner CompanyVisited;
    AutoCompleteTextView Visited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        CompanyVisited = findViewById(R.id.CompanyVisited);
        Visited = findViewById(R.id.Visited);
        btn_check = findViewById(R.id.btn_check);


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
        @SuppressLint("ResourceType") ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.List_CompanyVisited));
        CompanyVisited.setAdapter(adapter);
    }

    private void register() {
        Toast.makeText(this,"Success", Toast.LENGTH_LONG).show();
    }
}

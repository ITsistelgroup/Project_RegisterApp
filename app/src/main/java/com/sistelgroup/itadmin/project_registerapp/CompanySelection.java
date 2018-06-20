package com.sistelgroup.itadmin.project_registerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CompanySelection extends AppCompatActivity {

    ImageView logoDigi, logoSmartLift, logoSASistel, logo6TL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_selection);

        logo6TL=findViewById(R.id.logo_6TL);
        logoDigi=findViewById(R.id.logo_Digi);
        logoSASistel=findViewById(R.id.logo_sistel);
        logoSmartLift=findViewById(R.id.logo_SmartLift);

        logoSmartLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanySelected(3);
            }
        });

        logoSASistel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanySelected(1);
            }
        });

        logoDigi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanySelected(2);
            }
        });

        logo6TL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanySelected(7);
            }
        });
    }

    private void CompanySelected(int i) {
        Intent myIntent = new Intent(this, MainActivity.class);


        switch (i){
            case 1:
                myIntent.putExtra("CompanyVisited","S.A.Sistel");
                break;
            case 2:
                myIntent.putExtra("CompanyVisited","DigiProces S.A.");
                break;
            case 3:
                myIntent.putExtra("CompanyVisited","SmartLift S.L.");
                break;
            case 7:
                myIntent.putExtra("CompanyVisited","6TL Engineering");
                break;
            default:
                myIntent.putExtra("CompanyVisited","");
        }

        startActivity(myIntent);

    }
}

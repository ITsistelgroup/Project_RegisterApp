package com.sistelgroup.itadmin.project_registerapp;

import android.app.ActionBar;
import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Locale;
import java.util.Objects;


public class CompanySelection extends AppCompatActivity {

    ImageView logoDigi, logoSmartLift, logoSASistel, logo6TL;

    //public String companySelected;

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme2);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sistelgroup);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_company_selection);

        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();

// set the icon
        /*if (actionBar != null) {
            actionBar.setIcon(R.mipmap.sistelgroup);
        }*/

        /*Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        /*getActionBar().setIcon(R.mipmap.sistelgroup);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);*/

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


        //TODO: setTheme a mitjes
        myIntent.putExtra("IDCompanyVisited",i);
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

    //Creació del Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.language_menu, menu);
        //changecolor = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Catala:
                Locale locale = new Locale("ca");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                Toast.makeText(this, R.string.idioma_canviat, Toast.LENGTH_LONG).show();
                return true;
            case R.id.Espanyol:
                Locale locale2 = new Locale("es");
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config2.locale = locale2;
                getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());
                Toast.makeText(this, R.string.idioma_canviat, Toast.LENGTH_LONG).show();
                return true;
            case R.id.Angles:
                Locale locale3 = new Locale("en");
                Locale.setDefault(locale3);
                Configuration config3 = new Configuration();
                config3.locale = locale3;
                getBaseContext().getResources().updateConfiguration(config3, getBaseContext().getResources().getDisplayMetrics());
                Toast.makeText(this, R.string.idioma_canviat, Toast.LENGTH_LONG).show();
                return true;
            case R.id.frances:
                Locale locale4 = new Locale("fr");
                Locale.setDefault(locale4);
                Configuration config4 = new Configuration();
                config4.locale = locale4;
                getBaseContext().getResources().updateConfiguration(config4, getBaseContext().getResources().getDisplayMetrics());
                Toast.makeText(this, R.string.idioma_canviat, Toast.LENGTH_LONG).show();
                return true;
            case R.id.alemany:
                Locale locale5 = new Locale("de");
                Locale.setDefault(locale5);
                Configuration config5 = new Configuration();
                config5.locale = locale5;
                getBaseContext().getResources().updateConfiguration(config5, getBaseContext().getResources().getDisplayMetrics());
                Toast.makeText(this, R.string.idioma_canviat, Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    //Final del Menu
}

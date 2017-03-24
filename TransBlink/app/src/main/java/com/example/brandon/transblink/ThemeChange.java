package com.example.brandon.transblink;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static android.os.Build.VERSION_CODES.M;
import static java.security.AccessController.getContext;


public class ThemeChange extends AppCompatActivity {


    private int themeSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeChange themeChg = new ThemeChange();
        themeSel = themeChg.findTheme(this);
        setTheme(themeSel);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_change);
        Button theme = (Button) findViewById(R.id.buttonChangeTheme);
        theme.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                themeChg();
            }
        }
        );

    }

    public void themeChg()
    {


        RadioButton def = (RadioButton) findViewById(R.id.radioButtonDefaultTheme);
        RadioButton dark= (RadioButton) findViewById(R.id.radioButtonDarkTheme);
        RadioButton light = (RadioButton) findViewById(R.id.radioButtonLightTheme);
        RadioButton crazy = (RadioButton) findViewById(R.id.radioButtonCrazyTheme);

        String hold = "";




        if(def.isChecked())
        {
            hold = "1";
        }
        if(dark.isChecked())
        {
            hold = "2";
        }
        if(light.isChecked())
        {
            hold = "3";
        }
        if(crazy.isChecked())
        {
            hold = "4";
        }

        try
        {
            OutputStreamWriter outFile = new OutputStreamWriter(openFileOutput("theme.txt", Context.MODE_PRIVATE));
            outFile.write(hold);
            outFile.close();

        }
        catch (Exception ex)
        {

        }

        Intent intent = new Intent(ThemeChange.this, MainActivity.class);
        startActivity(intent);


    }


    public int findTheme(Context con)  {
        String choice ="";
        try
        {

            InputStream inFile = con.openFileInput("theme.txt");
            InputStreamReader isr = new InputStreamReader(inFile);
            BufferedReader br = new BufferedReader(isr);
            choice = br.readLine();
            inFile.close();

        }
        catch (Exception ex)
        {

        }

        switch (choice)
        {
            case "1":
                themeSel = R.style.AppTheme;
                break;
            case "2":
                themeSel = R.style.themeDark;
                break;
            case"3":
                themeSel = R.style.themeLight;
                break;
            case"4":
                themeSel = R.style.themeCrazy;
                break;
            default:
                themeSel = R.style.AppTheme;
        }

        return themeSel;
    }


}

package com.example.brandon.transblink;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import static android.os.Build.VERSION_CODES.M;


public class ThemeChange extends AppCompatActivity {


    private int themeSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent =  getIntent();
        themeSel =  intent.getIntExtra("theme", 0 );
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

        if(def.isChecked())
        {
            themeSel = R.style.AppTheme;
        }
        if(dark.isChecked())
        {
            themeSel = R.style.themeDark;
        }
        if(light.isChecked())
        {
            themeSel = R.style.themeLight;
        }



        Intent intent = new Intent(ThemeChange.this, MainActivity.class);
        intent.putExtra("theme",themeSel);

        startActivity(intent);

    }


}

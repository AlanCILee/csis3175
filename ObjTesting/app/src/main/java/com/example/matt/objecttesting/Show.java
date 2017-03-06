package com.example.matt.objecttesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Show extends AppCompatActivity {
    ArrayList<Path> paths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView tvPath = (TextView)findViewById(R.id.tvPath);
        Intent intent = getIntent();
        paths = (ArrayList<Path>)intent.getSerializableExtra("paths");
        Path path = paths.get(0);

        StringBuilder pathString = new StringBuilder();
        for(int i=0; i<path.pathStops.size(); i++){
            pathString.append(path.pathStops.get(i).getFullName() + "\n");

        }
        tvPath.setText(pathString);
    }
}

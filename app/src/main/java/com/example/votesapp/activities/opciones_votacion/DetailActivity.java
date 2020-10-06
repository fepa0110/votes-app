package com.example.votesapp.activities.opciones_votacion;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.votesapp.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("param"));
    }
}

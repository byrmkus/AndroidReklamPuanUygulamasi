package com.baykus.reklampuanuygulamasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SonrakiBolumActivity extends AppCompatActivity {
private TextView textViewSonrakiBolum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonraki_bolum);
        textViewSonrakiBolum = findViewById(R.id.textViewSonrakiBolum);
    }
}
package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.swiftmart.Adapter.LanguageAdapter;
import com.example.swiftmart.Adapter.LanguageModel;

import java.util.ArrayList;

public class Language_Screen extends AppCompatActivity {

    Toolbar toolbar;
    RelativeLayout done;
    RecyclerView recycle;
    com.example.swiftmart.Adapter.LanguageAdapter languageAdapter;

    RecyclerView.LayoutManager layoutManager;
    ArrayList<com.example.swiftmart.Adapter.LanguageModel> arrlanguage=new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_screen);

        done = findViewById(R.id.right);
        recycle = findViewById(R.id.recycle);
        layoutManager = new GridLayoutManager(this, 2);

        recycle.setLayoutManager(new GridLayoutManager(Language_Screen.this, 2));

        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img, "Afrikaans"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_15, "Arabic"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_2, "English"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_3, "French"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_4, "Hindi"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_5, "German"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_6, "Chinese"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_7, "Russians"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_8, "Spanish"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_9, "Japanese"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_10, "Indonesian"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_11, "Italian"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_12, "Portuguese"));
        arrlanguage.add(new com.example.swiftmart.Adapter.LanguageModel(R.drawable.img_13, "Vietnamese"));

        languageAdapter = new com.example.swiftmart.Adapter.LanguageAdapter(Language_Screen.this, arrlanguage);
        recycle.setAdapter(languageAdapter);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Language_Screen.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

}
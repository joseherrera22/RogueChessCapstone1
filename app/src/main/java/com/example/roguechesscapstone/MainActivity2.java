package com.example.roguechesscapstone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private Button button1;
    private Button easy;
    private Button medium;
    private Button hard;
    public String difficulty;
    public String PlayVS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //converter = tf.lite.TFLiteConverter.from_saved_model(ml)


        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); //get a stable fullscreen layout
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);
        button1 = (Button) findViewById(R.id.Home);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        easy = (Button) findViewById(R.id.Easy);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEasy();
            }
        });
        medium = (Button) findViewById(R.id.Medium);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMedium();
            }
        });
        hard = (Button) findViewById(R.id.Hard);
        hard.getText();
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHard();
            }
        });

    }

    public void openMainActivity(){
        Intent intent = new Intent(this, com.example.roguechesscapstone.MainActivity.class);
        startActivity(intent);
    }
    public void openEasy(){
        difficulty = "easy";
        String PlayerVS = getIntent().getStringExtra("PvC");

        Intent intent2 = new Intent(this, ChessEasy.class);
        intent2.putExtra("mode", difficulty);
        //intent2.putExtra("tested", test);
        if(PlayerVS.equals("PvPMatch")){
            PlayVS = "PvP";
            intent2.putExtra("Pv", PlayVS);
        }
        else{
            if(PlayerVS.equals("SingleMatch")){
                PlayVS = "PvC";
                intent2.putExtra("Pv", PlayVS);
            }
        }

        startActivity(intent2);
    }
    public void openMedium(){
        difficulty = "medium";
        String PlayerVS = getIntent().getStringExtra("PvC");

        Intent intent2 = new Intent(this, ChessEasy.class);
        intent2.putExtra("mode", difficulty);
        //intent2.putExtra("tested", test);
        if(PlayerVS.equals("PvPMatch")){
            PlayVS = "PvP";
            intent2.putExtra("Pv", PlayVS);
        }
        else{
            if(PlayerVS.equals("SingleMatch")){
                PlayVS = "PvC";
                intent2.putExtra("Pv", PlayVS);
            }
        }

        startActivity(intent2);
    }
    public void openHard(){
        difficulty = "hard";
        String PlayerVS = getIntent().getStringExtra("PvC");

        Intent intent2 = new Intent(this, ChessEasy.class);
        intent2.putExtra("mode", difficulty);
        //intent2.putExtra("tested", test);
        if(PlayerVS.equals("PvPMatch")){
            PlayVS = "PvP";
            intent2.putExtra("Pv", PlayVS);
        }
        else{
            if(PlayerVS.equals("SingleMatch")){
                PlayVS = "PvC";
                intent2.putExtra("Pv", PlayVS);
            }
        }

        startActivity(intent2);
    }


}

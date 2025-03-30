package com.example.navegacionappet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MiPerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mi_perfil);
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView,
                                            int position,
                                            long id) {
                        if(position == 0){
                            Intent intent = new Intent(MiPerfilActivity.this, VerInformacion.class);
                            startActivity(intent);
                        }else if(position == 1){
                            Intent intent = new Intent(MiPerfilActivity.this, CambiarContrasena.class);
                            startActivity(intent);
                        }
                    }
                };
        ListView listView = findViewById(R.id.list_options_miPerfil);
        listView.setOnItemClickListener(itemClickListener);
    }
}
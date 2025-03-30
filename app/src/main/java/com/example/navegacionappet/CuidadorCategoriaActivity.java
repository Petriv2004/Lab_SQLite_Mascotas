package com.example.navegacionappet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CuidadorCategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cuidador_categoria);
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView,
                                            int position,
                                            long id) {
                        if(position == 0){
                            Intent intent = new Intent(CuidadorCategoriaActivity.this,
                                    CuidadorVerCategoriaActivity.class);
                            startActivity(intent);
                        }else if(position == 1){
                            Intent intent = new Intent(CuidadorCategoriaActivity.this,
                                    ChatActivity.class);
                            startActivity(intent);
                        }else if(position == 2){
                            new AlertDialog.Builder(CuidadorCategoriaActivity.this)
                                    .setTitle("Confirmación")
                                    .setMessage("¿Está seguro de despedir a su cuidador?")
                                    .setPositiveButton("Sí", (dialog, which) -> {
                                        Toast.makeText(CuidadorCategoriaActivity.this,"Se ha despedido al cuidador", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    }
                };
        ListView listView = findViewById(R.id.list_options_cuidador);
        listView.setOnItemClickListener(itemClickListener);
    }
}
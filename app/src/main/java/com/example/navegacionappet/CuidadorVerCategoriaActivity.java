package com.example.navegacionappet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CuidadorVerCategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cuidador_ver_categoria);
        ArrayAdapter<Cuidador> listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                Cuidador.cuidadores);
        ListView listdrinks = findViewById(R.id.lista_cuidador);
        listdrinks.setAdapter(listAdapter);

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listDrinks,
                                            View itemView,
                                            int position,
                                            long id){
                        Intent intent = new Intent(CuidadorVerCategoriaActivity.this,
                                CuidadorActivity.class);
                        intent.putExtra(CuidadorActivity.EXTRA_CUIDADORID, (int) id);
                        startActivity(intent);
                    }
                };

        listdrinks.setOnItemClickListener(itemClickListener);
    }



}
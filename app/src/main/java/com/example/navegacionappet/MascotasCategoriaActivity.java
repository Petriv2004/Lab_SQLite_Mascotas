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

public class MascotasCategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mascotas_categoria);
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView,
                                            int position,
                                            long id) {
                        if(position == 0){
                            Intent intent = new Intent(MascotasCategoriaActivity.this,
                                    RegistrarMascotaActivity.class);
                            startActivity(intent);
                        }else if(position == 1){
                            Intent intent = new Intent(MascotasCategoriaActivity.this,
                                    MascotaVerCategoriaActivity.class);
                            startActivity(intent);
                        }else if(position == 2){
                            Intent intent = new Intent(MascotasCategoriaActivity.this,
                                    RegitrarHistorial.class);
                            startActivity(intent);
                        }else if(position == 3){
                            Intent intent = new Intent(MascotasCategoriaActivity.this,
                                    VueltasMascota.class);
                            startActivity(intent);
                        }
                    }
                };
        ListView listView = findViewById(R.id.list_options_mascotas);
        listView.setOnItemClickListener(itemClickListener);
    }
}
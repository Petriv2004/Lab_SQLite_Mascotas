package com.example.navegacionappet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MascotaVerCategoriaActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ArrayList<String> mascotaNombres;
    private ArrayList<Integer> mascotaIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mascota_ver_categoria);

        dbHelper = new DatabaseHelper(this);
        mascotaNombres = new ArrayList<>();
        mascotaIds = new ArrayList<>();
        cargarMascotasDesdeBD();

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                mascotaNombres);

        ListView listMascotas = findViewById(R.id.list_mascotas);
        listMascotas.setAdapter(listAdapter);

        listMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int realId = mascotaIds.get(position);
                Intent intent = new Intent(MascotaVerCategoriaActivity.this, MascotaActivity.class);
                intent.putExtra(MascotaActivity.EXTRA_MASCOTAID, realId);
                startActivity(intent);
            }
        });
    }

    private void cargarMascotasDesdeBD() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int idUsuario = prefs.getInt("idUsuario", -1);
        if (idUsuario == -1) {
            Toast.makeText(this, "Debe Iniciar sesión primero", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cursor = dbHelper.obtenerTodasLasMascotas(idUsuario);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id_mascota"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));

                mascotaNombres.add(nombre);
                mascotaIds.add(id);
            }
            cursor.close();
        }
    }
}
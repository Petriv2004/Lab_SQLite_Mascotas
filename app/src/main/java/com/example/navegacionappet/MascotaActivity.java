package com.example.navegacionappet;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MascotaActivity extends AppCompatActivity {
    public static final String EXTRA_MASCOTAID = "mascotaId";
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mascota);

        dbHelper = new DatabaseHelper(this);
        int mascotaId = getIntent().getIntExtra(EXTRA_MASCOTAID, -1);

        if (mascotaId != -1) {
            cargarMascotaDesdeBD(mascotaId);
        }
    }

    private void cargarMascotaDesdeBD(int mascotaId) {
        Cursor cursor = dbHelper.obtenerMascotaPorId(mascotaId);
        if (cursor != null && cursor.moveToFirst()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
            String raza = cursor.getString(cursor.getColumnIndexOrThrow("raza"));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
            int edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"));
            String sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo"));

            TextView name = findViewById(R.id.name);
            name.setText(nombre);

            TextView razaView = findViewById(R.id.raza);
            razaView.setText(raza);

            TextView descripcionView = findViewById(R.id.description);
            descripcionView.setText(descripcion);

            TextView edadView = findViewById(R.id.edad);
            edadView.setText(String.valueOf(edad));

            TextView sexoView = findViewById(R.id.sexo);
            sexoView.setText(sexo);

            cursor.close();
        }
    }
}

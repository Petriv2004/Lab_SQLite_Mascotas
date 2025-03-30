package com.example.navegacionappet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CambiarContrasena extends AppCompatActivity {

    private EditText etContrasenaActual, etNuevaContrasena, etConfirmarContrasena;
    private Button btnGuardar;
    private DatabaseHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        etContrasenaActual = findViewById(R.id.et_contrasena_actual);
        etNuevaContrasena = findViewById(R.id.et_nueva_contrasena);
        etConfirmarContrasena = findViewById(R.id.et_confirmar_contrasena);
        btnGuardar = findViewById(R.id.btn_guardar_contrasena);

        dbHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        userId = sharedPreferences.getInt("idUsuario", -1);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarContrasena();
            }
        });
    }

    private void cambiarContrasena() {
        String contrasenaActual = etContrasenaActual.getText().toString().trim();
        String nuevaContrasena = etNuevaContrasena.getText().toString().trim();
        String confirmarContrasena = etConfirmarContrasena.getText().toString().trim();

        if (contrasenaActual.isEmpty() || nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean cambiado = dbHelper.cambiarContrasena(userId, contrasenaActual, nuevaContrasena);

        if (cambiado) {
            Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al cambiar la contraseña. Verifica tu contraseña actual.", Toast.LENGTH_SHORT).show();
        }
    }
}
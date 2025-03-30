package com.example.navegacionappet;

import android.content.Intent;
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

public class EditarInformacion extends AppCompatActivity {
    private EditText etNombre, etNombreUsuario, etCorreo, etDireccion;
    private Button btnGuardar;
    private DatabaseHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_informacion);

        etNombre = findViewById(R.id.et_nombre);
        etNombreUsuario = findViewById(R.id.et_nombreUsuario);
        etCorreo = findViewById(R.id.et_correo);
        etDireccion = findViewById(R.id.et_direccion);
        btnGuardar = findViewById(R.id.btn_guardar);

        dbHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        userId = sharedPreferences.getInt("idUsuario", -1);

        if (userId != -1) {
            cargarDatosUsuario(userId);
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });
    }

    private void cargarDatosUsuario(int userId) {
        Usuario usuario = dbHelper.obtenerUsuarioPorId(userId);
        if (usuario != null) {
            etNombre.setText(usuario.getNombreCompleto());
            etNombreUsuario.setText(usuario.getNombre());
            etCorreo.setText(usuario.getCorreo());
            etDireccion.setText(usuario.getDireccion());
        }
    }

    private void guardarCambios() {
        String nombreCompleto = etNombre.getText().toString().trim();
        String nombre = etNombreUsuario.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();

        if (nombreCompleto.isEmpty() || nombre.isEmpty() || correo.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean actualizado = dbHelper.actualizarUsuario(userId, nombreCompleto, nombre, correo, direccion);

        if (actualizado) {
            Toast.makeText(this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MiPerfilActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
        }
    }
}
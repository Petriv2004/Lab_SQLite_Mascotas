package com.example.navegacionappet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarMascotaActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etNombre;
    private EditText etRaza;
    private EditText etDescripcion;
    private EditText etEdad;
    private RadioGroup rgSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_mascota);

        dbHelper = new DatabaseHelper(this);
        etNombre = findViewById(R.id.et_nombre);
        etRaza = findViewById(R.id.et_raza);
        etDescripcion = findViewById(R.id.et_descripcion);
        etEdad = findViewById(R.id.et_edad);
        rgSexo = findViewById(R.id.rg_sexo);
    }

    public void RegistrarMascota(View view) {
        String nombre = etNombre.getText().toString().trim();
        String raza = etRaza.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        String edadStr = etEdad.getText().toString().trim();
        if (edadStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa la edad de la mascota", Toast.LENGTH_SHORT).show();
            return;
        }
        int edad = Integer.parseInt(edadStr);

        int sexoId = rgSexo.getCheckedRadioButtonId();
        if (sexoId == -1) {
            Toast.makeText(this, "Por favor, selecciona el sexo de la mascota", Toast.LENGTH_SHORT).show();
            return;
        }
        String sexoString = (sexoId == R.id.rb_macho) ? "M" : "F";

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int idUsuario = sharedPreferences.getInt("idUsuario", -1);

        if (idUsuario == -1) {
            Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show();
            return;
        }
        long resultado = dbHelper.insertMascota(nombre, raza, descripcion, edad, sexoString, idUsuario);

        if (resultado == -1) {
            Toast.makeText(this, "Error al registrar la mascota", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mascota registrada con éxito", Toast.LENGTH_SHORT).show();
            etNombre.setText("");
            etRaza.setText("");
            etDescripcion.setText("");
            etEdad.setText("");
            rgSexo.clearCheck();
        }
    }
}
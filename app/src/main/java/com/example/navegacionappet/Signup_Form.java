package com.example.navegacionappet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navegacionappet.R;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Signup_Form extends AppCompatActivity {
    private EditText birthdateField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form);
        birthdateField = findViewById(R.id.dateofbirth);
        birthdateField.setOnClickListener(v -> showDatePicker());
    }
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(selectedYear, selectedMonth, selectedDay);

            Calendar eighteenYearsAgo = Calendar.getInstance();
            eighteenYearsAgo.add(Calendar.YEAR, -18);

            if (selectedDate.after(eighteenYearsAgo)) {
                Toast.makeText(this, "Debes ser mayor de 18 años", Toast.LENGTH_SHORT).show();
            } else {
                birthdateField.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    public void onClickRegister(View view) {
        EditText etNombreCompleto = findViewById(R.id.nombreCompleto);
        EditText etNombreUsuario = findViewById(R.id.nombreUsuario);
        EditText etCorreo = findViewById(R.id.correo);
        EditText etDireccion = findViewById(R.id.physicaladdress);
        EditText etFechaNacimiento = findViewById(R.id.dateofbirth);
        EditText etContrasena = findViewById(R.id.contrasena);
        EditText etConfirmarContrasena = findViewById(R.id.confirmarContrasena);
        RadioGroup rgGenero = findViewById(R.id.radioGroupGenero);

        String nombreCompleto = etNombreCompleto.getText().toString().trim();
        String nombreUsuario = etNombreUsuario.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim().toLowerCase();
        String direccion = etDireccion.getText().toString().trim();
        String fechaNacimiento = etFechaNacimiento.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String confirmarContrasena = etConfirmarContrasena.getText().toString().trim();

        if (nombreCompleto.isEmpty() || nombreUsuario.isEmpty() || correo.isEmpty() ||
                direccion.isEmpty() || fechaNacimiento.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!contrasena.equals(confirmarContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        int selectedGenderId = rgGenero.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            Toast.makeText(this, "Seleccione un género", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedGenderButton = findViewById(selectedGenderId);
        String sexo = selectedGenderButton.getText().toString();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        boolean registrado = dbHelper.registrarUsuario(nombreCompleto, nombreUsuario, correo, direccion, fechaNacimiento, sexo, contrasena);

        if (registrado) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.navegacionappet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MascotasDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USUARIO = "Usuario";
    private static final String COLUMN_ID_USUARIO = "id_usuario";
    private static final String COLUMN_NOMBRE_COMPLETO_USUARIO = "nombreCompleto";
    private static final String COLUMN_NOMBRE_USUARIO = "nombre";
    private static final String COLUMN_CORREO_USUARIO = "correo";
    private static final String COLUMN_DIRECCION_USUARIO = "direccion";
    private static final String COLUMN_FECHA_NACIMIENTO_USUARIO = "fecha_nacimiento";
    private static final String COLUMN_SEXO_USUARIO = "sexo";
    private static final String COLUMN_CONTRASENA_USUARIO = "contrasena";
    private static final String TABLE_MASCOTA = "Mascota";
    private static final String COLUMN_ID_MASCOTA = "id_mascota";
    private static final String COLUMN_NOMBRE_MASCOTA = "nombre";
    private static final String COLUMN_RAZA_MASCOTA = "raza";
    private static final String COLUMN_DESCRIPCION_MASCOTA = "descripcion";
    private static final String COLUMN_EDAD_MASCOTA = "edad";
    private static final String COLUMN_SEXO_MASCOTA = "sexo";
    private static final String COLUMN_ID_USUARIO_FK = "id_usuario";
    private static final String TABLE_CHAT = "Chat";
    private static final String COLUMN_ID_MENSAJE = "id_mensaje";
    private static final String COLUMN_FECHA_MENSAJE = "fecha";
    private static final String COLUMN_ID_CHAT_USUARIO_FK = "id_usuario";
    private static final String COLUMN_CONTENIDO_MENSAJE = "contenido";

    private static final String CREATE_TABLE_USUARIO =
            "CREATE TABLE " + TABLE_USUARIO + " (" +
                    COLUMN_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE_COMPLETO_USUARIO + " TEXT NOT NULL, " +
                    COLUMN_NOMBRE_USUARIO + " TEXT NOT NULL, " +
                    COLUMN_CORREO_USUARIO + " TEXT NOT NULL, " +
                    COLUMN_DIRECCION_USUARIO + " TEXT NOT NULL, " +
                    COLUMN_FECHA_NACIMIENTO_USUARIO + " TEXT NOT NULL, " +
                    COLUMN_SEXO_USUARIO + " TEXT NOT NULL, " +
                    COLUMN_CONTRASENA_USUARIO + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_MASCOTA =
            "CREATE TABLE " + TABLE_MASCOTA + " (" +
                    COLUMN_ID_MASCOTA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE_MASCOTA + " TEXT NOT NULL, " +
                    COLUMN_RAZA_MASCOTA + " TEXT NOT NULL, " +
                    COLUMN_DESCRIPCION_MASCOTA + " TEXT, " +
                    COLUMN_EDAD_MASCOTA + " INTEGER NOT NULL, " +
                    COLUMN_SEXO_MASCOTA + " TEXT NOT NULL, " +
                    COLUMN_ID_USUARIO_FK + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_ID_USUARIO_FK + ") REFERENCES " + TABLE_USUARIO + "(" + COLUMN_ID_USUARIO + "));";

    private static final String CREATE_TABLE_CHAT =
            "CREATE TABLE " + TABLE_CHAT + " (" +
                    COLUMN_ID_MENSAJE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ID_CHAT_USUARIO_FK + " INTEGER, " +
                    COLUMN_FECHA_MENSAJE + " TEXT NOT NULL, " +
                    COLUMN_CONTENIDO_MENSAJE + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + COLUMN_ID_CHAT_USUARIO_FK + ") REFERENCES " + TABLE_USUARIO + "(" + COLUMN_ID_USUARIO + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_MASCOTA);
        db.execSQL(CREATE_TABLE_CHAT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASCOTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        onCreate(db);
    }
    public boolean registrarUsuario(String nombreCompleto, String nombre, String correo, String direccion, String fechaNacimiento, String sexo, String contrasena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOMBRE_COMPLETO_USUARIO, nombreCompleto);
        values.put(COLUMN_NOMBRE_USUARIO, nombre);
        values.put(COLUMN_CORREO_USUARIO, correo);
        values.put(COLUMN_DIRECCION_USUARIO, direccion);
        values.put(COLUMN_FECHA_NACIMIENTO_USUARIO, fechaNacimiento);
        values.put(COLUMN_SEXO_USUARIO, sexo);
        values.put(COLUMN_CONTRASENA_USUARIO, contrasena);

        long result = db.insert(TABLE_USUARIO, null, values);
        return result != -1;
    }
    public int validarUsuario(String correo, String contrasena) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID_USUARIO + " FROM " + TABLE_USUARIO +
                " WHERE " + COLUMN_CORREO_USUARIO + "=? AND " + COLUMN_CONTRASENA_USUARIO + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{correo, contrasena});

        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        return userId;
    }
    public void guardarMensaje(int userId, String contenido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_CHAT_USUARIO_FK, userId);
        values.put(COLUMN_FECHA_MENSAJE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        values.put(COLUMN_CONTENIDO_MENSAJE, contenido);

        db.insert(TABLE_CHAT, null, values);
        db.close();
    }

    public List<String> obtenerMensajes(int userId) {
        List<String> mensajes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_CONTENIDO_MENSAJE + ", " + COLUMN_FECHA_MENSAJE +
                " FROM " + TABLE_CHAT +
                " WHERE " + COLUMN_ID_CHAT_USUARIO_FK + " = ? ORDER BY " + COLUMN_FECHA_MENSAJE + " ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                String mensaje = cursor.getString(0);
                String fecha = cursor.getString(1);
                mensajes.add(fecha + " - " + mensaje);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mensajes;
    }

    public long insertMascota(String nombre, String raza, String descripcion, int edad, String sexo, int idUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_MASCOTA, nombre);
        values.put(COLUMN_RAZA_MASCOTA, raza);
        values.put(COLUMN_DESCRIPCION_MASCOTA, descripcion);
        values.put(COLUMN_EDAD_MASCOTA, edad);
        values.put(COLUMN_SEXO_MASCOTA, sexo);
        values.put(COLUMN_ID_USUARIO_FK, idUsuario);
        return db.insert(TABLE_MASCOTA, null, values);
    }

    public Cursor obtenerTodasLasMascotas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUMN_ID_MASCOTA + ", " + COLUMN_NOMBRE_MASCOTA + " FROM " + TABLE_MASCOTA, null);
    }

    public Cursor obtenerTodasLasMascotas(int idUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUMN_ID_MASCOTA + ", " + COLUMN_NOMBRE_MASCOTA + " FROM " + TABLE_MASCOTA + " WHERE " + idUsuario + " = " + COLUMN_ID_USUARIO_FK, null);
    }

    public Cursor obtenerMascotaPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM mascota WHERE id_mascota = ?", new String[]{String.valueOf(id)});
    }

    public Usuario obtenerUsuarioPorId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USUARIO + " WHERE " + COLUMN_ID_USUARIO + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            usuario = new Usuario(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_COMPLETO_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORREO_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECCION_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_NACIMIENTO_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEXO_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTRASENA_USUARIO))
            );
        }
        cursor.close();
        db.close();
        return usuario;
    }

    public boolean actualizarUsuario(int idUsuario, String nombreCompleto, String nombre, String correo, String direccion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_COMPLETO_USUARIO, nombreCompleto);
        values.put(COLUMN_NOMBRE_USUARIO, nombre);
        values.put(COLUMN_CORREO_USUARIO, correo);
        values.put(COLUMN_DIRECCION_USUARIO, direccion);

        int filasAfectadas = db.update(TABLE_USUARIO, values, COLUMN_ID_USUARIO + " = ?", new String[]{String.valueOf(idUsuario)});
        return filasAfectadas > 0;
    }

    public boolean cambiarContrasena(int idUsuario, String contrasenaActual, String nuevaContrasena) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT contrasena FROM usuario WHERE id_usuario = ?", new String[]{String.valueOf(idUsuario)});

        if (cursor.moveToFirst()) {
            String contrasenaBD = cursor.getString(0);
            if (contrasenaBD.equals(contrasenaActual)) {
                ContentValues values = new ContentValues();
                values.put("contrasena", nuevaContrasena);
                int filasAfectadas = db.update("usuario", values, "id_usuario = ?", new String[]{String.valueOf(idUsuario)});
                cursor.close();
                return filasAfectadas > 0;
            }
        }
        cursor.close();
        return false;
    }

}

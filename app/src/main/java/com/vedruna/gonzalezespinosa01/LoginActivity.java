package com.vedruna.gonzalezespinosa01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Clase LoginActivity que maneja la funcionalidad de inicio de sesión.
 * Esta clase permite a los usuarios iniciar sesión y crear nuevos usuarios si es necesario.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity"; // Etiqueta para los registros de depuración
    private TextView mensajeBienvenida;
    private SQLiteDatabase db;
    private AdminSQliteOpenHelper dbHelper;
    private static final String USUARIO_CORRECTO = "admin";
    private static final String CONTRASENA_CORRECTA = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mensajeBienvenida = findViewById(R.id.textView);
        dbHelper = new AdminSQliteOpenHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * Método invocado cuando se hace clic en el botón de iniciar sesión.
     * Verifica las credenciales del usuario e inicia sesión si son correctas.
     * Muestra un mensaje de error si las credenciales son incorrectas.
     */
    public void logearse(View view) {
        EditText nombreEditText = findViewById(R.id.editTextTextPassword);
        EditText contrasenaEditText = findViewById(R.id.editTextTextPassword2);

        String username = nombreEditText.getText().toString();
        String password = contrasenaEditText.getText().toString();

        boolean credencialesCorrectas = dbHelper.verificarCredenciales(username, password);

        if (credencialesCorrectas) {
            nombreEditText.setText("");
            contrasenaEditText.setText("");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la base de datos
        db.close();
    }

    /**
     * Método invocado cuando se hace clic en el botón de crear usuario.
     * Crea un nuevo usuario con el nombre de usuario y contraseña proporcionados.
     * Muestra mensajes de éxito o error según el resultado.
     */
    public void crearUsuario(View view) {
        EditText nombreEditText = findViewById(R.id.editTextTextPassword);
        EditText contrasenaEditText = findViewById(R.id.editTextTextPassword2);

        String username = nombreEditText.getText().toString().trim();
        String password = contrasenaEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Rellena usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean usuarioCreado = dbHelper.crearUsuario(username, password);
        if (usuarioCreado) {
            Toast.makeText(this, "Usuario creado satisfactoriamente", Toast.LENGTH_SHORT).show();
            nombreEditText.setText("");
            contrasenaEditText.setText("");
        } else {
            Toast.makeText(this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show();
            nombreEditText.setText("");
            contrasenaEditText.setText("");
        }
    }


}
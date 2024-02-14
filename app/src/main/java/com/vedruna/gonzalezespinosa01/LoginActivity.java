package com.vedruna.gonzalezespinosa01;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

/**
 * Clase LoginActivity que maneja la funcionalidad de inicio de sesión.
 * Esta clase permite a los usuarios iniciar sesión y crear nuevos usuarios si es necesario.
 */
public class LoginActivity extends AppCompatActivity {

    private TextView mensajeBienvenida;
    private SQLiteDatabase db;
    private AdminSQliteOpenHelper dbHelper;
    private static final String USUARIO_CORRECTO = "admin";
    private static final String CONTRASENA_CORRECTA = "admin";
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String imageUrl = "https://ethic.es/wp-content/uploads/2023/03/imagen.jpg"; // URL de la imagen

        ImageView imageView = findViewById(R.id.imageView1); // Obtener referencia al ImageView en tu diseño

        // Cargar y mostrar la imagen usando Picasso
        Picasso.get().load(imageUrl).into(imageView);

        findViewById(R.id.textView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));

            }
        });

        mensajeBienvenida = findViewById(R.id.textView);
        dbHelper = new AdminSQliteOpenHelper(this);
        db = dbHelper.getWritableDatabase();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END config_signin]

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
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


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    /*
        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
     */


    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(

            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                        try {
                            // Google Sign In was successful, authenticate with Firebase
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                            firebaseAuthWithGoogle(account.getIdToken());
                        } catch (ApiException e) {
                            // Google Sign In failed, update UI appropriately
                            Log.w(TAG, "Google sign in failed", e);
                        }
                    }
                }
            }
    );

    // [END signin]

    private void updateUI(FirebaseUser user) {
FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
if(currentUser !=null){
    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    startActivity(intent);
    finish();
}
    }

}
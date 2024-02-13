package com.vedruna.gonzalezespinosa01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Clase principal que gestiona la actividad principal de la aplicación.
 * Se encarga de manejar la navegación entre los diferentes fragmentos de la aplicación.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String valorRecibido = intent.getStringExtra("valor");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.homeFragment) {
                navController.navigate(R.id.homeFragment);
            } else if (item.getItemId() == R.id.crearFragment) {
                navController.navigate(R.id.crearFragment);
            } else if (item.getItemId() == R.id.modificarFragment){
                navController.navigate(R.id.modificarFragment);
            }  else if (item.getItemId() == R.id.eliminarFragment){
                navController.navigate(R.id.eliminarFragment);
            } else if (item.getItemId() == R.id.navigation_salir){
                navController.navigate(R.id.salirFragment);
            }
            return true;
        });



    }
}

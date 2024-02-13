package com.vedruna.gonzalezespinosa01;

import com.vedruna.gonzalezespinosa01.Producto;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento HomeFragment que muestra una lista de productos.
 * Los productos se cargan desde una base de datos SQLite y se muestran en un RecyclerView.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Producto> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Cargar los elementos de la base de datos al iniciar el fragmento
        productList = cargarElementosDesdeBaseDeDatos();
        adapter = new MyAdapter(productList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Actualizar la lista cada vez que el fragmento se reanude
        cargarElementosDesdeBaseDeDatos();
    }

    /**
     * Método privado para cargar los elementos desde la base de datos.
     * @return Una lista de productos obtenidos de la base de datos.
     */
    private List<Producto> cargarElementosDesdeBaseDeDatos() {
        // Instancia de la base de datos SQLite
        AdminSQliteOpenHelper adminSqLiteOpenHelper = new AdminSQliteOpenHelper(getContext());
        SQLiteDatabase database = adminSqLiteOpenHelper.getReadableDatabase();

        // Lista para almacenar los productos obtenidos de la base de datos
        List<Producto> productos = new ArrayList<>();

        // Consulta a la base de datos para obtener todos los productos
        Cursor cursor = database.rawQuery("SELECT * FROM articulos", null);

        // Obtener los índices de las columnas
        int codigoIndex = cursor.getColumnIndex("codigo");
        int nombreIndex = cursor.getColumnIndex("nombre");
        int descripcionIndex = cursor.getColumnIndex("descripcion");
        int precioIndex = cursor.getColumnIndex("precio");

        // Verificar si los índices son válidos
        if (codigoIndex >= 0 && nombreIndex >= 0 && descripcionIndex >= 0 && precioIndex >= 0) {
            // Iterar a través del cursor y agregar cada producto a la lista
            if (cursor.moveToFirst()) {
                do {
                    int codigo = cursor.getInt(codigoIndex);
                    String nombre = cursor.getString(nombreIndex);
                    String descripcion = cursor.getString(descripcionIndex);
                    double precio = cursor.getDouble(precioIndex);

                    Producto producto = new Producto(codigo, nombre, descripcion, precio);
                    productos.add(producto);
                } while (cursor.moveToNext());
            }
        } else {
            // Manejar el caso en el que una columna no existe en el conjunto de resultados
        }

        // Cerrar el cursor y la conexión de la base de datos
        cursor.close();
        database.close();

        // Devolver la lista de productos
        return productos;
    }
}

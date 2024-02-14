package com.vedruna.gonzalezespinosa01;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * Fragmento CrearFragment que permite al usuario crear un nuevo producto.
 * El usuario puede ingresar información como código, nombre, descripción y precio del producto.
 */
public class CrearFragment extends Fragment {

    private EditText etCodigo, etNombre, etDescripcion, etPrecio;
    private Button btnAgregar;

    public CrearFragment() {
        // Constructor público vacío requerido por la clase Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_crear, container, false);

        // Inicializar vistas
        etCodigo = view.findViewById(R.id.etCodigo);
        etNombre = view.findViewById(R.id.etNombre);
        etDescripcion = view.findViewById(R.id.etDescripcion);
        etPrecio = view.findViewById(R.id.etPrecio);
        btnAgregar = view.findViewById(R.id.btnAgregar);

        // Configurar el filtro para el campo de precio
        etPrecio.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // Verificar si el texto ingresado contiene solo números o punto decimal
                if (source.toString().matches("[0-9.]+")) {
                    return source;
                }
                return "";
            }
        }});

        // Configurar el botón de agregar producto
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos ingresados por el usuario
                String codigo = etCodigo.getText().toString().trim();
                String nombre = etNombre.getText().toString().trim();
                String descripcion = etDescripcion.getText().toString().trim();
                String precio = etPrecio.getText().toString().trim();

                // Validar los datos ingresados por el usuario y agregar el producto a la base de datos
                validarYAgregarProducto(codigo, nombre, descripcion, precio);
            }
        });

        return view;
    }

    /**
     * Método privado para validar los datos ingresados por el usuario y agregar el producto a la base de datos.
     * @param codigo El código del producto.
     * @param nombre El nombre del producto.
     * @param descripcion La descripción del producto.
     * @param precio El precio del producto.
     */
    private void validarYAgregarProducto(String codigo, String nombre, String descripcion, String precio) {
        // Validar el código del producto
        if (!codigo.isEmpty() && Integer.parseInt(codigo) > 0) {
            // Validar el nombre del producto
            if (nombre.length() > 0 && Character.isLetter(nombre.charAt(0))) {
                // Validar la descripción del producto
                if (descripcion.length() > 0 && Character.isLetter(descripcion.charAt(0))) {
                    // Validar el precio del producto
                    if (!precio.isEmpty()) {
                        // Agregar el producto a la base de datos
                        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(getContext());
                        SQLiteDatabase bd = admin.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("codigo", codigo);
                        values.put("nombre", nombre);
                        values.put("descripcion", descripcion);
                        values.put("precio", precio);
                        long result = bd.insert("articulos", null, values);
                        bd.close();

                        // Mostrar un Toast con el resultado de la operación
                        if (result != -1) {
                            etCodigo.setText("");
                            etNombre.setText("");
                            etDescripcion.setText("");
                            etPrecio.setText("");
                            Toast.makeText(getContext(), "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error al agregar el producto", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Mostrar un Toast si el precio está vacío
                        Toast.makeText(getContext(), "Por favor, ingrese un precio", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Mostrar un Toast si la descripción está vacía
                    Toast.makeText(getContext(), "Por favor, ingrese una URL de Imagen válida", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Mostrar un Toast si el nombre está vacío
                Toast.makeText(getContext(), "Por favor, ingrese un nombre válido", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Mostrar un Toast si el código no es válido
            Toast.makeText(getContext(), "El código debe ser un número positivo", Toast.LENGTH_SHORT).show();
        }
    }
}

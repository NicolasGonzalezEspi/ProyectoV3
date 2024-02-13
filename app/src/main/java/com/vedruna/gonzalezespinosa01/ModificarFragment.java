package com.vedruna.gonzalezespinosa01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragmento ModificarFragment que permite al usuario modificar un producto existente.
 * El usuario puede ingresar el código del producto a modificar y los nuevos datos del producto.
 */
public class ModificarFragment extends Fragment {

    private EditText editTextCodigo;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextPrecio;
    private Button buttonModificar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_modificar, container, false);

        // Inicializar vistas
        editTextCodigo = view.findViewById(R.id.editTextCodigo);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextPrecio = view.findViewById(R.id.editTextPrecio);
        buttonModificar = view.findViewById(R.id.buttonModificar);

        // Configurar el listener para el botón "Modificar"
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Invocar al método para modificar el producto
                modificarProducto();
            }
        });

        return view;
    }

    /**
     * Método privado para modificar el producto en la base de datos.
     * Valida los datos ingresados por el usuario y realiza la modificación si son válidos.
     */
    private void modificarProducto() {
        // Obtener el código del producto ingresado por el usuario
        String codigoProductoStr = editTextCodigo.getText().toString().trim();
        int codigoProducto;

        // Verificar si se ingresó un código válido
        try {
            codigoProducto = Integer.parseInt(codigoProductoStr);
        } catch (NumberFormatException e) {
            // Mostrar un mensaje de error si el código no es un número válido
            Toast.makeText(getContext(), "Por favor, ingrese un código de producto válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el código es mayor que 0
        if (codigoProducto <= 0) {
            // Mostrar un mensaje de error si el código es menor o igual a 0
            Toast.makeText(getContext(), "Por favor, ingrese un código de producto mayor que 0", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el nuevo nombre, descripción y precio del producto
        String nuevoNombre = editTextNombre.getText().toString().trim();
        String nuevaDescripcion = editTextDescripcion.getText().toString().trim();
        double nuevoPrecio;

        // Verificar si se ingresaron datos válidos para el precio
        try {
            nuevoPrecio = Double.parseDouble(editTextPrecio.getText().toString().trim());
        } catch (NumberFormatException e) {
            // Mostrar un mensaje de error si el precio no es un número válido
            Toast.makeText(getContext(), "Por favor, ingrese un precio válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que al menos uno de los campos de nombre o descripción tenga al menos un carácter
        if (nuevoNombre.isEmpty() || nuevaDescripcion.isEmpty()) {
            // Mostrar un mensaje de error si ambos campos de nombre y descripción están vacíos
            Toast.makeText(getContext(), "Por favor, ingrese un nombre y una descripción para el producto", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear una instancia de AdminSQliteOpenHelper para modificar el producto en la base de datos
        AdminSQliteOpenHelper adminSQLiteOpenHelper = new AdminSQliteOpenHelper(getContext());

        // Llamar al método modificarProducto y obtener la cantidad de registros modificados
        int cantidadModificada = adminSQLiteOpenHelper.modificarProducto(codigoProducto, nuevoNombre, nuevaDescripcion, nuevoPrecio);

        // Mostrar un mensaje según el resultado de la modificación
        if (cantidadModificada > 0) {
            Toast.makeText(getContext(), "Producto modificado con éxito", Toast.LENGTH_SHORT).show();

            // Limpiar los EditText después de modificar el producto
            editTextCodigo.setText("");
            editTextNombre.setText("");
            editTextDescripcion.setText("");
            editTextPrecio.setText("");
        } else {
            Toast.makeText(getContext(), "No se encontró ningún producto con ese código", Toast.LENGTH_SHORT).show();
        }
    }
}

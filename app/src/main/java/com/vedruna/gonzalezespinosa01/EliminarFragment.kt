package com.vedruna.gonzalezespinosa01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Fragmento EliminarFragment que permite al usuario eliminar un producto existente.
 * El usuario puede ingresar el código del producto a eliminar.
 */
class EliminarFragment : Fragment() {

    private lateinit var editTextCodigo: EditText
    private lateinit var buttonEliminar: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_eliminar, container, false)

        // Inicializar vistas
        editTextCodigo = view.findViewById(R.id.editTextCodigo)
        buttonEliminar = view.findViewById(R.id.buttonEliminar)

        // Configurar el listener para el botón "Eliminar"
        buttonEliminar.setOnClickListener {
            // Invocar al método para eliminar el producto
            eliminarProducto()
        }

        return view
    }

    /**
     * Método privado para eliminar un producto de la base de datos.
     * Valida el código ingresado por el usuario y realiza la eliminación si es válido.
     */
    private fun eliminarProducto() {
        // Obtener el código del producto ingresado por el usuario
        val codigoProducto = editTextCodigo.text.toString().trim()

        // Verificar si se ingresó un código válido
        if (codigoProducto.isNotEmpty()) {
            // Convertir el código del producto a Int
            val codigoProductoInt = codigoProducto.toInt()

            // Obtener una instancia de la base de datos
            val admin = AdminSQliteOpenHelper(requireContext())
            val db = admin.writableDatabase

            // Realizar la eliminación del producto con el código proporcionado
            val cant = admin.eliminarProducto(codigoProductoInt)

            // Cerrar la conexión a la base de datos
            db.close()

            // Limpiar el EditText después de eliminar el producto
            editTextCodigo.text.clear()

            // Mostrar un mensaje según el resultado de la eliminación
            if (cant == 1) {
                Toast.makeText(context, "Producto eliminado con éxito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No se encontró ningún producto con ese código", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Mostrar un mensaje de error si el campo está vacío
            Toast.makeText(context, "Por favor, ingrese un código de producto válido", Toast.LENGTH_SHORT).show()
        }
    }

}

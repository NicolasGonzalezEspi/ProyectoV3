package com.vedruna.gonzalezespinosa01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adaptador personalizado para el RecyclerView que muestra la lista de productos.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Producto> productList;

    public MyAdapter(List<Producto> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de cada elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Vincular los datos de un producto con las vistas de ViewHolder
        Producto producto = productList.get(position);
        holder.bind(producto);
    }

    @Override
    public int getItemCount() {
        // Devolver el número total de elementos en la lista de productos
        return productList.size();
    }

    /**
     * Clase ViewHolder que representa cada elemento de la lista de productos.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView codigoTextView, nombreTextView, descripcionTextView, precioTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar vistas dentro de ViewHolder
            codigoTextView = itemView.findViewById(R.id.codigoTextView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            precioTextView = itemView.findViewById(R.id.precioTextView);
        }

        /**
         * Método para enlazar los datos de un producto con las vistas de ViewHolder.
         * @param producto Objeto Producto que contiene los datos a mostrar.
         */
        public void bind(Producto producto) {
            if (producto != null) {
                // Mostrar los datos del producto en las vistas correspondientes
                codigoTextView.setText(String.valueOf(producto.getCodigo()));
                nombreTextView.setText(producto.getNombre());
                descripcionTextView.setText(producto.getDescripcion());
                precioTextView.setText(String.valueOf(producto.getPrecio()));
            }
        }
    }
}

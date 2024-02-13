package com.vedruna.gonzalezespinosa01;

// Clase que representa un producto
public class Producto {
    private int codigo; // Código del producto
    private String nombre; // Nombre del producto
    private String descripcion; // Descripción del producto
    private double precio; // Precio del producto

    /**
     * Constructor de la clase Producto.
     * @param codigo Código del producto.
     * @param nombre Nombre del producto.
     * @param descripcion Descripción del producto.
     * @param precio Precio del producto.
     */
    public Producto(int codigo, String nombre, String descripcion, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    /**
     * Método getter para obtener el código del producto.
     * @return Código del producto.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Método getter para obtener el nombre del producto.
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método getter para obtener la descripción del producto.
     * @return Descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método getter para obtener el precio del producto.
     * @return Precio del producto.
     */
    public double getPrecio() {
        return precio;
    }
}

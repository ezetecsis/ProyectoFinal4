        package techlab;

import java.util.ArrayList;

// Clase Producto
class producto {
    private final int id;
    private final String nombre;
    private final String descripcion;
    private final double precio;
    private int stock;

    public producto(int id, String nombre, String descripcion, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }

    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Precio: $" + precio + ", Stock: " + stock;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

        // Clase LíneaPedido
        public record LineaPedido(techlab.producto producto, int cantidad) {
            public double getSubtotal() {
                return producto.getPrecio() * cantidad;
            }
        }

// Clase Pedido
class Pedido {
    private final int id;
    private final ArrayList<LineaPedido> lineas;
    private String estado;

    public Pedido(int id) {
        this.id = id;
        this.lineas = new ArrayList<>();
        this.estado = "Pendiente";
    }

    public void agregarLinea(producto p, int cantidad) {
        this.lineas.add(new LineaPedido(p, cantidad));
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido lp : lineas) {
            total += lp.getSubtotal();
        }
        return total;
    }

    public ArrayList<LineaPedido> getLineas() {
        return lineas;
    }

    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido ID: ").append(id).append(", Estado: ").append(estado).append("\n");
        for (LineaPedido lp : lineas) {
            sb.append(" - ").append(lp.producto().getNombre())
                    .append(" x").append(lp.cantidad())
                    .append(" ($").append(lp.getSubtotal()).append(")\n");
        }
        sb.append("Total: $").append(calcularTotal());
        return sb.toString();
    }
}


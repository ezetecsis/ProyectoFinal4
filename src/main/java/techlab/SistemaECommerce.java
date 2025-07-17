package techlab;

import java.util.ArrayList;
import java.util.Scanner;

// Clase principal
public class SistemaECommerce {
    static ArrayList<producto> productos = new ArrayList<>();
    static ArrayList<Pedido> pedidos = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static int nextProductoId = 1;
    static int nextPedidoId = 1;

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    listarProductos();
                    break;
                case 3:
                    buscarProducto();
                    break;
                case 4:
                    actualizarStock();
                    break;
                case 5:
                    crearPedido();
                    break;
                case 6:
                    listarPedidos();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    static void mostrarMenu() {
        System.out.println("\n--- SISTEMA E-COMMERCE ---");
        System.out.println("1. Agregar Producto");
        System.out.println("2. Listar Productos");
        System.out.println("3. Buscar Producto");
        System.out.println("4. Actualizar Stock");
        System.out.println("5. Crear Pedido");
        System.out.println("6. Listar Pedidos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    static void agregarProducto() {
        System.out.println("Ingrese nombre del producto:");
        String nombre = scanner.nextLine();
        System.out.println("Descripción:");
        String descripcion = scanner.nextLine();
        System.out.println("Precio:");
        double precio = Double.parseDouble(scanner.nextLine());
        System.out.println("Stock:");
        int stock = Integer.parseInt(scanner.nextLine());

        producto p = new producto(nextProductoId++, nombre, descripcion, precio, stock);
        productos.add(p);
        System.out.println("Producto agregado.");
    }

    static void listarProductos() {
        System.out.println("\n--- Lista de Productos ---");
        for (producto p : productos) {
            System.out.println(p);
        }
    }

    static void buscarProducto() {
        System.out.println("Ingrese ID del producto:");
        int id = Integer.parseInt(scanner.nextLine());
        producto p = buscarProductoPorId(id);
        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    static producto buscarProductoPorId(int id) {
        for (producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    static void actualizarStock() {
        System.out.println("Ingrese ID del producto:");
        int id = Integer.parseInt(scanner.nextLine());
        producto p = buscarProductoPorId(id);
        if (p != null) {
            System.out.println("Stock actual: " + p.getStock());
            System.out.println("Nuevo stock:");
            int stockNuevo = Integer.parseInt(scanner.nextLine());
            p.setStock(stockNuevo);
            System.out.println("Stock actualizado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    static void crearPedido() {
        Pedido pedido = new Pedido(nextPedidoId++);
        boolean agregarMas;
        do {
            System.out.println("Ingrese ID del producto a agregar:");
            int idProd = Integer.parseInt(scanner.nextLine());
            producto p = buscarProductoPorId(idProd);
            if (p != null) {
                System.out.println("Cantidad:");
                int cantidad = Integer.parseInt(scanner.nextLine());
                if (cantidad <= p.getStock()) {
                    pedido.agregarLinea(p, cantidad);
                    System.out.println("Producto agregado al pedido");
                } else {
                    System.out.println("Stock insuficiente");
                }
            } else {
                System.out.println("Producto no encontrado");
            }
            System.out.println("¿Agregar otro producto? (s/n)");
            agregarMas = scanner.nextLine().equalsIgnoreCase("s");
        } while (agregarMas);

        // Confirmar pedido y disminuir stock
        for (LineaPedido lp : pedido.getLineas()) {
            producto p = lp.producto();
            p.setStock(p.getStock() - lp.cantidad());
        }
        pedidos.add(pedido);
        System.out.println("Pedido creado:\n" + pedido);
    }

    static void listarPedidos() {
        System.out.println("\n--- Lista de Pedidos ---");
        for (Pedido p : pedidos) {
            System.out.println(p);
            System.out.println("---------------------------");
        }
    }
}

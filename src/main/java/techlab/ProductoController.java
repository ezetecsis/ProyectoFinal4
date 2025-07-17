package techlab;


import com.techlab.ecommerce.modelo.Producto;
import com.techlab.ecommerce.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(producto -> ResponseEntity.ok().body(producto))
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo producto
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // Actualizar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoDetalles) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoDetalles.getNombre());
            producto.setDescripcion(productoDetalles.getDescripcion());
            producto.setPrecio(productoDetalles.getPrecio());
            producto.setStock(productoDetalles.getStock());
            Producto actualizado = productoRepository.save(producto);
            return ResponseEntity.ok().body(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        return productoRepository.findById(id).map(producto -> {
            productoRepository.delete(producto);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
package ie.atu.week4.jpa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private List<Product> productList = new ArrayList<>();
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/getProducts")
    public List<Product> getProducts() {
        return productList;
    }

    @PostMapping("/addProduct")
    public ResponseEntity<List> addProduct(@RequestBody Product product) {
        productList = productService.add(product);

        return ResponseEntity.ok(productList);
    }

    private Product findProductById(int id) {
        for (Product product : productList) {
            if (product.getProductCode() == id) {
                return product;
            }
        }
        return null;
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        Product existingProduct = findProductById(id);

        if (existingProduct != null) {
            productService.existingProduct.setProductName(updatedProduct.getProductName());
            productService.existingProduct.setProductDescription(updatedProduct.getProductDescription());
            existingProduct.setProductPrice(updatedProduct.getProductPrice());
            return ResponseEntity.ok(existingProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<List<Product>> deleteProduct(@PathVariable int id) {
        Product existingProduct = productService.findProductById(id);

        if (existingProduct != null) {
            productList = productService.remove(existingProduct);
            return ResponseEntity.ok(productList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

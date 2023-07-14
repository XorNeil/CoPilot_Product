package com.productmgt.ims.Controller;

import com.productmgt.ims.Model.Product;
import com.productmgt.ims.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        LocalDateTime current = LocalDateTime.now();
        product.setCreated_at(current);
        product.setUpdated_at(current);
        Product p = service.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProducts(@PathVariable int id){
        Product p = service.getProduct(id);
        return ResponseEntity.ok().body(p);
    }
    @GetMapping("/products")
    public List< Product > getProducts(@RequestParam (value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                       @RequestParam (value = "noOfRecords", defaultValue = "10", required = false) int noOfRecords,
                                       @RequestParam (value = "sortBy", defaultValue = "asc", required = false) String sortBy,
                                       @RequestParam (value = "columnName", defaultValue = "id", required = false) String columnName)
    {
        System.out.println(pageNumber + " " + noOfRecords + " " + sortBy + " " + columnName);
        Page<Product> data = service.getProductPagingAndSorting(pageNumber, noOfRecords, sortBy, columnName);
        return data.getContent();
    }
    @GetMapping("/products/search")
    public List<Product> searchProducts(@RequestParam List<String> name,@RequestParam List<String> status)
    {
        System.out.println("name"+name);
        System.out.println("status"+status);
        List<Product> l= service.searchProducts(name,status);
        return l;
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestBody Product product) throws Exception {
            LocalDateTime current = LocalDateTime.now();
            product.setUpdated_at(current);
            String msg= service.updateProduct(id,product);
            return ResponseEntity.ok().body(msg);
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        String msg= service.deleteProduct(id);
        return ResponseEntity.ok().body(msg);
    }
    @GetMapping("/products/getFilter")
    public ResponseEntity<List<Product>> getProductByFilter(@RequestParam (value = "name", defaultValue = "", required = false) String name,
                                                            @RequestParam (value = "category", defaultValue = "", required = false)  String category,
                                                            @RequestParam (value = "vendor", defaultValue = "", required = false)  String vendor,
                                                            @RequestParam (value = "status", defaultValue = "", required = false) String status,
                                                            @RequestParam (value = "price", defaultValue = "0.00", required = false)  double price
                                                            ){
        //System.out.println("category"+category);
        List<Product> p = service.getProductByFilter(name,category,vendor,status,price);
        return ResponseEntity.ok().body(p);
    }
}

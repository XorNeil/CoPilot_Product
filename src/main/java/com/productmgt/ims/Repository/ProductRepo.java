package com.productmgt.ims.Repository;

import com.productmgt.ims.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>
{
    Product findByName(String name);
    List<Product> findByNameOrCategoryOrVendorOrStatusOrPrice(String name, String category, String vendor, String status,double price);
    @Query("select o from Product o where o.name in :name and o.status in :status")
    List<Product> findByNameAndStatus(List<String> name, List<String> status);
}

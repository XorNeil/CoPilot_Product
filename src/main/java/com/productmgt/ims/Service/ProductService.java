package com.productmgt.ims.Service;

import com.productmgt.ims.Exception.ProductNameAlreadyExistsException;
import com.productmgt.ims.Exception.ProductNotExistsException;
import com.productmgt.ims.Model.Product;
import com.productmgt.ims.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    public Product createProduct(Product product)
    {
        if(repo.findByName(product.getName())!=null)
        {
            System.out.println("Product Name already exists!!");
            throw new ProductNameAlreadyExistsException("Product Name already exists!!");
        }
        return repo.save(product);
    }

    public Product getProduct(int id){
        return repo.findById(id).orElseThrow(
                () -> new ProductNotExistsException("No product exists with this id = " + id));
    }

    public List<Product> getProductByFilter(String name,
                                            String category,
                                            String vendor,
                                            String status,double price
                                            )
    {
        return repo.findByNameOrCategoryOrVendorOrStatusOrPrice(name,category,vendor,status,price);
    }
   public  Page<Product> getProductPagingAndSorting(int pageNumber, int noOfRecords, String sortBy, String columnName)
    {
        Pageable pageable = null;
        Sort sort = sortBy.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(columnName).ascending() : Sort.by(columnName).descending();
        System.out.println("sort"+sort);
        pageable = PageRequest.of(pageNumber, noOfRecords, sort);
        System.out.println("pageable"+pageable);
        return repo.findAll(pageable);
    }
    public List<Product> searchProducts(List<String> name, List<String> status)
    {
        return repo.findByNameAndStatus(name,status);
    }
    public String updateProduct(int id,Product product)  {
try {
    Product existingProduct_byID = repo.findById(id).get();
   // System.out.println("existingProduct_byID"+existingProduct_byID);
    existingProduct_byID.setName(product.getName());
    existingProduct_byID.setPrice(product.getPrice());
    existingProduct_byID.setQuantity(product.getQuantity());
    existingProduct_byID.setStatus(product.getStatus());
    existingProduct_byID.setCategory(product.getCategory());
    existingProduct_byID.setDescription(product.getDescription());
    existingProduct_byID.setVendor(product.getVendor());
    existingProduct_byID.setUpdated_at(product.getUpdated_at());
    repo.save(existingProduct_byID);
        }
        catch (NoSuchElementException e)
        {
            throw new ProductNotExistsException("No product exists with this id= " + id);
        }
        return "Product updated successfully";
    }
    public String deleteProduct(int id)
    {
     try {
            Product existingProduct = repo.findById(id).get();
            repo.delete(existingProduct);
        }
        catch (NoSuchElementException e)
        {
            throw new ProductNotExistsException("No product exists with this id= " + id);
        }
        return "Product deleted successfully";
    }
    }




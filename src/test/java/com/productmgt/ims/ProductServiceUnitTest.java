package com.productmgt.ims;

import com.productmgt.ims.Exception.ProductNotExistsException;
import com.productmgt.ims.Model.Product;
import com.productmgt.ims.Repository.ProductRepo;
import com.productmgt.ims.Service.ProductService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {
    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    LocalDateTime current = LocalDateTime.now();
    @Test
    public void testCreateProduct()
    {
        Product p=BuildAddProduct();
        when(productRepo.save(ArgumentMatchers.any(Product.class))).thenReturn(p);
        Product created = productService.createProduct(p);
        assertThat(created.getName()).isSameAs(p.getName());
        verify(productRepo).save(p);
    }
    // write unit test cases for create product exception
    @Test
    public void testCreateProductException()
    {
        Product p=BuildAddProduct();
        when(productRepo.findByName(p.getName())).thenReturn(p);
        try {
            productService.createProduct(p);
        }
        catch (Exception e)
        {
            assertEquals("Product Name already exists!!",e.getMessage());
        }
    }
    //write unit test cases for get product
    @Test
    public void testGetProduct()
    {
        Product p=BuildAddProduct();
        when(productRepo.findById(10)).thenReturn(java.util.Optional.of(p));
        assertEquals(p,productService.getProduct(10));
    }
    // write unit test cases for get product exception
    @Test
    public void testGetProductException()
    {
        when(productRepo.findById(10)).thenThrow(new ProductNotExistsException("No product exists with this id = 10"));
        try {
            productService.getProduct(10);
        }
        catch (Exception e)
        {
            assertEquals("No product exists with this id = 10",e.getMessage());
        }
    }
    // write unit test cases for update product
    @Test
    public void testUpdateProduct() throws Exception {
        Product p=BuildAddProduct();
        p.setVendor("Test1");
        when(productRepo.findById(10)).thenReturn(java.util.Optional.of(p));
        assertEquals("Product updated successfully",productService.updateProduct(10,p));
    }
// write unit test cases for update product exception
    @Test
    public void testUpdateProductException()
    {
        Product p=BuildAddProduct();
        when(productRepo.findById(10)).thenThrow(new ProductNotExistsException("No product exists with this id = 10"));
        try {
            productService.updateProduct(10,p);
        }
        catch (Exception e)
        {
            assertEquals("No product exists with this id = 10",e.getMessage());
        }
    }
    // write unit test cases for delete product
    @Test
    public void testDeleteProduct()
    {
        Product p=BuildAddProduct();
        when(productRepo.findById(10)).thenReturn(java.util.Optional.of(p));
        assertEquals("Product deleted successfully",productService.deleteProduct(10));
    }
// write unit test cases for delete product exception
    @Test
public void testDeleteProductException()
    {
        when(productRepo.findById(10)).thenThrow(new ProductNotExistsException("No product exists with this id = 10"));
        try {
            productService.deleteProduct(10);
        }
        catch (Exception e)
        {
            assertEquals("No product exists with this id = 10",e.getMessage());
        }
    }
    // write unit test cases for search product
    @Test
  public void testSearchProduct()
    {
        List<String> l1= Arrays.asList("Test");
        List<String> l2= Arrays.asList("Active");
        Product p=BuildAddProduct();
        when(productRepo.findByNameAndStatus(l1,l2)).thenReturn(List.of((p)));
        assertEquals(List.of(p),productService.searchProducts(l1,l2));
    }
// write unit test cases for search product exception
    @Test
    public void testSearchProductException()
    {
        List<String> l1= Arrays.asList("Test");
        List<String> l2= Arrays.asList("Active");
        when(productRepo.findByNameAndStatus(l1,l2)).thenReturn(List.of());
        try {
            productService.searchProducts(l1,l2);
        }
        catch (Exception e)
        {
            assertEquals("No products found!!",e.getMessage());
        }
    }
   //write the unit test cases for getProductByFilter
    @Test
    public void testGetProductByFilter()
    {
        Product p=BuildAddProduct();
        when(productRepo.findByNameOrCategoryOrVendorOrStatusOrPrice(p.getName(),p.getCategory(),p.getVendor(),p.getStatus(),p.getPrice())).thenReturn(List.of((p)));
        assertEquals(List.of(p),productService.getProductByFilter(p.getName(),p.getCategory(),p.getVendor(),p.getStatus(),p.getPrice()));
    }
//write the unit test cases for getProductByFilterException
    @Test
    public void testGetProductByFilterException()
    {
        Product p=BuildAddProduct();
        when(productRepo.findByNameOrCategoryOrVendorOrStatusOrPrice(p.getName(),p.getCategory(),p.getVendor(),p.getStatus(),p.getPrice())).thenReturn(List.of());
        try {
            productService.getProductByFilter(p.getName(),p.getCategory(),p.getVendor(),p.getStatus(),p.getPrice());
        }
        catch (Exception e)
        {
            assertEquals("No products found!!",e.getMessage());
        }
    }
// write the unit test cases for GetProductPagingAndSortingException
    @Test
    public void testGetProductPagingAndSorting() {
        Pageable paging = PageRequest.of(0, 1, Sort.by("name"));
        Page<Product> page = productService.getProductPagingAndSorting(0, 1, "asc", "name");
        assertEquals(page, productRepo.findAll(paging));
    }
// write the unit test cases for GetProductPagingAndSortingException
    @Test
    public void testGetProductPagingAndSortingException() {
        Pageable paging = PageRequest.of(0, 1, Sort.by("name"));
        when(productRepo.findAll(paging)).thenReturn(Page.empty());
        try {
            productService.getProductPagingAndSorting(0, 1, "asc", "name");
        } catch (Exception e) {
            assertEquals("No products found!!", e.getMessage());
        }
    }
    private Product BuildAddProduct()
    {
        Product p=new Product();
        p.setId(10);
        p.setName("Test");
        p.setStatus("Active");
        p.setPrice(100);
        p.setQuantity(10);
        p.setCategory("Shoes");
        p.setDescription("Test");
        p.setVendor("Test");
        p.setUpdated_at(current);
        p.setCreated_at(current);
        return p;
    }

}

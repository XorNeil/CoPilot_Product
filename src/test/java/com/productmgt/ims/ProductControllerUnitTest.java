package com.productmgt.ims;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productmgt.ims.Controller.ProductController;
import com.productmgt.ims.Exception.ProductNameAlreadyExistsException;
import com.productmgt.ims.Model.Product;
import com.productmgt.ims.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;
    LocalDateTime current = LocalDateTime.now();
    // write unit test cases for create product at controller level
    @Test
    public void testCreateProduct()
    {
        Product p=BuildAddProduct();
        when(service.createProduct(p)).thenReturn(p);
        try {
            mockMvc.perform(post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(p)))
                    .andExpect(status().isCreated());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    // write unit test cases for get product at controller level
    @Test
public void testGetProduct()
    {
        Product p=BuildAddProduct();
        when(service.getProduct(p.getId())).thenReturn(p);
        try {
            int id=10;
            mockMvc.perform(get("/products/{id}",id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(p.getName()))
                    .andExpect(jsonPath("$.description").value(p.getDescription()))
                    .andExpect(jsonPath("$.price").value(p.getPrice()))
                    .andExpect(jsonPath("$.quantity").value(p.getQuantity()))
                    .andExpect(jsonPath("$.category").value(p.getCategory()))
                    .andExpect(jsonPath("$.vendor").value(p.getVendor()))
                    .andExpect(jsonPath("$.status").value(p.getStatus()))
                    .andDo(print());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
// write unit test cases for get product exception at controller level
    @Test
    public void testGetProductException()
    {
        Product p=BuildAddProduct();
        when(service.getProduct(p.getId())).thenThrow(new RuntimeException("Product not found!!"));
        try {
            int id=10;
            mockMvc.perform(get("/products/{id}",id))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
// write unit test cases for update product at controller level
    @Test
    public void testUpdateProduct() throws Exception {
        Product p=BuildAddProduct();
        p.setVendor("Test_updated");
        when(service.updateProduct(p.getId(),p)).thenReturn("Test");
        try {
            int id=10;
            mockMvc.perform(put("/products/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(p)))
                    .andExpect(status().isOk())
                    .andDo(print());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

//write unit test cases for delete product at controller level
    @Test
    public void testDeleteProduct()
    {
        Product p=BuildAddProduct();
        when(service.deleteProduct(p.getId())).thenReturn("Test");
        try {
            int id=10;
            mockMvc.perform(delete("/products/{id}",id))
                    .andExpect(status().isOk())
                    .andDo(print());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
// write unit test cases for delete product exception at controller level
    @Test
    public void testDeleteProductException()
    {
        Product p=BuildAddProduct();
        when(service.deleteProduct(p.getId())).thenThrow(new RuntimeException("Product not found!!"));
        try {
            int id=10;
            mockMvc.perform(delete("/products/{id}",id))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
 // write unit test cases for getProductByFilter at controller level
    @Test
    public void testGetProductByFilter()
    {
        Product p=BuildAddProduct();
        when(service.getProductByFilter(p.getName(),p.getCategory(),p.getVendor(),p.getStatus(),p.getPrice())).thenReturn(Arrays.asList(p));
        try {
            mockMvc.perform(get("/products/getFilter")
                    .param("name",p.getName())
                    .param("category",p.getCategory())
                    .param("vendor",p.getVendor())
                    .param("status",p.getStatus())
                    .param("price",String.valueOf(p.getPrice())))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(p.getId()))
                    .andExpect(jsonPath("$[0].name").value(p.getName()))
                    .andExpect(jsonPath("$[0].description").value(p.getDescription()))
                    .andExpect(jsonPath("$[0].price").value(p.getPrice()))
                    .andExpect(jsonPath("$[0].quantity").value(p.getQuantity()))
                    .andExpect(jsonPath("$[0].category").value(p.getCategory()))
                    .andExpect(jsonPath("$[0].vendor").value(p.getVendor()))
                    .andExpect(jsonPath("$[0].status").value(p.getStatus()))
                    .andDo(print());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
// write unit test cases for getProductByFilter exception at controller level
    @Test
    public void testGetProductByFilterException()
    {
        Product p=BuildAddProduct();
        when(service.getProductByFilter(p.getName(),p.getCategory(),p.getVendor(),p.getStatus(),p.getPrice())).thenThrow(new RuntimeException("Product not found!!"));
        try {
            mockMvc.perform(get("/products/getFilter")
                    .param("name",p.getName())
                    .param("category",p.getCategory())
                    .param("vendor",p.getVendor())
                    .param("status",p.getStatus())
                    .param("price",String.valueOf(p.getPrice())))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
//write unit test cases for getProductPagingAndSorting at controller level
    @Test
    public void testGetProductPagingAndSorting()
    {
        Product p=BuildAddProduct();
        Page<Product> page=new PageImpl<>(Arrays.asList(p));
        when(service.getProductPagingAndSorting(0, 1, "asc", "name")).thenReturn(page);
        try {
            mockMvc.perform(get("/products")
                    .param("pageNumber","0")
                    .param("noOfRecords","1")
                    .param("sortBy","asc")
                    .param("columnName","name"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(p.getId()))
                    .andExpect(jsonPath("$[0].name").value(p.getName()))
                    .andExpect(jsonPath("$[0].description").value(p.getDescription()))
                    .andExpect(jsonPath("$[0].price").value(p.getPrice()))
                    .andExpect(jsonPath("$[0].quantity").value(p.getQuantity()))
                    .andExpect(jsonPath("$[0].category").value(p.getCategory()))
                    .andExpect(jsonPath("$[0].vendor").value(p.getVendor()))
                    .andExpect(jsonPath("$[0].status").value(p.getStatus()))
                    .andDo(print());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
// write unit test cases for getProductPagingAndSorting exception at controller level
    @Test
    public void testGetProductPagingAndSortingException()
    {
        Product p=BuildAddProduct();
        when(service.getProductPagingAndSorting(0, 1, "asc", "name")).thenThrow(new RuntimeException("Product not found!!"));
        try {
            mockMvc.perform(get("/products")
                    .param("pageNumber","0")
                    .param("noOfRecords","1")
                    .param("sortBy","asc")
                    .param("columnName","name"))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

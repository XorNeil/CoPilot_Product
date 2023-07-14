package com.productmgt.ims.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "product",uniqueConstraints = @UniqueConstraint(columnNames = "name")) // this annotation creates a table named product in the database
public class Product {
    public Product() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotNull(message = "Product Name cannot be null")
    private String  name;
    @Column
    @NotNull(message = "Product Description cannot be null")
    private String  description;
    @Column
    @NotNull(message = "Product Quantity cannot be null")
    private int quantity;
    @Column
    @NotNull(message = "Product Price cannot be null")
    private double  price;
    @Column
    @NotNull(message = "Product Vendor cannot be null")
    private String vendor;
    @Column
    @NotNull(message = "Product Category cannot be null")
    private String category;
    @Column
    @NotNull(message = "Product Status cannot be null")
    private String status;
    @Column
    private LocalDateTime created_at;
    @Column
    private LocalDateTime updated_at;

    public Product(int id, String name, String description, int quantity, double price, String vendor, String category, String status, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.vendor = vendor;
        this.category = category;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", vendor='" + vendor + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
// declare getters and setters for all the fields
// right click on the class name and select Source -> Generate Getters and Setters
}

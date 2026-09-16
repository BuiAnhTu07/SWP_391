/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class ProductVariant {
    private int id;
    private Product product;
    private Size size;
    private int stock;
    private int productId;
    private int sizeId;

    public ProductVariant(int id, Product product, Size size, int stock) {
        this.id = id;
        this.product = product;
        this.size = size;
        this.stock = stock;
    }

    public ProductVariant(int id, int stock, int productId, int sizeId) {
        this.id = id;
        this.stock = stock;
        this.productId = productId;
        this.sizeId = sizeId;
    }


    public ProductVariant() {
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }
}

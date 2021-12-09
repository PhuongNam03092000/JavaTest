/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javatest;

/**
 *
 * @author DELL
 */
public class Product {
    private int Id;
    private String tenSanPham;
    private long giaSanPham;

    public Product(int Id, String tenSanPham, long giaSanPham) {
        this.Id = Id;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
    }

   

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public long getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(long giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
    
    
}

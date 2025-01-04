package com.example.swiftmart.Model;

import java.util.List;

public class ProductModel {

    String pid, name, description, price, company, category;
    private List<String> imgurls;
    private boolean isWishlisted;

    public ProductModel(){

    }

    public ProductModel(
            String pid,
            String name,
            String description,
            String price,
            String company,
            String category,
            List<String> imgurls,
            boolean isWishlisted
    ){
        this.pid = pid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.company = company;
        this.category = category;
        this.imgurls = imgurls;
        this.isWishlisted = isWishlisted;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getImgurls() {
        return imgurls;
    }

    public void setImgurls(List<String> imgurls) {
        this.imgurls = imgurls;
    }

    public boolean isWishlisted() {
        return isWishlisted;
    }

    public void setWishlisted(boolean wishlisted) {
        isWishlisted = wishlisted;
    }
}

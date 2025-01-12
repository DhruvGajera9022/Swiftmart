// FilterData.java
package com.example.swiftmart.Utils;

import java.util.List;

public class FilterData {
    private float minPrice;
    private float maxPrice;
    private List<String> categories;
    private int sortOption;

    public FilterData(float minPrice, float maxPrice, List<String> categories, int sortOption) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.categories = categories;
        this.sortOption = sortOption;
    }

    // Getters
    public float getMinPrice() {
        return minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getSortOption() {
        return sortOption;
    }

    // Setters
    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setSortOption(int sortOption) {
        this.sortOption = sortOption;
    }

    @Override
    public String toString() {
        return "FilterData{" +
                "minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", categories=" + categories +
                ", sortOption=" + sortOption +
                '}';
    }
}
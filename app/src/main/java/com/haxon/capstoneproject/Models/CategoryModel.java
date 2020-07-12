package com.haxon.capstoneproject.Models;

public class CategoryModel {

    private String categoryName;
    private int CategoryIconLink;

    public CategoryModel(String categoryName, int categoryIconLink) {
        this.categoryName = categoryName;
        CategoryIconLink = categoryIconLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryIconLink() {
        return CategoryIconLink;
    }

    public void setCategoryIconLink(int categoryIconLink) {
        CategoryIconLink = categoryIconLink;
    }
}

package model;

import java.util.ArrayList;
import java.util.List;


public class Category {
    private final int id;
    private String name;
    private List<Category> subcategories;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.subcategories = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Category> subcategories) {
        this.subcategories = subcategories;
    }

    public void addSubcategory(Category subcategory) {
        this.subcategories.add(subcategory);
    }

    public void addSubcategories(List<Category> subcategories) {
        if (subcategories != null) {
            this.subcategories.addAll(subcategories);
        }
    }

    public void removeSubcategory(Category subcategory) {
        this.subcategories.remove(subcategory);
    }


}

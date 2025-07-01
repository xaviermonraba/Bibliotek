package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@jakarta.persistence.Entity
public class Category {
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> subcategories = new ArrayList<>();

    public Category() {
        this.subcategories = new ArrayList<Category>();
    }
    public Category(String name) {
        this.name = name;
        this.subcategories = new ArrayList<>();
    }

    public long getId() {
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

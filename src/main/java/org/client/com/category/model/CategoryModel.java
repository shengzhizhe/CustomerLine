package org.client.com.category.model;

public class CategoryModel {
    private String id;
    private String cnames;

    public CategoryModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnames() {
        return cnames;
    }

    public void setCnames(String cnames) {
        this.cnames = cnames;
    }

    public CategoryModel(String id, String cnames) {
        this.id = id;
        this.cnames = cnames;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "id='" + id + '\'' +
                ", cnames='" + cnames + '\'' +
                '}';
    }
}

package org.client.com.category.model;

public class CategoryModel {
    private String id;
    private String cname;

    public CategoryModel() {
    }

    public CategoryModel(String id, String cname) {
        this.id = id;
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "id='" + id + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String name) {
        this.cname = cname;
    }
}

package com.bg.BoardGame.model;


import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;
    private String categoryName;
    private String decription;
    private String imageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


//  "id": 1,

//        {
//        "categoryName": "Worker Placement",
//        "decription": "Build the most efficient economy by placing your workers.",
//        "imageUrl": "https://1.bp.blogspot.com/-s8eeH6_dN0g/YORyrU8FCRI/AAAAAAAASEY/4bR0QCH--xMI8qhqpm0WQ4omM1v8VRNewCPcBGAYYCw/s1920/quetzal_miasto_swietych_ptakow_recenzja_3.jpg"
//        }
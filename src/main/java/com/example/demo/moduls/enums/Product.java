package com.example.demo.moduls.enums;

import com.example.demo.moduls.Image;
import com.example.demo.moduls.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="description", columnDefinition = "text")
    private String description;
    @Column(name="price")
    private int price;
    @Column(name="city")
    private String city;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    mappedBy = "product")
    private List<Image> images = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    private Long previewImageId;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToProduct(Image image){
        image.setProduct(this);
        images.add(image);
    }

}

package com.localbrand.dal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "collection")
@Data
public class Collection {
    @Id
    @Column(updatable = false, nullable = false)
    private String id;
    private String name;
    @Column (name="image_link")
    private String imageLink;
}
package org.example.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="tbl_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 255)
    private String image;
}
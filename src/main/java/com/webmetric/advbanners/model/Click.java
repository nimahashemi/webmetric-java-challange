package com.webmetric.advbanners.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table( name = "clicks")
public class Click {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "impression_id", nullable = false)
    private String impressionId;
    @Column(name = "revenue")
    private double revenue;

}

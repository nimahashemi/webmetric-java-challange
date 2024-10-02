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
@Table(name = "impressions")
public class Impression {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "impression_id", nullable = true)
    private String impressionId;
    @Column(name = "app_id", nullable = false)
    private int appId;
    @Column(name = "country_code", nullable = true, length = 2)
    private String countryCode;
    @Column(name = "advertiser_id", nullable = false)
    private int advertiserId;

}

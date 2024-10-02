package com.webmetric.advbanners.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DimensionDTO {
    private int appId;
    private String countryCode;
    private String clicks;
    private double revenue;
}

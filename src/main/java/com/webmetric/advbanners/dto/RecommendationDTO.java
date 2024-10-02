package com.webmetric.advbanners.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationDTO {
    private int appId;
    private String countryCode;
    private List<Integer> recommendedAdvertiserIds;
}

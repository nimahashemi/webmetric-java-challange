package com.webmetric.advbanners.service;

import com.webmetric.advbanners.dto.DimensionDTO;
import com.webmetric.advbanners.model.Impression;

import java.util.List;
import java.util.UUID;

public interface ImpressionService {
    Impression saveIpression(Impression impression);
    List<Impression> saveBulkImpression(List<Impression> impressionList);
    List<Impression> getAllImpression();
    Impression getImpressionById(String id);
    List<Object[]> getDimensionReport();
    List<Object[]> getRecommendationReport();
}

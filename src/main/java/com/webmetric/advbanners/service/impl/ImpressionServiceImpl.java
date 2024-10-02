package com.webmetric.advbanners.service.impl;

import com.webmetric.advbanners.dto.DimensionDTO;
import com.webmetric.advbanners.model.Impression;
import com.webmetric.advbanners.repository.ImpressionRepository;
import com.webmetric.advbanners.service.ImpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImpressionServiceImpl implements ImpressionService {


    private final ImpressionRepository impressionRepository;

    @Autowired
    public ImpressionServiceImpl(ImpressionRepository impressionRepository) {
        this.impressionRepository = impressionRepository;
    }

    @Override
    public Impression saveIpression(Impression impression) {
        return impressionRepository.save(impression);
    }

    @Override
    public List<Impression> saveBulkImpression(List<Impression> impressionList) {
        return impressionRepository.saveAll(impressionList);
    }

    @Override
    public List<Impression> getAllImpression() {
        return impressionRepository.findAll();
    }

    @Override
    public Impression getImpressionById(String id) {
        Optional<Impression> impression = impressionRepository.findById(id);
        if(impression.isPresent()) return impression.get();
        else throw new RuntimeException();
    }

    @Override
    public List<Object[]> getDimensionReport() {
        return impressionRepository.getDimensionReport();
    }

    @Override
    public List<Object[]> getRecommendationReport() {
        return impressionRepository.getRecommendationReport();
    }
}

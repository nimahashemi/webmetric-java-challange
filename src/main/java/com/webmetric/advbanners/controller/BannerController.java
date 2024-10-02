package com.webmetric.advbanners.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.webmetric.advbanners.dto.ClickDTO;
import com.webmetric.advbanners.dto.DimensionDTO;
import com.webmetric.advbanners.dto.RecommendationDTO;
import com.webmetric.advbanners.model.Click;
import com.webmetric.advbanners.model.Impression;
import com.webmetric.advbanners.service.ClickService;
import com.webmetric.advbanners.service.ImpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {

    private final ImpressionService impressionService;
    private final ClickService clickService;

    @Value("classpath:data/impressions.json")
    Resource impressionFile;

    @Value("classpath:data/clicks.json")
    Resource clickFile;

    @Autowired
    public BannerController(ImpressionService impressionService,
                            ClickService clickService) {
        this.impressionService = impressionService;
        this.clickService = clickService;
    }

    @PostMapping("/save")
    public ResponseEntity<List<String>> saveData() {
        List<Impression> impressions = new ArrayList<>();
        List<Click> clicks = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        try {
            byte[] impressionData = Files.readAllBytes(Paths.get(impressionFile.getURI()));
            byte[] clickData = Files.readAllBytes(Paths.get(clickFile.getURI()));

            mapper.readValue(impressionData, new TypeReference<List<Impression>>() {
            }).stream().forEach(obj -> {
                System.out.println(obj.toString());
                Impression impression = new Impression();
                impression.setImpressionId(obj.getId());
                impression.setAdvertiserId(obj.getAdvertiserId());
                impression.setCountryCode(obj.getCountryCode());
                impression.setAppId(obj.getAppId());
                impressions.add(impression);
            });
            impressionService.saveBulkImpression(impressions);

            mapper.readValue(clickData, new TypeReference<List<ClickDTO>>() {
            }).stream().forEach(obj -> {
                Click click = new Click();
                click.setImpressionId(obj.getImpressionId());
                click.setRevenue(obj.getRevenue());
                clicks.add(click);
            });

            clickService.saveBulkClick(clicks);

        } catch (IOException e) {
            throw new RuntimeException();
        }

        return new ResponseEntity<List<String>>(impressions.stream().map(Impression::getId).toList(), HttpStatus.CREATED);
    }

    @GetMapping("/dimensions")
    public List<DimensionDTO> dimensionReport() {
        List<DimensionDTO> output = new ArrayList<>();
        List<Object[]> list = impressionService.getDimensionReport();
        for (Object[] result: list) {
            DimensionDTO dto = new DimensionDTO();
            dto.setAppId((Integer) result[0]);
            dto.setCountryCode((String) result[1]);
            long clicks = (long) result[2];
            dto.setClicks(String.valueOf(clicks));
            long revenue = (long) result[2];
            dto.setRevenue((double) revenue);
            output.add(dto);
        }
        return output;
    }

    @GetMapping("/recommendation")
    public List<RecommendationDTO> recommendationReport() {
        List<RecommendationDTO> output = new ArrayList<>();
        List<Object[]> list = impressionService.getRecommendationReport();
        for (Object[] result: list) {
            RecommendationDTO dto = new RecommendationDTO();
            if ((result[2] != null && !Objects.equals((String) result[2], "")) && (result[3] != null && !Objects.equals((String) result[3], ""))) {
                dto.setAppId((Integer) result[1]);
                dto.setCountryCode((String) result[2]);

                int count = 0;
                List<Integer> advertiser = new ArrayList<>();
                List<Object[]> filterList = list.stream().filter(o -> Objects.equals((Integer) o[1], (Integer) result[1]) && Objects.equals((String) o[2], (String) result[2])).toList();
                for (Object[] obj : filterList) {
                    if (count < 5) {
                        advertiser.add((Integer) obj[0]);
                    }
                    count++;
                }
                dto.setRecommendedAdvertiserIds(advertiser);
                output.add(dto);
            }
        }
        return output;
    }
}

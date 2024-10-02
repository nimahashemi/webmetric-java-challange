package com.webmetric.advbanners.repository;

import com.webmetric.advbanners.dto.DimensionDTO;
import com.webmetric.advbanners.model.Impression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImpressionRepository extends JpaRepository<Impression, String> {

    @Query(value = "SELECT\n" +
            "    t1.app_id,\n" +
            "    t1.country_code,\n" +
            "    COUNT(t1.impression_id) AS impressions,\n" +
            "    CONVERT((SELECT count(*) FROM clicks as t3 WHERE t3.impression_id = t1.impression_id), CHAR) as clicks,\n" +
            "    (SELECT SUM(t2.revenue) FROM clicks as t2 WHERE t2.impression_id = t1.impression_id) as revenue\n" +
            "FROM\n" +
            "    impressions AS t1 LEFT JOIN\n" +
            "    clicks AS t4  ON t1.impression_id = t4.impression_id\n" +
            "GROUP BY t1.impression_id, t1.app_id, t1.country_code;", nativeQuery=true)
    List<Object[]> getDimensionReport();

    @Query(value = "SELECT\n" +
            "    t1.advertiser_id,\n" +
            "    t1.app_id,\n" +
            "    t1.country_code,\n" +
            "    CONVERT(((SELECT SUM(t2.revenue) FROM clicks as t2 WHERE t2.impression_id = t1.impression_id) / COUNT(t1.impression_id)), CHAR) as rate\n" +
            "FROM\n" +
            "    impressions AS t1 LEFT JOIN\n" +
            "    clicks AS t4  ON t1.impression_id = t4.impression_id\n" +
            "GROUP BY t1.impression_id, t1.country_code, t1.app_id, t1.advertiser_id\n" +
            "ORDER BY t1.country_code, rate desc", nativeQuery = true)
    List<Object[]> getRecommendationReport();
}

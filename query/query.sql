#Report1
SELECT
    t1.app_id,
    t1.country_code,
    COUNT(t1.impression_id) AS impressions,
    CONVERT((SELECT count(*) FROM clicks as t3 WHERE t3.impression_id = t1.impression_id), CHAR) as clicks,
    (SELECT SUM(t2.revenue) FROM clicks as t2 WHERE t2.impression_id = t1.impression_id) as revenue
FROM
    impressions AS t1 LEFT JOIN
    clicks AS t4  ON t1.impression_id = t4.impression_id
GROUP BY t1.impression_id, t1.app_id, t1.country_code;

#Report2
SELECT
    t1.advertiser_id,
    t1.app_id,
    t1.country_code,
    ((SELECT SUM(t2.revenue) FROM clicks as t2 WHERE t2.impression_id = t1.impression_id) / COUNT(t1.impression_id)) as recomended
FROM
    impressions AS t1 LEFT JOIN
    clicks AS t4  ON t1.impression_id = t4.impression_id
GROUP BY t1.impression_id, t1.country_code, t1.app_id, t1.advertiser_id;
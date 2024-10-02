package com.webmetric.advbanners.repository;

import com.webmetric.advbanners.model.Click;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClickRepository extends JpaRepository<Click, String> {
}

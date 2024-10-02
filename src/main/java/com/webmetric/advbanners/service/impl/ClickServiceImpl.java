package com.webmetric.advbanners.service.impl;

import com.webmetric.advbanners.model.Click;
import com.webmetric.advbanners.repository.ClickRepository;
import com.webmetric.advbanners.service.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClickServiceImpl implements ClickService {

    private final ClickRepository clickRepository;

    @Autowired
    public ClickServiceImpl(ClickRepository clickRepository) {
        this.clickRepository = clickRepository;
    }

    @Override
    public Click saveClick(Click click) {
        return clickRepository.save(click);
    }

    @Override
    public List<Click> saveBulkClick(List<Click> clickList) {
        return clickRepository.saveAll(clickList);
    }

    @Override
    public List<Click> getAllClick() {
        return clickRepository.findAll();
    }

    @Override
    public Click getClickById(String id) {
        Optional<Click> click = clickRepository.findById(id);
        if (click.isPresent()) return click.get();
        else throw new RuntimeException();
    }
}

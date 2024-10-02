package com.webmetric.advbanners.service;

import com.webmetric.advbanners.model.Click;

import java.util.List;
import java.util.UUID;

public interface ClickService {
    Click saveClick(Click click);
    List<Click> saveBulkClick(List<Click> clickList);
    List<Click> getAllClick();
    Click getClickById(String id);
}

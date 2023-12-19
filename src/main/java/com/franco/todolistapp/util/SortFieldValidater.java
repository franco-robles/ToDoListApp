package com.franco.todolistapp.util;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SortFieldValidater {
    private final List<String> allowedSortFields = Arrays.asList("id", "title",
                                                                "description", "dateOfCreation",
                                                                "estimateEndTime", "finished", "taskStatus");

    public boolean isValidSort(Sort sort) {
        if (sort == null) {
            return true; // Accept default sorting
        }
        for (Sort.Order order : sort.toList()){
            if(!allowedSortFields.contains(order.getProperty())){
                return false;
            }
        }
        return true;
    }
}

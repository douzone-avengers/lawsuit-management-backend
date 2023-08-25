package com.avg.lawsuitmanagement.lawsuit.type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLawsuitSortOrder implements Converter<String, LawsuitSortOrder> {
    @Override
    public LawsuitSortOrder convert(String input) {
        return LawsuitSortOrder.valueOf(input.toUpperCase());
    }
}

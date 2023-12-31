package com.avg.lawsuitmanagement.common.type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/***
 * form에서 enum 값을 받아 올 때, 클라이언트에서 소문자 값이 와도 적절한 enum으로 컨버팅 하기 위한 컨버터
 */
@Component
public class StringToSortOrder implements Converter<String, SortOrder> {
    @Override
    public SortOrder convert(String input) {
        return SortOrder.valueOf(input.toUpperCase());
    }
}

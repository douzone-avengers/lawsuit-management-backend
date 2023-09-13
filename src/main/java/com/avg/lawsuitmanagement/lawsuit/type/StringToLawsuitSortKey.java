package com.avg.lawsuitmanagement.lawsuit.type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLawsuitSortKey implements Converter<String, LawsuitSortKey> {
    @Override
    public LawsuitSortKey convert(String input) {
        String upperInput = input.toUpperCase();
        return switch (upperInput) {
            case "CREATEDAT" -> LawsuitSortKey.CREATED_AT;
            case "LAWSUITNUM" -> LawsuitSortKey.LAWSUIT_NUM;
            case "LAWSUITSTATUS" -> LawsuitSortKey.LAWSUIT_STATUS;
            case "COMMISSIONFEE" -> LawsuitSortKey.COMMISSION_FEE;
            case "CONTINGENTFEE" -> LawsuitSortKey.CONTINGENT_FEE;
            default -> LawsuitSortKey.valueOf(upperInput);
        };
    }
}

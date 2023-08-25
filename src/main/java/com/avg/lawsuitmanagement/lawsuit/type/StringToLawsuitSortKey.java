package com.avg.lawsuitmanagement.lawsuit.type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLawsuitSortKey implements Converter<String, LawsuitSortKey> {
    @Override
    public LawsuitSortKey convert(String input) {
        String upperInput = input.toUpperCase();
        if(upperInput.equals("CREATEDAT")) {
            return LawsuitSortKey.CREATED_AT;
        }
        else if(upperInput.equals("LAWSUITNUM")) {
            return LawsuitSortKey.LAWSUIT_NUM;
        }
        else if(upperInput.equals("LAWSUITSTATUS")) {
            return LawsuitSortKey.LAWSUIT_STATUS;
        }
        else if (upperInput.equals("COMMISSIONFEE")) {
            return LawsuitSortKey.COMMISSION_FEE;
        }
        else if (upperInput.equals("CONTINGENTFEE")) {
            return LawsuitSortKey.CONTINGENT_FEE;
        }
        return LawsuitSortKey.valueOf(upperInput);
    }
}

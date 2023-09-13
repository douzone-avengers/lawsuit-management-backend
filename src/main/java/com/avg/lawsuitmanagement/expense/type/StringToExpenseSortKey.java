package com.avg.lawsuitmanagement.expense.type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToExpenseSortKey implements Converter<String, ExpenseSortKey> {
    @Override
    public ExpenseSortKey convert(String input) {
        String upperInput = input.toUpperCase();

        return switch (upperInput) {
            case "SPENINGAT" -> ExpenseSortKey.SPENING_AT;
            case "CONTENTS" -> ExpenseSortKey.CONTENTS;
            case "AMOUNT" -> ExpenseSortKey.AMOUNT;
            default -> ExpenseSortKey.valueOf(upperInput);
        };

    }
}

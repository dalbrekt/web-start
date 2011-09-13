package com.jworx.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

import au.com.bytecode.opencsv.CSVParser;

public class StockQuotesConverter {

    private final CSVParser csvParser = new CSVParser(',', '"');

    private final String csv;

    public StockQuotesConverter(String csv) {
        this.csv = csv;
    }

    public String toJson() {

        String[] lines = csv.split("\\n");
        Object[] array = new Object[lines.length];

        for (int i = 0; i < lines.length; i++) {
            array[i] = toMap(lines[i]);
        }
        return JSON.toString(toArray());
    }

    public Object[] toArray() {

        String[] lines = csv.split("\\n");
        Object[] array = new Object[lines.length];

        for (int i = 0; i < lines.length; i++) {
            array[i] = toMap(lines[i]);
        }
        return array;
    }

    private Map<String, String> toMap(String line) {
        String[] fields = toFieldArray(line);
        Map<String, String> map = new HashMap<String, String>();
        map.put("symbol", fields[0]);
        map.put("name", replaceIllegalChars(fields[1]));
        map.put("lastTrade", replaceIllegalChars(fields[2]));
        map.put("lastTradeDate", replaceIllegalChars(fields[3]));
        map.put("lastTradeTime", replaceIllegalChars(fields[4]));
        map.put("highValue", replaceIllegalChars(fields[5]));
        map.put("lowValue", replaceIllegalChars(fields[6]));
        map.put("pricePerEarning", replaceIllegalChars(fields[7]));
        return map;
    }

    private String[] toFieldArray(String line) {
        try {
            return csvParser.parseLine(line);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse csv: " + line, e);
        }
    }

    String replaceIllegalChars(String str) {

        if (str != null) {
            return str.replaceAll("\"", "").replaceAll(",", "").trim();
        }

        return null;
    }
}

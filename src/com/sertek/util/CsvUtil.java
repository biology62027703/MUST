package com.sertek.util;

import com.csvreader.CsvReader;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.sertek.util.*;
/**
 *
 */
public class CsvUtil {
    //讀取csv文件
    public List<String[]> readCsv(String filePath) throws Exception {
        List<String[]> csvList = new ArrayList<String[]>();
        if (isCsv(filePath)) {
            CsvReader reader = new CsvReader(filePath, ',', Charset.forName("utf-8"));
            //reader.readHeaders(); // 跳過表頭   如果需要表頭的話，不要寫這句。
            while (reader.readRecord()) { //逐行讀入除表頭的數據
                csvList.add(reader.getValues());
            }
            reader.close();
        } else {
            System.out.println("此文件不是CSV文件！");
        }
        System.out.println("資料數:"+csvList.size());
        return csvList;
    }
    //判斷是否是csv文件
    private boolean isCsv(String fileName) {
        return fileName.matches("^.+\\.(?i)(csv)$");
    }


}
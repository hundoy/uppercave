package com.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wgy on 16-5-13.
 */
public class ExcelArrange {

    public static final String[] PRIORITY = {"stg_x3router_acceinfo_error","stg_user_action","stg_cj_org_part","stg_fin_provider","stg_user_org_app_rel","stg_fin_product_info","stg_cj_app_user_orgrel","stg_account_bussdata","stg_cj_app_user","stg_error_line","stg_cj_app_user_login","stg_cj_app_manager","stg_official_acceinfo","stg_subscribe","stg_cj_app_organization","stg_user_subscribe","stg_cj_org_part_apprel","stg_fin_loan_request","stg_x3router_acceinfo","stg_app_user","stg_app_organization","stg_cj_user_external","stg_cj_org_manager"};

    public static void main(String[] args){
        try{
            Map<String, List<XSSFRow>> tableMap = new HashMap<String, List<XSSFRow>>();
            File file = new File("/home/wgy/text/labro/zhiban.xlsx");
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
            XSSFWorkbook workBook = new XSSFWorkbook();

            for (int j=0;j<6*2;j+=2){
                XSSFSheet satSheet = xwb.getSheetAt(j);
                System.out.println("name: "+satSheet.getSheetName());

                String lastRowName = "";
                for (int i=2; i<25; i++){
                    XSSFRow row = satSheet.getRow(i);
                    System.out.println("cell: "+row.getCell(0).toString());
                    String curRowName = row.getCell(0).toString();
                    List<XSSFRow> rowList = new ArrayList<XSSFRow>();
                    if (curRowName==null || curRowName.trim().length()==0){
                        curRowName = lastRowName;
                        rowList = tableMap.get(curRowName);
                    }
                    rowList.add(row);
                    tableMap.put(curRowName, rowList);
                    lastRowName = curRowName;
                }


                XSSFSheet sheet = workBook.createSheet(satSheet.getSheetName());
                for (int k=0;k<3;k++){
                    int offset = k*6;
                    int rowNum = 0;

                    for (String pri: PRIORITY){
                        System.out.println("get pri: "+pri);
                        if (tableMap.containsKey(pri)){
                            List<XSSFRow> rows = tableMap.get(pri);
                            for (XSSFRow row: rows){
                                XSSFRow newRow = null;
                                if (k==0)
                                    newRow = sheet.createRow(rowNum);
                                else
                                    newRow = sheet.getRow(rowNum);

                                for (int i=0;i<6;i++){
                                    XSSFCell code = newRow.createCell(i+offset);
                                    code.setCellType(XSSFCell.CELL_TYPE_STRING);
                                    XSSFCell cell = row.getCell(i+offset);
                                    String cellstr = "";
                                    if (cell!=null) cellstr = cell.toString();
                                    XSSFRichTextString codeContent = new XSSFRichTextString(cellstr);
                                    code.setCellValue(codeContent);
                                    CellStyle newStyle = workBook.createCellStyle();
                                    newStyle.cloneStyleFrom(row.getCell(i+offset).getCellStyle());
                                    code.setCellStyle(newStyle);
                                }
                                rowNum++;
                            }
                        } else{
                            XSSFRow newRow = sheet.createRow(rowNum);
                            for (int i=0;i<6;i++){
                                XSSFCell code = newRow.createCell(i+offset);
                                CellStyle newStyle = workBook.createCellStyle();
                                newStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
                                code.setCellStyle(newStyle);
                            }
                            rowNum++;
                        }
                    }
                }

            }


            FileOutputStream fos = new FileOutputStream("/home/wgy/text/labro/zhibangai2.xlsx");
            workBook.write(fos);
            fos.flush();
            fos.close();
            System.out.println("文件生成");


        } catch (Exception e){
            e.printStackTrace();
        } finally {
        }
    }
}

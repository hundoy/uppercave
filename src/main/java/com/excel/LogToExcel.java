package com.excel;

import com.zohar.common.util.FileUtil;
import com.zohar.common.util.ToolUtil;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/21.
 */
public class LogToExcel {
    public static void main(String[] args){
        System.out.println("start parse!");
        String logPath = "D:/logslogs/0928/";
        String[] logFiles = {"stg_book_info", "stg_org_info", "stg_biz_info", "stg_bizfocus_info", "stg_subject_sheet"
                            ,"stg_balance_sheet", "stg_profit_sheet", "stg_fina_sheet", "stg_biz_sum", "stg_useraction_sum"
                            ,"stg_balance_sheet_xml", "stg_profit_sheet_xml", "stg_accounting_period"};
        String outputPath = "D:/logslogs/logResult[date].xlsx";

        boolean isTest = false;

        try{
            File file = new File("D:/logslogs/tplusTemplate.xlsx");
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
            for (int i=0; i<logFiles.length; i++){
                XSSFSheet satSheet = xwb.getSheetAt(i);
                System.out.println("name: "+satSheet.getSheetName());

                String logStr = "";
                if (isTest){
                    logStr = FileUtil.getStringFromFile(logPath+"145/"+logFiles[i]);
                    logStr += "\n" + FileUtil.getStringFromFile(logPath+"146/"+logFiles[i]);
                } else{
                    logStr = FileUtil.getStringFromFile(logPath+logFiles[i]);
                }
                String[] logLines = logStr.split("\n");
                int len = logLines.length;
                for (int j=0; j<len;j++){
//                    System.out.println(j+"/"+len+"  -  "+(j*1.0f/len)*100+"%");
                    XSSFRow row = satSheet.createRow(j+1);
                    String logLine = logLines[j];
                    String[] cols = logLine.split(",");
                    for (int k=0; k<cols.length; k++){
                        XSSFCell cell = row.createCell(k+1);
                        XSSFRichTextString cellVal = new XSSFRichTextString(cols[k]);
                        cell.setCellValue(cellVal);
                    }
                    // 插入发送日期到第一列
                    try {
                        String sendDate = ToolUtil.parseDateStrToStrWithFormat(cols[cols.length-1], "yyyyMMddHHmmss", "HH:mm:ss yyyy-MM-dd");
                        XSSFCell cell = row.createCell(0);
                        XSSFRichTextString cellVal = new XSSFRichTextString(sendDate);
                        cell.setCellValue(cellVal);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(logLine);
                    }
                }
            }
            String outFile = outputPath.replace("[date]", ToolUtil.parseTime(new Date()));
            FileOutputStream fos = new FileOutputStream(outFile);
            xwb.write(fos);
            fos.flush();
            fos.close();
            System.out.println("文件生成");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

package com.my;

import com.zohar.common.util.FileUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;

import static com.zohar.common.util.RegExpUtil.regFindAllByFirstGroup;

/**
 * Created by Administrator on 2016/8/9.
 */
public class GenFieldConfig {

    public static void main(String[] args){
        String html = FileUtil.getStringFromFile("d:/test/financeHtml.txt");

        String fieldsHtml = "";

        String[] lines = html.split("\n");
        for (int i=0; i<lines.length; i++){
            String line = lines[i];
            if (line.contains("<p>一、接口描述")){
                fieldsHtml = line;
                break;
            }
        }

        List<String> rs = regFindAllByFirstGroup("(<h3[^>]*>.*?<\\/h3><div.*?</div>)", fieldsHtml);

        try {
            JSONArray jarr = new JSONArray();
            for (String tableHtml: rs){
                HtmlAnalyze analy = new HtmlAnalyze(tableHtml);
                analy.analyze();
                if (analy.isValid()) {
                    FTable ftable = analy.getResult();
                    JSONObject jobj = new JSONObject();
                    jobj.put("tableName", ftable.getTname());
                    JSONArray farr = new JSONArray();
                    for (FCol col: ftable.getCols()){
                        if (col.getTfield().equalsIgnoreCase("无")) continue;
                        JSONObject colObj = new JSONObject();
                        colObj.put("fieldName", col.getTfield());
                        colObj.put("desc", col.getDesc());
                        String type = col.getConfigType();
                        if (!type.equalsIgnoreCase("STRING")){
                            colObj.put("type", type);
                        }
                        if (col.getTfield().equalsIgnoreCase("book_id") || col.getTfield().equalsIgnoreCase("Base_DuoJiGou_JiaMengShang")
                                || type.equals("YEAR") || type.equals("MONTH")
                                || type.equals("INT") || type.equals("BIGDECIMAL") || type.contains("ENUM")){
                            colObj.put("required", "TRUE");
                        }
                        farr.put(colObj);
                    }
                    jobj.put("fields", farr);
                    jarr.put(jobj);
                }
            }

            // format
            StringBuffer sb = new StringBuffer();
            sb.append("[\r\n");
            for (int i=0; i<jarr.length(); i++){
                JSONObject table = jarr.getJSONObject(i);
                sb.append("  {\r\n");
                sb.append("    \"tableName\":\"").append(table.get("tableName")).append("\",\r\n");
                sb.append("    \"fields\":[\r\n");
                for (int j=0; j<table.getJSONArray("fields").length(); j++){
                    JSONObject field = table.getJSONArray("fields").getJSONObject(j);
                    sb.append("      ").append(field.toString());
                    if (j<table.getJSONArray("fields").length()-1){
                        sb.append(",");
                    }
                    sb.append("\r\n");
                }
                sb.append("    ]\r\n");
                sb.append("  }\r\n");
                if (i<jarr.length()-1){
                    sb.append(",\r\n");
                }
            }
            sb.append("]\r\n");

            FileUtil.writeStringToFile("d:/test/fieldConfig.json", sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

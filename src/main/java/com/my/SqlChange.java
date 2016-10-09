package com.my;

import com.zohar.common.util.FileUtil;

import java.io.File;

/**
 * Created by Administrator on 2016/9/30.
 */
public class SqlChange {

    public static void main(String[] args){
        System.out.println("start");

        String sql = FileUtil.getStringFromFile(new File("D:\\text\\sql\\temp_profit_temp_zcfz.sql"));

        int[] nums = {199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,221,222,223,224,227};

        StringBuffer sb = new StringBuffer();
        for (int n : nums){
            System.out.println(n);
            sb.append(sql.replace("_199", "_"+n));
        }

        FileUtil.writeStringToFile("D:/text/sql/temp_profit_temp_zcfz_change.sql", sb.toString());
        System.out.println("fin");
    }
}

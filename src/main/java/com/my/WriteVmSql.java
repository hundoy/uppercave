package com.my;

import com.zohar.common.util.FileUtil;
import com.zohar.common.util.RegExpUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class WriteVmSql {

    public static void main(String[] args){

        String ddlPath = "/";
        List<File> files = FileUtil.dirFiles(ddlPath, null);

        List<Ddl> ddlList = new ArrayList<Ddl>();

        for (File f: files){
            String ddl = FileUtil.getStringFromFile(f);
            String[] lines = ddl.split("\n");
            Ddl ddlOne = new Ddl();
            ddlOne.tname = f.getName();
            ddlOne.fields = new ArrayList<DdlField>();
            for (String line: lines){
                List<String> rs = RegExpUtil.regFindFirstByAllGroup("^\"(\\S+)\"\\s+(\\S+)\\s.*", line);
                DdlField ddlField = new DdlField(rs.get(0), rs.get(1));
                ddlOne.fields.add(ddlField);
            }
            ddlList.add(ddlOne);
        }


        

    }
}

package com.my;

import com.zohar.common.util.FileUtil;
import com.zohar.common.util.RegExpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public class VmSqlArrange {
    int[] nums = {199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,221,222,223,224,227};

    public static void main(String[] args){
        String filePath = "D:\\svn\\vm-job\\week\\accountbook.sql";
        String bigsql = FileUtil.getStringFromFile(filePath);
//        String sqlreg = "-- (\\S+)\\s+";
        String sqlreg = "-- (\\S+)\\s+select(.*?)from";
        List<List<String>> rs = RegExpUtil.regFindAllByAllGroup(sqlreg, bigsql);

        List<String> totalFields = new ArrayList<String>();
        List<String> totalFieldsType = new ArrayList<String>();

        StringBuffer sb = new StringBuffer();
        sb.append("create table XXX (");

        for (int i=0; i<rs.get(0).size(); i++){
            VmTable vmt = new VmTable(rs.get(0).get(i).trim());
            String fsstr = rs.get(1).get(i).replaceAll("(\\(.*?),(.*?\\))", "\\1，\\2");
            String[] fs = fsstr.split(",");
//            System.out.print("");
            for (int j=0; j<fs.length; j++){
                String fstr = fs[j].trim().replace("\n", "");
                String val = "";
                String fname = "";
                if (fstr.contains(" as ")){
                    List<String> f = RegExpUtil.regFindFirstByAllGroup("(\\S+)\\s+as\\s+(\\S+)", fstr);
                    val = f.get(0);
                    fname = f.get(1);
                } else if (fstr.contains(" ")){
                    List<String> f = RegExpUtil.regFindFirstByAllGroup("(\\S+)\\s+(\\S+)", fstr);
                    val = f.get(0);
                    fname = f.get(1);
                } else {
                    val = fstr;
                    fname = fstr;
                }

                if (fname.contains(".")){
                    fname = fname.substring(fname.indexOf(".")+1);
                }

                boolean isFake = false;
                String type="";
                if (val.equalsIgnoreCase("''") || val.equalsIgnoreCase("0")
                        || val.equalsIgnoreCase("0.0") || val.equalsIgnoreCase("false")
                        || val.equalsIgnoreCase("NULL")){
                    isFake = true;

                    if (val.equalsIgnoreCase("''")) type = "varchar";
                    if (val.equalsIgnoreCase("0")) type = "int8";
                    if (val.equalsIgnoreCase("0.0")) type = "numeric";
                    if (val.equalsIgnoreCase("NULL")) type = "timestamp";
                }

                if (i==0){
                    totalFields.add(fname);
                }

                if (!isFake){
                    vmt.fields.add(fname);
                    vmt.totalFields.add(totalFields.get(j));
                    totalFieldsType.add(type);
                }

                if (i==0){
                    // 拼大SQL
                    sb.append("\n").append(totalFields.get(j)).append(" ").append(type).append(",");
                }
            }
            System.out.println("________________________");
            System.out.println(vmt.toString());
        }

        sb.append(");");
        System.out.println(sb.toString());

        System.out.println("over");
    }
}

class VmTable{
    String tname;
    List<String> fields;
    List<String> totalFields;
    List<String> type;

    VmTable(String n){
        tname = n;
        fields = new ArrayList<String>();
        totalFields = new ArrayList<String>();
        type = new ArrayList<String>();
    }

    @Override
    public String toString() {
        String s = "Table: "+tname;
        if (fields.size()>0){
            for (int i=0; i<fields.size(); i++){
                s+="\n"+fields.get(i)+"  -  "+totalFields.get(i);
            }
        }
        return s;
    }
}

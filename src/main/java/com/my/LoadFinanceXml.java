package com.my;

import com.zohar.common.UpperCave.UpperCaveBase;

/**
 * Created by Administrator on 2016/9/26.
 */
public class LoadFinanceXml extends UpperCaveBase {
    final static String className = "com.my.LoadFinanceXml::";

    public static void main(String[] args) {
        prepareUI();
        e.draw("Finance Xml Viewer", 800, 600);
    }

    private static void prepareUI() {
        init();

        p("baseP").setLayout("B");

        p("baseP").addP("chfiP", "FL", "N");
        p("baseP").addP("tableP", "B", "C");

          p("chfiP").add(tf("nameTF(20)"));
          p("chfiP").add(btn("chfiBTN(Open)", className+"chooseFile"));

          p("tableP").add(table("xmlTB"), "C");
    }

    public static void chooseFile(){

    }
}

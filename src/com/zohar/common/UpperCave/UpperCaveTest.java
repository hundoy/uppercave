package com.zohar.common.UpperCave;

import java.util.Date;

public class UpperCaveTest extends UpperCaveBase{
	final static String className = "com.zohar.common.UpperCave.UpperCaveTest::";
	public static void main(String[] args) {
		
		// init the swing with two ways.
		// For no-tab frame, init it with layout.
		// For tab frame, init with 'tab'.
//		init("G:1,2");
		init("tab");
		// after init, there will be a 'baseP' as the basic panel. It is same with other panel object.
		p("baseP").addTab("base1P", "normalTab").setLayout("G:1,2");
		p("baseP").addTab("base2P", "TabTab").setLayout("G:2,1");;
		p("baseP").addTab("base3P", "EmptyTab");
		
		// add a panel with name and layout:BorderLayout.
		p("base1P").addP("leftP", "B");
			// add elements with position(Because of BorderLayout). 
			p("leftP").add(ltf("leftTF(输入的地方,10)"), "N"); // TextField with name and length
			p("leftP").add(table("leftTB"), "C"); // Table is simple. No advanced functions for it.
			p("leftP").add(btn("leftBTN(LeftBtn)", className+"leftBtnEvent"), "S"); // Button with name and event function.
		
		// tb is Title Border.
		p("base1P").addP("rightP", "G:3,1").tb("Right Panel");
			p("rightP").add(lta("leftTA(结果在此,29,30)"));
			p("rightP").add(list("rightList(1)", className+"rightListEvent").s()).tb("Test List"); // List and event function. 1 is single select List. s is Scroll Pane.
			p("rightP").add(btn("right1BTN(Btn1)", className+"right1BtnEvent"));
		
			
		p("base2P").addP("upP", "G:1,2");
			p("upP").add(lta("upLeftTA(一个输入框,20,30)")); // TextArea with name, row, column.
			p("upP").addP("upRightP", "B");
				p("upRightP").tb("Right Panel");
				p("upRightP").add(list("upRightList(2)", null).s(), "C");
				// Flow(center align) Layout
				p("upRightP").addP("upRightBtnP", "FC", "S");
					p("upRightBtnP").add(btn("upRight1Btn(OK)", className+"okEvent"));
					p("upRightBtnP").add(btn("upRight2Btn(Cancel)", className+"cancelEvent"));
					
		p("base2P").addP("downP", "tab");
			p("downP").addTab("tab1P", "tab1").setLayout("B");
				p("tab1P").add(ta("tab1TA(10,20)"), "C");
			p("downP").addTab("tab2P", "tab2").setLayout("B");
				p("tab2P").add(btn("tab2BTN(UselessButton)", className+"popDialog"), "C");
			p("downP").addTab("tab3P", "tab3");
			p("downP").addTab("tab4P", "tab4");
		
			
		// 子窗口设置
		// define a child window to pop up. (refer to the popDialog() method below.)
		p("diaBaseP").setLayout("B");
		p("diaBaseP").add(list("diaList(1)", null), "C");
		p("diaBaseP").addP("diaDownBtnP", "FC", "S");
			p("diaDownBtnP").add(btn("getBtn(GET)", className+"getValue"));
			p("diaDownBtnP").add(btn("closeBtn(CLOSE)", className+"handCloseDialog"));
			
		// 填充初始化数据
		// fill the init data for List above.
		ls("rightList").add(new String[]{"yellow dog", "little yellow", "small yellow", "cute yellow"});
		ls("diaList").add(new String[]{"1.yellow dog", "2.little yellow", "3.small yellow", "4.cute yellow"});
		
		// fill the init data for Table above.
		Object[][] data = {
				{"Slime", 12, 2, 1},
				{"Bat", 17, 3, 2},
				{"Worm", 10, 6, 3},
				{"Butterfly", 20, 10, 1},
				{"Flower", 19, 22, 19}
		};
		tb("leftTB").dmod().setDataVector(data, new Object[]{"Name", "HP", "EXP", "Gold"});
		
		ls("upRightList").add(new String[]{"Big", "middle", "small", "mini", "micro"});
		
		// start paint frame with title and width, height.
		e.draw("Title", 640, 480);
	}
	
	private static String value = "";
	
	public static void getValue(){
		// alert just like javascript.
		value = ls("diaList").getI().toString();
		alert("Value get successfully!");
		System.out.println(value);
		b("upLeftTA").val(value);
	}
	
	// when click X in the child window.
	public static void handCloseDialog(){
		closeDialog();
		// close the child window.
		e.closePop("getValDLG");
	}
	public static void closeDialog(){
		// confirm result: 0-yes 1-no 2-X
		if (confirm("Do you want to save your selected value?")==0){
			getValue();
		}
	}
	
	public static void popDialog(){
		// pop up a child window.
		e.pop("getValDLG", "dialog", p("diaBaseP"), className+"closeDialog");
	}
	
	public static void okEvent(){
		// alert a message with a title.
		alert("Title", "This is ok!!");
		System.out.println("OK!");
	}
	
	public static void cancelEvent(){
		// confirm window. return 0,1,2 when click yes, no and X.
		int r = confirm("is it cancel?");
		if (r==0){
			System.out.println("Yes!");
		} else if (r==1){
			System.out.println("no!");
		} else{
			System.out.println("X");
		}
	}
	
	public static void leftBtnEvent(){
		// clear list api.
		ls("rightList").cls();
	}
	
	public static void right1BtnEvent(){
		System.out.println("right1BtnEvent!!!");

		// get and set from TextField and TextArea.
		String input = b("leftTF").val();
		b("leftTA").val(input);
		// add element to List
		ls("rightList").add(input+(new Date()));
	}
	
	public static void rightListEvent(){
		System.out.println("right list!");
		//ls("rightList").add(new Date());
	}
}

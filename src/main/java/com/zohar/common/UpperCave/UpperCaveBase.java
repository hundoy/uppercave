package com.zohar.common.UpperCave;

import java.awt.Component;

public abstract class UpperCaveBase { 
	protected static UpperCaveEditor e = null;
	protected static UpperCaveFactory f = null;
	
	protected static void init(){
		e = new UpperCaveEditor();
		f = e.getUcf();
	}
	
	protected static void init(String lmStr){
		e = new UpperCaveEditor(lmStr);
		f = e.getUcf();
	}
	
	protected static UpperCaveBean l(String nameArgs){
		return f.l(nameArgs);
	}
	
	protected static UpperCaveBean ltf(String nameArgs){
		return f.ltf(nameArgs);
	}
	
	protected static UpperCaveBean tf(String nameArgs){
		return f.tf(nameArgs);
	}
	
	protected static UpperCaveBean lta(String nameArgs){
		return f.lta(nameArgs);
	}
	protected static UpperCaveBean ta(String nameArgs){
		return f.ta(nameArgs);
	}
	
	protected static UpperCaveBean btn(String nameValue, String clsmth){
		return f.btn(nameValue, clsmth);
	}
	
	protected static UpperCaveBean ucb(Component comp, String name){
		return f.ucb(comp, name);
	}
	
	protected static UpperCavePanel p(String str){
		return e.p(str);
	}
	protected static UpperCaveList ls(String str){
		return e.ls(str);
	}
	protected static UpperCaveTable tb(String str){
		return e.tb(str);
	}
	protected static UpperCaveBean b(String str){
		return e.b(str);
	}
	protected static UpperCaveBean list(String nameArgs, String clsmth){
		return f.list(nameArgs, clsmth);
	}
	protected static UpperCaveTable table(String nameArgs){
		return f.table(nameArgs);
	}
	protected static void alert(String msg){
		e.alert(msg);
	}
	protected static void alert(String title, String msg){
		e.alert(title, msg);
	}
	protected static int confirm(String msg){
		return e.confirm(msg);
	}
	protected static int confirm(String title, String msg){
		return e.confirm(title, msg);
	}
}

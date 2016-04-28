package com.zohar.common.UpperCave;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * bean of component
 * @author zohar
 */
public class UpperCaveBean {
	protected UpperCaveEditor uce = null;
	protected Component comp;
	protected Component outComp;
	protected String name;
	protected int level;
	protected String parentName;
	
	public UpperCaveBean(UpperCaveEditor uce, Component comp, String name){
		this.uce = uce;
		this.comp = comp;
		this.outComp = comp;
		this.name = name;
	}
	
	public UpperCaveBean(UpperCaveEditor uce, Component comp, Component outComp, String name){
		this.uce = uce;
		this.comp = comp;
		this.outComp = outComp;
		this.name = name;
	}
	
	/**
	 * add title border
	 * @param title
	 * @return
	 */
	public UpperCaveBean tb(String title){
		if (outComp instanceof JComponent){
			JComponent jc = (JComponent)outComp;
			jc.setBorder(BorderFactory.createTitledBorder(title));
		}
		return this;
	}
	
	/**
	 * add a jscroll to this ucb
	 * @return
	 */
	public UpperCaveBean s(){
		if (outComp instanceof JComponent){
			JComponent jc = (JComponent)outComp;
			JScrollPane jsp = new JScrollPane(jc);
			outComp = jsp;
		}
		return this;
	}
	
	public String val(){
		if (comp instanceof JTextField){
			return ((JTextField)comp).getText();
		}
		if (comp instanceof JTextArea){
			return ((JTextArea)comp).getText();
		}
		if (comp instanceof JButton){
			return ((JButton)comp).getText();
		}
		
		return null;
	}
	
	public void val(String v){
		if (comp instanceof JTextField){
			((JTextField)comp).setText(v);
		}
		if (comp instanceof JTextArea){
			((JTextArea)comp).setText(v);
		}
		if (comp instanceof JButton){
			((JButton)comp).setText(v);
		}
	}

	public void active(boolean b){
        if (comp instanceof JTextField){
            ((JTextField) comp).setEditable(b);
        }
        if (comp instanceof JTextArea){
            ((JTextArea) comp).setEditable(b);
        }
        if (comp instanceof JButton){
            ((JButton)comp).setEnabled(b);
        }
    }
	
	public Component getComp() {
		return comp;
	}
	public void setComp(Component comp) {
		this.comp = comp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public UpperCaveEditor getUce() {
		return uce;
	}

	public void setUce(UpperCaveEditor uce) {
		this.uce = uce;
	}

	public Component getOutComp() {
		return outComp;
	}

	public void setOutComp(Component outComp) {
		this.outComp = outComp;
	}
	
}

package com.zohar.common.UpperCave;

import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

public class UpperCaveList extends UpperCaveBean{
	
	public UpperCaveList(UpperCaveEditor uce, Component comp, String name) {
		super(uce, comp, name);
	}
	
	public JList list(){
		JList list = (JList) getComp();
		return list;
	}
	
	public ListModel mod(){
		JList list = (JList) getComp();
		ListModel lm = list.getModel();
		return lm;
	}
	
	public DefaultListModel dmod(){
		JList list = (JList) getComp();
		ListModel lm = list.getModel();
		if (lm instanceof DefaultListModel){
			DefaultListModel dlm = (DefaultListModel)lm;
			return dlm;
		}
		System.out.println("Error: this list is not a DefaultListModel!!");
		return null;
	}
	
	// add 
	public void add(int i, Object o){
		DefaultListModel dlm = dmod();
		if (dlm!=null){
			dlm.add(i, o);
		}
	}
	
	public void add(Object o){
		DefaultListModel dlm = dmod();
		if (dlm!=null){
			dlm.addElement(o);
		}
	}
	
	public void add(Object[] arr){
		DefaultListModel dlm = dmod();
		if (dlm!=null){
			for (Object o: arr){
				add(o);
			}
		}
	}
	
	// delete
	public void del(int i){
		DefaultListModel dlm = dmod();
		if (dlm!=null){
			dlm.remove(i);
		}
	}
	
	public void del(Object o){
		DefaultListModel dlm = dmod();
		if (dlm!=null){
			dlm.removeElement(o);
		}
	}
	
	public void cls(){
		DefaultListModel dlm = dmod();
		if (dlm!=null){
			dlm.clear();
		}
	}
	
	// alter
	public void set(int i, Object o){
		DefaultListModel dlm = dmod();
		if (dlm!=null){
			dlm.set(i, o);
		}
	}
	
	public void set(int i, Object[] arr){
		DefaultListModel dlm = dmod();
		if (dlm!=null){
			dlm.clear();
			for (Object o: arr){
				add(o);
			}
		}
	}
	
	// query
	public Object get(int i){
		DefaultListModel dlm = dmod();
		if (dlm!=null){
			return dlm.get(i);
		}
		return null;
	}
	
	public int i(){
		JList list = list();
		return list.getSelectedIndex();
	}
	
	public int[] iz(){
		JList list = list();
		return list.getSelectedIndices();
	}
	
	public Object getI(){
		return get(i());
	}
	
	public Object[] getIz(){
		int[] iz = iz();
		Object[] objs = new Object[iz.length];
		for (int i: iz){
			objs[i] = get(i);
		}
		return objs;
	}
}

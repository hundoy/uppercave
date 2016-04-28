package com.zohar.common.UpperCave;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.zohar.common.ucassist.UcCheckBoxTableCellRenderer;

public class UpperCaveTable extends UpperCaveBean{

	public UpperCaveTable(UpperCaveEditor uce, Component comp,
			Component outComp, String name) {
		super(uce, comp, outComp, name);
	}

	public DefaultTableModel dmod(){
		JTable table = (JTable) getComp();
		TableModel tm = table.getModel();
		if (tm instanceof DefaultTableModel){
			DefaultTableModel dtm = (DefaultTableModel)tm;
			return dtm;
		}
		System.out.println("Error: this table is not a DefaultTableModel!!");
		return null;
	}
	
	public JTable table(){
		return (JTable)getComp();
	}
	
	public void setColWidth(int num, int width){
		JTable table = (JTable) getComp();
		TableColumn col = table.getColumnModel().getColumn(num);
		col.setPreferredWidth(width);
		//col.setWidth(width);
		col.setMinWidth(width);
		col.setMaxWidth(width);
	}
	
	public void setRowHeight(int width){
		JTable table = (JTable) getComp();
		table.setRowHeight(width);
	}

	// set a column to a checkbox column
	public void setColCheckBox(int colIndex) {
		JTable table = (JTable) getComp();
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(colIndex).setCellEditor(new DefaultCellEditor(new JCheckBox()));    
        tcm.getColumn(colIndex).setCellRenderer(new UcCheckBoxTableCellRenderer());     
	}
	
	public void setColComboBox(int colIndex, String[] arr){
		JTable table = (JTable) getComp();
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(colIndex).setCellEditor(new DefaultCellEditor(new JComboBox(arr)));    
        //tcm.getColumn(colIndex).setCellRenderer(new UcComboBoxTableCellRenderer());     
	}
	
	public void selColCheckBox(int colIndex, boolean b){
		JTable table = (JTable) getComp();
		for (int i=0; i<table.getRowCount(); i++){
			table.setValueAt(b, i, colIndex);
		}
	}

	public Object[] getColData(int colIndex) {
		JTable table = (JTable) getComp();
		Object[] colData = new Object[table.getRowCount()];
		for (int i=0; i<table.getRowCount(); i++){
			Object data = table.getValueAt(i, colIndex);
			colData[i] = data;
		}
		return colData;
	}
}



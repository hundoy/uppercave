package com.zohar.common.ucassist;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class UcCheckBoxTableCellRenderer extends JCheckBox implements TableCellRenderer{
	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Boolean b = (Boolean) value;    
        this.setSelected(b.booleanValue());    
        return this;
	}
}

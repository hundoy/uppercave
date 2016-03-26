package com.zohar.common.ucassist;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class UcComboBoxTableCellRenderer extends JComboBox implements TableCellRenderer{
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean arg3, int arg4, int arg5) {
		if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
		
		//System.out.println(value);
		setSelectedItem(value);
        return this;
	}

}

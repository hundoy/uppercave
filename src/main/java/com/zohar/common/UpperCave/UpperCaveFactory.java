package com.zohar.common.UpperCave;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.zohar.common.util.ToolUtil;

public class UpperCaveFactory {
	private UpperCaveEditor uce = null;
	
	public UpperCaveFactory(UpperCaveEditor uce){
		this.uce = uce;
	}
	
	/**
	 * create a label
	 * @param nameArgs
	 * @return
	 */
	public UpperCaveBean l(String nameArgs){
		String name = getName(nameArgs);
		String text = "";
		
		String[] args = getArgs(nameArgs);
		if (!ToolUtil.isNullOrEmpty(args)){
			text = args[0];
		} else{
			text = name;
		}
		JLabel jl = new JLabel(text);
		
		UpperCaveBean ucb = new UpperCaveBean(uce, jl, name);
		
		return ucb;
	}
	
	/**
	 * create a label+textArea
	 * @param nameArgs
	 * @return
	 */
	public UpperCaveBean lta(String nameArgs){
		String name = getName(nameArgs);
		String labelText = "";
		Integer row = null;
		Integer col = null;
		JLabel jl = null;
		JTextArea jta = null;
		
		String[] args = getArgs(nameArgs);
		if (!ToolUtil.isNullOrEmpty(args)){
			labelText = args[0];
			
			if (args.length>=3){
				row = Integer.valueOf(args[1]);
				col = Integer.valueOf(args[2]);
			}
		}
		
		if (!ToolUtil.isNullOrBlank(labelText)){
			jl = new JLabel(labelText);
		}
		
		if (col==null){
			jta = new JTextArea();
		} else{
			jta = new JTextArea(row, col);
		}
		jta.setLineWrap(true);
		
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp.add(jl);
		jp.add(jta);
		
		UpperCaveBean ucb = new UpperCaveBean(uce, jta, jp, name);
		
		return ucb;
	}
	
	/**
	 * create a textArea
	 * @param nameArgs
	 * @return
	 */
	public UpperCaveBean ta(String nameArgs){
		String name = getName(nameArgs);
		Integer row = null;
		Integer col = null;
		JTextArea jta = null;
		
		String[] args = getArgs(nameArgs);
		if (!ToolUtil.isNullOrEmpty(args)){
			if (args.length>=2){
				row = Integer.valueOf(args[0]);
				col = Integer.valueOf(args[1]);
			}
		}
		
		if (col==null){
			jta = new JTextArea();
		} else{
			jta = new JTextArea(row, col);
		}
		jta.setLineWrap(true);
		
		UpperCaveBean ucb = new UpperCaveBean(uce, jta, name);
		
		return ucb;
	}
	
	/**
	 * create a label+textfield
	 * @param nameArgs
	 * @return
	 */
	public UpperCaveBean ltf(String nameArgs){
		String name = getName(nameArgs);
		String labelText = "";
		Integer col = null;
		JLabel jl = null;
		JTextField jf = null;
		
		String[] args = getArgs(nameArgs);
		if (!ToolUtil.isNullOrEmpty(args)){
			labelText = args[0];
			
			if (args.length>=2){
				col = Integer.valueOf(args[1]);
			}
		}
		
		if (!ToolUtil.isNullOrBlank(labelText)){
			jl = new JLabel(labelText);
		}
		
		if (col==null){
			jf = new JTextField();
		} else{
			jf = new JTextField(col);
		}
		
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp.add(jl);
		jp.add(jf);
		
		UpperCaveBean ucb = new UpperCaveBean(uce, jf, jp, name);
		
		return ucb;
	}
	
	/**
	 * create a textfield
	 * @param nameArgs
	 * @return
	 */
	public UpperCaveBean tf(String nameArgs){
		String name = getName(nameArgs);
		Integer col = null;
		JTextField jf = null;
		
		String[] args = getArgs(nameArgs);
		if (!ToolUtil.isNullOrEmpty(args)){
			col = Integer.valueOf(args[0]);
		}
		
		if (col==null){
			jf = new JTextField();
		} else{
			jf = new JTextField(col);
		}
		
		UpperCaveBean ucb = new UpperCaveBean(uce, jf, name);
		
		return ucb;
	}
	
	/**
	 * create a button
	 * @param nameValue testBTN(TestButton)
	 * @param clsmth
	 * @return
	 */
	public UpperCaveBean btn(final String nameValue, final String clsmth){
		String name = getName(nameValue);
		String value = "";
		
		String[] args = getArgs(nameValue);
		if (!ToolUtil.isNullOrEmpty(args)){
			value = args[0];
		}
		
		JButton jb = new JButton(value);
		
		// invoke the event method
		final Method m = getMethod(clsmth);
		if (m!=null){
			jb.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						m.invoke(m.getClass(), new Object[]{});
					} catch (Exception e) {
						System.out.println(nameValue + " - " + clsmth);
						e.printStackTrace();
					}
				}
			});
		}
		
		UpperCaveBean ucb = new UpperCaveBean(uce, jb, name);
		
		return ucb;
	}
	
	/**
	 * create a list
	 * @param
	 * @param clsmth
	 * @return
	 */
	public UpperCaveList list(String nameArgs, String clsmth){
		String name = getName(nameArgs);
		String[] args = getArgs(nameArgs);
		int model = ListSelectionModel.SINGLE_SELECTION;
		
		if (!ToolUtil.isNullOrEmpty(args)){
			if (!args[0].equals("1")){
				model = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
			}
		}
		
		DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		list.setSelectionMode(model);
		if (model == ListSelectionModel.SINGLE_SELECTION){
			list.setSelectedIndex(0);
		}
		
		final Method m = getMethod(clsmth);
		if (m!=null){
			list.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()){
						try {
							m.invoke(m.getClass(), new Object[]{});
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}
		
		UpperCaveList ucl = new UpperCaveList(uce, list, name);
		return ucl;
	}
	
	public UpperCaveTable table(String nameArgs){
		String name = getName(nameArgs);
		
		JTable jtb = new JTable();
		JScrollPane jsp = new JScrollPane(jtb);
		jtb.setFillsViewportHeight(true);
		UpperCaveTable uct = new UpperCaveTable(uce, jtb, jsp, name);
		
		return uct;
	}
	
	public UpperCaveBean ucb(Component comp, String name){
		return new UpperCaveBean(uce, comp, name);
	}
	
	public UpperCaveEditor getUce() {
		return uce;
	}

	public void setUce(UpperCaveEditor uce) {
		this.uce = uce;
	}
	
	
	private String getName(String str){
		if (str==null){
			return "";
		}
		int si = str.indexOf("(");
		if (si>-1){
			return str.substring(0, si);
		} else{
			return str;
		}
	}
	
	private String[] getArgs(String str){
		String[] args = null;
		int si = str.indexOf("(");
		int ei = str.indexOf(")");
		if (si>-1 && ei>-1){
			String argsStr = str.substring(si+1, ei);
			args = argsStr.split(",");
		}
		return args;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getMethod(String clsmth){
		if (!ToolUtil.isNullOrBlank(clsmth)){
			String[] clsmthArr = clsmth.split("::");
			Class c;
			try {
				c = Class.forName(clsmthArr[0]);
				final Method m = c.getMethod(clsmthArr[1], new Class[]{});
				return m;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

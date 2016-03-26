package com.zohar.common.UpperCave;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * insist on handwriting swing
 * 
 * @author Zohar
 */
public class UpperCaveEditor {
	private Map<String, UpperCaveBean> ucMap = null;
	private UpperCaveFactory ucf = null;
	private JFrame frame = new JFrame();
	private Map<String, JDialog> dialogMap = new LinkedHashMap<String, JDialog>();
	
	public UpperCaveEditor(){
		ucMap = new HashMap<String, UpperCaveBean>();
		p("baseP");
	}
	
	/**
	 * create uce with a layout for baseP
	 * @param lmStr
	 */
	public UpperCaveEditor(String lmStr){
		ucMap = new HashMap<String, UpperCaveBean>();
		p("baseP", lmStr);
	}
	
	public UpperCaveFactory getUcf(){
		if (ucf==null){
			ucf = new UpperCaveFactory(this);
		}
		return ucf;
	}
	
	/**
	 * create or get a jpanel
	 * @param pName
	 * @return
	 */
	public UpperCavePanel p(String pName){
		if (ucMap.containsKey(pName)){
			return (UpperCavePanel) ucMap.get(pName);
		} else{
			UpperCavePanel newP = new UpperCavePanel(this, new JPanel(), pName);
			addUcb(newP);
			return newP;
		}
	}
	
	/**
	 * create or get a tab panel
	 */
	public UpperCavePanel tabP(String pName){
		if (ucMap.containsKey(pName)){
			return (UpperCavePanel) ucMap.get(pName);
		} else{
			UpperCavePanel newP = new UpperCavePanel(this, new JTabbedPane(), pName);
			addUcb(newP);
			return newP;
		}
	}
	
	/**
	 * get a element by name
	 * @param name
	 * @return
	 */
	public UpperCaveBean b(String name){
		if (ucMap.containsKey(name)){
			return ucMap.get(name);
		}
		return null;
	}
	
	/**
	 * get a list by name
	 * @param name
	 * @return
	 */
	public UpperCaveList ls(String name){
		UpperCaveBean b = b(name);
		if (b!=null && b instanceof UpperCaveList){
			return (UpperCaveList)b;
		} else{
			return null;
		}
	}
	
	/**
	 * get a table by name
	 * @param name
	 * @return
	 */
	public UpperCaveTable tb(String name){
		UpperCaveBean b = b(name);
		if (b!=null && b instanceof UpperCaveTable){
			return (UpperCaveTable)b;
		} else{
			return null;
		}
	}
	
	/**
	 * create a jpanel with a layout. Only available when CREATE
	 * @param pName
	 * @param lmStr
	 * @return
	 */
	public UpperCavePanel p(String pName, String lmStr){
		boolean isCreate = false;
		if (!ucMap.containsKey(pName)){
			isCreate = true;
		}
		
		UpperCavePanel p = null;
		if ("tab".equals(lmStr)){
			p = tabP(pName);
		} else{
			p = p(pName);
			if (isCreate){
				p.setLayout(lmStr);
			}
		}
		
		return p;
	}
	
	
	public void addUcb(UpperCaveBean ucb){
		if (ucMap.containsKey(ucb.getName())){
			System.out.println("Warn: new ucb["+ucb.getName()+"] will replace the old one!");
		}
		ucMap.put(ucb.getName(), ucb);
	}
	
	/**
	 * pop a alert
	 * @param title
	 * @param msg
	 */
	public void alert(String title, String msg){
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void alert(String msg){
		JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * pop a confirm dialog
	 * @param title
	 * @param msg
	 * @return
	 */
	public int confirm(String title, String msg){
		int r = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION);
		return r;
	}
	
	public int confirm(String msg){
		int r = JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_OPTION);
		return r;
	}
	
	/**
	 * pop up a dialog
	 * @param title
	 * @param ucp
	 * @param clsmth
	 */
	public void pop(final String name, String title, UpperCavePanel ucp, String clsmth){
		final JDialog jd = new JDialog(frame, title, true);
		jd.getContentPane().add(ucp.getOutComp(), BorderLayout.CENTER);
		jd.setSize(320, 240);
		jd.setLocationRelativeTo(null);
		dialogMap.put(name, jd);
		final Method m = UpperCaveFactory.getMethod(clsmth);
		jd.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	try {
					m.invoke(m.getClass(), new Object[]{});
					dialogMap.remove(name);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });
		jd.setVisible(true);
	}
	
	public void closePop(String name){
		if (dialogMap.containsKey(name)){
			dialogMap.get(name).dispose();
			dialogMap.remove(name);
			//dialogMap.get(name).setVisible(false);
		}
	}
	
	/**
	 * generate a JFrame with title.
	 * @param title
	 * @return
	 */
	public JFrame draw(String title, int width, int height){
		frame.setTitle(title);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(p("baseP").getOutComp(),
                BorderLayout.CENTER); // 自定义的panel
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        return frame;
	}
}

package com.zohar.common.UpperCave;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UpperCavePanel extends UpperCaveBean{
	private LayoutManager layout = null;
	private boolean haveSetLayout = false; // have set layout for panel?
	List<UpperCaveBean> children = null;
	
	public UpperCavePanel(UpperCaveEditor uce, Component comp, String name) {
		super(uce, comp, name);
	}
	
	public UpperCavePanel(UpperCaveEditor uce, Component comp,  Component outcomp, String name) {
		super(uce, comp, outcomp, name);
	}
	
	public void setLayout(LayoutManager lm){
		layout = lm;
	}
	
	/**
	 * easy version for set layout, only support Flow Grid Border now.
	 * @param lmStr [FL:5,1]  FL  FR  FC  F  [G:4,3]  G:4,3,2,2  G  [B:2,2]  B
	 */
	public void setLayout(String lmStr){
		if (comp instanceof JPanel){
			// FL:5,1  FL  FR  FC  F 
			if (lmStr.startsWith("F")){
				int hg = 5;
				int vg = 1;
				int align = FlowLayout.LEFT;
				
				if (lmStr.length()>1){
					String alignStr = lmStr.substring(1);
					if (alignStr.startsWith("R")){
						align = FlowLayout.RIGHT;
					} else if (alignStr.startsWith("C")){
						align = FlowLayout.CENTER;
					}
					
					int maoIndex = lmStr.indexOf(":");
					if (maoIndex>-1){
						String hv = lmStr.substring(maoIndex+1);
						String[] hvArr = hv.split(",");
						hg = Integer.valueOf(hvArr[0]);
						vg = Integer.valueOf(hvArr[1]);
					}
				}
				
				layout = new FlowLayout(align, hg, vg);
			}
			
			// G:4,3  G:4,3,2,2  G
			if (lmStr.startsWith("G")){
				int rows=1;
				int cols=1;
				int hg=0;
				int vg=0;
				
				if (lmStr.length()==1){
					layout = new GridLayout();
				} else{
					int maoI = lmStr.indexOf(":");
					if (maoI>-1){
						String rchv = lmStr.substring(maoI+1);
						String[] rchvArr = rchv.split(",");
						rows = Integer.valueOf(rchvArr[0]);
						cols = Integer.valueOf(rchvArr[1]);
						if (rchvArr.length==2){
							layout = new GridLayout(rows, cols);
						} else{
							hg = Integer.valueOf(rchvArr[2]);
							vg = Integer.valueOf(rchvArr[3]);
							
							layout = new GridLayout(rows, cols, hg, vg);
						}
					}
				}
			}
			
			// B:2,2  B
			if (lmStr.startsWith("B")){
				int hg = 0;
				int vg = 0;
				
				if (lmStr.length()>1){
					int maoI = lmStr.indexOf(":");
					if (maoI>-1){
						String hv = lmStr.substring(maoI+1);
						String[] hvArr = hv.split(",");
						hg = Integer.valueOf(hvArr[0]);
						vg = Integer.valueOf(hvArr[1]);
					}
				}
				
				layout = new BorderLayout(hg, vg);
			}
		} else{
			System.out.println("Error: "+name+" is not a JPanel, cannot set layout!");
		}
		
	}
	
	public UpperCaveBean add(UpperCaveBean ucb, String posStr){
		if (comp instanceof JPanel){
			// set layout
			if (layout==null){
				// default layout
				layout = new FlowLayout(FlowLayout.LEFT, 5, 1); 
			}
			
			JPanel jp = (JPanel) comp;
			if (!haveSetLayout){
				jp.setLayout(layout);
				haveSetLayout = true;
			}
			
			// add ucb
			if (posStr==null){
				jp.add(ucb.getOutComp());
			} else{
				String pos = BorderLayout.CENTER;
				if ("W".equals(posStr)){
					pos = BorderLayout.WEST;
				} else if ("E".equals(posStr)){
					pos = BorderLayout.EAST;
				} else if ("S".equals(posStr)){
					pos = BorderLayout.SOUTH;
				} else if ("N".equals(posStr)){
					pos = BorderLayout.NORTH;
				} else if ("C".equals(posStr)){
					pos = BorderLayout.CENTER;
				}
				jp.add(ucb.getOutComp(), pos);
			}
			
			addChild(ucb);
			ucb.setParentName(name);
			if (!(ucb instanceof UpperCavePanel)){
				uce.addUcb(ucb);
			}
			return ucb;
		} else{
			System.out.println("Error: "+name+" is a Tab Panel, only allow to add a Panel.");
			return ucb;
		}
	}
	
	public UpperCaveBean add(UpperCaveBean ucb){
		return add(ucb, null);
	}
	
	/**
	 * easy version of add ucb to panel
	 * @param
	 */
	@Deprecated
	public UpperCaveBean add(String ucbStrWithArgs, String args, String pos){
		String ucbStr = ucbStrWithArgs;
//		if (ucbStrWithArgs.indexOf("(")>-1){
//			ucbStr = ucbStr.substring(0, ucbStrWithArgs.indexOf("("));
//		}
		
		// xxxP - add a ucp( use addP instead...)
		if (ucbStr.endsWith("P")){
			return add(uce.p(ucbStr));
		}
		
		// xxxTF - add a textfield
		if (ucbStr.endsWith("TF")){
			int col = 0;
			JTextField tf = new JTextField();
			if (args==null){
				tf = new JTextField();
			} else{
				col = Integer.valueOf(args);
				tf = new JTextField(col);
			}
			UpperCaveBean ucb = new UpperCaveBean(uce, tf, ucbStr);
			return add(ucb, pos);
		}
		
		// xxxTA - add a textarea
		if (ucbStr.endsWith("TA")){
			int row = 0;
			int col = 0;
			JTextArea ta = new JTextArea();
			if (args==null){
				ta = new JTextArea();
			} else{
				String[] rowcolArr = args.split(",");
				row = Integer.valueOf(rowcolArr[0]);
				col = Integer.valueOf(rowcolArr[1]);
				ta = new JTextArea(row, col);
				ta.setLineWrap(true);
			}
			UpperCaveBean ucb = new UpperCaveBean(uce, ta, ucbStr);
			return add(ucb, pos);
		}
		
		// xxxBTN - add a button
		if (ucbStr.endsWith("BTN")){
			JButton jb = null;
			if (args==null){
				jb = new JButton();
			} else{
				jb = new JButton(args);
			}
			UpperCaveBean ucb = new UpperCaveBean(uce, jb, ucbStr);
			return add(ucb, pos);
		}
		
		return null;
	}
	
	@Deprecated
	public UpperCaveBean add(String ucbStr, String argsOrPos){
		if (getPos(argsOrPos)!=null){
			// pos
			return add(ucbStr, null, argsOrPos);
		} else{
			// args
			return add(ucbStr, argsOrPos, null);
		}
	}
	
	/**
	 * short method of add a ucp.
	 * @param ucpStr
	 * @return
	 */
	public UpperCavePanel addP(String ucpStr){
		UpperCaveBean ucp = add(uce.p(ucpStr));
		return (UpperCavePanel)ucp;
	}
	
	/**
	 * short method of add a ucp with layout, title and borderlayout position
	 * @param ucpStr
	 * @param lmStr layout of the new panel
	 * @param args title name of panel
	 * @param pos only for BorderLayout
	 * @return
	 */
	public UpperCavePanel addP(String ucpStr, String lmStr, String args, String pos){
		UpperCavePanel ucp = uce.p(ucpStr, lmStr);
		if (args!=null){
			ucp.tb(args);
		}
		return (UpperCavePanel)add(ucp, pos);
	}
	
	/**
	 * short method of add a ucp with layout
	 * @param ucpStr
	 * @param lmStr
	 * @param argsOrPos
	 * @return
	 */
	public UpperCavePanel addP(String ucpStr, String lmStr, String argsOrPos){
		if (getPos(argsOrPos)!=null){
			// pos
			return addP(ucpStr, lmStr, null, argsOrPos);
		} else{
			// args
			return addP(ucpStr, lmStr, argsOrPos, null);
		}
	}
	
	/**
	 * short method of add a ucp with layout
	 * @param ucpStr
	 * @param lmStr
	 * @return
	 */
	public UpperCavePanel addP(String ucpStr, String lmStr){
		return addP(ucpStr, lmStr, null, null);
	}
	
	/**
	 * add a panel to tab panel with tab name.
	 * @param ucpStr
	 * @param tabName
	 * @return
	 */
	public UpperCavePanel addTab(String ucpStr, String tabName){
		UpperCavePanel ucp = uce.p(ucpStr);
		JTabbedPane tabP = (JTabbedPane) comp;
		tabP.addTab(tabName, null, ucp.getOutComp(), tabName);
		
		addChild(ucp);
		ucp.setParentName(name);
		
		return ucp;
	}
	
	private void addChild(UpperCaveBean ucb){
		if (children==null){
			children = new ArrayList<UpperCaveBean>();
		}
		children.add(ucb);
	}
	
	private String getPos(String posStr){
		String pos = null;
		if ("W".equals(posStr)){
			pos = BorderLayout.WEST;
		} else if ("E".equals(posStr)){
			pos = BorderLayout.EAST;
		} else if ("S".equals(posStr)){
			pos = BorderLayout.SOUTH;
		} else if ("N".equals(posStr)){
			pos = BorderLayout.NORTH;
		} else if ("C".equals(posStr)){
			pos = BorderLayout.CENTER;
		}
		return pos;
	}

	/**
	 * switch tab. only useful for tab panel
	 * @param index
     */
	public void tabTo(int index){
        if (comp instanceof JTabbedPane){
            ((JTabbedPane)comp).setSelectedIndex(index);
        }
	}
}

package notepad;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.text.SimpleDateFormat;

public class notepad  extends JFrame implements ActionListener{
     JMenuItem jmiOpen,jmiSave,jmiExit,jmiAbout,jmiSaveAs;
     JMenuItem jmiFind,jmiSlectAll,jmiCut,jmiCopy,jmiPaster; // 编辑
     
     JTextArea jta = new JTextArea();// 记事本编辑框
     JLabel jlblStatus=new JLabel();
     JFileChooser jFileChooser=new JFileChooser();
     File savePath = null;
     
     public notepad(){
    	 JMenuBar mb=new JMenuBar();
    	 setJMenuBar(mb);
    	 JMenu fileMenu = new JMenu("文件");
    	 mb.add(fileMenu);
    	 JMenu editMenu=new JMenu("编辑");
    	 mb.add(editMenu);
    	 JMenu helpMenu=new JMenu("帮助");
    	 mb.add(helpMenu);
    	 
    	 jta.setLineWrap(true);
    	 jta.setWrapStyleWord(true);
    	 fileMenu.add(jmiOpen=new JMenuItem("打开"));
    	 // 给保存设置快捷键
    	 jmiSave=new JMenuItem("保存");
    	 jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	 fileMenu.add(jmiSave);
    	
    	 jmiSaveAs=new JMenuItem("另存为");
    	 fileMenu.add(jmiSaveAs);
    
    	 fileMenu.addSeparator();
    	 fileMenu.add(jmiExit=new JMenuItem("退出"));
    	 
    	 
    	 // 编辑主菜单
    	 jmiSlectAll=new JMenuItem("全选");
    	 jmiSlectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	 editMenu.add(jmiSlectAll);
    	 
    	 jmiCut=new JMenuItem("剪切");
    	 jmiCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	 editMenu.add(jmiCut);
    	 
    	 jmiCopy=new JMenuItem("复制");
    	 jmiCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		 editMenu.add(jmiCopy);
		 
    	 jmiPaster=new JMenuItem("粘贴");
    	 jmiPaster.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		 editMenu.add(jmiPaster);
		 
    	 jmiFind=new JMenuItem("查找");
		 jmiFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		 editMenu.add(jmiFind);

    	 
		 // 帮助主菜单
    	 jmiAbout=new JMenuItem("关于");
    	 jmiAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	 helpMenu.add(jmiAbout);
    	 jFileChooser.setCurrentDirectory(new File("."));
    	 getContentPane().add(new JScrollPane(jta),BorderLayout.CENTER);
    	 getContentPane().add(jlblStatus,BorderLayout.SOUTH);
    	 
    	 // 设置按钮监听
    	 jmiOpen.addActionListener(this);
    	 jmiSave.addActionListener(this);
    	 jmiExit.addActionListener(this);
    	 jmiAbout.addActionListener(this);
    	 jmiSaveAs.addActionListener(this);
    	 jmiFind.addActionListener(this);
    	 jmiSlectAll.addActionListener(this);
    	 jmiCopy.addActionListener(this);
    	 jmiPaster.addActionListener(this);
    	 jmiCut.addActionListener(this);
    	 
    	 setTitle("简单记事本");
    	 setSize(800,450);   //设置界面大小
    	 setLocation(300,100);
    	 setVisible(true);  //设置对象可见
    	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时终止程序的运行
     }
     
     public void actionPerformed(ActionEvent e){
    	String actionCommand=e.getActionCommand();
    	switch(actionCommand){
    		case "打开":	open();break;
    		case "保存":	save();break;
    		case "全选": selectAll();break;
    		case "剪切": jta.cut();break;
    		case "复制": jta.copy();break;
    		case "粘贴": jta.paste();break;
    		case "查找":	find();break;
    		case "另存为":saveAs();break;
    		case "关于":	help();break;
    		case "退出":	System.exit(0);break;
    		default:;;break;
    	}
     }
   
     // 打开文件
     private void open(){
    	 if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
    		 File file = jFileChooser.getSelectedFile();
    		 savePath = file;
    		 try{
	    		FileInputStream fin=new FileInputStream(file);//文件输入流
	    		BufferedInputStream in=new BufferedInputStream(fin);//连接成带缓冲区的输入流
	    		byte[] b=new byte[in.available()];//创建byte类型的数组
	    		in.read(b,0,b.length);//读取多个字节放入数组b中
	    		jta.setText(new String(b,0,b.length));
	    		in.close();//关闭输入流
	    		jlblStatus.setText(savePath+"    "+file.getName()+" is open");
    		 }catch(IOException ex){
	    		jlblStatus.setText("Error opening"+file.getName());
    		 }
    	 }
    	
     }
     
     // 保存
     private void save(){
    	 // 如果文件已经保存过了，就没必要再打开保存文件的窗口了
    	 if(savePath == null){
    		//如果选中的是“保存”对话框的“保存”按钮
        	 if(jFileChooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
        		 save(jFileChooser.getSelectedFile());//调用save方法传入文件路径
        	 }
    	 }else{
    		 save(savePath);
    	 }
    	
     }
     
     private void save(File file){
    	 savePath = file;
    	 
    	 try{
			 FileOutputStream fout = new FileOutputStream(savePath);
			 //连接成带缓冲区的输出流
	        byte[] b=(jta.getText()).getBytes(); //创建byte数组
	        fout.write(b,0,b.length);//向输出流中写入字节数组b
	        fout.close();//关闭输出流
	        jlblStatus.setText(savePath+"    "+file.getName()+" save successfly");
        }catch(IOException ex){
        	jlblStatus.setText("Error saving"+file.getName());
        }
     }
     
     // 全选
     public void selectAll(){
    	 jta.selectAll();
     }
     
     // 另存为
     private void saveAs(){
    	//如果选中的是“保存”对话框的“保存”按钮
    	 if(jFileChooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
    		 save(jFileChooser.getSelectedFile());//调用save方法传入文件路径
    	 }
     }
     
     // 显示查找窗口
     public void find(){
    	JFrame frame = new JFrame("查找");	// new 一个容器，传入标题
    	JPanel panel_field,panel_btn;
    	JButton find_btn,update_btn;
    	JTextField field;
 		Container winContent = frame.getContentPane(); // 获取容器句柄
 		
 		panel_btn = new JPanel(new GridLayout(1,2));
 		find_btn = new JButton("查找");
 		update_btn = new JButton("替换");
 		panel_btn.add(find_btn);
 		panel_btn.add(update_btn);
 		
 		// 初始化显示框
 		panel_field = new JPanel(new GridLayout(1,1));
 		field = new JTextField(50);
 		field.setFont(new Font("宋体", Font.PLAIN, 25) );
 		field.setEditable(true);
 		panel_field.add(field);
 		panel_field.setPreferredSize(new Dimension(0, 50));
 		
 		// 显示把容器在窗口中显示
 		winContent.add(panel_btn, BorderLayout.CENTER);
 		winContent.add(panel_field, BorderLayout.NORTH);

 		frame.setSize(370,130);
 		frame.setLocation(450,200); // 设置计算器在屏幕中的显示位置
 		frame.setResizable(false); // 固定窗口大小
 		frame.setVisible(true);
     }
     
     // 帮助
     public void help(){
    	 JOptionPane.showMessageDialog(this, 
					"该记事本目前只提供一些简单的功能！"
					,"帮助信息"
					,JOptionPane.INFORMATION_MESSAGE);
     }
     
     public static void main(String[] args) {
    	 // 实例化
    	 notepad frame=new notepad();
	}

}

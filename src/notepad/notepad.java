package notepad;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.text.SimpleDateFormat;

public class notepad  extends JFrame implements ActionListener{
     JMenuItem jmiOpen,jmiSave,jmiExit,jmiAbout,jmiSaveAs;
     JMenuItem jmiFind,jmiSlectAll,jmiCut,jmiCopy,jmiPaster; // �༭
     
     JTextArea jta = new JTextArea();// ���±��༭��
     JLabel jlblStatus=new JLabel();
     JFileChooser jFileChooser=new JFileChooser();
     File savePath = null;
     
     public notepad(){
    	 JMenuBar mb=new JMenuBar();
    	 setJMenuBar(mb);
    	 JMenu fileMenu = new JMenu("�ļ�");
    	 mb.add(fileMenu);
    	 JMenu editMenu=new JMenu("�༭");
    	 mb.add(editMenu);
    	 JMenu helpMenu=new JMenu("����");
    	 mb.add(helpMenu);
    	 
    	 jta.setLineWrap(true);
    	 jta.setWrapStyleWord(true);
    	 fileMenu.add(jmiOpen=new JMenuItem("��"));
    	 // ���������ÿ�ݼ�
    	 jmiSave=new JMenuItem("����");
    	 jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	 fileMenu.add(jmiSave);
    	
    	 jmiSaveAs=new JMenuItem("���Ϊ");
    	 fileMenu.add(jmiSaveAs);
    
    	 fileMenu.addSeparator();
    	 fileMenu.add(jmiExit=new JMenuItem("�˳�"));
    	 
    	 
    	 // �༭���˵�
    	 jmiSlectAll=new JMenuItem("ȫѡ");
    	 jmiSlectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	 editMenu.add(jmiSlectAll);
    	 
    	 jmiCut=new JMenuItem("����");
    	 jmiCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	 editMenu.add(jmiCut);
    	 
    	 jmiCopy=new JMenuItem("����");
    	 jmiCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		 editMenu.add(jmiCopy);
		 
    	 jmiPaster=new JMenuItem("ճ��");
    	 jmiPaster.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		 editMenu.add(jmiPaster);
		 
    	 jmiFind=new JMenuItem("����");
		 jmiFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		 editMenu.add(jmiFind);

    	 
		 // �������˵�
    	 jmiAbout=new JMenuItem("����");
    	 jmiAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	 helpMenu.add(jmiAbout);
    	 jFileChooser.setCurrentDirectory(new File("."));
    	 getContentPane().add(new JScrollPane(jta),BorderLayout.CENTER);
    	 getContentPane().add(jlblStatus,BorderLayout.SOUTH);
    	 
    	 // ���ð�ť����
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
    	 
    	 setTitle("�򵥼��±�");
    	 setSize(800,450);   //���ý����С
    	 setLocation(300,100);
    	 setVisible(true);  //���ö���ɼ�
    	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //�رմ���ʱ��ֹ���������
     }
     
     public void actionPerformed(ActionEvent e){
    	String actionCommand=e.getActionCommand();
    	switch(actionCommand){
    		case "��":	open();break;
    		case "����":	save();break;
    		case "ȫѡ": selectAll();break;
    		case "����": jta.cut();break;
    		case "����": jta.copy();break;
    		case "ճ��": jta.paste();break;
    		case "����":	find();break;
    		case "���Ϊ":saveAs();break;
    		case "����":	help();break;
    		case "�˳�":	System.exit(0);break;
    		default:;;break;
    	}
     }
   
     // ���ļ�
     private void open(){
    	 if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
    		 File file = jFileChooser.getSelectedFile();
    		 savePath = file;
    		 try{
	    		FileInputStream fin=new FileInputStream(file);//�ļ�������
	    		BufferedInputStream in=new BufferedInputStream(fin);//���ӳɴ���������������
	    		byte[] b=new byte[in.available()];//����byte���͵�����
	    		in.read(b,0,b.length);//��ȡ����ֽڷ�������b��
	    		jta.setText(new String(b,0,b.length));
	    		in.close();//�ر�������
	    		jlblStatus.setText(savePath+"    "+file.getName()+" is open");
    		 }catch(IOException ex){
	    		jlblStatus.setText("Error opening"+file.getName());
    		 }
    	 }
    	
     }
     
     // ����
     private void save(){
    	 // ����ļ��Ѿ�������ˣ���û��Ҫ�ٴ򿪱����ļ��Ĵ�����
    	 if(savePath == null){
    		//���ѡ�е��ǡ����桱�Ի���ġ����桱��ť
        	 if(jFileChooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
        		 save(jFileChooser.getSelectedFile());//����save���������ļ�·��
        	 }
    	 }else{
    		 save(savePath);
    	 }
    	
     }
     
     private void save(File file){
    	 savePath = file;
    	 
    	 try{
			 FileOutputStream fout = new FileOutputStream(savePath);
			 //���ӳɴ��������������
	        byte[] b=(jta.getText()).getBytes(); //����byte����
	        fout.write(b,0,b.length);//���������д���ֽ�����b
	        fout.close();//�ر������
	        jlblStatus.setText(savePath+"    "+file.getName()+" save successfly");
        }catch(IOException ex){
        	jlblStatus.setText("Error saving"+file.getName());
        }
     }
     
     // ȫѡ
     public void selectAll(){
    	 jta.selectAll();
     }
     
     // ���Ϊ
     private void saveAs(){
    	//���ѡ�е��ǡ����桱�Ի���ġ����桱��ť
    	 if(jFileChooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
    		 save(jFileChooser.getSelectedFile());//����save���������ļ�·��
    	 }
     }
     
     // ��ʾ���Ҵ���
     public void find(){
    	JFrame frame = new JFrame("����");	// new һ���������������
    	JPanel panel_field,panel_btn;
    	JButton find_btn,update_btn;
    	JTextField field;
 		Container winContent = frame.getContentPane(); // ��ȡ�������
 		
 		panel_btn = new JPanel(new GridLayout(1,2));
 		find_btn = new JButton("����");
 		update_btn = new JButton("�滻");
 		panel_btn.add(find_btn);
 		panel_btn.add(update_btn);
 		
 		// ��ʼ����ʾ��
 		panel_field = new JPanel(new GridLayout(1,1));
 		field = new JTextField(50);
 		field.setFont(new Font("����", Font.PLAIN, 25) );
 		field.setEditable(true);
 		panel_field.add(field);
 		panel_field.setPreferredSize(new Dimension(0, 50));
 		
 		// ��ʾ�������ڴ�������ʾ
 		winContent.add(panel_btn, BorderLayout.CENTER);
 		winContent.add(panel_field, BorderLayout.NORTH);

 		frame.setSize(370,130);
 		frame.setLocation(450,200); // ���ü���������Ļ�е���ʾλ��
 		frame.setResizable(false); // �̶����ڴ�С
 		frame.setVisible(true);
     }
     
     // ����
     public void help(){
    	 JOptionPane.showMessageDialog(this, 
					"�ü��±�Ŀǰֻ�ṩһЩ�򵥵Ĺ��ܣ�"
					,"������Ϣ"
					,JOptionPane.INFORMATION_MESSAGE);
     }
     
     public static void main(String[] args) {
    	 // ʵ����
    	 notepad frame=new notepad();
	}

}

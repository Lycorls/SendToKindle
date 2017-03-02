package fileslist;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ListFiles extends JFrame {
	
	private static final long serialVersionUID = -4060711742299578672L;

	private static Vector<String> filelist = new Vector<String>();
	
	
    public static final int BOARD_WIDTH = 540;
    public static final int BOARD_HEIGHT = 570;
    public static final int X_LOCATION = 100;
    public static final int Y_LOCATION = 50;
    
    
    //�ѽ�Ŀ��Ϊ��������Panel
    JPanel northPanel = new JPanel();    
    JPanel centerPanel = new JPanel();
    JPanel southPanel = new JPanel();   
       
    //����
    JTextField pathField = new JTextField(30);
    JLabel pathLabel = new JLabel("�ļ���·��: ", JLabel.RIGHT);   
    JButton chosepathButton = new JButton("ѡ��·��");
    //���
    JTextArea fileListArea = new JTextArea(24, 40);
    JLabel fileListLabel = new JLabel("�ļ��ṹ");    
    //��ť
    JButton startButton = new JButton("�г��ļ��ṹ");
    JButton cancelButton = new JButton("ȡ��");    
    
    public void launchFrame() throws Exception{        
        //�����ʼ��    	
    	
        this.setLocation(X_LOCATION, Y_LOCATION);
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new FlowLayout()); 
        northPanel.setSize(20, BOARD_WIDTH);
        this.add(northPanel);
        centerPanel.setSize(600, BOARD_WIDTH);
        this.add(centerPanel);
        southPanel.setSize(20, BOARD_WIDTH);
        this.add(southPanel);     
        
        
        northPanel.add(pathLabel);
        northPanel.add(pathField);
        northPanel.add(chosepathButton);
        centerPanel.setLayout(new FlowLayout());
        centerPanel.add(fileListLabel);
        centerPanel.add(new JScrollPane(fileListArea));
        southPanel.setLayout(new FlowLayout());
        southPanel.add(startButton);
        southPanel.add(cancelButton);

        
        chosepathButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFileChooser jfc = new JFileChooser();  
        		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );  
        		jfc.showDialog(new JLabel(), "ѡ��");  
        		
        		File file=jfc.getSelectedFile();  
        		String path = file.getAbsolutePath();
        		pathField.setText(path);
        	}
        });
    
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

            	final String path = pathField.getText();
            	
            	southPanel.setCursor(new Cursor (Cursor.WAIT_CURSOR));
            	getFiles(path);               
            	southPanel.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
            	
                fileListArea.setText("");
                if(filelist.size() != 0){
	                for (Iterator<String> iterator = filelist.iterator(); iterator
							.hasNext();) {
						fileListArea.append(iterator.next() + "\r\n");					
					}                
	                filelist.clear();
                }else{
                	fileListArea.append("���ļ���Ϊ��");	
                }
                
            }
        });   
        
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileListArea.setText("");
                filelist.removeAll(filelist);
            }
        });         
    }
    

	private static int indent = 0;
	static void getFiles(String filePath){
		File root = new File(filePath);
		if(false == root.exists()){
//			System.out.println("�ļ��в�����");
			filelist.add("�ļ��в�����");
			return ;
		}
		
		File[] files = root.listFiles();
		if(files == null){
			return;
		}

		for(File file:files){     
			if(file.isDirectory()){
				
				StringBuilder name = new StringBuilder(indent);
				for (int i = 0; i < indent; i++) {
				name.append('-');
				}
				indent += 4;
				
//				System.out.println(name + file.getName());
				filelist.add(name + file.getName());  
				getFiles(file.getAbsolutePath());
				indent -= 4;
			}else{
				StringBuilder name = new StringBuilder(indent);
				for (int i = 0; i < indent; i++) {
					name.append('-');
				}
//				System.out.println(name + file.getName());
				filelist.add(name + file.getName());
			}     
		}
	}
    
    public static void main(String[] args)throws Exception {
          new ListFiles().launchFrame();
    }
    
}

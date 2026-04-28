import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;

class Tester extends Frame implements ActionListener{
	String currentFile = null;
	String previousText = "";

	TextArea ta1;
	MenuBar mb1;
	Menu file, edit;
	MenuItem newFile, exitIcon, openFile, saveFile, saveAsFile,cut, copy, paste,delete;
	Tester(){
		setSize(800,700);
		setTitle("Notepad-Clone");
		ta1 = new TextArea();
		ta1.setFont(new Font("Arial",Font.BOLD, 20));
		add(ta1);

		mb1 = new MenuBar();
		setMenuBar(mb1);

		file = new Menu("File");
		edit = new Menu("Edit");
	
		newFile = new MenuItem("New ", new MenuShortcut(KeyEvent.VK_N));
		openFile = new MenuItem("Open... ", new MenuShortcut(KeyEvent.VK_O));
		saveFile = new MenuItem("Save ", new MenuShortcut(KeyEvent.VK_S));
		saveAsFile = new MenuItem("Save As... ");
		exitIcon = new MenuItem("Exit ");
		
		file.add(newFile);
		file.add(openFile);
		file.add(saveFile);
		file.add(saveAsFile);
		file.addSeparator();
		file.add(exitIcon);
		
		cut = new MenuItem("Cut 	",new MenuShortcut(KeyEvent.VK_X));
		copy = new MenuItem("Copy  	",new MenuShortcut(KeyEvent.VK_C));
		paste = new MenuItem("Paste 	",new MenuShortcut(KeyEvent.VK_V));
		delete = new MenuItem("Delete 	");

		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(delete);
		
		mb1.add(file);
		mb1.add(edit);
		
		
		newFile.addActionListener(this);
		openFile.addActionListener(this);
		saveFile.addActionListener(this);
		saveAsFile.addActionListener(this);
		exitIcon.addActionListener(this);
		
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		delete.addActionListener(this);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == newFile){
			ta1.setText("");
		}
		else if( e.getSource() == openFile){
			FileDialog fd = new FileDialog(this, "Open File", FileDialog.LOAD);
			fd.setVisible(true);

			String fileName = fd.getFile();
			String dir = fd.getDirectory();
			
			if(fileName != null){
				try{
					FileReader fr = new FileReader(dir+ fileName);
					ta1.setText("");
					
					int ch;
					
					while((ch = fr.read()) != -1){
						ta1.append(String.valueOf((char)ch));
					}
					fr.close();
				}
				catch(Exception ex){
					System.out.println(ex);
				}
			}
		}
		
		else if( e.getSource() == saveFile){
			if(currentFile != null){
				try{
					FileWriter fw = new FileWriter(currentFile);
					fw.write(ta1.getText());
					fw.close();
				}
				catch(Exception ex){
					System.out.println(ex);
				}
			}
			else{
			
				FileDialog fd = new FileDialog(this,"Save File", FileDialog.SAVE);
				fd.setVisible(true);
		
				String fileName = fd.getFile();
				String dir = fd.getDirectory();

				if( fileName != null){
					currentFile = dir + fileName;
					try{
						FileWriter fw = new FileWriter(currentFile);
						fw.write(ta1.getText());
						fw.close();
					}
					catch(Exception ex){
						System.out.println(ex);
					}
				}
			}
		}
		else if( e.getSource() == saveAsFile){
			FileDialog fd = new FileDialog(this,"Save As ", FileDialog.SAVE);
			fd.setVisible(true);
			
			String fileName = fd.getFile();
			String dir = fd.getDirectory();

			if(fileName != null){
				currentFile = dir + fileName;
				try{
					FileWriter fw = new FileWriter(currentFile);
					fw.write(ta1.getText());
					fw.close();
				}
				catch(Exception ex){
					System.out.println(ex);
				}
			}
		}
		else if( e.getSource() == exitIcon){
			System.exit(0);
		}
		else if( e.getSource() == cut){
			String selectedText = ta1.getSelectedText();
			
			if(selectedText != null){
				StringSelection ss = new StringSelection(selectedText);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);

				ta1.replaceRange("",ta1.getSelectionStart(), ta1.getSelectionEnd());
			} 
		}
		else if( e.getSource() == copy){
			String selectedText = ta1.getSelectedText();
			
			if(selectedText != null){
				StringSelection ss = new StringSelection(selectedText);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
			}
		}
		else if( e.getSource() == paste){
			try{
		
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable t = cb.getContents(null);
	
				if(t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)){
						String text = (String) t.getTransferData(DataFlavor.stringFlavor);
						ta1.insert(text, ta1.getCaretPosition());
				}
			}
			catch(Exception ex){
				System.out.println(ex);
			}
		}
		else if( e.getSource() == delete){
			int start = ta1.getSelectionStart();
			int end = ta1.getSelectionEnd();
			
			String text = ta1.getText();

			if(start != end && end <= text.length()){
				ta1.replaceRange("", start, end);
			}
		}
	}	
		

	public static void main(String args[]){
		Tester t1 = new Tester();
	}
}

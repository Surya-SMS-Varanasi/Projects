import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Calculator extends JFrame implements ActionListener{
	
	JTextField screen;
	JPanel buttonPanel;
	String buttons[] = {
		"CE","C","<-","/",
		"9","8","7","*",
		"6","5","4","-",
		"3","2","1","+",
		"%","0",".","="
		};
	double num1 = 0, num2 = 0, result = 0;
	String operator = "";
	boolean isResultShown = false;
	Calculator(){
		getContentPane().setBackground(new Color(30,30,60));
		
		screen = new JTextField();
		screen.setFont(new Font("Verdana", Font.BOLD, 28));
		screen.setHorizontalAlignment(JTextField.RIGHT);
		screen.setEditable(false);
		screen.setBackground(new Color(20,20,40));
		screen.setForeground(Color.WHITE);

		add(screen, BorderLayout.NORTH);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5,4,10,10));
		buttonPanel.setBackground(new Color(30,30,60));
		for( String button : buttons){
			JButton btn = new JButton(button);
			btn.setFont(new Font("Verdana", Font.BOLD, 14));
			btn.setBackground(new Color(70,70,120));

			btn.setForeground(Color.WHITE);

			btn.addActionListener(this);
			buttonPanel.add(btn);
		}
		
		add(buttonPanel, BorderLayout.CENTER);
		
		setSize(400,500);
		setTitle("Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		String data = e.getActionCommand(); // it brings data from the textfeild with the help of event object
		if(data.matches("[0-9.]")){
			if(data.equals(".")){
				if(screen.getText().contains(".")){
					return;
				}
			}
			if(isResultShown){
				screen.setText("");
				isResultShown = false;
			}
			screen.setText(screen.getText() + data);
		}
		else if( data.equals("+") || data.equals("-") || data.equals("*") || data.equals("/") || data.equals("%")){
			if(!screen.getText().equals("")){//prevents from crash if screen is empty and user presses any operator
				if(operator.equals("")){
					num1 = Double.parseDouble(screen.getText());
				}
				else{
					num2 = Double.parseDouble(screen.getText());
				
					switch(operator){
						case "+" : num1 = num1 + num2; break;
						case "-" : num1 = num1 - num2; break;
						case "*" : num1 = num1 * num2; break;
						case "/" : if(num2 == 0 ){
								screen.setText("Error");
								operator = "";
								return;
							}
							else
								num1 = num1 / num2; break;
						case "%" : num1 = num1 % num2; break;	
						
					}
				}
				operator = data;
				screen.setText("");
			}
		}
		else if (data.equals("=")){
			if(!screen.getText().equals("")){
				num2 = Double.parseDouble(screen.getText());
			
				switch(operator){
					case "+" : result = num1 + num2; break;
					case "-" : result = num1 - num2; break;
					case "*" : result = num1 * num2; break;
					case "/" : if(num2 == 0 ){
							screen.setText("Error");
							operator = "";
							return;
						}
						else{
							result = num1 / num2; break;
						}
					case "%" : result = num1 % num2; break;
				}
				screen.setText(""+result);
				isResultShown = true;
				operator = "";
			}
		}
		else if(data.equals("C") || data.equals("CE")){
			screen.setText("");
			num1 = num2 = result = 0;
			operator ="";
			isResultShown = false;
		}
		else if(data.equals("<-")){
			String text = screen.getText();
			if(text.length() > 0){
					screen.setText(text.substring(0, text.length() -1));
			}
		}
	}
	
	public static void main(String args[]){
		new Calculator();
	}
}

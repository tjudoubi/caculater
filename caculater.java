package pojectwork;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.awt.event.ActionEvent;

public class caculate extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton Button2;
	private JButton Button3;
	private JButton Button4;
	private JButton Button5;
	private JButton Button6;
	private JButton Button7;
	private JButton Button8;
	private JButton Button9;
	private JButton Button0;
	private JButton Add;
	private JButton Sub;
	private JButton Mul;
	private JButton Div;
	private JButton Spot;
	private JButton Equal;
	private JButton Bracket_L;
	private JButton Bracket_R;
	private ArrayList<Struct> list = new ArrayList<Struct>();
	private String str = new String();
	private String ss = new String();
	private Stack<Float> stack_num = new Stack<Float>();
	private Stack<String> stack_sign = new Stack<String>();
	
	
	private static HashMap<String,Integer> Map = new HashMap<String,Integer>();
	private JTextField textField_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Map.put("+",0);
				Map.put("-",0);
				Map.put("*", 1);
				Map.put("/", 1);
				Map.put(")",-1);
				try {
					caculate frame = new caculate();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Float mov(Float x,Float y,String s){
		if(s.equals("+")){
			return x+y;
		}else if(s.equals("-")){
			return x-y;
		}else if(s.equals("*")){
			return x*y;
		}else if(s.equals("/")){
			return x/y;
		}else{
			return (float)0;
		}
		
	}
	
	
	public void init(){
		stack_num.clear();
		stack_sign.clear();
		list.clear();
		ss = "";
		str = textField.getText();
	}
	
	public void cacu(){
		while(!list.isEmpty()){
			int lk = list.size();
			Struct temp = list.get(lk-1);
			if(temp.flag == 0){
				stack_num.push(temp.num);
				list.remove(lk-1);
			}else{
				if(stack_sign.isEmpty()){
					stack_sign.push(temp.sign);
				}else{
					
					if(temp.sign.equals("(")){
						while(!stack_sign.peek().equals(")")){
							String peek_sign = stack_sign.pop();
							Float x = stack_num.pop();
							Float y = stack_num.pop();
							stack_num.push(mov(x,y,peek_sign));
						}
						stack_sign.pop();
					}else{
					
						String skr = stack_sign.peek();
						int a = Map.get(temp.sign);
						int b = Map.get(skr);
						if(a < b&&!temp.sign.equals(")")){
							Float x = stack_num.pop();
							Float y = stack_num.pop();
						
							stack_num.push(mov(x,y,skr));
						
							stack_sign.pop();
							stack_sign.push(temp.sign);
						}else{
							stack_sign.push(temp.sign);
						}
					}
					
				}
				list.remove(lk-1);
			}
		}
		System.out.println(stack_num.size());
		System.out.println(stack_sign.size());
		
		while(!stack_num.isEmpty()&&!stack_sign.isEmpty()){
			String peek_sign = stack_sign.pop();
			Float x = stack_num.pop();
			Float y = stack_num.pop();
			float p = mov(x,y,peek_sign);
			stack_num.push(mov(x,y,peek_sign));
		}
		textField.setText(stack_num.peek().toString());
		init();
	}
	
	public boolean bool(){
		if(str.length() == 0){
			return true;
		}else if(((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)=='.')){
			return true;
		}else{
			return false;
		}
	}
	
	public void func(String s){
		if(bool()){
			str += s;
			ss += s;
			textField.setText(str);
			textField_1.setText(ss);
		}else{
			str += s;
			Struct struct = new Struct(1,null,ss);
			System.out.println(ss);
			list.add(struct);
			ss = "";
			ss += s;
			textField.setText(str);
			textField_1.setText(ss);
		}
	}
	/**
	 * Create the frame.
	 */
	public caculate() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setBounds(10, 10, 473, 55);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton Button1 = new JButton("1");
		Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("1");
			}
		});
		Button1.setBounds(76, 112, 81, 41);
		contentPane.add(Button1);
		
		Button2 = new JButton("2");
		Button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("2");
			}
		});
		Button2.setBounds(157, 112, 81, 41);
		contentPane.add(Button2);
		
		Button3 = new JButton("3");
		Button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("3");
			}
		});
		Button3.setBounds(238, 112, 81, 41);
		contentPane.add(Button3);
		
		Button4 = new JButton("4");
		Button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("4");
			}
		});
		Button4.setBounds(76, 150, 81, 41);
		contentPane.add(Button4);
		
		Button5 = new JButton("5");
		Button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("5");
			}
		});
		Button5.setBounds(157, 150, 81, 41);
		contentPane.add(Button5);
		
		Button6 = new JButton("6");
		Button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("6");
			}
		});
		Button6.setBounds(238, 150, 81, 41);
		contentPane.add(Button6);
		
		Button7 = new JButton("7");
		Button7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("7");
			}
		});
		Button7.setBounds(76,191,81,41);
		contentPane.add(Button7);
		
		Button8 = new JButton("8");
		Button8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("8");
			}
		});
		Button8.setBounds(157,191,81,41);
		contentPane.add(Button8);
		
		Button9 = new JButton("9");
		Button9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("9");
			}
		});
		Button9.setBounds(238,191,81,41);
		contentPane.add(Button9);
		
		Button0 = new JButton("0");
		Button0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("0");
			}
		});
		Button0.setBounds(157,232,81,41);
		contentPane.add(Button0);
		
		Add = new JButton("+");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						Struct struct = new Struct(0,Float.valueOf(ss),null);
						list.add(struct);
					}
					str += "+";
					ss = "";
					ss += "+";
					textField.setText(str);
					textField_1.setText(ss);
				}
			}
		});
		Add.setBounds(329, 112, 81, 41);
		contentPane.add(Add);
		
		Sub = new JButton("-");
		Sub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(str.length() == 0||str.charAt(str.length()-1)=='('){
					if(str.length() != 0&&str.charAt(str.length()-1)=='('){
						Struct struct = new Struct(1,null,"(");
						list.add(struct);
						System.out.println("x");
					}
					Struct struct_2 = new Struct(0,(float)0,null);
					list.add(struct_2);
					System.out.println("x"+list.size());
					str += "-";
					
					ss = "";
					ss += "-";
					textField.setText(str);
					textField_1.setText(ss);
				}
				else if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						Struct struct = new Struct(0,Float.valueOf(ss),null);
						list.add(struct);
					}
					str += "-";
					ss = "";
					ss += "-";
					textField.setText(str);
					textField_1.setText(ss);
				}
			}
		});
		Sub.setBounds(329, 150, 81, 41);
		contentPane.add(Sub);
		
		Mul = new JButton("*");
		Mul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						Struct struct = new Struct(0,Float.valueOf(ss),null);
						list.add(struct);
					}
					str += "*";
					ss = "";
					ss += "*";
					textField.setText(str);
					textField_1.setText(ss);
				}
			}
		});
		Mul.setBounds(329, 191, 81, 41);
		contentPane.add(Mul);
		
		Div = new JButton("¡Â");
		Div.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						Struct struct = new Struct(0,Float.valueOf(ss),null);
						list.add(struct);
					}
					str += "/";
					ss = "";
					ss += "/";
					textField.setText(str);
					textField_1.setText(ss);
				}
			}
		});
		Div.setBounds(329, 232, 81, 41);
		contentPane.add(Div);
		
		Spot= new JButton(".");
		Spot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func(".");
			}
		});
		Spot.setBounds(76, 232, 81, 41);
		contentPane.add(Spot);
		
		Equal = new JButton("=");
		Equal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ss.charAt(ss.length()-1) <= '9'&&ss.charAt(ss.length()-1) >= '0'){
					Struct struct = new Struct(0,Float.valueOf(ss),null);
					list.add(struct);
				}else{
					Struct struct = new Struct(1,null,ss);
					list.add(struct);
				}
				System.out.println(list.size());
				
				cacu();
				
			}
		});
		Equal.setBounds(238,232,81,41);
		contentPane.add(Equal);
		
		Bracket_L = new JButton("(");
		Bracket_L.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("(");
			}
		});
		Bracket_L.setBounds(76, 75, 81, 41);
		contentPane.add(Bracket_L);
		
		Bracket_R = new JButton(")");
		Bracket_R.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0'){
					str += ")";
					Struct struct = new Struct(0,Float.valueOf(ss),null);
					list.add(struct);
					ss = "";
					ss += ")";
					textField.setText(str);
					textField_1.setText(ss);
				}
			}
		});
		Bracket_R.setBounds(157, 75, 81, 41);
		contentPane.add(Bracket_R);
		
		textField_1 = new JTextField();
		textField_1.setBounds(513, 10, 118, 29);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}
}

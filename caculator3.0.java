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




/////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////表达式输入方法:通过字符串ss来存储每一次的输入值，通过对 操作符号 和 数字 进行表达式输入
///////////////////////例如输入 123.334后，会在textField_1中显示出来并在ss中存储并附加在字符串str后于textField中显示，当继续输入 操作符号"+" 时，进行程序判别str的最后一个字符ascii码，将ss中存储的123.334存入类Struct形成的对象中，输入表达式
///////////////////////此时textField_1中存储的值为 操作符号 "+",在继续输入233，通过程序判别，将 "+"存入类Struct的对象中，存入表达式列表......
public class caculator extends JFrame {

	private JPanel Button_10x;
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
	private float epsilon = (float) 0.001;
	
	
	private static HashMap<String,Integer> Map = new HashMap<String,Integer>();
	private JTextField textField_1;
	private JButton Button_xy;
	private JButton Button_sin;
	private JButton Button_cos;
	private JButton Button_tan;
	private JButton Button_log;
	private JButton Button_exp;
	private JButton Button_sqrt;
	private JButton btnNewButton;
	private JButton Button_Mod;
	private JButton Button_factorial;
	private JButton Clean;
	private JButton Button_Madd;
	private JButton Button_Msub;
	private JButton Button_MR;
	private Float global = (float) 0;
	private Float global_temp = (float) 0;
	private JButton Back;
	private int Back_times = 0;
	private String back_number = "";
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
				Map.put("^",2);
				Map.put("l",3);
				Map.put("e",1);
				Map.put("c",3);
				Map.put("s",3);
				Map.put("t",3);
				Map.put("√",2);
				Map.put("m",1);
				Map.put("!",2);
				try {
					caculator frame = new caculator();
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
		}else if(s.equals("^")){
			return (float) Math.pow(x, y);
		}else if(s.equals("e")){
			float a = (float) Math.pow(10,y);
			return x*a;
		}else if(s.equals("m")){
			return x%y;
		}else if(s.equals("l")){
			return (float) Math.log10(y);
		}else if(s.equals("!")){
			float a = 1;
			for(int i = 1;i <= x;i++){
				a = a*i;
			}
			return a*y;
		}else if(s.equals("s")){
			return (float) Math.sin(y);
		}else if(s.equals("c")){
			return (float) Math.cos(y);
		}else if(s.equals("t")){
			return (float) Math.tan(y);
		}else if(s.equals("√")){
			return (float) Math.sqrt(y);
		}else{
			return (float)0;
		}
		
	}
	
	
	
	
	//////M+ M- = 操作的函数
	public void acquire(boolean f){
		if(ss.charAt(ss.length()-1) <= '9'&&ss.charAt(ss.length()-1) >= '0'){
			
			Struct struct = new Struct(0,Float.valueOf(ss),null);
			list.add(struct);
		}else{
			Struct struct = new Struct(1,null,ss);
			list.add(struct);
		}
		System.out.println(list.size());
		
		print();
		
		cacu(f);
	}
	
	
	
	public void init(){
		stack_num.clear();
		stack_sign.clear();
		list.clear();
		ss = "";
		str = textField.getText();
		Back_times = 0;
	}
	
	
	////////中缀式求值
	public void cacu(boolean s){
		System.out.println(list.size());
		print();
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
		
		
		while(!stack_num.isEmpty()&&!stack_sign.isEmpty()){
			String peek_sign = stack_sign.pop();
			
				Float x = stack_num.pop();
				Float y = stack_num.pop();
				float p = mov(x,y,peek_sign);
				stack_num.push(mov(x,y,peek_sign));
			
		}
		textField.setText(stack_num.peek().toString());
		if(s){
			global_temp = stack_num.peek();
		}
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
	
	public void funca(String s){
		Back_times = 0;
		back_number = "";
		if(bool()){
			ss += s;
			textField.setText(str);
			textField_1.setText(ss);
		}else{
			Struct struct = new Struct(1,null,ss);
			System.out.println(ss);
			list.add(struct);
			ss = "";
			ss += s;
			textField.setText(str);
			textField_1.setText(ss);
		}
	}
	
	public void func(String s){
		back_number = "";
		Back_times = 0;
		if(bool()){
			
			str += s;
			ss += s;
			textField.setText(str);
			textField_1.setText(ss);
		}else{
			str += s;
			
			
			Struct struct = new Struct(1,null,ss);
			if(!ss.equals("")){/////////此处一定不能少了if判断，用于应对back space 退格所造成的ss空串问题
				               ////////例如6+3 此处使用退格 变为6+ ，而对于原过程来世来说，6+后输入3，流程为
				               ////////输入3，判断str末尾字符(准确来说是子串)为 操作符+，此时ss同str末尾字符(子串)相同，将ss 也就是 操作符+ 录入表达式；而经过back space后
				               ////////6+3 退格变为 6+，再输入3，此时str末尾字符仍为操作符+，而不同的是经退格操作，表达式中已录入该操作符，而ss也为空串，所以不if会出错
				list.add(struct);
			}
			
			
			ss = "";
			ss += s;
			textField.setText(str);
			textField_1.setText(ss);
			print();
		}
	}
	/**
	 * Create the frame.
	 */
	public caculator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 652, 367);
		Button_10x = new JPanel();
		Button_10x.setBorder(new EmptyBorder(5, 5, 5, 5));
		Button_10x.setLayout(null);
		setContentPane(Button_10x);
		
		textField = new JTextField();
		textField.setBounds(10, 10, 485, 55);
		Button_10x.add(textField);
		textField.setColumns(10);
		
		JButton Button1 = new JButton("1");
		Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("1");
			}
		});
		Button1.setBounds(76, 112, 81, 41);
		Button_10x.add(Button1);
		
		Button2 = new JButton("2");
		Button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("2");
			}
		});
		Button2.setBounds(157, 112, 81, 41);
		Button_10x.add(Button2);
		
		Button3 = new JButton("3");
		Button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("3");
			}
		});
		Button3.setBounds(238, 112, 81, 41);
		Button_10x.add(Button3);
		
		Button4 = new JButton("4");
		Button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("4");
			}
		});
		Button4.setBounds(76, 150, 81, 41);
		Button_10x.add(Button4);
		
		Button5 = new JButton("5");
		Button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("5");
			}
		});
		Button5.setBounds(157, 150, 81, 41);
		Button_10x.add(Button5);
		
		Button6 = new JButton("6");
		Button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("6");
			}
		});
		Button6.setBounds(238, 150, 81, 41);
		Button_10x.add(Button6);
		
		Button7 = new JButton("7");
		Button7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("7");
			}
		});
		Button7.setBounds(76,191,81,41);
		Button_10x.add(Button7);
		
		Button8 = new JButton("8");
		Button8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("8");
			}
		});
		Button8.setBounds(157,191,81,41);
		Button_10x.add(Button8);
		
		Button9 = new JButton("9");
		Button9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("9");
			}
		});
		Button9.setBounds(238,191,81,41);
		Button_10x.add(Button9);
		
		Button0 = new JButton("0");
		Button0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				func("0");
			}
		});
		Button0.setBounds(157,232,81,41);
		Button_10x.add(Button0);
		
		Add = new JButton("+");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'||str.charAt(str.length()-1)=='!'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						if(!ss.equals("")){
							Struct struct = new Struct(0,Float.valueOf(ss),null);
							list.add(struct);
						}
					}
					str += "+";
					ss = "";
					ss += "+";
					textField.setText(str);
					textField_1.setText(ss);
				}
				print();
			}
		});
		Add.setBounds(329, 112, 81, 41);
		Button_10x.add(Add);
		
		Sub = new JButton("-");///////不能只考虑减法操作，还要考虑负数的输入问题，可将负数转化为表达式，比如 "-12" 转化为 "0-12"
		Sub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
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
				else if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'||str.charAt(str.length()-1)=='!'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						if(!ss.equals("")){
							Struct struct = new Struct(0,Float.valueOf(ss),null);
							list.add(struct);
						}
					}
					str += "-";
					ss = "";
					ss += "-";
					textField.setText(str);
					textField_1.setText(ss);
				}
				print();
			}
		});
		Sub.setBounds(329, 150, 81, 41);
		Button_10x.add(Sub);
		
		Mul = new JButton("*");
		Mul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'||str.charAt(str.length()-1)=='!'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						if(!ss.equals("")){
							Struct struct = new Struct(0,Float.valueOf(ss),null);
							list.add(struct);
						}
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
		Button_10x.add(Mul);
		
		Div = new JButton("÷");
		Div.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'||str.charAt(str.length()-1)=='!'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						if(!ss.equals("")){
							Struct struct = new Struct(0,Float.valueOf(ss),null);
							list.add(struct);
						}
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
		Button_10x.add(Div);
		
		Spot= new JButton(".");
		Spot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Back_times = 0;
				func(".");
			}
		});
		Spot.setBounds(76, 232, 81, 41);
		Button_10x.add(Spot);
		
		Equal = new JButton("=");
		Equal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				acquire(false);
				
			}
		});
		Equal.setBounds(238,232,81,41);
		Button_10x.add(Equal);
		
		Bracket_L = new JButton("(");
		Bracket_L.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				func("(");
			}
		});
		Bracket_L.setBounds(76, 75, 81, 41);
		Button_10x.add(Bracket_L);
		
		Bracket_R = new JButton(")");
		Bracket_R.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)=='!'){
					str += ")";
					if(!ss.equals("")){
						Struct struct = new Struct(0,Float.valueOf(ss),null);
						list.add(struct);
					}
					ss = "";
					ss += ")";
					textField.setText(str);
					textField_1.setText(ss);
				}
			}
		});
		Bracket_R.setBounds(157, 75, 81, 41);
		Button_10x.add(Bracket_R);
		
		textField_1 = new JTextField();
		textField_1.setBounds(513, 10, 118, 29);
		Button_10x.add(textField_1);
		textField_1.setColumns(10);
		
		
		
		JButton Button_x2 = new JButton("X^2");
		Button_x2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						if(!ss.equals("")){
							Struct struct = new Struct(0,Float.valueOf(ss),null);
							list.add(struct);
						}
					}
					str += "^2";
					ss = "";
					ss += "^";
					Struct struct = new Struct(1,null,"^");
					list.add(struct);
					ss = "";
					ss += "2";
					textField.setText(str);
					textField_1.setText(ss);
				}
				
			}
		});
		Button_x2.setBounds(238, 75, 81, 41);
		Button_10x.add(Button_x2);
		
		
		
		Button_xy = new JButton("X^y");
		Button_xy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						if(!ss.equals("")){
							Struct struct = new Struct(0,Float.valueOf(ss),null);
							list.add(struct);
						}
					}
					str += "^";
					ss = "";
					ss += "^";
					textField.setText(str);
					textField_1.setText(ss);
				}
			}
		});
		Button_xy.setBounds(329, 75, 81, 41);
		Button_10x.add(Button_xy);
		
		Button_sin = new JButton("sin");
		Button_sin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				funca("1");
				Struct struct = new Struct(0,(float) 1,null);
				list.add(struct);
				str += "s";
				ss = "";
				ss += "s";
				textField.setText(str);
				textField_1.setText(ss);
			}
		});
		Button_sin.setBounds(414, 75, 81, 41);
		Button_10x.add(Button_sin);
		
		Button_cos = new JButton("cos");
		Button_cos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				funca("1");
				Struct struct = new Struct(0,(float) 1,null);
				list.add(struct);
				str += "c";
				ss = "";
				ss += "c";
				textField.setText(str);
				textField_1.setText(ss);
			}
		});
		Button_cos.setBounds(414, 112, 81, 41);
		Button_10x.add(Button_cos);
		
		Button_tan = new JButton("tan");
		Button_tan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				funca("1");
				Struct struct = new Struct(0,(float) 1,null);
				list.add(struct);
				str += "t";
				ss = "";
				ss += "t";
				textField.setText(str);
				textField_1.setText(ss);
			}
		});
		Button_tan.setBounds(414, 150, 81, 41);
		Button_10x.add(Button_tan);
		
		Button_log = new JButton("log");
		Button_log.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
					funca("1");
					Struct struct = new Struct(0,(float) 1,null);
					list.add(struct);
					str += "l";
					ss = "";
					ss += "l";
					textField.setText(str);
					textField_1.setText(ss);
					
			}
		});
		Button_log.setBounds(414, 191, 80, 41);
		Button_10x.add(Button_log);
		
		Button_exp = new JButton("exp");
		Button_exp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Back_times = 0;back_number = "";
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						if(!ss.equals("")){
							Struct struct = new Struct(0,Float.valueOf(ss),null);
							list.add(struct);
						}
					}
					str += "e";
					ss = "";
					ss += "e";
					textField.setText(str);
					textField_1.setText(ss);
				}
			}
		});
		Button_exp.setBounds(414, 232, 81, 41);
		Button_10x.add(Button_exp);
		
		Button_sqrt = new JButton("√");
		Button_sqrt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Back_times = 0;back_number = "";
				funca("1");
				Struct struct = new Struct(0,(float) 1,null);
				list.add(struct);
				str += "√";
				ss = "";
				ss += "√";
				textField.setText(str);
				textField_1.setText(ss);
			}
		});
		Button_sqrt.setBounds(500, 75, 81, 41);
		Button_10x.add(Button_sqrt);
		
		btnNewButton = new JButton("10^x");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Back_times = 0;back_number = "";
				func("1");
				func("0");
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						if(!ss.equals("")){
							Struct struct = new Struct(0,Float.valueOf(ss),null);
							list.add(struct);
						}
					}
					str += "^";
					ss = "";
					ss = "";
					ss += "^";
					textField.setText(str);
					textField_1.setText(ss);
				}
			}
		});
		btnNewButton.setBounds(500, 112, 81, 41);
		Button_10x.add(btnNewButton);
		
		Button_Mod = new JButton("Mod");
		Button_Mod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Back_times = 0;back_number = "";
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						if(!ss.equals("")){
							Struct struct = new Struct(0,Float.valueOf(ss),null);
							list.add(struct);
						}
					}
					str += "m";
					ss = "";
					ss += "m";
					textField.setText(str);
					textField_1.setText(ss);
				}
			}
		});
		Button_Mod.setBounds(500, 150, 81, 41);
		Button_10x.add(Button_Mod);
		
		
		
		
		
		Button_factorial = new JButton("n!");///////将n!改编为二元运算符 n!1 ,使其效果相当于 (n!)*1
		Button_factorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Back_times = 0;back_number = "";
				if((str.charAt(str.length()-1)<='9'&&str.charAt(str.length()-1) >= '0')||str.charAt(str.length()-1)==')'){
					if(str.charAt(str.length()-1)==')'){
						Struct struct = new Struct(1,null,")");
						list.add(struct);
					}else{
						if(!ss.equals("")){
							Struct struct = new Struct(0,Float.valueOf(ss),null);
							list.add(struct);
						}
					}
					str += "!";
					ss = "";
					ss += "!";
					Struct struct = new Struct(1,null,"!");
					list.add(struct);
					ss = "";
					ss += "1";
					textField.setText(str);
					textField_1.setText(ss);
					
				}
			}
		});
		Button_factorial.setBounds(500, 191, 81, 41);
		Button_10x.add(Button_factorial);
		
		Clean = new JButton("Clean");
		Clean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				init();
			}
		});
		Clean.setBounds(500, 232, 81, 41);
		Button_10x.add(Clean);
		
		Button_Madd = new JButton("M+");
		Button_Madd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				acquire(true);
				global += global_temp;
				global_temp = (float) 0;
			}
		});
		Button_Madd.setBounds(10, 75, 61, 78);
		Button_10x.add(Button_Madd);
		
		Button_Msub = new JButton("M-");
		Button_Msub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back_number = "";
				Back_times = 0;
				acquire(true);
				global -= global_temp;
				global_temp = (float) 0;
			}
		});
		Button_Msub.setBounds(10, 150, 61, 69);
		Button_10x.add(Button_Msub);
		
		Button_MR = new JButton("MR");
		Button_MR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(global.toString());
				global = (float) 0;
				global_temp = (float) 0;
			}
		});
		Button_MR.setBounds(10, 221, 61, 52);
		Button_10x.add(Button_MR);
		
		Back = new JButton("Back   space");
		Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ss = "";
				textField_1.setText("");
				System.out.println(str);
				if(str.charAt(str.length()-1)=='l'||str.charAt(str.length()-1)=='s'||str.charAt(str.length()-1)=='c'
						||str.charAt(str.length()-1)=='t'
						||str.charAt(str.length()-1)=='√'||str.charAt(str.length()-1)=='!'){
					back_number = "";
					if(Back_times == 0){
						list.remove(list.size()-1);
						str = str.substring(0,str.length()-1);
						textField.setText(str);
					}else if(Back_times > 0){
						list.remove(list.size()-1);
						list.remove(list.size()-1);
						str = str.substring(0,str.length()-1);
						textField.setText(str);
					}
					
					
					
					
					print();
					
					
					
				}else if((str.charAt(str.length()-1) <= '9'&& str.charAt(str.length()-1)>= '0')||str.charAt(str.length()-1) == '.'){
					if(str.charAt(str.length()-1) == '.'){
						back_number = ""+'.'+back_number;
					}else{
						back_number = ""+str.charAt(str.length()-1)+back_number;
						Float a = Float.valueOf(back_number);
						Float b = list.get(list.size()-1).num;
						if(b != null&&Math.abs(b - a) < epsilon){
							list.remove(list.size()-1);
						}
					}
					str = str.substring(0,str.length()-1);
					textField.setText(str);
					System.out.println("back_number: "+ Float.valueOf(back_number) + " :"+list.get(list.size()-1).num);
					print();
				}else{
					back_number = "";
					if(Back_times == 0){
						str = str.substring(0,str.length()-1);
						textField.setText(str);
					}else if(Back_times > 0){
						list.remove(list.size()-1);
						str = str.substring(0,str.length()-1);
						textField.setText(str);
					}
					
					print();
					
				}
				Back_times += 1;
			}
		});
		Back.setBounds(20, 283, 561, 35);
		Button_10x.add(Back);
		
		
	}
	
	public void print(){
		System.out.println(" ");
		System.out.println("asdasdasdasdasdas14356789786546789087654356789");
		for(int i = 0 ; i < list.size();i++){
			if(list.get(i).flag == 0){
				System.out.print(list.get(i).num);
			}else{
				System.out.print(list.get(i).sign);
			}
		}
		System.out.println(" ");
		System.out.println(list.size());
		System.out.println("sfafsfasdasdasdas14356789786546789087654356789");
		System.out.println(" ");
	}
}

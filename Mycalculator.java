/*
 * File Name: Mycalculator.java
 * Name: GU Bon Hyeok
 * Student ID: 20172601*/
import java.util.*;
class Stack
{
	int top;
	Object[] stack;
	
	public Stack(int initialCapacity) //Constructor
	{
		if(initialCapacity<1)
		{
			throw new IllegalArgumentException(" ");
		}
		stack=new Object[initialCapacity];
		top=-1;
	}
	/*
	 * Method: isEmpty
	 * Input: None
	 * Output: boolean
	 * Tmi: 스택이 비어있는지 확인한다.
	 */
	public boolean isEmpty()
	{
		return (top==-1);
	}
	
	/*
	 * Method: peek
	 * Input: None
	 * Output: stack[top]
	 * Tmi: 스택에서 가장 높은 곳에 있는 원소를 리턴한다.
	 */
	public Object peek()
	{
		return stack[top];
		
	}
	
	/*
	 * Method: push
	 * Input: theElement/Object
	 * Output: None
	 * Tmi: 원소를 입력 받으면 스택에 추가한다. 만약 공간이 없으면 공간을 확보 후 스택에 푸쉬한다.
	 */
	public void push(Object theElement)
	{
		if(top==stack.length-1) 
			ensureCapacity();
		stack[++top]=theElement;
	}
	
	/*
	 * Method: pop
	 * Input: None
	 * Output: stack[top]//Object
	 * Tmi: 스택의 탑 원소를 pop한다.
	 */
	public Object pop()
	{
		Object topElement=stack[top];
		stack[top--]=null;
		return topElement;
	}
	
	/*
	 * Method: ensureCapacity
	 * Input: None
	 * Output: None
	 * Tmi: 스택의 공간을 확장한다.
	 */
	private void ensureCapacity()
	{
		Object[] larger=new Object[stack.length*2];
		for(int index=0;index<stack.length;index++)
		{
			larger[index]=stack[index];
		}
		stack=larger;
	}
	
	/*
	 * Method: toString
	 * Input: None
	 * Output: result//String
	 * Tmi: 문자열을 생성하는 기능을 한다.
	 */
	public String toString()
	{
		if(isEmpty())
			return "emptyStack";
		String result="<stack:";
		for(int i=top;i>=top;i--)
		{
			result+=stack[i]+" ";
		}
		return result+">";
	}
	
	
	public Stack()
	{
		this(10);
	}
}

class Calculation
{
	/*
	 * Method: postfix
	 * Input: infixExp//String
	 * Output: postfixExp//String
	 * Tmi: infix수식을 받아 postfix로 바꾸는 기능을 한다.
	 */
	String postfix(String infixExp)
	{
		boolean eofNumber=false;
		String postfixExp=new String();
		Stack stk=new Stack();
		for(int i=0;i<infixExp.length();i++)
		{
			switch(infixExp.charAt(i))
			{
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '.':
				postfixExp=postfixExp.concat(infixExp.charAt(i)+"");
				eofNumber=true;
				break;
			case '(':
				if(eofNumber==true)
				{
					postfixExp=postfixExp.concat(" ");
					eofNumber=false;
				}
				stk.push('(');//(new Character('('));
				break;
			case ')':
				if(eofNumber==true)
				{
					postfixExp=postfixExp.concat(" ");
					eofNumber=false;
				}
				while(((Character)stk.peek()).charValue()!='(')
					postfixExp=postfixExp.concat(((Character)stk.pop()).toString());
				Object openParen=stk.pop();
				break;
			case '+':
			case '-':
			case '*':
			case '/':
				if(eofNumber==true)
				{
					postfixExp=postfixExp.concat(" ");
					eofNumber=false;
				}
				while(!stk.isEmpty()&&((Character)stk.peek()).charValue()!='('&& getOrder(infixExp.charAt(i)) >= getOrder(((Character)stk.peek()).charValue()))
				{
					postfixExp=postfixExp.concat(((Character)stk.pop()).toString());
					
				}
				stk.push(infixExp.charAt(i));//(new Character(infixExp.charAt(i)));
				break;
			}
		}
		if(eofNumber==true)
		{
			postfixExp=postfixExp.concat(" ");
			eofNumber=false;
		}
		while(!stk.isEmpty())
		{
			postfixExp=postfixExp.concat(((Character)stk.pop()).toString());
		}
		//System.out.println(postfixExp);
		return postfixExp;
	}
	
	/*
	 * Method: result
	 * Input: postfixExp
	 * Output: stack peek//Double
	 * Tmi: 후위식을 받고 값을 뽑아 연산하여  리턴한다. 
	 */
	Double result(String postfixExp)
	{
		//System.out.println(postfixExp);
		Double value, buffer;
		String temp=new String();
		Stack stk=new Stack();
		for(int i=0;i<postfixExp.length();i++)
		{
			switch(postfixExp.charAt(i))
			{
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '.':
				temp=temp.concat(postfixExp.charAt(i)+"");
				break;
			case ' ':
				stk.push(Double.parseDouble(temp));
				temp=new String();
				break;
			case '+':
				value=((Double)stk.pop()).doubleValue()+((Double)stk.pop()).doubleValue();
				stk.push(value);
				break;
			case '-':
				buffer=((Double)stk.pop()).doubleValue();
				value=((Double)stk.pop()).doubleValue()-buffer.doubleValue();
				stk.push(value);
				break;
			case '*':
				value=((Double)stk.pop()).doubleValue()*((Double)stk.pop()).doubleValue();
				stk.push(value);
				break;
			case '/':
				buffer=((Double)stk.pop()).doubleValue();
				if(buffer==0.0)
				{
					System.out.println("0으로 나눌 수 없습니다.");
				}
				value=((Double)stk.pop()).doubleValue()/buffer.doubleValue();
				stk.push(value);
				break;
			}
		}
		return (Double)stk.peek();
	}
	
	/*
	 * Mehod: getOrder
	 * Input: operator//op//char
	 * Output: order//int
	 * Tmi: 연산자를 입력받아 순서를 결정한다.
	 */
	int getOrder(char op)
	{
		int order=0;
		switch(op)
		{
		case '*':
		case '/':
			order=1;
			break;
		case '+':
		case '-':
			order=2;
			break;
		}
		return order;
	}
	
	/*
	 * Method: isBalanced
	 * Input: expression//String
	 * Output: boolean
	 * Tmi: 괄호의 수가 맞는지 판별한다.
	 */
	public boolean isBalanced(String expression)
	{
		Stack stk=new Stack();
		for(int i=0;i<expression.length();i++)
		{
			char ch=expression.charAt(i);
			if(ch=='(')
			{
				stk.push((Character)ch);
			}
			else if(ch==')')
			{
				if(stk.isEmpty()) return false;
				char parenthesesFromStack=((Character)stk.pop()).charValue();
				if(ch==')'&&parenthesesFromStack!='(')
					return false;
				
			}
		}
		return stk.isEmpty();
	}
	
}

class EmptyStackException extends RuntimeException
{
	public EmptyStackException()
	{
		super("the stack is empty");
	}
	public EmptyStackException(String message)
	{
		super(message);
	}
}
public class Mycalculator {

	
	public static void main(String[] args) {
		
		String input;
		
		
		Calculation c=new Calculation();
		Double result;
		String answer="Y";
		String proceedToCalculation;
		Scanner a=new Scanner(System.in);
		Scanner sc=new Scanner(System.in);
		System.out.println("======= MyCalculator ========");
		System.out.println("MyCalculator를 사용해주셔서 감사합니다.");
		while(true)
		{
			
			System.out.println("Infix로 수식을 입력하세요");
			input=sc.nextLine();
			input=input.replaceAll(" ","");
			if(!c.isBalanced(input))
			{
				System.out.println("괄호의 개수가 맞지 않습니다.");
				continue;
			}
			/**/
			try {
				String output=c.postfix(input);
				result=c.result(output);
				System.out.println("postfix로 변환: "+output);
				System.out.println("계산을 시작할까요? (Y/N)");
				proceedToCalculation=a.next();
				if(proceedToCalculation.equals("N")||proceedToCalculation.equals("n"))
				{
					break;
				}
				else
				{
					System.out.println("계산값: "+result);
				}
			} catch(Exception e) {
				System.out.println("입력값이 잘못되었습니다.");
			}
			
			System.out.println("계속하시겠습니까?");
			answer=a.next();
			if((answer.equals("N"))||(answer.equals("n")))
				break;
		}
		System.out.println("사용해주셔서 감사합니다.");
		System.out.println("프로그램을 종료합니다.");
		System.out.println("=============================");
		sc.close();
		a.close();
	}

}

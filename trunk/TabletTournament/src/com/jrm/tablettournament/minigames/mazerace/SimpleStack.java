package com.jrm.tablettournament.minigames.mazerace;

/*+----------------------------------------------------------------------
||
||  Class SimpleStack 
||
||         Author:  Ryan Courreges
||
||        Purpose:  This class is a simple stack operation class. It provides
||					push, pop, peek and isEmpty operations. It uses an
||					ArrayDeque object to represent the stack.
||
||  Inherits From:  None.
||
||     Interfaces:  None
||
|+-----------------------------------------------------------------------
||
||      Constants:  None.
||
|+-----------------------------------------------------------------------
||
||   Constructors:  SimpleStack()
||
||  Class Methods:  None.
||
||  Inst. Methods:  void     push()
||					E        pop()
||					E        peek()
||					boolean  isEmpty()
||
++-----------------------------------------------------------------------*/
import java.util.ArrayDeque;

public class SimpleStack<E> {

	// instance variables
	private ArrayDeque<E> stack;
	
	/*---------------------------------------------------------------------
    |  Method SimpleStack() (Constructor)
    |
    |  Purpose:  The constructor creates a new ArrayDeque object.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: New SimpleStack object is created.
    |
    |  Parameters:  None.
    |
    |  Returns:  A new SimpleStack object.
    *-------------------------------------------------------------------*/
	public SimpleStack(){
		
		stack = new ArrayDeque<E>();
		
	}//constructor
	
	/*---------------------------------------------------------------------
    |  Method push(E)
    |
    |  Purpose:  Pushes input variable to the top of the stack.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: Input is added to the top of the stack
    |
    |  Parameters:  E input - object to be added to the stack
    |
    |  Returns:  Nothing.
    *-------------------------------------------------------------------*/
	public void push(E input){
		
		stack.add(input);
		
	}//push()
	
	/*---------------------------------------------------------------------
    |  Method peek()
    |
    |  Purpose:  Returns the value currently at the top of the stack.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: Top object in the stack is returned.
    |
    |  Parameters:  None.
    |
    |  Returns:  Top item in the stack.
    *-------------------------------------------------------------------*/
	public E peek(){
		
		return stack.getLast();
		
	}//peek()
	
	/*---------------------------------------------------------------------
    |  Method pop()
    |
    |  Purpose:  Removes and returns the top item in the stack.
    |
    |  Pre-condition:  stack
    |
    |  Post-condition: Top item is removed from the stack and returned.
    |
    |  Parameters:  None.
    |
    |  Returns:  The top item of the stack.
    *-------------------------------------------------------------------*/
	public E pop(){
		if(!this.isEmpty()){
			return stack.removeLast();
		}
		else return null;
		
	}//pop()
	
	/*---------------------------------------------------------------------
    |  Method isEmpty()
    |
    |  Purpose:  Returns whether or not the stack is empty.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: Empty condition of the stack is returned.
    |
    |  Parameters:  None.
    |
    |  Returns:  Empty condition of the stack.
    *-------------------------------------------------------------------*/
	public boolean isEmpty(){
		
		return stack.isEmpty();
		
	}//isEmpty()
	
}//SimpleStack

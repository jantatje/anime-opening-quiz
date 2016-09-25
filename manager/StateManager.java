package main.manager;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import main.state.IState;

public class StateManager {
	
	public Map<String,IState> states = new HashMap<String,IState>();
	public Stack<IState> stack = new Stack<IState>();
	
	public void tick(){
		IState top = peek();
		top.tick();
	}
	public void render(Graphics g){
		IState top = peek();
		top.render(g);
	}
	
	public void add(String name, IState state) {
		states.put(name, state);
	}
	public IState peek(){
		IState state = stack.peek();
		return state;
		
	}
	public void push(String name) {
		IState state = states.get(name);
		stack.push(state);
		init(state);
		onEnter(state);
	}
	public void push(IState state) {
		stack.push(state);
		init(state);
		onEnter(state);
	}
	public void pop() {
		if(peek()!=null){
			onExit(peek());
			stack.pop();
		}
	}
	public void init(IState state){
		state.init();
	}
	public void onEnter(IState state){
		state.onEnter();
	}
	public void onExit(IState state){
		state.onExit();
	}
	public void dispose(IState state){
		state.dispose();
	}
}

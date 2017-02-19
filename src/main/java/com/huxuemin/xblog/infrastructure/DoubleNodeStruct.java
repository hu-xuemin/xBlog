package com.huxuemin.xblog.infrastructure;

public class DoubleNodeStruct<T> {
	private DoubleNodeStruct<T> prev = null;
	private DoubleNodeStruct<T> next = null;
	private T data = null;
	
	public DoubleNodeStruct(){
	}
	
	public DoubleNodeStruct(T t){
		data = t;
	}
	
	public T getNodeData(){
		return data;
	}
	
	public void setNodeData(T t){
		data = t;
	}
	
	public boolean hasPrevNode(){
		return prev != null;
	}
	
	public DoubleNodeStruct<T> getPrevNode(){
		return prev;
	}
	
	public void setPrevNode(DoubleNodeStruct<T> dn){
		prev = dn;
	}
	
	public boolean hasNextNode(){
		return next != null;
	}
	
	public DoubleNodeStruct<T> getNextNode(){
		return next;
	}
	
	public void setNextNode(DoubleNodeStruct<T> dn){
		next = dn;
	}
}

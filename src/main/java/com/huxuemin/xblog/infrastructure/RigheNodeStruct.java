package com.huxuemin.xblog.infrastructure;

public class RigheNodeStruct<T> {
	private RigheNodeStruct<T> next = null;
	private T data = null;
	
	public RigheNodeStruct(){
	}
	
	public RigheNodeStruct(T t){
		data = t;
	}
	
	public T getNodeData(){
		return data;
	}
	
	public void setNodeData(T t){
		data = t;
	}
	
	public boolean hasNextNode(){
		return next != null;
	}
	
	public RigheNodeStruct<T> getNextNode(){
		return next;
	}
	
	public void setNextNode(RigheNodeStruct<T> dn){
		next = dn;
	}
}

package com.github.binome.murderhobo.map;

public class Node extends Cell implements Comparable<Node>
{
	private Node parent;
	private int g = 0;
	private int f,h;
	private boolean closed;
	
	public Node(int x, int y, boolean passable)
	{
		super(x, y, Cell.TileType.EWALL);
		if (passable){
			this.setTileType(Cell.TileType.FLOOR);
		}
	}

	public Node getParent()
	{
		return parent;
	}

	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	public int getF()
	{
		return f;
	}

	public void calcF()
	{
		f = g + h;
	}

	public int getG()
	{
		return g;
	}

	public void setG(int g)
	{
		this.g = g;
	}

	public int getH()
	{
		return h;
	}

	public void setH(int h)
	{
		this.h = h;
	}

	@Override
	public int compareTo(Node n)
	{
		if (f < n.getF()) return -1;
		else if (f > n.getF()) return 1;
		else return 0; //Cause why not?
	}

	public void close(){
		closed = true;
	}
	
	public boolean isClosed(){
		return closed;
	}
}

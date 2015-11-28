package com.github.binome.murderhobo.map;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Pathfinder {
	private static Node start;
	private static Node end;

	private final static int LATERAL_COST = 10;
	private final static int DIAGONAL_COST = 14;

	private static PriorityQueue<Node> open;
	private static Node[][] grid;

	public static ArrayList<Cell> findPath(Cell s, Cell e, Cell[][] g) {
		start = new Node(s.getX(), s.getY());
		end = new Node(e.getX(), e.getY());

		//System.out.println(g.length + " " + g[0].length);
		grid = new Node[g.length][g[0].length];
		//System.out.println(grid.length + " " + grid[0].length);
		
		open = new PriorityQueue<Node>();
		for (int i = 0; i < g.length;i++){
			for (int j = 0; j < g[0].length;j++){
				grid[i][j]=new Node (i , j);
			}
		}

		ArrayList<Node> nodePath = findPathUsingAStar();
		ArrayList<Cell> path = new ArrayList<Cell>();
		for (int i = 0; i < nodePath.size(); i++) {
			path.add(i, nodePath.get(i));
		}
		return path;
		// System.out.println("The path is " + path);

	}

	private static ArrayList<Node> findPathUsingAStar() {
		open.add(start);
		Node current = null;

		for (int i = 0; i < grid.length;i++){
			for (int j = 0; j < grid[0].length;j++){
				Node newNode = new Node(i, j);
				grid[i][j] = newNode;
				newNode.setH(AStarHeuristic(newNode));
			}
		}
		//System.out.println(grid.length + " " + grid[0].length);
		
		while (!open.isEmpty()) {
			current = open.poll();

			//System.out.println(current + " x: " + current.getX() + " y: " + current.getY());
			current.close();
			if (current.passable) {
				if (current.getX() == end.getX() && current.getY() == end.getY())
					break;

				Node to;
				if (current.getX() >= 1) {
					to = grid[current.getX() - 1][current.getY()];
					// System.out.println(to + " x: " + to.getX() + " y: " +
					// to.getY());
					doCostCalc(current, to, current.getF() + LATERAL_COST);

					if (current.getY() >= 1) {
						to = grid[current.getX() - 1][current.getY() - 1];
						// System.out.println(to + " x: " + to.getX() + " y: " +
						// to.getY());
						doCostCalc(current, to, current.getF() + DIAGONAL_COST);

					}

					if (current.getY() + 1 < grid[0].length) {
						to = grid[current.getX() - 1][current.getY() + 1];
						// System.out.println(to + " x: " + to.getX() + " y: " +
						// to.getY());
						doCostCalc(current, to, current.getF() + DIAGONAL_COST);
					}
				}

				if (current.getY() >= 1) {
					to = grid[current.getX()][current.getY() - 1];
					// System.out.println(to + " x: " + to.getX() + " y: " +
					// to.getY());
					doCostCalc(current, to, current.getF() + LATERAL_COST);
				}

				if (current.getY() + 1 < grid[0].length) {
					to = grid[current.getX()][current.getY() + 1];
					// System.out.println(to + " x: " + to.getX() + " y: " +
					// to.getY());
					doCostCalc(current, to, current.getF() + LATERAL_COST);

				}

				if (current.getX() + 1 < grid.length) {
					to = grid[current.getX() + 1][current.getY()];
					// System.out.println(to + " x: " + to.getX() + " y: " +
					// to.getY());
					doCostCalc(current, to, current.getF() + LATERAL_COST);

					if (current.getY() - 1 >= 0) {
						to = grid[current.getX() + 1][current.getY() - 1];
						// System.out.println(to + " x: " + to.getX() + " y: " +
						// to.getY());
						doCostCalc(current, to, current.getF() + DIAGONAL_COST);
					}

					if (current.getY() + 1 < grid[0].length) {
						to = grid[current.getX() + 1][current.getY() + 1];
						// System.out.println(to + " x: " + to.getX() + " y: " +
						// to.getY());
						doCostCalc(current, to, current.getF() + DIAGONAL_COST);
					}
				}
			}
		}

		ArrayList<Node> path = new ArrayList<Node>();

		Node n = current;
		// System.out.println("End: " + n + " EndParent: " + n.getParent());
		while (n.getParent() != null) {
			path.add(n);
			n = n.getParent();
		}

		// System.out.println("Sending back path: " + path);
		return path;
	}

	private static void doCostCalc(Node current, Node to, int cost) {
		if (to.isClosed())
			return;

		int to_cost = cost + to.getH();

		boolean inOpen = open.contains(to);
		if (!inOpen || to_cost < to.getF()) {
			to.setG(cost);
			to.calcF();
			to.setParent(current);
			if (!inOpen) {
				open.add(to);
			}
		}
	}

	private static int AStarHeuristic(Cell c) {
		double dx = end.getX() - c.getX();
		double dy = end.getY() - c.getY();
		return (int) Math.sqrt((dx*dx) + (dy*dy));

	}
}

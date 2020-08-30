import java.util.*; 

public class eightPuzzle6
{
	/*
	NEED:
	- BREADTH FIRST SEARCH
	- DEPTH FIRST SEARCH
	- UNIFORM COST SEARCH
	- BEST-FIRST SEARCH
	- A* H(1)
	- A* H(2)
	*/
	
	public static int[] goalState = {1, 2, 3, 8, 0, 4, 7, 6, 5}; //Goal State
	public static Queue<Node> bfsQ = new LinkedList<Node>(); //BFS Queue
	public static int length = 0; //Length of the solution path
	public static int cost = 0; //Cost of the solution path
	public static int time = 0; //Number of nodes popped off the queue
	public static int maxSize = 0; //Space, size of the queue at its max size
	public static int flucSize = 0; //Size that fluctuates
	public static Queue<Node> path = new LinkedList<Node>(); //finds all nodes until it finds the goal
	public static Stack<Node> correctPath = new Stack<Node>(); //finds the correct path from the start to goal
	public static Queue<Node> explored = new LinkedList<Node>(); 
	
	public static class Node{
		//Node class with bookkeeping 
		public int[] state;
		public Node parent = null; 
		public Queue<Node> child = new LinkedList<Node>(); 
		public int depth; 
		public int pathCost; 
		public String action; 
		public boolean beenTo; 
	}

	public static Node createRoot(int[] initialState){
		//this creates the root node by passing the original board state
		Node root = new Node(); 
		root.state = initialState; 
		root.parent = null; 
		root.depth = 0; 
		root.beenTo = true;
		
		return root; 
	}
	
	public static void printState(Node n)
	{
		//This function prints the board state of the eight puzzle
		for(int i = 0; i < n.state.length; i++)
		{
			System.out.print(n.state[i] + " ");
		}
		System.out.println(); 
	}
	
	public static void swap(int[] s, int z, int pos){
		//This function swaps the position of two integers in the array
		//Used for Operators such as moveRight, moveDown, etc. 
		int store = s[z];
		s[z] = s[pos];
		s[pos] = store; 
	}
	
	public static boolean canMoveRight(int [] s){
		// This function checks to see if the blank space can move right 1
		
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] == 0 && (i == 2 || i == 5 || i == 8)){
				return false;
			}
		}
		return true; 
	}
	
	public static boolean canMoveLeft(int [] s){
		// This function checks to see if the blank space can move left 1
		
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] == 0 && (i == 0 || i == 3 || i == 6)){
				return false;
			}
		}
		return true; 
	}
	
	public static boolean canMoveUp(int [] s){
		// This function checks to see if the blank space can move up 1
		
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] == 0 && (i == 0 || i == 1 || i == 2)){
				return false;
			}
		}
		return true; 
	}
	
	public static boolean canMoveDown(int [] s){
		// This function checks to see if the blank space can move down 1
		
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] == 0 && (i == 6 || i == 7 || i == 8)){
				return false;
			}
		}
		return true; 
	}

	public static void moveRight(int[] s)
	{
		//This function moves the position of the blank space right 1
		int tempPos = 0; 
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] == 0){
				tempPos = i; 
			}
		}
		swap(s, tempPos, tempPos + 1); 
	}
	
	public static void moveLeft(int[] s)
	{
		//This function moves the position of the blank space left 1
		int tempPos = 0; 
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] == 0){
				tempPos = i; 
			}
		}
		swap(s, tempPos, tempPos - 1); 
	}
	
	public static void moveUp(int[] s)
	{
		//This function moves the position of the blank space up 1
		int tempPos = 0; 
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] == 0){
				tempPos = i; 
			}
		}
		swap(s, tempPos, tempPos - 3); 
	}
	
	public static void moveDown(int[] s)
	{
		//This function moves the position of the blank space down 1
		int tempPos = 0; 
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] == 0){
				tempPos = i; 
			}
		}
		swap(s, tempPos, tempPos + 3); 
	}
	
	public static void generateChildren(Node n)
	{
		//This function generates the children of the node passed and adds to the Queue 
		if(canMoveLeft(n.state)){
			int storeArrayLeft[] = Arrays.copyOf(n.state, n.state.length);
			moveLeft(storeArrayLeft);
			Node newLeft = new Node();
			newLeft.state = storeArrayLeft; 
			newLeft.parent = n;
			n.child.add(newLeft); 
			newLeft.depth = n.depth + 1; 
			newLeft.action = "Left";
			newLeft.beenTo = false;
			if(!explored.contains(newLeft) || !bfsQ.contains(newLeft)){
				bfsQ.add(newLeft);
				flucSize += 1;
				if(flucSize > maxSize){
					maxSize = flucSize;
				}
			}
		}
		
		if(canMoveRight(n.state)){
			int storeArrayRight[] = Arrays.copyOf(n.state, n.state.length);
			moveRight(storeArrayRight);
			Node newRight = new Node();
			newRight.state = storeArrayRight; 
			newRight.parent = n;
			n.child.add(newRight); 
			newRight.depth = n.depth + 1; 
			newRight.action = "Right"; 
			newRight.beenTo = false;
			if(!explored.contains(newRight) || !bfsQ.contains(newRight)){
				bfsQ.add(newRight);
				flucSize += 1;
				if(flucSize > maxSize){
					maxSize = flucSize;
				}
			}
		}
		
		if(canMoveDown(n.state)){
			int storeArrayDown[] = Arrays.copyOf(n.state, n.state.length); 
			moveDown(storeArrayDown); 
			Node newDown = new Node(); 
			newDown.state = storeArrayDown;
			newDown.parent = n; 
			n.child.add(newDown);
			newDown.depth = n.depth + 1; 
			newDown.action = "Down"; 
			newDown.beenTo = false;
			if(!explored.contains(newDown) || !bfsQ.contains(newDown)){
				bfsQ.add(newDown);
				flucSize += 1;
				if(flucSize > maxSize){
					maxSize = flucSize;
				}
			}
		}
		
		if(canMoveUp(n.state)){
			int storeArrayUp[] = Arrays.copyOf(n.state, n.state.length); 
			moveUp(storeArrayUp);
			Node newUp = new Node(); 
			newUp.state = storeArrayUp; 
			newUp.parent = n; 
			n.child.add(newUp);
			newUp.depth = n.depth + 1;
			newUp.action = "Up"; 
			newUp.beenTo = false;
			if(!explored.contains(newUp) || !bfsQ.contains(newUp)){
				bfsQ.add(newUp);
				flucSize += 1;
				if(flucSize > maxSize){
					maxSize = flucSize;
				}
			}
		}
	}
	
	public static void printBFSQ()
	{
		//This function prints the states of the bfsQ 
		for(Node n: bfsQ)
		{
			printState(n); 
		} 
	}
	
	public static void printChildren(Node n)
	{
		//This function prints the children of a node n
		for(Node i : n.child)
		{
			printState(i);
		}
	}
	
	public static Stack<Node> bfs(Node root, int[] goal)
	{ 
		//This function performs a breadth first search until it finds the goalState 
		bfsQ.add(root);
		while(bfsQ.size() != 0)
		{
			Node temp_node = bfsQ.poll();
			time += 1; //number of nodes popped increased
			flucSize -= 1; //fluctuating size decreases by 1
			generateChildren(temp_node); 
			if(!bfsQ.contains(temp_node)){
				path.add(temp_node);
			}
			
			if(Arrays.equals(temp_node.state, goalState)){
				while(temp_node != null)
				{
					correctPath.push(temp_node);
					temp_node = temp_node.parent;
				} 
				return correctPath;
			}
			
			for(Node n : temp_node.child)
			{
				if(!bfsQ.contains(n)){
					path.add(n);
				}
			}
		}
		
		return null; 
	}
	
	public static void printStats(){
		//Prints out the stats of the search
		System.out.println("Length of the solution path: " + length); 
		System.out.println("Cost of the solution path: " + cost);
		System.out.println("Number of nodes popped off the queue: " + time);
		System.out.println("Size of the queue at its max: " + maxSize); 
	}
	
	public static void printPath(Stack<Node> n)
	{
		//This function prints the path from the start to end goal 
		while(!n.empty())
		{
			Node i = n.pop();
			length += 1; 
			printState(i);
		}
	}
	
	public static void resetStats(){
		//This function resets the stats to 0 
		length = 0;
		cost = 0;
		time = 0;
		maxSize = 0;
		flucSize = 0;
		path.clear();
		correctPath.clear();
		bfsQ.clear(); 
	}
	
	public static void main(String[] args){
		//BFS Easy
		int[] easy = {1, 3, 4, 8, 6, 2, 7, 0, 5};
		Node n = createRoot(easy); 
		printPath(bfs(n, goalState));
		printStats();

		System.out.println();
		resetStats();
		
		//BFS Medium
		int[] medium = {2, 8, 1, 0, 4, 3, 7, 6, 5};
		Node n2 = createRoot(medium);
		printPath(bfs(n2, goalState));
		printStats();

		System.out.println();
		resetStats();
		
		//BFS Hard
		int[] hard = {5, 6, 7, 4, 0, 8, 3, 2, 1};
		Node n3 = createRoot(hard);
		printPath(bfs(n3, goalState));
		printStats(); 
	}
	/*public static void main(String[] args){
		//Currently just making sure that goalState can be accessed and printed correctly 
		
		for(int i = 0; i < goalState.length; i++)
		{
			System.out.print(goalState[i] + " "); 
		}
		System.out.println();
		
		//Checking equals function
		int[] a = {1, 3, 6, 8, 0, 4, 5, 2, 7};
		int[] b = {1, 2, 3, 8, 0, 4, 7, 6, 5};
		System.out.println(Arrays.equals(a, goalState)); //Should return false
		System.out.println(Arrays.equals(b, goalState)); //Should return true
		
		//Checking that the swap function works 
		swap(a, 1, 7); 
		
		//Swaps integers 3 and 2: {1, 2, 6, 8, 0, 4, 5, 3, 7} 
		for(int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
		System.out.println();
		
		//Checking if operator booleans work
		System.out.println(canMoveRight(a)); //print true
		swap(a, 5, 4); // a = {1, 2, 6, 8, 4, 0, 5, 3, 7}
		for(int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
		System.out.println(); 
		System.out.println(canMoveRight(a)); //print false (i.e 0 is in position 2, 5, or 8)
		swap(a, 5, 4); 

		//Checking that the move functions work

		if(canMoveRight(a)){
			moveRight(a); 
		}

		//a = {1, 2, 6, 8, 4, 0, 5, 3, 7}
		for(int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
		System.out.println();
		
		if(canMoveLeft(a)){
			moveLeft(a);
		}
		
		//a = {1, 2, 6, 8, 0, 4, 5, 3, 7}
		for(int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
		System.out.println();
		
		if(canMoveUp(a)){
			moveUp(a);
		}
		
		//a = {1, 0, 6, 8, 2, 4, 5, 3, 7}
		for(int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
		System.out.println();
		
		if(canMoveDown(a)){
			moveDown(a);
		}
		
		//a = {1, 2, 6, 8, 0, 4, 5, 3, 7}
		for(int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
		System.out.println();

		//Queue test
		Queue<Integer> q = new LinkedList<Integer>(); 
		q.add(1);  
		q.add(2); 
		q.add(3);
		
		//1 2 3
		for(Integer i: q){
			System.out.print(i + " ");
		}
		
		System.out.println(); 
		
		//Test createRoot
		Node r = createRoot(a);
		System.out.println(r.beenTo); //true
		System.out.println(r.depth); //0 
		System.out.println(r.parent); //null
		printState(r); //1, 2, 6, 8, 0, 4, 5, 3, 7
		System.out.println(); 
		
		//Test print bfsQ
		bfsQ.add(r);
		printBFSQ(); //1, 2, 6, 8, 0, 4, 5, 3, 7
		System.out.println(); 
		
		//Test generateChildren
		generateChildren(r); 
		printBFSQ();

		//Test printChildren
		printChildren(r); 
	}*/
}
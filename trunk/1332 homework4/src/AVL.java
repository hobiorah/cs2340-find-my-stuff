import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class AVL<T extends Comparable<T>> {
	
	private Node<T> root;
	private Node<?> phantom;
	boolean is = false;

	private int size;
	
	/**
	 * Adds a data entry to the AVL tree
	 * 
	 * @param data The data entry to add
	 */
	public void add(T data) {
		
		root = trueAdd(root,data);
		
		rotate(root);
		size++;
		
	}
	
	private Node<T> trueAdd(Node<T> current, T data) {
		if(current!=null)
		{
			if(current.getData()!= null){//if currents data  isnt equal to null we arent dealing with infinite
				//so we proceed and as long as data isnt infinite, we can compare normally
				//when data is null go to right cause infinite
			if(data!= null && current.getData().compareTo(data) >0 ) 
			{
				
				current.setLeft(trueAdd(current.getLeft(),data));
				
			}else if((data!= null && current.getData().compareTo(data) <0 ))
			
			{
				
				current.setRight(trueAdd(current.getRight(),data));
				
			}else
			
			{
				
				current.setRight(trueAdd(current.getRight(),data));
			}
			
			} else//if data of current is infinte, automaticall set current left ot number since infinite always bigger
				
			{
				current.setLeft(trueAdd(current.getLeft(),data));
			}
			
		}else if(current == null)
		{
			current = new Node<T>(data);
			
		}
		
		updateHeightAndBF(current);
		
		if(current!=root){
		rotate(current);
		}
		
		
	return current;
	}
	/**
	 * Adds each data entry from the collection to this AVL tree
	 * 
	 * @param c The collection 
	 */
	public void addAll(Collection<? extends T> c) {
		for(T a: c){
			add(a);
		}
	}
	
	/**
	 * Removes a data entry from the AVL tree
	 * 
	 * Return null if the value does not exist
	 * 
	 * @param data The data entry to be removed
	 * @return The removed data entry
	 */
	public T remove(T data) {
		

		if(contains(data)){
			size--;
			root=trueRemove(root,data);
			updateHeightAndBF(root);
			rotate(root);
			return data;
		}else{
		return null;
		}
	}
	
	private Node<T> trueRemove(Node<T> current, T data) {
		if(current !=null)
		{
			if(data!=null){
			
			//finding the data, stops on data thats equal to desired data
			if(current.getData().compareTo(data) ==0  ){
				
				current = helpRemove(current);
			}
			else if(current.getData().compareTo(data) > 0 )
			{
				current.setLeft(trueRemove(current.getLeft(),data));
				
			}else if(current.getData().compareTo(data) < 0 )
			{
				
				current.setRight(trueRemove(current.getRight(),data));
			}
			} else{	
					if(current.getData()!=null){
						
						current.setRight(trueRemove(current.getRight(),data));
						
					}else{
						
						current = helpRemove(current);
					}
					
				}
			}
		if(current!=null){
		updateHeightAndBF(current);
		}
		if(current!=root && current!=null){
			rotate(current);
			}
		
		
		return current;
			}
		
		
	
		// find what node to replace rode to remove it
		private Node<T> helpRemove(Node<T> what){

			Node<T> startLeft =what;
			Node<T> parent = null;
			    //this is the node to be returned and will be set by node that called method
			//to get this node to be current so set null since no children and removing
			if(what.getLeft() ==null && what.getRight() ==null )
			{
					what = null;
			
			}
				
			
			//go to right, and go all the way to left but stop at one before the leaf
			//get leaf data and then set the one before leaf left to null
			
			else if(what.getLeft()!=null && what.getRight()!=null)
			{
				
				
				startLeft = what.getLeft();
				
				if(startLeft.getRight()!=null)
				{
				while(startLeft.getRight().getRight() !=null)
				{

					startLeft = startLeft.getRight();
				}
				parent = startLeft;
				
				
				what.setData(parent.getRight().getData());
				parent.setRight(parent.getRight().getLeft());
				
				}else//if we find that we go left and theres nothing to go to the right anymore, we set the node to remove to the left one and if has left, set the node to removes left to it
				{
				what.setData(startLeft.getData());
				what.setLeft(startLeft.getLeft());
				}
				
			} 
			else
			{
				if(what.getLeft()==null){
					what = what.getRight();
				} else{
					what = what.getLeft();
				}
				
			}
			return what;
			
		}
	/**
	 * Checks if the AVL tree contains a data entry
	 * 
	 * @param data The data entry to be checked
	 * @return If the data entry is in the AVL tree 
	 */
	public boolean contains(T data) {
		return realContains(root,data);
		}
	
	public boolean realContains(Node<T> current, T data){
		
		//boolean doesnt work here
			is = false;
		
		if(current !=null)
		{

			//finding the data, stops on data thats equal to desired data
			if(data!=null &&  current.getData()!= null && current.getData().compareTo(data) > 0 )
			{
				
				realContains(current.getLeft(),data);
			}else if(data!=null && current.getData()!= null && current.getData().compareTo(data) < 0)
			{
				realContains(current.getRight(),data);
			}
			else if(data!=null && current.getData()!= null && current.getData().compareTo(data) == 0)
			{
			 is = true;
			 
			}else if(current.getData()!=data){
				realContains(current.getRight(),data);
			}else if(current.getData()==data){
				is=true;
				
			}
		}
		return is;
	}
	
	
	/**
	 * Calculates the current height and balance factor for a node and updates the values
	 * 
	 * THIS DOES NOT RECURSIVELY UPDATE N AND ALL OF N'S CHILDREN, ONLY UPDATE N
	 * (caps because it's important! Don't kill the running time of everything!)
	 * 
	 * @param n The node whose values are to be calculated and updated
	 * @return The node passed in with updated values
	 */
	private Node<T> updateHeightAndBF(Node<T> n) {
		if(n!=null){
		n.setBf(getBalance(n));
		n.setHeight(height(n));
		}
		return n;
	}
	
	private int getBalance(Node<T> n){
		int balance = 0;
		int left = 0;
		int right = 0;
		if(n!=null){
			
	    left = height(n.getLeft());
	   right = height(n.getRight());
		}
		
		balance = left -right;
		return balance;
	}
	
	private int height(Node<T> n){
		if(n==null)
			return -1;
		else
			return Math.max(height(n.getLeft()), height(n.getRight())) +1;

	}


	
	/**
	 * Determines what rotation, if any, needs to be performed on a node and does the appropriate rotation
	 * 
	 * @param n The node to potentially be rotated
	 * @return The new root of the subtree that is now balanced due to the rotation 
	 * 			(possibly the same node that was passed in) 
	 */
	private Node<T> rotate(Node<T> n) {

		if(n!=null){
			
		//this cheks if its left heave 
				if(n.getBf() >1) 
				{
					updateHeightAndBF(n.getLeft());
					if(n.getLeft().getBf() >= 0){//and if its all the way left heavy
						right(n);
					}else{

						leftRight(n);
					}
				}else if(n.getBf() < -1)
				{//means we got right heay
					
					
						updateHeightAndBF(n.getRight());
			             
					
					if(n.getRight().getBf() <= 0){//and if its all the way right heavy
						
						left(n);
					}else{
						
						rightLeft(n); 
					}
			
				}
		}
		
		return n;
	}
	
	 

		
		
	/**
	 * Performs a left rotation on a node
	 * 
	 * @param n The node to have the left rotation performed on
	 * @return The new root of the subtree that is now balanced due to the rotation
	 */
	private Node<T> left(Node<T> n) {
		if(n!=root){
		//hold the unbalanced
		Node<T> leftO = n.getLeft();
		Node<T> orphan = n.getRight().getLeft();
		T old = n.getData();
		T newD = n.getRight().getData();
		
		n.setData(newD);
		n.getRight().setData(old);
		n.setLeft(n.getRight());
	    n.setRight(n.getRight().getRight());
	    n.getLeft().setRight(orphan);
		n.getLeft().setLeft(leftO);
		
		}else
		{

			Node<T> newHead= root.getRight();
			Node<T> orphan= newHead.getLeft();
			newHead.setLeft(root);
			root.setRight(orphan);
			root = newHead;
			n = root;
		}
		return n ;
	}
	
	/**
	 * Performs a right rotation on a node
	 * 
	 * @param n The node to have the right rotation performed on
	 * @return The new root of the subtree that is now balanced due to the rotation
	 */
	private Node<T> right(Node<T> n) {
		if(n!=root){
			Node<T> rightO = n.getRight();
			Node<T> orphan = n.getLeft().getRight();
			T old = n.getData();
			T newD = n.getLeft().getData();
			
			n.setData(newD);
			n.getLeft().setData(old);
			n.setRight(n.getLeft());
		    n.setLeft(n.getLeft().getLeft());
		    n.getRight().setRight(rightO);
			n.getRight().setLeft(orphan);
			n = n;
			
		

		}else{
			Node<T> orphan = root.getLeft().getRight();
			Node<T> newHead = root.getLeft();
			newHead.setRight(root);
			root.setLeft(orphan);
			root = newHead;

			n = root;
		}
		return n;
	}
	
	/**
	 * Performs a left right rotation on a node
	 * NOT DONE
	 * @param n The node to have the left right rotation performed on
	 * @return The new root of the subtree that is now balanced due to the rotation
	 */
	private Node<T> leftRight(Node<T> n) {
		if(n!=root){
			Node<T> orphan = n.getLeft().getRight().getLeft();//A
			Node<T> swap = n.getLeft().getRight();//9
			swap.setLeft(n.getLeft());//9 left is 8
			n.getLeft().setRight(orphan);
			n.setLeft(swap);//10 left is 9
		
		n = right(n);
	}else{
		Node<T> hold = root.getLeft();//8
		Node<T> orphan = root.getLeft().getRight().getLeft();//A
		root.setLeft(hold.getRight());//root left is 9
		root.getLeft().setLeft(hold);//9 left is 8 
		hold.setRight(orphan);
		n= right(root);
	}
		return n;
	}
	
	/**
	 * Performs a right left rotation on a node
	 * 
	 * @param n The node to have the right left rotation performed on
	 * @return The new root of the subtree that is now balanced due to the rotation
	 */
	private Node<T> rightLeft(Node<T> n) {
		if(n!=root){
		Node<T> orphan = n.getRight().getLeft().getRight();
		Node<T> swap = n.getRight().getLeft();
		swap.setRight(n.getRight());
		n.getRight().setLeft(orphan);
		n.setRight(swap);
		
		
		
		
		n= left(n);
		}else{
			Node<T> hold = root.getRight();
			Node<T> orphan = root.getRight().getLeft().getRight();
			root.setRight(hold.getLeft());
			root.getRight().setRight(hold);
			hold.setLeft(orphan);
			n= left(root);
		}
		return n;
	}
	
	/**
	 * Checks to see if the AVL tree is empty
	 * 
	 * @return If the AVL tree is empty or not
	 */
	public boolean isEmpty() {
		if (root!=null){
			return true;
		}else
			return false;
	}
	
	/**
	 * Clears this AVL tree
	 */
	public void clear() {
		size = 0;
		root=null;
	}
	
	public static void main(String[] args){
		
	}
	
	/*
	 * Getters and Setters: Do not modify anything below this point
	 */
	
	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}
	
	public int size() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public static class Node<K extends Comparable<K>> {
		
		private K data;
		private Node<K> left, right;
		private int height;
		private int bf;
		
		public Node(K data) {
			setData(data);
		}

		public K getData() {
			return data;
		}

		public void setData(K data) {
			this.data = data;
		}
		
		public Node<K> getLeft() {
			return left;
		}
		
		public void setLeft(Node<K> left) {
			this.left = left;
		}
		
		public Node<K> getRight() {
			return right;
		}
		
		public void setRight(Node<K> right) {
			this.right = right;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getBf() {
			return bf;
		}

		public void setBf(int bf) {
			this.bf = bf;
		}
	}
}

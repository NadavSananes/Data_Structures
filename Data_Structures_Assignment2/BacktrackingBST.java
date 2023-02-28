

import java.util.NoSuchElementException;

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    private BacktrackingBST.Node root = null;
    private int counter = 0; //counting the num of backtracking operations since the last operation of delete/ insert

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
    	if (root == null) {
    		throw new NoSuchElementException("empty tree has no root");
    	}
        return root;
    }
	
    public Node search(int k) {
    	if (root == null)
    		return null;
    	else if(root.getKey() == k)
			return root;
		else 
			return root.search(k);
    }

    public void insert(Node node) {
    	if (node == null)
    		throw new RuntimeException("can not insert null");
    	if (isEmpty()) {
    		root = node;
    		root.setParent(null);
    	}
    	else
    		root.insert(node);
    	
    	stack.push(node);  // used in backtracking.
    	stack.push(-1);
    	counter = 0;
    		
    }
    private boolean isEmpty() {
    	return root == null;
    	
    }

    
    public void delete(Node node) {
    	if (node == null)
    		throw new RuntimeException("can not remove null");
    	if(!isEmpty()){          
    		counter = 0;
    		stack.push(node);
    		Node parent = node.getParent();
    		
    		//we divide to three cases: the deleted node has- 1. no children 2. one child 3. 2 children
    		if(numberOfChildren(node) == 0) { //case num. 1
    			stack.push(0);
    			
    			if (parent == null)  // root
    				root = null;
    			else if(parent.left == node)
					parent.left = null;
    				
				else 
					parent.right = null;
    			
    		}
    		else if(numberOfChildren(node) == 1) { //case num. 2
    			stack.push(1);
    			Node son;
    			
    			if (node.right == null)  
    				son = node.left;
    			else 
    				son = node.right;
    
    			if(parent == null) {  // root
    				root = son;
    				root.setParent(null);
    			}
    			else if(parent.getKey() > node.getKey()) {  // node is the left son of his parent
    					parent.left = null;
    					insert(son);
    					stack.pop();
    					stack.pop();
				}
    				
				else {
					parent.right = null;
					insert(son);
					stack.pop();
					stack.pop();
				}
			}
    		else {  //case num. 3
    			Node suc = successor(node);
    			stack.push(suc.parent);
    			stack.push(2);
    			delete(suc);
    			stack.pop();  // undo the push that occur in the delete function.
    			stack.pop();
    			suc.left = node.left;
    			suc.right = node.right;
    			
    			if(suc.left != null)
    				suc.left.setParent(suc);
    			if(suc.right != null)
    				suc.right.setParent(suc);
    			
    			if(parent == null) {  // root
    				root = suc;
    				suc.setParent(null);
    			}
    			else {   // not root
    				suc.setParent(parent);
    				if(parent.getKey() < suc.getKey())
    					parent.right = suc;
    				else
    					parent.left = suc;
    		    }
    		}
    	}
    }
    
    private int numberOfChildren(Node node) { //checking the num. of children of a node
    	if(node.left == null & node.right == null)
    		return 0;
    	else if(node.left == null | node.right == null)
    		return 1;
    	else return 2;
    }
    
    public Node minimum() {
    	if(isEmpty())
    		throw new NoSuchElementException("the tree is empty");
    	else
    		return root.minimum();
    }

    public Node maximum() {
    	if(isEmpty())
    		throw new NoSuchElementException("the tree is empty");
    	else
    		return root.maximum();
    }

    public Node successor(Node node) {
    	Node suc = node.successor();
    	if(suc == null)
    		throw new NoSuchElementException();
    	else
    		return suc;
    }

    public Node predecessor(Node node) {
    	Node pred = node.predecessor();
    	if(pred == null)
    		throw new NoSuchElementException();
    	else
    		return pred;
    }
    
    @Override
    public void backtrack() {
    	if(!stack.isEmpty()) {
    		int count = counter;  // used for miscalculation of the counter.
    		int sign = (int) stack.pop();
    		
        	if(sign == 0) {  // first case- inserting back a deleted node with no children
        		Node toReturn = (Node)stack.pop();
        		redoStack.push(toReturn);
            	Node parent = toReturn.getParent();
        		if(parent == null) // toReturn was a root.
        			root = toReturn;
        		else {
        			if(parent.getKey() > toReturn.getKey())   // toReturn was the left child.
        				parent.left = toReturn;
        			
        			else                      	    		 // toReturn was the right child.
        				parent.right = toReturn;
        		}
        	}
        	else if (sign == 1) {  // second case- inserting back a deleted node with one child
        		Node toReturn = (Node)stack.pop();
        		Node parent = toReturn.getParent();
        		redoStack.push(toReturn);
        		if(parent == null) {   // root
        			root = toReturn;
        		}
        		else if(parent.getKey() > toReturn.getKey())  // node is the left son.
					parent.left = toReturn;
        	    else
    				parent.right = toReturn;		
        			
        		if(toReturn.left != null)      // update the parent of the node children that maybe has changed during the delete.
    				toReturn.left.setParent(toReturn);
    			if(toReturn.right != null)
    				toReturn.right.setParent(toReturn);
        		
        	}
        	else if(sign == 2) {  // third case- inserting back a deleted node with two children
        		Node sucParent = (Node)stack.pop();
        		Node toReturn = (Node)stack.pop();
        		Node parent = toReturn.getParent();
        		Node suc;
        		redoStack.push(toReturn);
        		
        		if(parent == null) {   // we deleted the root the last time.
        			suc = root;
        			root = toReturn;
        		}
        			
        		else {       // not the root.
        			if(parent.getKey() > toReturn.getKey()) {
        				suc = parent.left;
        				parent.left = toReturn;
        			}
        			else {
        				suc = parent.right;
        				parent.right = toReturn;
        			}
        		}
        		if (sucParent.key < suc.key) {  // in this case, suc was the right child of the deleted node
    				if(sucParent.right == null) { // if the suc was a leaf
    					sucParent.right = suc;
    					suc.setParent(sucParent);
    					suc.left = null;
    					suc.right = null;
    				}
    				else {   // there is a right son to the parent.
    					if(sucParent.right.key > suc.key) {                                    
    						suc.right = sucParent.right;
    						sucParent.right = suc;
    						suc.left = null;
    						
    						suc.right.setParent(suc);
    						suc.setParent(sucParent);
    					}
    					
    				}
    			}
    			else {  // suc was the left child before the delete.
    				if(sucParent.left == null) { // if the suc was a leaf.
    					sucParent.left = suc;
    					suc.setParent(sucParent);
    					suc.left = null;
    					suc.right = null;
    				}
    				else {   // there is a left son to the parent.
    					if(sucParent.left.key > suc.key) {
    						suc.right = sucParent.left;
    						sucParent.left = suc;
    						suc.left = null;
    						
    						suc.right.setParent(suc);
    						suc.setParent(sucParent);
    					}
    					
    				}
    			}
        		
        		if(toReturn.left != null)
    				toReturn.left.setParent(toReturn);
    			if(toReturn.right != null)
    				toReturn.right.setParent(toReturn);
        	}
        	else {   // return value is -1, case number 4.
        		Node toDelete = (Node)stack.pop();
        		redoStack.push(toDelete);
        		delete(toDelete); // undo the last insertion.
    			stack.pop();
    			stack.pop();
        	}
        	redoStack.push(sign);
        	counter = count + 1;
    		
    	}
    }
    
    @Override
    public void retrack() {
        if(!redoStack.isEmpty() & counter > 0) {
        	int count = counter;
        	int sign = (int)redoStack.pop();
        	if (sign == -1) {   // undo the backtracking of the last insert.
        		insert((Node)redoStack.pop());
        	}
        	else {  // undo the backtracking of the last delete.
        		delete((Node)redoStack.pop());
        	}
        	counter = count - 1;
        }
    }

    public void printPreOrder(){
        if(!isEmpty()) {
        	String str = "";
        	str = root.printPreOrder(str);
        	System.out.println(str.substring(0, str.length() - 1));
        }
    }

    @Override
    public void print() {
    	printPreOrder();
    }
    
    public static class Node {
    	// These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;
        
        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }


		public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
        public Node search(int k) {
        	if (k > key & right != null) {  // search in the right sub-tree
        		if(k == right.key)
        			return right;
        		return right.search(k);
        	}
        	if(k < key & left != null) {  // search in the left sub-tree
        		if(k == left.key)
        			return left;
        		return left.search(k);
        	}
        	return null; // did not found.
        }
        public void setParent(BacktrackingBST.Node node) {
        	parent = node;
        }
        public Node getParent() {
			return parent;
        }
        
        public void insert(BacktrackingBST.Node node) {
			if(node.key > key) {    // insert to the right sub-tree
				if(right == null) {
					right = node;
					right.setParent(this); 
					
				}
				else right.insert(node);
			}
			else {                  // insert to the left sub-tree
				if(left == null) {
					left = node;
					left.setParent(this);
				}
				else left.insert(node);
			}
		}
        
		
		
		public BacktrackingBST.Node minimum() {
			if(left == null) 
				return this;
			else
				return left.minimum();
		}

		public BacktrackingBST.Node maximum() {
			if(right == null) 
				return this;
			else
				return right.maximum();
		}
		
		public String printPreOrder(String str) {
			str = str + key + " ";
			if(left != null)
				str = left.printPreOrder(str);
			if(right != null)
				str = right.printPreOrder(str);
			return str;
		}
		
		public Node successor() {
	    	if(right != null)          // the node has right son.
	    		return right.minimum();
	    	else {                          
	    		if(getParent().left != null & getParent().left == this) // the node does not has right son and he himself is a left son.
	    			return getParent();   
	    		else {                           // the node does not has right son and he himself is a right son.
	    			Node suc = this;
	    			while(suc.getParent() != null) {
	    				if(suc.getParent().getKey() > suc.getKey())
	    					return suc.getParent();
	    				suc = suc.getParent();
	    			}
	    			return null;
	    		}
	    	}
	    }

	    public Node predecessor() {
	    	if(left != null)          // the node has left son.
	    		return left.maximum();
	    	else {                          
	    		if(getParent().right != null & getParent().right == this) // the node does not has left son and he himself is a right son.
	    			return getParent();   
	    		else {                           // the node does not has left son and he himself is a left son.
	    			Node pre = this;
	    			while(pre.getParent() != null) {
	    				if(pre.getParent().getKey() < pre.getKey())
	    					return pre.getParent();
	    				pre = pre.getParent();
	    			}
	    			return null;
	    		}
	    	}
	    }
    }
}

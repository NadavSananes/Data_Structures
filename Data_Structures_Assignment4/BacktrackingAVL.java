import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class BacktrackingAVL extends AVLTree {
    // For clarity only, this is the default ctor created implicitly.
    public BacktrackingAVL() {
        super();
    }

	//You are to implement the function Backtrack.
    public void Backtrack() {
    	if(!deque.isEmpty()) {
    		Node node = (Node)deque.pop();         // the node to perform undoInsertaion on.
    		Integer val = (Integer)deque.pop();		
    		undoInsertion(val, node);
    		if(val != 0) {                          // there was a rotation at the last insert.
    			Object obj = deque.pop();
    			if(obj instanceof Node) {           // there was only one rotation.
    				node = (Node)obj;
    				val = (Integer)deque.pop();
    				undoInsertion(val, node);       // delete the node that was inserted.     
    			}
    			else {                              // there were two rotation.
    				val = (Integer)obj;            
    				undoInsertion(val, node);
    				node = (Node)deque.pop();
    				val = (Integer)deque.pop();
    				undoInsertion(val, node);       // delete the node that was inserted.
    			}
    		}
    		
    		
    		
    	}
    }
    private void undoInsertion(Integer val, Node node) {
    	switch (val) {
		case 0:
			if(node.parent == null) // the case we delete the root.
    			root = null;
			else
				node.deleteLeaf(node);
			break;
		case 1:
			node.cancelLeftRotation(node);
			break;
			
		case 2:
			node.cancelRightRotation(node);
	break;

		default:
			break;
		}
    }
    
    //Change the list returned to a list of integers answering the requirements
    public static List<Integer> AVLTreeBacktrackingCounterExample() {
    	List<Integer> lst = new LinkedList<Integer>();
    	lst.add(8);
    	lst.add(9);
    	lst.add(30);
    	return lst;
    }
    
    public int Select(int index) {
    	if(root == null || index <= 0 || root.size < index)
    		throw new NoSuchElementException();
    	Node ans = SelectNode(root, index);
    	return ans.value;
    }
    private Node SelectNode(Node node, int index) {
    	int currSize = 1;
    	if(node.left != null)   
    		currSize = node.left.size + 1;
    	
    	if(currSize == index)
    			return node;
    	
    	else if(index < currSize & node.left != null)
    		return SelectNode(node.left, index);
    	
    	else return SelectNode(node.right, index - currSize);
    	
    }
    
    public int Rank(int value) {
        return Rank(root, value);
    }
    private int Rank(Node node, int value) {
    	if(node == null)
    		return 0;
    	
        if(node.value == value) {
        	if(node.left != null)
        		return node.left.size;
        	else
        		return 0;
        }
        else if(value > node.value) {
        	if(node.left != null)
        		return Rank(node.right, value) + node.left.size + 1;
        	else
        		return Rank(node.right, value) + 1;
        }
        else
        	return Rank(node.left, value);
        
        
        
    }
}

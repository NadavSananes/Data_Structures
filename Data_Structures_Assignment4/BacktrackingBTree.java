import java.util.List;
import java.util.LinkedList;

public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {
	// For clarity only, this is the default ctor created implicitly.
	public BacktrackingBTree() {
		super();
	}

	public BacktrackingBTree(int order) {
		super(order);
	}

	//You are to implement the function Backtrack.
	public void Backtrack() {
		if(!deque.isEmpty()) {
			T valToDelete = (T)deque.removeLast() ;
			Node<T> nodeToOperate = this.getNode(valToDelete) ;
			
			//deleting the value from the leaf
			nodeToOperate.removeKey(valToDelete); //the function also updates the numOfKeys
			size = size - 1 ;
			
			
			while (deque.peekLast() != (Object)false) {
				T movedToParentValue = (T)deque.removeLast() ;
				Node<T> parent = this.getNode(movedToParentValue) ;
				int index = parent.indexOf(movedToParentValue) ;
				Node<T> leftSon = parent.children[index] ;
				Node<T> rightSon = parent.children[index + 1] ;
				
				MergingSplitedNode(leftSon, rightSon, movedToParentValue) ;
			}
			deque.removeLast() ; //removing the false element from the deque
			
			if(this.size == 0)
				root = null ;
		}
		
	    
    }
	
	private void MergingSplitedNode(Node<T> leftSon, Node<T> rightSon, T movedToParentValue) {
		leftSon.keys[leftSon.numOfKeys] = movedToParentValue ;
		leftSon.numOfKeys = leftSon.numOfKeys + 1 ;
				
		int i = 0 ;
		while (i < rightSon.numOfKeys) {
			leftSon.keys[leftSon.numOfKeys] = rightSon.keys[i] ;
			leftSon.numOfKeys = leftSon.numOfKeys + 1 ;
				
			i = i + 1 ;
		}
		
		int j = 0 ;
		while (j < rightSon.numOfChildren) {
			leftSon.children[leftSon.numOfChildren] = rightSon.children[j] ;
			leftSon.numOfChildren = leftSon.numOfChildren + 1 ;
			rightSon.children[j].parent = leftSon ;
			
			j = j + 1 ;
		}
		
		leftSon.parent.removeKey(movedToParentValue) ;
		leftSon.parent.removeChild(rightSon) ;
		
		if(leftSon.parent == root & root.numOfKeys == 0) {
			root = leftSon ;
			leftSon.parent = null ;
		}
		
	}
	
	
	//Change the list returned to a list of integers answering the requirements
	public static List<Integer> BTreeBacktrackingCounterExample(){
	    List<Integer> lst = new LinkedList<Integer>();
    	lst.add(8);
    	lst.add(9);
    	lst.add(30);
    	lst.add(40);
    	lst.add(15);
    	lst.add(7);
    	return lst;
	}
}

import java.util.NoSuchElementException;

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    public int[] arr; // This field is public for grading purposes. By coding conventions and best practice it should be private.
    private int index;

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        index = 0 ;
    }
    
    @Override
    public Integer get(int index){
    	if (arr != null & index < this.index & index >=0) {
        	return arr[index] ;
        }
        throw new NoSuchElementException("there is no element belonging to the set in this index") ;
    }

    @Override
    public Integer search(int k) {
        return binarySearch(arr, k, 0, index - 1);
    }
    /**
     * Binary search.
     */
    private Integer binarySearch(int[] arr, int x, int left, int right) {
    	if (left > right)
    		return -1;
    	
    	int middle = (left + right)/2;
    
    	if (arr[middle] > x)
    		return binarySearch(arr, x, left, middle -1);
    	if (arr[middle] < x)
    			return binarySearch(arr, x, middle +1, right);
    		else 
    			return middle;
    }

    @Override
    public void insert(Integer x) {
    	if(arr == null | arr.length == 0 | index >= arr.length) {
    		throw new RuntimeException("array is null, full or empty") ;   		
    	}
    	
        int ind = 0 ;
        while (ind < index && arr[ind] < x) //looking for the right index which x will be inserted to
        	ind = ind + 1 ;
        for(int i = index; i > ind; i = i - 1) { //copying tha rest of the elements to create space
        	arr[i] = arr[i - 1] ; 
        }
        arr[ind] = x ;
        index = index + 1 ;
        stack.push(-ind) ; //saving the index of the inserted value for backtracking
    }

    @Override
    public void delete(Integer index) {
    	if(arr == null | arr.length == 0 | index < 0 | index >= this.index) {
    		throw new RuntimeException("there is no element belonging to the set in this index") ;   		
    	}
    	stack.push(arr[index]) ; //saving the deleted value for backtracking
    	stack.push(1) ; //a sign for backtracking
    	
        for(int i = index; i < this.index; i = i + 1) { //copying tha rest of the elements to reduce space
        	arr[i] = arr[i + 1] ;
        }
        
        this.index = this.index - 1 ;
                
    }

    @Override
    public Integer minimum() {
    	if(arr == null | arr.length == 0 | index <= 0) {
    		throw new NoSuchElementException("the array is empty") ;   		
    	}
    	return 0; // the array is sorted
    }

    @Override
    public Integer maximum() {
    	if(arr == null | arr.length == 0 | index <= 0) {
    		throw new NoSuchElementException("the array is empty") ;   		
    	}
    	return index - 1; // the array is sorted
    }

    @Override
    public Integer successor(Integer index) {
    	if(arr == null | arr.length == 0 | index >= this.index - 1 | index < 0) {
    		throw new NoSuchElementException("there is no element belonging to the set in this index") ;   		
    	}
    	
    	return index + 1; // the array is sorted
    }

    @Override
    public Integer predecessor(Integer index) {
    	if(arr == null | arr.length == 0 | index >= this.index | index <= 0) {
    		throw new NoSuchElementException("there is no element belonging to the set in this index") ;   		
    	}
    	
    	return index - 1; // the array is sorted
    }

    @Override
    public void backtrack() {
        if(!stack.isEmpty()) {
        	Integer popped = (Integer)stack.pop() ;
        	if(popped <= 0) { //deleting the last insertion
        	delete(Math.abs(popped));
        	stack.pop();
        	stack.pop();
        	}
        	else { //inserting the last deletion
        		insert((Integer)stack.pop());
        		stack.pop();
        	}
        }
    }

    @Override
    public void retrack() {
		/////////////////////////////////////
		// Do not implement anything here! //
		/////////////////////////////////////
    }

    @Override
    public void print() {
    	String str = "";
    	if (arr != null & index > 0) {
	    	for (int i = 0; i < index; i = i + 1 ) {
	         	str = str + arr[i] + " ";
	        }
	    	System.out.println(str.substring(0, str.length() -1));
        }
    }
    
}

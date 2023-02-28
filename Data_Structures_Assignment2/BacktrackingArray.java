import java.util.NoSuchElementException;

public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int index;

    
    public BacktrackingArray(Stack stack, int size) {
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
    	if (arr != null & index > 0) {
    		for (int i = 0; i < index; i = i + 1 ) {
	        	if(arr[i] == k)
	        		return i ; // in case which the value exists in the set
	        }
        }
       	return -1 ; // in case which the value doesn't exist in the set
    }

    @Override
    public void insert(Integer x) {
    	if (arr != null && index < arr.length) {
    		arr[index] = x ;
            stack.push(-1); //a sign for backtracking
            index = index + 1 ;
    	}
    	else
    		throw new RuntimeException("the array is null or full") ; 
        
    }

    @Override
    public void delete(Integer index) {
    	if(index < this.index & index >=0) {
    		stack.push(arr[index]); //saving the value for backtracking
            stack.push(index); //saving the index of the deleted value
            
            arr[index] = arr[this.index - 1] ;
            this.index = this.index - 1 ;
    	}
    	else
    		throw new RuntimeException("there is no element belonging to the set in this index") ; 
                
    }

    @Override
    public Integer minimum() {
    	if (index > 0) {
        	Integer min = arr[0] ;
        	Integer output = 0 ;
	    	for (int i = 0; i < index; i = i + 1 ) {
	         	if(arr[i] < min) {
	         		min = arr[i] ; // in case which the value exists in the set
	         		output = i ;
	         	}
	        }
	    	return output ;
        }
    	throw new NoSuchElementException("the array is empty") ;
    }

    @Override
    public Integer maximum() {
        if (index > 0) {
        	Integer max = arr[0] ;
        	Integer output = 0 ;
	    	for (int i = 0; i < index; i = i + 1 ) {
	         	if(arr[i] > max) {
	         		max = arr[i] ; // in case which the value exists in the set
	         		output = i ;
	         	}
	        }
	    	return output ;
        }
        throw new NoSuchElementException("the array is empty") ;
    }

    @Override
    public Integer successor(Integer index) {
    	if (index < this.index & index >=0) { //checking if the index is valid
        	Integer suc = arr[maximum()];
        	Integer output = 0 ;
        	
	    	for (int i = 0; i < this.index; i = i + 1 ) { //looking for a successor
	         	if(arr[i] > arr[index] & arr[i] < suc) {
	         		suc = arr[i] ;
	         		output = i ;
	         	}
	        }
	    	if (suc != arr[index]) { //checking if the suc's value is the value at the given index
	    		return output ;
	    	}
        }
    	throw new NoSuchElementException("index is not valid") ;
    }

    @Override
    public Integer predecessor(Integer index) {
    	if (index < this.index & index >=0) { //checking if the index is valid
        	Integer pred = arr[minimum()] ;
        	Integer output = 0 ;
        	
	    	for (int i = 0; i < this.index; i = i + 1 ) { //looking for a predecessor
	         	if(arr[i] < arr[index] & arr[i] > pred) {
	         		pred = arr[i] ;
	         		output = i ;
	         	}
	        }
	    	if (pred != arr[index]) { //checking if the pred's value is the value at the given index
	    		return output ;
	    	}
        }
    	throw new NoSuchElementException("index is not valid") ;
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()) { //checking if there is a backtracking operation could be done
        	Integer toReturn = (Integer)stack.pop();
        	if (toReturn == -1) { //deleting the last insertion
        		index = index - 1;
        	}
        	else {  //inserting the last deletion
        		index = index + 1 ; //arr[index + 1] contains already the value of arr[toReturn]
        		arr[toReturn] = (Integer)stack.pop() ;
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

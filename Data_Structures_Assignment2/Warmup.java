

public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {
    	int index = 0;
    	int steps = 0;
    	    	
    	while (index < arr.length) { 
    		myStack.push(arr[index]);
        	steps = steps + 1;
        	
    		if (arr[index] == x)
    			return index;
    		
    		if(steps == forward) {  
    			steps = 0;
    			index = index - back;
    			for(int i = 0; i < back; i=i+1)
    			myStack.pop();
    		}
    		index = index +1;
    	}
    	return -1;
    }
	
    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
       return recursionBinarySearch(arr, x, myStack, 0, arr.length - 1, 0);
    }
    private static int recursionBinarySearch(int[] arr, int x, Stack myStack, int left, int right, int inconsistencies) {
    	
    	while(inconsistencies > 0) {
    		right = (int) myStack.pop();
    		System.out.println(right);
    		left = (int) myStack.pop();
    		System.out.println(left);
    		inconsistencies = inconsistencies - 1;
    	}
    	
    	inconsistencies = Consistency.isConsistent(arr);
    	
    	if (left > right)
    		return -1;
    	
    	int middle = (left + right)/2;
    	myStack.push(left);
    	myStack.push(right);
    	
    	if (arr[middle] > x)
    		return recursionBinarySearch(arr, x, myStack, left, middle -1, inconsistencies);
    	if (arr[middle] < x)
    			return recursionBinarySearch(arr, x, myStack, middle +1, right, inconsistencies);
    		else 
    			return middle;
    }
    // iterative
    /**
    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
    	int right = arr.length - 1;
    	int left = 0;
    	int inconsistencies = 0;
    	
    	while(right >= left) {
    		myStack
    		int middle = (right + left)/2;
    		if (arr[middle] == x)
    			return middle;
    		else if (arr[middle] > x)
    			right = middle - 1;
    		else if(arr[middle] < x)
    			left = middle + 1;
    	}**/
     }
    


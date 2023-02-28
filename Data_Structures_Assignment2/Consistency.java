public class Consistency {

	public static int[] incosistencies = {0,1,0,0,2,0,0,0,0,0,0,0,0,0,0,0};
    public static int incosistenciesIndex = 0;
    
    public static int isConsistent(int[] arr) {
        return incosistencies[incosistenciesIndex++];
    }
	
}

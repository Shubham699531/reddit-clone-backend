package practice;

public class NthCatalanNumber {
	
	public static void main(String[] args) {
		int n=5;
		int result = findNthCatalanNumber(n);
		System.out.println(result);
	}

	private static int findNthCatalanNumber(int n) {
		int[] catalanNumbers = new int[] {1,1};
		if(n==0 || n==1)
			return 1;
		for(int i=0;i<n;i++) {
			catalanNumbers[i+2] += catalanNumbers[i]*catalanNumbers[n-i-1];
		} 
		
		return catalanNumbers[n];
	}
	
	

}

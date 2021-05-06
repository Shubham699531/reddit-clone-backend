package practice;

public class QuickSelectAlgorithm {
	
	public static void main(String[] args) {
		int[] arr = new int[] {1,4,2,5,3,6,2};
		//1 2 3 5 4
		int k = 3;
		int kthSmallestNumber = quickSelectKthSmallestNumber(arr, k);
//		System.out.println(kthSmallestNumber);
	}

	private static int quickSelectKthSmallestNumber(int[] arr, int k) {
		int pivotIndex = arr.length-1;
//		int pivotElement = arr[pivotIndex];
		int j=-1, temp;
		while((pivotIndex) != k) {
		for(int i=0;i<pivotIndex;i++) {
			if(arr[i]<arr[pivotIndex]) {
				j++;
				if(i!=j) {
					temp = arr[j];
					arr[j] = arr[i];
					arr[i] = temp;
				}
			}
		}
		System.out.println(pivotIndex);
		temp = arr[j+1];
		arr[j+1] = arr[pivotIndex];
		arr[pivotIndex] = temp;
		pivotIndex = j+1;
		System.out.println(pivotIndex);
		}
		
		for(int a: arr)
			System.out.print(a + " ");
		
		return 0;
	}

}

package practice;

public class FindMissingNumberInArraySorted {
	
	public static void main(String[] args) {
		int[] arr = new int[] {1, 3, 4, 5, 6, 7, 8, 9, 10, 
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 
				23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 
				35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 
				47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 
				59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 
				71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 
				83, 84, 85, 86, 87, 88,89, 90, 91, 92, 93, 94, 
				95, 96, 97};
		
		int missingNum = findMissingNumberInArraySorted(arr);
		System.out.println(missingNum);
		
		System.out.println(findMissingNumberInArraySortedBinarySearch(arr));
	}

	//Naive Approach
	//Time Complexity: O(N)
	//No. of passes: 1
	//Space Complexity: O(1)
	private static int findMissingNumberInArraySorted(int[] arr) {
		for(int i=0;i<arr.length;i++) {
			if(arr[i]!=i+1) {
				return i+1;
			}
		}
		return 0;
	}
	
	//Improved approach
	//Time Complexity: O(N)
	//No. of passes: 1
	//Space Complexity: O(1)
	private static int findMissingNumberInArraySortedBinarySearch(int[] arr) {
		int low = 0;
		int high = arr.length;
		int mid;
		while(high>low && low>=0) {
			mid = (low+high)/2;
			System.out.println("Mid: " + mid + " LOw: " + low + " High: " + high);
			if(arr[mid-1]==mid && arr[mid]>(mid+1)) {
				return mid+1;
			}
			else if(arr[mid]>(mid+1)) {
			high = mid-1;
			}else {
			low = mid+1;
		}
		}
		return 0;
	}

}

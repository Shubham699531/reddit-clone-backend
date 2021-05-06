package practice;

public class MiddleNodeLL {
	
	//Time Complexity: O(N)
	//No. of passes: 2 (1st for calculating length 
	// and 2nd for traversing to middle node)
	//Space Complexity: O(1)
	public static LLNode findMiddleNode(LLNode head) {
		LLNode currentNode = head;
		int length = 0;
		while(currentNode != null) {
			++length;
			currentNode = currentNode.next;
		}
		int mid = (length/2)+1;
		currentNode = head;
		while(--mid>0) {
			currentNode = currentNode.next;
		}
		
		return currentNode;
	}
	
	//Time Complexity: O(N) -> N/2 to be very specific
	//No. of passes: 1
	//Space Complexity: O(1)
	//Logic: Fast pointer travels twice as fast as slow pointer,
	// that's the reason it will cover twice the distance as slow pointer.
	//And thus, when fast pointer reaches the end of list, slow pointer is
	//exactly at the middle node.
	public static LLNode findMiddleNodeUsing2Pointers(LLNode head) {
		LLNode slowPtr = head;
		LLNode fastPtr = head;
		
		while(fastPtr != null && fastPtr.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next;
			if(fastPtr != null) {
				fastPtr = fastPtr.next;
			}
		}
		
		return slowPtr;
	}
	
	public static void main(String[] args) {
		LLNode head = new LLNode(4);
		head.next = new LLNode(5);
		head.next.next = new LLNode(6);
		head.next.next.next = new LLNode(7);
		head.next.next.next.next = new LLNode(8);
//		head.next.next.next.next.next = new LLNode(10);
		new SinglyLinkedList(head);
		System.out.println(findMiddleNodeUsing2Pointers(head).data);
	}

}

package practice;

public class FindCycleInLL {
	
	//Time Complexity: O(N)
	//No. of passes: 3 -> (1st pass for finding the node where slow
	// pointer is equal to fast pointer. NOTE: NOT ALWAYS THE JUNCTION NODE,
	// 2nd pass for finding the junction point by setting the slow 
	// pointer to head, 3rd pass for printing the loop node values)
	//Space Complexity: O(1)
	//Logic: Fast pointer travels twice as fast as slow pointer,
	// that's the reason it will cover twice the distance as slow pointer.
	public static boolean findCycleInLL(LLNode head) {
		LLNode slowPtr = head, fastPtr = head;
		while(fastPtr != null && fastPtr.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next;
			if(fastPtr != null) {
				fastPtr = fastPtr.next;
			}
			if(fastPtr==slowPtr) {
				slowPtr = head;
				while(fastPtr!=slowPtr) {
					slowPtr = slowPtr.next;
					fastPtr = fastPtr.next;
				}
				System.out.print(fastPtr.data + " ");
				fastPtr = fastPtr.next;
				while(fastPtr!=slowPtr) {
					System.out.print(fastPtr.data + " ");
					fastPtr=fastPtr.next;
				}
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		LLNode head = new LLNode(1);
		head.next = new LLNode(2);
		head.next.next = new LLNode(3);
		head.next.next.next = new LLNode(4);
		head.next.next.next.next = new LLNode(5);
		head.next.next.next.next.next = head.next.next;
		new SinglyLinkedList(head);
		System.out.println(findCycleInLL(head));
	}

}

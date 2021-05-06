package practice;

public class ReverseLinkedList {
	//Time Complexity: O(N)
	//No. of passes: 1
	//Space Complexity: O(1)
	//Logic: Initialize three nodes as follows:
	// CURRENT_NODE = head (to keep track of current node), 
	// PREV_NODE = null (to keep track of previous node) and 
	// NEXT_NODE = null (to keep track of next node)
	// 1.Store CURRENT_NODE.NEXT in NEXT_NODE, to keep track of next node
	// once the link is broken.
	// 2.Store PREV_NODE in CURRENT_NODE.NEXT, to break the front link
	// and reverse it to point to PREV_NODE;
	// 3.Now, update PREV_NODE and store CURRENT_NODE into it.
	// 4. Finally, update CURRENT_NODE and store NEXT_NODE into it;
	public static LLNode reverseLinkedList(LLNode head) {
		LLNode currentNode = head;
		LLNode prevNode = null;
		LLNode nextNode = null;
		while(currentNode != null) {
			nextNode = currentNode.next;
			currentNode.next = prevNode;
			prevNode = currentNode;
			currentNode = nextNode;
		}
		head = prevNode;
		
		return head;
	}
	
	public static void main(String[] args) {
		LLNode head = new LLNode(1);
		head.next = new LLNode(2);
		head.next.next = new LLNode(3);
		head.next.next.next = new LLNode(4);
		head.next.next.next.next = new LLNode(5);
		head.next.next.next.next.next = new LLNode(8);
		SinglyLinkedList ll = new SinglyLinkedList(head);
		ll.printLinkedList(head);
		System.out.println();
		ll.printLinkedList(reverseLinkedList(head));
	}

}

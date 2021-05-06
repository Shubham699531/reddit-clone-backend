package practice;

public class SinglyLinkedList {
	
	LLNode head;
	
	public SinglyLinkedList(LLNode head) {
		this.head = head;
	}
	
	public void printLinkedList(LLNode head) {
		LLNode currentNode = head;
		while(currentNode!=null) {
			System.out.println(currentNode.data);
			currentNode = currentNode.next;
		}
	}

}

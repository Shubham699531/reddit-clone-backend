package practice;

public class Main {
	
	public static void main(String[] args) {
		BST bst = new BST();
		bst.root = new BSTNode(25);
		
		bst.root.leftChild = new BSTNode(15);
		bst.root.leftChild.leftChild = new BSTNode(10);
		bst.root.leftChild.rightChild = new BSTNode(22);
		bst.root.leftChild.rightChild.leftChild = new BSTNode(18);
		bst.root.leftChild.rightChild.rightChild = new BSTNode(24);
		bst.root.leftChild.leftChild.leftChild = new BSTNode(4);
		bst.root.leftChild.leftChild.rightChild = new BSTNode(12);
		bst.root.leftChild.leftChild.leftChild.leftChild = new BSTNode(2);
		
		bst.root.rightChild = new BSTNode(50);
		bst.root.rightChild.leftChild = new BSTNode(35);
		bst.root.rightChild.rightChild = new BSTNode(70);
		bst.root.rightChild.rightChild.leftChild = new BSTNode(66);
		bst.root.rightChild.rightChild.rightChild = new BSTNode(90);
		bst.root.rightChild.leftChild.leftChild = new BSTNode(31);
		bst.root.rightChild.leftChild.rightChild = new BSTNode(44);
		
		
		bst.printPreorder(bst.root);
		System.out.println();
		bst.printInorder(bst.root);
		System.out.println();
		bst.printPostorder(bst.root);
		System.out.println();
		System.out.println(bst.checkIfBinaryTree(bst.root, null, null));
		System.out.println(bst.calculateHeightOfTree(bst.root));
	}
	

}

class BSTNode{
	int data;
	BSTNode leftChild;
	BSTNode rightChild;
	
	public BSTNode(int data) {
		this.data = data;
		this.leftChild = null;
		this.rightChild = null;
	}
}

class BST{
	BSTNode root;
	
	public BST() {
		this.root = null;
	}
	
	public void printPreorder(BSTNode root) {
		if (root == null)
			return;
		System.out.print(root.data + " ");
		printPreorder(root.leftChild);
		//printPreorder(9)
		printPreorder(root.rightChild);
		//printPreorder(10
	}
	
	public void printInorder(BSTNode root) {
		if(root == null) 
			return;
		printInorder(root.leftChild);
		System.out.print(root.data + " ");
		printInorder(root.rightChild);
	}
	public void printPostorder(BSTNode root) {
		if(root == null) 
			return;
		printPostorder(root.leftChild);
		printPostorder(root.rightChild);
		System.out.print(root.data + " ");
	}

	public boolean checkIfBinaryTree(BSTNode root, BSTNode left, BSTNode right) {
		if(root==null) {
			return true;
		}
		if(left!=null && left.data>=root.data) {
			return false;
		}
		if(right!=null && right.data<=root.data) {
			return false;
		}
		return (checkIfBinaryTree(root.leftChild, left, root) && 
				checkIfBinaryTree(root.rightChild, root, right));
	}

	public int calculateHeightOfTree(BSTNode root2) {
		if(root2==null) {
			return 0;
		}else {
			int ldepth = calculateHeightOfTree(root2.leftChild);
			int rdepth = calculateHeightOfTree(root2.rightChild);
			
			if(ldepth>rdepth) {
				return ldepth+1;
			} else {
				return rdepth+1;
			}
		}
	}
}

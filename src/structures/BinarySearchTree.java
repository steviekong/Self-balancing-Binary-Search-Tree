package structures;

import java.util.*;
import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}

	public boolean contains(T t) throws NullPointerException {
		// TODO
		if(t==null){
			throw new NullPointerException();
		}
		return get(t)!=null;
	}

	public boolean remove(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException();
		}
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	protected BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	private T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	private BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			return node;
		}
	}

	public T get(T t) throws NullPointerException {
		if(t==null){
			throw new NullPointerException("null element");
		}
		Iterator<T> i=inorderIterator();
		while (i.hasNext()){
			T next=i.next();
			if(next.equals(t)){
				return next;
			}
		}
		return null;

	}


	public void add(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		root = addToSubtree(root, new BSTNode<T>(t, null, null));
	}

	protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
		if (node == null) {
			return toAdd;
		}
		int result = toAdd.getData().compareTo(node.getData());
		if (result <= 0) {
			node.setLeft(addToSubtree(node.getLeft(), toAdd));
		} else {
			node.setRight(addToSubtree(node.getRight(), toAdd));
		}
		return node;
	}

	@Override
	public T getMinimum() {
		// TODO
		if(isEmpty()){
			return null;
		}
		Iterator<T> i=inorderIterator();
		T min=null;
		while (i.hasNext()){
			T next=i.next();
			if(min==null){
				min=next;
			}
			else if(next.compareTo(min)<0){
				min=next;
			}
		}
		return min;
	}


	@Override
	public T getMaximum() {
		// TODO
		if(isEmpty()){
			return null;
		}
		Iterator<T> i=postorderIterator();
		T min=null;
		while (i.hasNext()){
			T next=i.next();
			if(min==null){
				min=next;
			}
			else if(next.compareTo(min)>0){
				min=next;
			}
		}
		return min;
	}


	@Override
	public int height() {
		// TODO
		if(root==null){
			return -1;
		}
		return hHelper(root)-1;
	}
	private int hHelper(BSTNode<T> b){
		if(b==null){
			return 0;
		}
		int h1=1+hHelper(b.getLeft());
		int h2=1+hHelper(b.getRight());
		if(h1>h2){
			return h1;
		}
		return h2;
	}
	public Iterator<T> preorderIterator() {
		// TODO
		Queue<T> queue = new LinkedList<T>();
		preorderTraverse(queue, root);
		return queue.iterator();
	}
	private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			queue.add(node.getData());
			preorderTraverse(queue, node.getLeft());
			preorderTraverse(queue, node.getRight());
		}
	}


	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}


	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> postorderIterator() {
		// TODO
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue, root);
		return queue.iterator();
	}
	private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			postorderTraverse(queue, node.getLeft());
			postorderTraverse(queue, node.getRight());
			queue.add(node.getData());
		}
	}


	@Override
	public boolean equals(BSTInterface<T> other) throws NullPointerException {
		// TODO
		if(other==null){
			throw new NullPointerException();
		}
		if(this.size()!=other.size()){
			return false;
		}
		Iterator<T> c=other.preorderIterator();
		Iterator<T> d=preorderIterator();
		while (c.hasNext()){
			if(!c.next().equals(d.next())){
				return false;
			}
		}
		return true;
	}


	@Override
	public boolean sameValues(BSTInterface<T> other) throws NullPointerException {
		// TODO
		if(other==null){
			throw new NullPointerException();
		}
		if(this.size()!=other.size()){
			return false;
		}
		Iterator<T> c=other.inorderIterator();
		Iterator<T> d=inorderIterator();
		while (c.hasNext()){
			if(!c.next().equals(d.next())){
				return false;
			}
		}
		return true;

	}

	@Override
	public boolean isBalanced() {
		// TODO
		if(size()==0){
			return true;
		}
		if(Math.pow(2,height())<=size()&&size()<Math.pow(2,height()+1))
		{
			return true;
		}
		return false;
	}

	@Override
    @SuppressWarnings("unchecked")

	public void balance() {
		// TODO
		Iterator<T> i=inorderIterator();
		List<T> tempi=new ArrayList<>();
		while (i.hasNext()){
			tempi.add(i.next());
		}
		Collections.sort(tempi);
		BinarySearchTree<T> tempTree=new BinarySearchTree<T>();
		sortedArray2BST(0,size()-1,tempi,tempTree);
		this.root=tempTree.root;
	}
	public void sortedArray2BST(int lower, int upper,List<T> l,BinarySearchTree<T> ntree) {
		if(lower>upper){
			return;
		}
		int mid = (lower + upper) / 2;
		ntree.add(l.get(mid));
		sortedArray2BST(lower,mid-1,l,ntree);
		sortedArray2BST(mid+1,upper,l,ntree);
	}


	@Override
	public BSTNode<T> getRoot() {
        // DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}

	public static void main(String[] args) {
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			BSTInterface<String> tree = new BinarySearchTree<String>();
			for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
				tree.add(s);
			}
			Iterator<String> iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.preorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.postorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();

			System.out.println(tree.remove(r));

			iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
		}

		BSTInterface<String> tree = new BinarySearchTree<String>();
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			tree.add(r);
		}
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
		tree.balance();
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
	}
}
package structures;

import java.util.Iterator;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {
	private int upperBound;


	@Override
	public void add(T t) throws NullPointerException {
		// TODO
		if(t==null){
			throw new NullPointerException();
		}
		upperBound++;
		BSTNode<T> temp=new BSTNode<T>(t, null, null);
		root = addToSubtree(root,temp);
		if(height()>Math.log(upperBound)/ Math.log((double)3/2)){
			BinarySearchTree<T> wsub=new BinarySearchTree<T>();
			BSTNode<T> goat=findGoat(temp);
			wsub.root=goat;
			wsub.balance();
			if(lrCheck(goat)) {
				goat.getParent().setRight(wsub.getRoot());
			}
			else {
				goat.getParent().setLeft(wsub.getRoot());
			}
		}
	}
	private BSTNode<T> findGoat(BSTNode<T> n){
		BSTNode<T> cur=n;
		while ((double)subtreeSize(cur)/subtreeSize(cur.getParent())<=((double)2/3)){
			cur=cur.getParent();
		}
		return cur;
 	}
 	private boolean lrCheck(BSTNode<T> b){
		if(b.getData().compareTo(b.getParent().getData())>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(T element) throws NullPointerException {
		// TODO
		if (element == null) {
			throw new NullPointerException();
		}
		boolean result = contains(element);
		if (result) {
			root = removeFromSubtree(root, element);
		}
		if(result&&upperBound>2*size()){
			balance();
			upperBound=size();
		}
		return result;
	}
}

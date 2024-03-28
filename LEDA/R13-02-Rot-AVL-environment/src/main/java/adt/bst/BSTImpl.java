package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return recursiveHeight(this.root);
	}

	private int recursiveHeight(BSTNode<T> node) {
		int height = 0;

		if (node.isEmpty()) {
			height = -1;
		} else {
			int leftHeight = recursiveHeight((BSTNode<T>) node.getLeft());
			int rightHeight = recursiveHeight((BSTNode<T>) node.getRight());

			if (leftHeight >= rightHeight) {
				height = 1 + leftHeight;
			} else {
				height = 1 + rightHeight;
			}
		}

		return height;
	}

	@Override
	public BSTNode<T> search(T element) {
		return recursiveSearch(element, this.root);
	}

	private BSTNode<T> recursiveSearch(T element, BSTNode<T> node) {
		BSTNode<T> out = null;

		if (node.isEmpty()) {
			out = node;

		} else if (node.getData().equals(element)) {
			out = node;

		} else if (element.compareTo(node.getData()) < 0) {
			out = recursiveSearch(element, (BSTNode<T>) node.getLeft());

		} else {
			out = recursiveSearch(element, (BSTNode<T>) node.getRight());

		}

		return out;
	}

	@Override
	public void insert(T element) {
		recursiveInsert(element, new BSTNode<T>(), this.root);
	}

	// TODO fazer com que só precise de 2 parâmetros depois.
	private void recursiveInsert(T element, BSTNode<T> parent, BSTNode<T> node) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setParent(parent);

			node.setLeft(new BSTNode<T>());
			node.getLeft().setParent(node);

			node.setRight(new BSTNode<T>());
			node.getRight().setParent(node);

		} else {
			if (element.compareTo(node.getData()) < 0) {
				recursiveInsert(element, node, (BSTNode<T>) node.getLeft());
			} else {
				recursiveInsert(element, node, (BSTNode<T>) node.getRight());
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return recursiveMaximum(this.root);
	}

	private BSTNode<T> recursiveMaximum(BSTNode<T> node) {
		BSTNode<T> max = null;

		if (!isEmpty()) {
			if (node.getRight().isEmpty()) {
				max = node;
			} else {
				max = recursiveMaximum((BSTNode<T>) node.getRight());
			}
		}

		return max;
	}

	@Override
	public BSTNode<T> minimum() {
		return recursiveMinimum(this.root);
	}

	private BSTNode<T> recursiveMinimum(BSTNode<T> node) {
		BSTNode<T> min = null;

		if (!isEmpty()) {
			if (node.getLeft().isEmpty()) {
				min = node;
			} else {
				min = recursiveMinimum((BSTNode<T>) node.getLeft());
			}
		}

		return min;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> succ = null;

		if (!isEmpty()) {
			if (element.compareTo(maximum().getData()) < 0) {
				if (search(element) != null) {
					succ = this.recursiveMinimum((BSTNode<T>) search(element).getRight());

				} else {
					BSTNode<T> current = this.root;

					while (!current.isEmpty()) {
						if (element.compareTo(current.getData()) < 0) {
							current = (BSTNode<T>) current.getLeft();
						} else {
							current = (BSTNode<T>) current.getRight();
						}
					}

					do {
						current = (BSTNode<T>) current.getParent();
					} while (element.compareTo(current.getData()) >= 0);

					succ = current;
				}
			}
		}

		return succ;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> pred = null;

		if (!isEmpty()) {
			if (element.compareTo(minimum().getData()) > 0) {
				if (search(element) != null) {
					pred = this.recursiveMaximum((BSTNode<T>) search(element).getLeft());

				} else {

					BSTNode<T> current = this.root;

					while (!current.isEmpty()) {
						if (element.compareTo(current.getData()) <= 0) {
							current = (BSTNode<T>) current.getLeft();
						} else {
							current = (BSTNode<T>) current.getRight();
						}
					}

					do {
						current = (BSTNode<T>) current.getParent();
					} while (element.compareTo(current.getData()) <= 0);

					pred = current;
				}
			}
		}

		return pred;
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			if (element != null) {
				BSTNode<T> toRemove = search(element);

				if (!toRemove.isEmpty()) {
					if (toRemove.isLeaf()) {
						if (toRemove == this.root) {
							this.root = new BSTNode<T>();
							this.root.setParent(new BSTNode<T>());
							this.root.setLeft(new BSTNode<T>());
							this.root.setRight(new BSTNode<T>());
						} else {
							if (toRemove.getData().compareTo(toRemove.getParent().getData()) < 0) {
								toRemove.getParent().setLeft(new BSTNode<T>());
								toRemove.getParent().getLeft().setParent(toRemove.getParent());
							} else {
								toRemove.getParent().setRight(new BSTNode<T>());
								toRemove.getParent().getRight().setParent(toRemove.getParent());
							}
						}

					} else if (!(toRemove.getLeft().isEmpty()) && toRemove.getRight().isEmpty()) {

						if (toRemove == this.root) {
							this.root = (BSTNode<T>) toRemove.getLeft();
							this.root.setParent(new BSTNode<T>());
						} else {
							toRemove.getLeft().setParent(toRemove.getParent());

							if (toRemove.getData().compareTo(toRemove.getParent().getData()) < 0) {
								toRemove.getParent().setLeft(toRemove.getLeft());
							} else {
								toRemove.getParent().setRight(toRemove.getLeft());
							}
						}

					} else if (!(toRemove.getRight().isEmpty()) && toRemove.getLeft().isEmpty()) {

						if (toRemove == this.root) {
							this.root = (BSTNode<T>) toRemove.getRight();
							this.root.setParent(new BSTNode<T>());
						} else {
							toRemove.getRight().setParent(toRemove.getParent());

							if (toRemove.getData().compareTo(toRemove.getParent().getData()) < 0) {
								toRemove.getParent().setLeft(toRemove.getRight());

							} else {
								toRemove.getParent().setRight(toRemove.getRight());
							}
						}

					} else {
						BSTNode<T> succ = sucessor(toRemove.getData());
						remove(succ.getData());
						toRemove.setData(succ.getData());

					}
				}
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T[] preOrder() {
		T[] preOrder = (T[]) new Comparable[this.size()];
		recursivePreOrder(this.root, preOrder, new int[] { 0 });
		return preOrder;
	}

	private void recursivePreOrder(BSTNode<T> node, T[] array, int[] index) {
		if (!node.isEmpty()) {
			array[index[0]++] = node.getData();

			recursivePreOrder((BSTNode<T>) node.getLeft(), array, index);

			recursivePreOrder((BSTNode<T>) node.getRight(), array, index);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T[] order() {
		T[] order = (T[]) new Comparable[this.size()];
		recursiveOrder(this.root, order, new int[] { 0 });
		return order;
	}

	private void recursiveOrder(BSTNode<T> node, T[] array, int[] index) {
		if (!node.isEmpty()) {
			recursiveOrder((BSTNode<T>) node.getLeft(), array, index);

			array[index[0]++] = node.getData();

			recursiveOrder((BSTNode<T>) node.getRight(), array, index);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T[] postOrder() {
		T[] postOrder = (T[]) new Comparable[this.size()];
		recursivePostOrder(this.root, postOrder, new int[] { 0 });
		return postOrder;
	}

	private void recursivePostOrder(BSTNode<T> node, T[] array, int[] index) {
		if (!node.isEmpty()) {
			recursivePostOrder((BSTNode<T>) node.getLeft(), array, index);

			recursivePostOrder((BSTNode<T>) node.getRight(), array, index);

			array[index[0]++] = node.getData();
		}
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
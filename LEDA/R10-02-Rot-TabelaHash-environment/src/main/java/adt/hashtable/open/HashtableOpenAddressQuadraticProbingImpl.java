package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable>
		extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size,
			HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		if (element != null && !isFull()) {
			int probing = 0;
			boolean elementInserted = false;

			do {
				int index = ((HashFunctionQuadraticProbing<T>) this.hashFunction).hash(element, probing);

				if (this.table[index] == null || this.table[index].equals(deletedElement)) {
					this.table[index] = element;
					this.elements++;

					elementInserted = true;

				} else {
					probing++;
					this.COLLISIONS++;

				}

			} while (probing < this.table.length && !elementInserted);
		}
	}

	@Override
	public void remove(T element) {
		if (element != null && !isEmpty()) {
			int probing = 0;
			boolean elementRemoved = false;
			boolean nullFound = false;

			do {
				int index = ((HashFunctionQuadraticProbing<T>) this.hashFunction).hash(element, probing);

				if (this.table[index] != null && this.table[index].equals(element)) {
					this.table[index] = deletedElement;
					this.elements--;

					elementRemoved = true;

				} else if (this.table[index] == null) {
					nullFound = true;

				} else {
					probing++;

				}

			} while (probing < this.table.length && !elementRemoved && !nullFound);

		}
	}

	@Override
	public T search(T element) {
		T out = null;

		if (element != null && !isEmpty()) {
			int probing = 0;
			boolean elementFound = false;
			boolean nullFound = false;

			do {
				int index = ((HashFunctionQuadraticProbing<T>) this.hashFunction).hash(element, probing);

				if (this.table[index] != null && this.table[index].equals(element)) {
					out = (T) this.table[index];

					elementFound = true;

				} else if (this.table[index] == null) {
					nullFound = true;

				} else {
					probing++;

				}

			} while (probing < this.table.length && !elementFound && !nullFound);
		}

		return out;
	}

	@Override
	public int indexOf(T element) {
		int out = -1;

		if (element != null && !isEmpty()) {
			int probing = 0;
			boolean elementFound = false;

			do {
				int index = ((HashFunctionQuadraticProbing<T>) this.hashFunction).hash(element, probing);

				if (this.table[index].equals(element)) {
					out = index;

					elementFound = true;

				} else {
					probing++;

				}

			} while (probing < this.table.length && !elementFound);
		}

		return out;
	}
}

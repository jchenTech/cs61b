public class IntList {
	public int first;
	public IntList rest;

	public IntList(int f, IntList r) {
		first = f;
		rest = r;
	}

	/** Return the size of the list using... recursion! */
	public int size() {
		if (this.rest == null) {/* base case */
			return 1;
		}
		return 1 + this.rest.size();
	}

	/** Return the size of the list using no recursion! */
	public int iterativeSize() {
		/* You can not assign "this" in Java*/
		IntList p = this;
		int totalSize = 0;
		while (p != null) {
			totalSize += 1;
			p = p.rest;
		}
		return totalSize;
	}

	/** Returns the ith value in this list.*/
	public int get(int i) {
		IntList p = this;
		int nth = 0;
		if (i >= p.size()) {
			return -1;
		}
		while (nth != i) {
			p = p.rest;
			nth += 1;
		}
		return p.first;
	}

	public static void main(String[] args) {
		IntList L = new IntList(15, null);
		L = new IntList(10, L);
		L = new IntList(5, L);
		System.out.println(L.size());
		System.out.println(L.iterativeSize());
		System.out.println(L.get(2));
	}
} 
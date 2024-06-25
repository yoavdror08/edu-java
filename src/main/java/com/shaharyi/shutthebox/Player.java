public class Player {
	private boolean[] stones;
	private boolean out;
	// 1 - high; 2 - low, 3 and up - random
	private int strategy;

	public Player(int strategy) {
		// array starts entirely false
		this.stones = new boolean[10];
		this.out = false;
		this.strategy = strategy;
	}

	public void reset() {
		for (int i = 0; i < stones.length; i++) {
			stones[i] = false;
		}
		out = false;
	}

	public boolean playHigh(int sum) {
		if (!playStone(sum))
			if (!searchHigh2(sum))
				return searchHigh3(sum);					
		return true;
	}

	public boolean allAreShut() {
		for (int i = 0; i < stones.length; i++) {
			if (!stones[i])
				return false;
		}
		return true;		
	}
	
	public boolean isOut() {
		return out;
	}

	public boolean playLow(int sum) {
		if (!searchLow3(sum))
			if (!searchLow2(sum))
				return playStone(sum);
		return true;
	}

	public boolean playStone(int i) {
		if (i < stones.length && !stones[i]) {
			stones[i] = true;
			return true;
		}
		return false;
	}

	public boolean searchLow2(int sum) {
		int a = 1, b = 2;
		while (a + b <= sum && a <= 5) {
			if (a + b == sum && !stones[a] && !stones[b]) {
				stones[a] = true;
				stones[b] = true;
				return true;
			}
			b++;
			if (b == 7) {
				a++;
				b = a + 1;
			}
		}
		return false;
	}
	
	public boolean searchHigh2(int sum) {
		int a = 5, b = 6;
		while (a + b >= sum && b >= 2) {
			if (a + b == sum && !stones[a] && !stones[b]) {
				stones[a] = true;
				stones[b] = true;
				return true;
			}
			a--;
			if (a == 0) {
				b--;
				a = b - 1;
			}
		}
		return false;
	}
	public boolean searchHigh3(int sum) {
		int a = 4, b = 5, c = 6;
		while (a + b +c >= sum && c >= 3) {
			if (a + b +c== sum && !stones[a] && !stones[b] && !stones[c]) {
				stones[a] = true;
				stones[b] = true;
				stones[c] = true;
				return true;
			}
			a--;
			if (a == 0) {
				b--;
				a = b - 1;
			}
			if (b == 1) {
				c--;
				b = c - 1;
				a = b - 1;
			}
		}
		return false;
	}

	public boolean searchLow3(int sum) {
		int a = 1, b = 2, c = 3;
		while (a + b + c <= sum && a <= 4) {
			if (a + b + c == sum && !stones[a] && !stones[b] && !stones[c]) {
				stones[a] = true;
				stones[b] = true;
				stones[c] = true;
				return true;
			}
			c++;
			if (c == 7) {
				b++;
				c = b + 1;
			}
			if (b == 6) {
				a++;
				b = a + 1;
				c = b + 1;
			}
		}
		return false;
	}

	public void copyArr(boolean[] a1, boolean[] a2) {
		for (int j = 0; j < a2.length; j++) {
			a2[j] = a1[j];
		}
	}

	/*
	 * Try add random stone, until hitting target or busted.  
	 * If busted, we start with zero sum again.
	 */
	public boolean playRandom(int target) {
		boolean found = false;
		int maxTries = 10000;
		int i = 0;
		int sum = 0;
		boolean[] stones2 = new boolean[stones.length];
		copyArr(stones, stones2);
		while (!found && i < maxTries) {
			int a = (int) (Math.random() * Math.min(9, target)) + 1;
			if (!stones2[a]) {
				sum += a;
				if (sum > target) {
					sum = 0;
					copyArr(stones, stones2);
				} else {
					stones2[a] = true;
					if (sum == target) {
						copyArr(stones2, stones);
						found = true;
					}
				}
			}
			i++;
		}
		return found;
	}

	public boolean play(int c1, int c2) {
		int sum = c1 + c2;
		boolean found = false;
		if (strategy == 1)
			found = playHigh(sum);
		if (strategy == 2)
			found = playLow(sum);
		if (strategy >= 3)
			found = playRandom(sum);
		out = !found;
		return found;
	}
}

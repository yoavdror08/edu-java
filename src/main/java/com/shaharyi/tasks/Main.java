public class Main {

	public static void main(String[] args) {
		testTasks();
	}

	public static void testTasks() {
		Queue<Task> q = new Queue<Task>();
		q.insert(new Task("a1", 2, 100));
		q.insert(new Task("a2", 2, 100));
		q.insert(new Task("a3", 2, 100));
		q.insert(new Task("a4", 4, 100));
		q.insert(new Task("a5", 4, 100));
		System.out.println(q);
		insert(q, new Task("b1", 7, 100));
		System.out.println(q);
		update(q, 95, false);
		System.out.println(q);
	}

	public static void insert(Queue<Task> q, Task task) {
		boolean inserted = false;
		q.insert(null);
		while (q.head() != null) {
			Task a = q.remove();
			if (a.getPriority() > task.getPriority() && !inserted) {
				q.insert(task);
				inserted = true;
			}
			q.insert(a);
		}
		if (!inserted)
			q.insert(task);
		q.remove();
	}

	public static void update(Queue<Task> q, int t, boolean done) {
		Task a = q.remove();
		if (!done) {
			a.subCredit(t);
			if (a.getCredit() < 10) {
				a.addCredit(100);
				a.decPriority();
			}
			insert(q, a);
		}
	}
}

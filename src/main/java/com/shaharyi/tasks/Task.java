package com.shaharyi.tasks;

public class Task {
	private String id;
	private int priority; // 1 is highest
	private int credit;

	public String getId() {
		return id;
	}

	public int getPriority() {
		return priority;
	}

	public int getCredit() {
		return credit;
	}

	public Task(String id, int priority, int credit) {
		super();
		this.id = id;
		this.priority = priority;
		this.credit = credit;
	}

	public void decPriority() {
		priority++;
	}

	public void addCredit(int c) {
		credit += c;
	}

	public void subCredit(int c) {
		credit -= c;
	}

	public String toString() {
		return "Task [id=" + id + ", priority=" + priority + ", credit=" + credit + "]";
	}
	
}

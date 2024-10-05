public class Main {
	public static void main(String[] args) {
		Athlete[] a = new Athlete[4];
		a[0] = new Athlete("Rami A", 10);
		a[0].addGrade("100m", 23.1);
		a[0].addGrade("200m", 24.1);
		a[1] = new Athlete("Rami B", 10);
		a[1].addGrade("100m", 25.1);
		a[1].addGrade("200m", 24.1);
		a[2] = new Athlete("Rami C", 10);
		a[2].addGrade("100m", 22.1);
		a[2].addGrade("200m", 21.1);
		a[3] = new Athlete("Rami D", 10);
		a[3].addGrade("100m", 23.1);
		a[3].addGrade("200m", 22.1);
		
		int r = averageAbove(23.1, a);
		System.out.println("Above 23.1: "+ r);
	}

	public static int averageAbove(double g, Athlete[] athletes) {
		int countAbove = 0;
		for (int i = 0; i < athletes.length; i++) {
			if (athletes[i].average() > g) {
				countAbove++;
			}
		}
		return countAbove;
	}

}

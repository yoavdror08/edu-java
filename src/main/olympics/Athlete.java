public class Athlete {

	private String name;
	private Grade[] grades;
	private int count;

	public Athlete(String name, int max) {
		this.name = name;
		count = 0;
		grades = new Grade[max];
	}

	public Athlete(Athlete other) {
		name = other.name;
		count = other.count;
		grades = new Grade[other.grades.length];
		for (int i = 0; i < count; i++) {
			grades[i] = other.grades[i];
		}
	}

	public boolean addGrade(String apparatus, double score) {
		if (count < grades.length) {
			grades[count] = new Grade(apparatus, score);
			count++;
			return true;
		}
		return false;
	}

	public boolean deleteGrade(String apparatus) {
		for (int i = 0; i < count; i++) {
			if (grades[i].getApparatus().equals(apparatus)) {
				for (int j = i; j < count; i++) {
					grades[j] = grades[j + 1];
				}
				grades[count - 1] = null;
				count--;
				return true;

			}
		}
		return false;
	}

	public double average() {
		double sum = 0;
		for (int i = 0; i < count; i++) {
			sum += grades[i].getScore();
		}
		return sum / count;
	}

	public boolean allGradesAbove(double num) {
		for (int i = 0; i < count; i++) {
			if (grades[i].getScore() <= num) {
				return false;
			}
		}
		return true;
	}

	public boolean isBetter(Athlete other) {
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < other.count; j++) {
				if (grades[i].getApparatus().equals(other.grades[j].getApparatus())) {
					if (grades[i].getScore() <= other.grades[j].getScore()) {
						return false;
					}
				}
			}
		}
		return true;
	}
}

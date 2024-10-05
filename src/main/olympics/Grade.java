class Grade {
	private String apparatus;
	private double score;

	public Grade(String apparatus, double score) {
		this.apparatus = apparatus;
		this.score = score;
	}

	public String getApparatus() {
		return this.apparatus;
	}

	public void setApparatus(String apparatus) {
		this.apparatus = apparatus;
	}

	public double getScore() {
		return this.score;

	}

	public void setScore(double score) {
		this.score = score;
	}

	public Grade(Grade g) {
		this.score = g.score;
		this.apparatus = g.apparatus;
	}

}
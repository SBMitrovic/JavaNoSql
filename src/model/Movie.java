package model;

public class Movie {
	int rank, year, budget;
	long box_office;
	double rating;
	String[] genres;
	String name, run_time, tagline;
	Cast cast;
	String certificate;
	
	public Movie(int rank, int year, double rating, int budget, long box_office,Cast cast, String name, String run_time, String tagline, String[] genres, String certificate) {
		this.rank = rank;
		this.year = year;
		this.rating = rating;
		this.budget = budget;
		this.box_office = box_office;
		this.cast = cast;
		this.name = name;
		this.run_time = run_time;
		this.tagline = tagline;
		this.genres = genres;
		this.certificate = certificate;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public long getBox_office() {
		return box_office;
	}

	public void setBox_office(long box_office) {
		this.box_office = box_office;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}




	public String[] getGenres() {
		return genres;
	}

	public void setGenres(String[] genres) {
		this.genres = genres;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRun_time() {
		return run_time;
	}

	public void setRun_time(String run_time) {
		this.run_time = run_time;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}


	public Cast getCast() {
		return cast;
	}

	public void setCast(Cast cast) {
		this.cast = cast;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}


		
	

}

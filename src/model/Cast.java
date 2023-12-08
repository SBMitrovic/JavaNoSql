package model;
import model.*;

public class Cast {
	
	String[] directors, actors, writers;
	public Cast(String[] directors, String[] actors, String[] writers) {
		
		this.directors = directors;
		this.actors = actors;
		this.writers = writers;
	}
	public String[] getDirectors() {
		return directors;
	}
	public void setDirectors(String[] directors) {
		this.directors = directors;
	}
	public String[] getActors() {
		return actors;
	}
	public void setActors(String[] actors) {
		this.actors = actors;
	}
	public String[] getWriters() {
		return writers;
	}
	public void setWriters(String[] writers) {
		this.writers = writers;
	}

	
}

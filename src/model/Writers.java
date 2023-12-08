package model;

import java.util.Arrays;

public class Writers {
	public String[] writers;
	
	public Writers(String[] writers) {
		this.writers = writers;
	}

	@Override
	public String toString() {
		return "Writers [writers=" + Arrays.toString(writers) + "]";
	}

}

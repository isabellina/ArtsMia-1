package it.polito.tdp.artsmia.model;

public class Adiacenza {
	
	private ArtObject artObj1;
	private ArtObject artObj2;
	private Integer weight;
	
	public Adiacenza(ArtObject artObj1, ArtObject artObj2, Integer weight) {
		super();
		this.artObj1 = artObj1;
		this.artObj2 = artObj2;
		this.weight = weight;
	}

	public ArtObject getArtObj1() {
		return artObj1;
	}

	public ArtObject getArtObj2() {
		return artObj2;
	}

	public Integer getWeight() {
		return weight;
	}
	
	
}

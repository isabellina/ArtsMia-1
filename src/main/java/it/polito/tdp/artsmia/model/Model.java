package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	private Graph<ArtObject, DefaultWeightedEdge> graph;
	private Map<Integer, ArtObject> artObjectIdMap;
	
	public Model() {
		this.dao = new ArtsmiaDAO();
		this.artObjectIdMap = new HashMap<>();
	}
	
	
	public void buildGraph() {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.maptObjects(artObjectIdMap);
		Graphs.addAllVertices(this.graph, this.artObjectIdMap.values());
		
		
		/*
		//Approccio 1 -> doppio ciclo for sui vertici, controllo se sono collegati
		for(ArtObject a1 : this.graph.vertexSet()) {
			for(ArtObject a2 : this.graph.vertexSet()) {
				if(!graph.containsEdge(a1, a2)) {
					int weight = this.dao.getWeight(a1, a2);
					if(weight > 0) {
						Graphs.addEdge(this.graph, a1, a2, weight);
					}
				}
			}
		}
		*/
		
		List<Adiacenza> adiacenze = new ArrayList<>(this.dao.listAdiacenza(artObjectIdMap));
		for(Adiacenza a : adiacenze) {
			Graphs.addEdge(this.graph, a.getArtObj1(), a.getArtObj2(), a.getWeight());
		}
		
		System.out.println("Vertici: "+this.graph.vertexSet().size()+" Archi: "+this.graph.edgeSet().size());
	}
	
	public int getNVertici() {
		return this.graph.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.graph.edgeSet().size();
	}

}

package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void maptObjects(Map<Integer, ArtObject> artObjectIdMap) {
		
		String sql = "SELECT * from objects";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(!artObjectIdMap.containsKey(res.getInt("object_id"))) {
					ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
					artObjectIdMap.put(artObj.getId(), artObj);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * FROM exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition e = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_departmente"), res.getString("exhibition_title"), res.getInt("begin"), res.getInt("end"));
				
				result.add(e);
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	public int getWeight(ArtObject a1, ArtObject a2) {
		String sql = "SELECT COUNT(*) AS C FROM exhibition_objects AS e1, exhibition_objects AS e2 WHERE e1.exhibition_id = e2.exhibition_id AND e1.object_id = ? AND e2.object_id = ?";
		Connection conn = DBConnect.getConnection();
		int weight = 0;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, a1.getId());
			st.setInt(2, a2.getId());
			ResultSet res = st.executeQuery();
			res.first();
			
			conn.close();
			weight = res.getInt("C");
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return weight;
	}
	
	public List<Adiacenza> listAdiacenza(Map<Integer, ArtObject> artObjectIdMap) {
		String sql = "SELECT e1.object_id, e2.object_id, COUNT(*) AS C FROM exhibition_objects AS e1, exhibition_objects AS e2 WHERE e1.exhibition_id = e2.exhibition_id AND e1.object_id > e2.object_id GROUP BY e1.object_id, e2.object_id";
		Connection conn = DBConnect.getConnection();
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Adiacenza a = new Adiacenza(artObjectIdMap.get(res.getInt("e1.object_id")), artObjectIdMap.get(res.getInt("e2.object_id")), res.getInt("C"));
				result.add(a);
			}
			conn.close();
			return result;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
}

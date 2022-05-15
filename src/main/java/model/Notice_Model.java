package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import db_connection.DB;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Notice_Model {

	private String res;
	
	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getNotice() {
		PreparedStatement ps;
		String data="";
		
		try {
			
			Connection conn = DB.getConn(); 
			ps = conn.prepareStatement("SELECT * FROM notice");
			
			ResultSet res_Set = ps.executeQuery();
			
			data = "<table>"
		            +"<tr>"
		            +"<th style='border-style: dotted;'>ID</th>"
	                +"<th style='border-style: dotted;'>Bill ID</th>"
	                +"<th style='border-style: dotted;'>Description</th>"
	                +"<th style='border-style: dotted;'>Date</th>"
	                +"<th style='border-style: dotted;'>Edit/Delete</th>"
	                +"</tr>";
			
			while (res_Set.next()) {
				
				String button = "<button>Edit</button><button>Delete</button>";
				
				data = data+"<tr><td style='border-style: dotted;'>"+res_Set.getString(1)+"</td>"
						+ "<td style='border-style: dotted;'>"+res_Set.getString(2)+"</td>"
						+ "<td style='border-style: dotted;'>"+res_Set.getString(3)+"</td>"
						+ "<td style='border-style: dotted;'>"+res_Set.getString(4)+"</td>"
		                + "<td style='border-style: dotted;'>"+button+"</td>"
					  + "</tr>";
				
			}
			
			ps.close();
			conn.close();
			
		}catch (ClassNotFoundException | SQLException  e) {

			System.out.println(e.getMessage());
		}
		
		return data+"</table>";
	}
	
	public void addNotice(String b_id,String description,String date) {
		PreparedStatement ps;
		
		try {
			Connection conn = DB.getConn();
			
			ps = conn.prepareStatement("insert into notice (b_id,description,date) values (?,?,?)");
			ps.setString(1, b_id);
			ps.setString(2, description);
			ps.setString(3, date);
			ps.execute();
			ps.close();
			conn.close();
			setRes("Done");
		
		}catch (ClassNotFoundException | SQLException  e) {
			setRes("Error");
		}
	}

	public void editNotice(int id,String b_id,String description,String date) {
		PreparedStatement ps;
		
		try {
			Connection conn = DB.getConn();
			
				ps = conn.prepareStatement("UPDATE notice SET where id=?");
	
				ps.setString(1, b_id);
				ps.setString(2, description);
				ps.setString(3, date);
			    ps.setInt(4,id);
				ps.execute();
				ps.close();
				conn.close();
				setRes("Done");
				
		
		}catch (ClassNotFoundException | SQLException  e) {
			setRes("Error");
		}
	}

	public void deleteNotice(int id) {
		PreparedStatement ps;
		
		try {
			Connection conn = DB.getConn();
			
			ps = conn.prepareStatement("DELETE FROM notice WHERE id=?");
			ps.setInt(1, id);
			ps.execute();
			setRes("Done");
		
		}catch (ClassNotFoundException | SQLException  e) {
			setRes("Error");
		}
	}
	public JSONObject getOneUsers(int id) {
		Connection connection;
		PreparedStatement preparedStatement;
		JSONObject json = new JSONObject();
		
		try {
			connection = DB.getConn();
			
			preparedStatement = connection.prepareStatement("SELECT * FROM notice where id=?");
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			while(rs.next())
			{
				json.put("id", rs.getInt(1));
				json.put("b_id", rs.getString(2));
				json.put("description", rs.getString(3));
				json.put("date", rs.getString(4));
				
				
				
			}
			
		}catch (ClassNotFoundException | SQLException  e) {
			setRes("Error");
		}
		return json;
	}

}

	
}

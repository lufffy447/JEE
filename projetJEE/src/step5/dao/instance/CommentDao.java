package step5.dao.instance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import step5.model.CommentBean;
import step5.model.RecipeModel;

public class CommentDao {
	private Connection connection;
	private String dB_HOST;
	private String dB_PORT;
	private String dB_NAME;
	private String dB_USER;
	private String dB_PWD;
	
	public CommentDao(String DB_HOST,String DB_PORT, String DB_NAME,String DB_USER,String DB_PWD) {
		dB_HOST = DB_HOST;
		dB_PORT = DB_PORT;
		dB_NAME = DB_NAME;
		dB_USER = DB_USER;
		dB_PWD = DB_PWD;
	}
	
	public void addComment(CommentBean comment) {
		// Création de la requête
		java.sql.Statement query;
		try {
			// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "INSERT INTO Comment(username, date, comment, idR) VALUES('"+comment.getUserName()+"','"+comment.getDate()+"','"+comment.getComment()+"','"+comment.getIdR()+"')";
			query = connection.prepareStatement(sql);
			query.executeUpdate(sql);
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<CommentBean> getAllComments(int recipeId){
		java.sql.Statement query;
		//return value
		ArrayList<CommentBean> commentList=new ArrayList<CommentBean>();
		try {
		// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "Select * from Comment where idR='"+recipeId+"'";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet results = query.executeQuery(sql);
			CommentBean comment;
			while(results.next()) {
				comment = new CommentBean(results.getString("username"), results.getString("date"), results.getString("comment"), results.getInt("idR"));
				commentList.add(comment);
			}
			results.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return commentList;
	}
	

}

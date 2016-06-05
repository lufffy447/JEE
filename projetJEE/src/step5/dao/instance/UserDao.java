package step5.dao.instance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import step5.model.RecipeModel;
import step5.model.UserModelBean;

public class UserDao {
	private Connection connection;
	private String dB_HOST;
	private String dB_PORT;
	private String dB_NAME;
	private String dB_USER;
	private String dB_PWD;
	
	public UserDao(String DB_HOST,String DB_PORT, String DB_NAME,String DB_USER,String DB_PWD) {
		dB_HOST = DB_HOST;
		dB_PORT = DB_PORT;
		dB_NAME = DB_NAME;
		dB_USER = DB_USER;
		dB_PWD = DB_PWD;
	}
	
	public void addUser(UserModelBean user) {
		// Création de la requête
		java.sql.Statement query;
		try {
			// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "INSERT INTO User(lastname, surname, age, email, login, pwd) VALUES('"+user.getLastname()+"','"+user.getSurname()+"','"+user.getAge()+"','"+user.getEmail()+"','"+user.getLogin()+"','"+user.getPwd()+"')";
			query = connection.prepareStatement(sql);
			query.executeUpdate(sql);
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<UserModelBean> getAllUser(){
		java.sql.Statement query;
		ArrayList<UserModelBean> userList=new ArrayList<UserModelBean>();
		try {
		// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "Select * from User";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet results = query.executeQuery(sql);
			UserModelBean user;
			while(results.next()) {
				user = new UserModelBean(results.getString("lastname"), results.getString("surname"), results.getInt("age"), results.getString("email"), results.getString("login"), results.getString("pwd"));
				userList.add(user);
			}
			results.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public UserModelBean checkUser(String login, String pwd) {
		java.sql.Statement query;
		UserModelBean user = null;
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "Select * from User where login ='"+login+"' and pwd='"+pwd+"'";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet result = query.executeQuery(sql);
			if(result.next() != false) {
				user = new UserModelBean(result.getString("lastname"), result.getString("surname"), result.getInt("age"), result.getString("email"), result.getString("login"), result.getString("pwd"));
			}else {
				user = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public UserModelBean checkAdmin(String login, String pwd) {
		java.sql.Statement query;
		UserModelBean user = null;
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "Select * from User where login ='"+login+"' "
					+ "and pwd='"+pwd+"' and '"+login+"' in (Select login from Admin)" ;

			query = connection.prepareStatement(sql);
			java.sql.ResultSet result = query.executeQuery(sql);
			if(result.next() != false) {
				user = new UserModelBean(result.getString("lastname"), result.getString("surname"), result.getInt("age"), result.getString("email"), result.getString("login"), result.getString("pwd"));
			}else {
				user = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public void delete(UserModelBean user){
		java.sql.Statement query;
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "delete from User where surname ='"+user.getSurname()+"' and login='"+user.getLogin()+"'";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet results = query.executeQuery(sql);
			results.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public void updateUser(UserModelBean user, boolean isAdmin){
		java.sql.Statement query;
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "UPDATE User SET "
					+ "pwd = '"+user.getPwd()+"', "
					+ "email = '"+user.getEmail()+"', "
					+ "age = '"+user.getAge()+"', "
					+ "lastname = '"+user.getLastname()+"', "
					+ "surname = '"+user.getSurname()+"' "
					+ "WHERE login='"+user.getLogin()+"'";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet results = query.executeQuery(sql);
			results.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public UserModelBean getUserDetails(UserModelBean user){
		java.sql.Statement query;
		UserModelBean userDetails = null;
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "Select * from User where surname ='"+user.getSurname()+"' and login='"+user.getLogin()+"'";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet results = query.executeQuery(sql);
			while(results.next()) {
				userDetails = new UserModelBean(results.getString("surname"), results.getString("lastname"), results.getInt("age"), results.getString("email"), results.getString("login"), results.getString("pwd"));
			}
			results.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDetails;		
	}
	
	private void setUserAdmin(UserModelBean user, boolean isAdmin)
	{
		// Création de la requête
		java.sql.Statement query;
		try {
			// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			//String sql = "Select * from Admin"
			String sql = "INSERT INTO User(lastname, surname, age, email, login, pwd) VALUES('"+user.getLastname()+"','"+user.getSurname()+"','"+user.getAge()+"','"+user.getEmail()+"','"+user.getLogin()+"','"+user.getPwd()+"')";
			query = connection.prepareStatement(sql);
			query.executeUpdate(sql);
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

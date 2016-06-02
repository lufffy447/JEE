package step1.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import step1.model.UserModel;

public class DB {
	private static final String DB_HOST="db-tp.cpe.fr";
	private static final String DB_PORT="3306";
	private static final String DB_NAME="binome14";
	private static final String DB_USER="binome14";
	private static final String DB_PWD="binome14";
	private Connection connection;
	
	public DB() {
		try {
			// ChargementduDriver, puis établissement de laconnexion
			Class.forName("com.mysql.jdbc.Driver");
			
			// Create Connection
			//connection = getConnection();
					
			
			/*}catch (SQLException e) {
				e.printStackTrace();
			}*/
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	public Connection getConnection() {
		try {
			if(connection == null || connection.isClosed()) {
				connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+DB_HOST+":"+DB_PORT+"/"+DB_NAME, DB_USER, DB_PWD);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public ArrayList<UserModel> getData() {
		// return value
		ArrayList<UserModel> userList = new ArrayList<UserModel>();
		
		//Creation de la requête
		java.sql.Statement query;
		try {
			//TODO
			query = getConnection().createStatement();
			String sql = "Select * from User";
			java.sql.ResultSet results = query.executeQuery(sql);
			UserModel user;
			while(results.next()) {
				user = new UserModel(results.getString("lastname"), results.getString("surname"), results.getInt("age"), results.getString("login"), results.getString("pwd"));
				userList.add(user);
			}
			results.close();
			query.close();
			getConnection().close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public void addUser(UserModel user) {
		//Création de la requête
		java.sql.Statement query;
		try {
			//Création de l'élément de la requête
			query = getConnection().createStatement();
			String sql = "INSERT INTO User(lastname, surname, age, login, pwd) VALUES("+user.getLastname()+","+user.getSurname()+","+user.getAge()+","+user.getLogin()+","+user.getPwd()+")";
			query.executeUpdate(sql);
			getConnection().commit();
			query.close();
			getConnection().close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

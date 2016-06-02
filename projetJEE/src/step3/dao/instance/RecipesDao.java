package step3.dao.instance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import step3.model.RecipeModelBean;
import step3.model.UserModelBean;

public class RecipesDao {
	private Connection connection;
	private String dB_HOST;
	private String dB_PORT;
	private String dB_NAME;
	private String dB_USER;
	private String dB_PWD;
	
	public RecipesDao(String DB_HOST,String DB_PORT, String DB_NAME,String DB_USER,String DB_PWD) {
		dB_HOST = DB_HOST;
		dB_PORT = DB_PORT;
		dB_NAME = DB_NAME;
		dB_USER = DB_USER;
		dB_PWD = DB_PWD;
	}
	
	public void addRecipe(RecipeModelBean recipe) {
		// Création de la requête
		java.sql.Statement query;
		try {
			// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "INSERT INTO Recipes(title, description, expertise, duration, nbpeople, type) VALUES('"+recipe.getTitle()+"','"+recipe.getDescription()+"','"+recipe.getExpertise()+"','"+recipe.getDuration()+"','"+recipe.getNbpeople()+"','"+recipe.getType()+"')";
			query = connection.prepareStatement(sql);
			query.executeUpdate(sql);
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<RecipeModelBean> getAllRecipes() {
		java.sql.Statement query;
		//return value
		ArrayList<RecipeModelBean> recipeList=new ArrayList<RecipeModelBean>();
		try {
		// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "Select * from Recipes";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet results = query.executeQuery(sql);
			RecipeModelBean recipe;
			while(results.next()) {
				recipe = new RecipeModelBean(results.getString("title"), results.getString("description"), results.getInt("expertise"), results.getInt("duration"), results.getInt("nbpeople"), results.getString("type"));
				recipeList.add(recipe);
			}
			results.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recipeList;
	}
}

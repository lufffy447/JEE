package step5.dao.instance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import step3.model.RecipeModelBean;
import step5.model.RecipeModel;
import step5.model.SearchRecipeBean;

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
	
	public void addRecipe(RecipeModel recipe) {
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
	
	public ArrayList<RecipeModel> getAllRecipes() {
		java.sql.Statement query;
		//return value
		ArrayList<RecipeModel> recipeList=new ArrayList<RecipeModel>();
		try {
		// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "Select * from Recipes";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet results = query.executeQuery(sql);
			RecipeModel recipe;
			while(results.next()) {
				recipe = new RecipeModel(results.getInt("id"), results.getString("title"), results.getString("description"), results.getInt("expertise"), results.getInt("duration"), results.getInt("nbpeople"), results.getString("type"));
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
	
	public ArrayList<RecipeModel> searchRecipes(RecipeModel recipe) {
		java.sql.PreparedStatement query;
		//return value
		ArrayList<RecipeModel> recipeList=new ArrayList<RecipeModel>();
		try {
		// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			
			String sql = "Select * from Recipes where title like ? AND description like ? AND type like ? AND expertise like ? AND duration like ? AND nbpeople like ?";
			
			query = connection.prepareStatement(sql);
			query.setString(1, searchValue(recipe.getTitle()));
			query.setString(2, searchValue(recipe.getDescription()));
			query.setString(3, searchValue(recipe.getType()));
			query.setString(4, searchValue(recipe.getExpertise()));
			query.setString(5, searchValue(recipe.getDuration()));
			query.setString(6, searchValue(recipe.getNbpeople()));
			//System.out.println("YOOOOOOOOOOOOOOOOOOOOOOOO "+query.toString());
			java.sql.ResultSet results = query.executeQuery();
			RecipeModel result;
			while(results.next()) {
				result = new RecipeModel(results.getInt("id"), results.getString("title"), results.getString("description"), results.getInt("expertise"), results.getInt("duration"), results.getInt("nbpeople"), results.getString("type"));
				recipeList.add(result);
			}
			results.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return recipeList;
	}

	private String searchValue(Object value) {
		String data=String.valueOf(value);
		if(SearchRecipeBean.ALL_VALUES_STRING.equals(data) || String.valueOf(SearchRecipeBean.ALL_VALUES_INT).equals(data)){
			return "%%";
		}
		return "%"+data+"%";
	}
	
	/*public List<String> getAllTypes() {
		java.sql.Statement query;
		//return value
		ArrayList<String> types=new ArrayList<String>();
		try {
		// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "Select type from Recipes";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet results = query.executeQuery(sql);
			while(results.next()) {
				types.add(results.getString("type"));
			}
			results.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return types;
	}*/
}
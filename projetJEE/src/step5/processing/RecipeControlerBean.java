package step5.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.sun.faces.context.SessionMap;

import step5.dao.fabric.DaoFabric;
import step5.dao.instance.CommentDao;
import step5.dao.instance.RecipesDao;
import step5.model.CommentBean;
import step5.model.RecipeListModelBean;
import step5.model.RecipeModel;

@ManagedBean(name="recipeControler")
@ApplicationScoped
public class RecipeControlerBean {
	private RecipesDao recipeDao;
	private CommentDao commentDao;

	public RecipeControlerBean() {
		this.recipeDao = DaoFabric.getInstance().createRecipesDao();
		this.commentDao = DaoFabric.getInstance().createCommentDao();
	}

	public void loadAllRecipe() {
		ArrayList<RecipeModel> list = this.recipeDao.getAllRecipes();
		RecipeListModelBean recipeList = new RecipeListModelBean();
		for (RecipeModel recipe : list) {
			recipeList.addRecipeList(recipe);
		}
		// récupère l'espace de mémoire de JSF
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		// place la liste de recette dans l'espace de mémoire de JSF
		sessionMap.put("recipeList", recipeList);
	}
	
	public String addRecipe(RecipeModel recipe){
		this.recipeDao.addRecipe(recipe);
		return "succesfulRegister";
	}
	
	public String searchRecipe(RecipeModel recipe){
		List<RecipeModel> recipeList = this.recipeDao.searchRecipes(recipe);
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("searchRecipeList", recipeList);
		return "recipesList";
	}
	
	public String displayRecipeDetail(RecipeModel recipe){
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		List<CommentBean> comments = new ArrayList<CommentBean>();
		int id = recipe.getId();
		comments = this.commentDao.getAllComments(id);
		sessionMap.put("idR", id);
		sessionMap.put("commentList", comments);
		sessionMap.put("displayRecipeDetails", recipe);
		
		return "recipeDetail";
	}
		
	/*public List<String> getTypes() {
		System.out.println("hello");
		List<String> types = new ArrayList<String>();
		types = this.recipeDao.getAllTypes();
		return types;
	}*/
}

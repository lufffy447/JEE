package step5.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="searchRecipeB")
@RequestScoped
public class SearchRecipeBean extends RecipeModel {
	public static final String ALL_VALUES_STRING = "[ALL]";
	public static final int ALL_VALUES_INT = -2;

	public SearchRecipeBean() {
		this.setDescription(ALL_VALUES_STRING);
		this.setTitle(ALL_VALUES_STRING);
		this.setType(ALL_VALUES_STRING);
		this.setExpertise(ALL_VALUES_INT);
		this.setNbpeople(ALL_VALUES_INT);
		this.setDuration(ALL_VALUES_INT);
	}
}

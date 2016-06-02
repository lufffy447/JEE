package step5.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="recipeSubmission")
@RequestScoped
//Durée de vue uniquement lors d'une requète
//même propriétés que UserModelBean mais portée différente
public class RecipeSubmissionModelBean extends RecipeModel {
	public RecipeSubmissionModelBean() {
	}
}

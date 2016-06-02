package step5.processing;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import step5.dao.fabric.DaoFabric;
import step5.dao.instance.CommentDao;
import step5.model.CommentBean;
import step5.model.CommentListModelBean;
import step5.model.RecipeListModelBean;
import step5.model.RecipeModel;
import step5.model.UserModelBean;

@ManagedBean
@RequestScoped
public class commentControlerBean{
	private CommentDao commentDao;

	public commentControlerBean() {
		this.commentDao = DaoFabric.getInstance().createCommentDao();
	}
	
	public void leaveComment(CommentBean comment) {
		if(comment.getComment() != "") {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			FacesContext externalContext = FacesContext.getCurrentInstance();
			Map<String, Object> sessionMap = externalContext.getExternalContext().getSessionMap();
			// place l'utilisateur dans l'espace de mémoire de JSF
			UserModelBean user = (UserModelBean) sessionMap.get("loggedUser");
			int idR = (int) sessionMap.get("idR");
			comment.setIdR(idR);
			comment.setUserName(user.getLogin());
			comment.setDate(dateFormat.format(date));
			this.commentDao.addComment(comment);
		}
	}
	
	/*public void getAllComments(){
		ArrayList<CommentBean> list = this.commentDao.getAllComments();
		CommentListModelBean commentList = new CommentListModelBean();
		for (CommentBean comment : list) {
			commentList.addCommentList(comment);
		}
		// récupère l'espace de mémoire de JSF
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		// place la liste de commentaires dans l'espace de mémoire de JSF
		sessionMap.put("commentList", commentList);
	}*/
	
}

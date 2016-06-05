package step5.processing;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import step5.dao.fabric.DaoFabric;
import step5.dao.instance.UserDao;
import step5.model.LoginBean;
import step5.model.UserListModelBean;
import step5.model.UserModelBean;
import step5.model.UserSubmissionModelBean;

@ManagedBean(name="userControler")
@ApplicationScoped
// Utilisation de application scope afin d'offrir un point d'entrée unique à
// l'ensemble des clients
public class UserControlerBean {
	private UserDao userDao;
	private int count = 0;

	public UserControlerBean() {
		this.userDao = DaoFabric.getInstance().createUserDao();
	}
	
	public int getCount() {
		return this.count;
	}

	public String checkUser(LoginBean loginBean) {
		UserModelBean user = this.userDao.checkUser(loginBean.getLogin(),
				loginBean.getPwd());
		FacesContext externalContext = FacesContext.getCurrentInstance();
		if (user != null) {
			setCount(count+1);
			// récupère l'espace de mémoire de JSF
			Map<String, Object> sessionMap = externalContext.getExternalContext().getSessionMap();
			// place l'utilisateur dans l'espace de mémoire de JSF
			sessionMap.put("loggedUser", user);
			// redirect the current page
			return "index";
		} else {
			// redirect the current page
			externalContext.addMessage(null, new FacesMessage("Unknow Login try again"));
			return "index";
		}
	}
	
	public String logOut(UserModelBean user) {
		FacesContext externalContext = FacesContext.getCurrentInstance();
		Map<String, Object> sessionMap = externalContext.getExternalContext().getSessionMap();
		if(sessionMap.get("loggedUser") == user) {
			setCount(count-1);
			sessionMap.remove("loggedUser");
			return "index";	
		} else {
			// redirect the current page
			externalContext.addMessage(null, new FacesMessage("Error try again"));
			return "index";
		}
	}	

	public void setCount(int count) {
		this.count = count;
	}

	public String checkAndAddUser(UserSubmissionModelBean userSubmitted) {
		// Vérifier les propriétés de l'utilisateur
		if (userSubmitted.getAge() > 0 && userSubmitted.getLastname() != null
				&& userSubmitted.getLogin() != null
				&& userSubmitted.getPwd() != null
				&& userSubmitted.getSurname() != null) {
			// ajout de l'utilisateur à la base de données
			this.userDao.addUser(userSubmitted, false);
		}
		return "index";
	}
	
	public ArrayList<UserModelBean> getAllUsers() {
		ArrayList<UserModelBean> list = this.userDao.getAllUser();
		UserListModelBean userList = new UserListModelBean();
		for (UserModelBean user : list) {
			userList.addUserList(user);
		}
		// récupère l'espace de mémoire de JSF
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		// place la liste de recette dans l'espace de mémoire de JSF
		sessionMap.put("userList", userList);
		return list;
	}
	
	public void delete(UserModelBean user) {
		this.userDao.delete(user);
	}
	
	public UserModelBean displayUserEdition(UserModelBean user){
		UserModelBean userDetails;
		userDetails = this.userDao.getUserDetails(user);
		return userDetails;
	}
}

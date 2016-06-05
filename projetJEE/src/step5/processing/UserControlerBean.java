package step5.processing;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
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
			this.userDao.addUser(userSubmitted);
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
	
	public void validateName(FacesContext context, UIComponent comp, Object value) {
		String name = (String) value;
		Pattern p = Pattern.compile("[a-zA-Z0-9]+");
		Matcher m = p.matcher(name);
		if(!m.matches()) {
            ((UIInput) comp).setValid(false);
            
            FacesMessage message = new FacesMessage(
                    "Name should match pattern [a-zA-Z0-9]+");
            context.addMessage(comp.getClientId(context), message);	
            
            comp.getAttributes().put("styleClass", "ui-input-invalid");
		}
		else {
			comp.getAttributes().put("styleClass", "ui-input-valid");
		}
	}
	
	public void validateAge(FacesContext context, UIComponent comp, Object value) {
		int age = (int) value;
		if(age > 100 || age <=0) {
            ((UIInput) comp).setValid(false);
            
            FacesMessage message = new FacesMessage(
                    "Age should be between 1 and 100");
            context.addMessage(comp.getClientId(context), message);	
            
            comp.getAttributes().put("styleClass", "ui-input-invalid");
		}
		else {
			comp.getAttributes().put("styleClass", "ui-input-valid");
		}		
	}
	
	public void validateEmail(FacesContext context, UIComponent comp, Object value) {
		String email = (String) value;
		Pattern p = Pattern.compile("[a-zA-Z0-9-._]+@[a-zA-Z0-9-._]+.[a-z]+");
		Matcher m = p.matcher(email);
		if(!m.matches()) {
            ((UIInput) comp).setValid(false);
            
            FacesMessage message = new FacesMessage(
                    "Email should match pattern [a-zA-Z0-9-._]+@[a-zA-Z0-9-._]+.[a-z]+");
            context.addMessage(comp.getClientId(context), message);			
            
            comp.getAttributes().put("styleClass", "ui-input-invalid");
		}
		else {
			comp.getAttributes().put("styleClass", "ui-input-valid");
		}		
	}
	
	public void validateLogin(FacesContext context, UIComponent comp, Object value) {
		String login = (String) value;
		Pattern p = Pattern.compile("[a-zA-Z0-9-._]+");
		Matcher m = p.matcher(login);
		if(!m.matches()) {
            ((UIInput) comp).setValid(false);
            
            FacesMessage message = new FacesMessage(
                    "Login should match pattern [a-zA-Z0-9-._]+");
            context.addMessage(comp.getClientId(context), message);		
            
            comp.getAttributes().put("styleClass", "ui-input-invalid");
		}
		else {
			comp.getAttributes().put("styleClass", "ui-input-valid");
		}		
	}
	
	public void validatePassword(FacesContext context, UIComponent comp, Object value) {
		UIInput otherInput = (UIInput)comp.findComponent("pwd");
	    String otherPass = (String)otherInput.getValue();
	    String pass = (String) value;
		if(!pass.equals(otherPass)) {
            ((UIInput) comp).setValid(false);
            
            FacesMessage message = new FacesMessage(
                    "Passwords should be similar !");
            context.addMessage(comp.getClientId(context), message);		
            
            otherInput.getAttributes().put("styleClass", "ui-input-invalid");
            comp.getAttributes().put("styleClass", "ui-input-invalid");
		}
		else {
			otherInput.getAttributes().put("styleClass", "ui-input-valid");
			comp.getAttributes().put("styleClass", "ui-input-valid");
		}		
	}
	
}

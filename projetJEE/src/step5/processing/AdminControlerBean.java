package step5.processing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import step5.dao.fabric.DaoFabric;
import step5.dao.instance.UserDao;
import step5.model.LoginBean;
import step5.model.UserModelBean;

@ManagedBean(name="adminControler")
@ApplicationScoped
public class AdminControlerBean 
{
	private UserDao userDao;
	
	public AdminControlerBean() 
	{
		this.userDao = DaoFabric.getInstance().createUserDao();
	}
	
	public String checkAdmin(LoginBean loginBean) 
	{
		UserModelBean user = this.userDao.checkAdmin(loginBean.getLogin(),
				loginBean.getPwd());
		FacesContext externalContext = FacesContext.getCurrentInstance();
		if (user != null) 
		{
			Map<String, Object> applicationMap = externalContext.getExternalContext().getApplicationMap();
			applicationMap.put("loggedAdmin", user);
			
			Date currentDate = new Date();
			applicationMap.put("currentAdminConnection", currentDate);

			return "adminManagement";
		} 
		else 
		{
			externalContext.addMessage(null, new FacesMessage("Unknown Login try again"));
			return "admin";
		}
	}
	

	
	public String logOut(UserModelBean user) 
	{
		FacesContext externalContext = FacesContext.getCurrentInstance();
		Map<String, Object> applicationMap = externalContext.getExternalContext().getApplicationMap();
		if(applicationMap.get("loggedAdmin") == user) 
		{
			applicationMap.remove("loggedAdmin");
			
			Date connectionDate = (Date) applicationMap.get("currentAdminConnection");
			applicationMap.put("lastAdminConnection", connectionDate);
			
			applicationMap.put("lastAdminDuration", connectionDate.getTime() - new Date().getTime());

			return "admin";	
		} 
		else 
		{
			externalContext.addMessage(null, new FacesMessage("Error try again"));
			return "admin";
		}
	}
	
	public String getLastConnectionDate()
	{
		FacesContext externalContext = FacesContext.getCurrentInstance();
		Map<String, Object> applicationMap = externalContext.getExternalContext().getApplicationMap();
	
		Date date = (Date) applicationMap.get("lastAdminConnection");
		if (date != null)
		{
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			return df.format(date);
		}
		else
		{
			return "First admin connection !";
		}
	}
	
	public String getLastConnectionDuration()
	{
		FacesContext externalContext = FacesContext.getCurrentInstance();
		Map<String, Object> applicationMap = externalContext.getExternalContext().getApplicationMap();
		
		Long duration = (Long) applicationMap.get("lastAdminDuration");
		if (duration != null)
		{
			return String.valueOf(TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)) + " min"; 
		}
		else
		{
			return "";
		}
	}
	
	public String deleteUser(UserModelBean user)
	{
		this.userDao.delete(user);
		
		return "";
	}
	
	public String updateUser(UserModelBean user, boolean isAdmin)
	{
		this.userDao.updateUser(user, isAdmin);
		return "";
	}
	
	public String createUser(UserModelBean user, boolean isAdmin)
	{
		if (user.getAge() > 0 
				&& user.getLastname() != null
				&& user.getLogin() != null
				&& user.getPwd() != null
				&& user.getSurname() != null) 
		{
			this.userDao.addUser(user, isAdmin);
		}
		
		return "";
	}
	
	public boolean isAdmin(UserModelBean user)
	{
		return this.userDao.isAdmin(user);
	}
	
	public void displayUserEdition(UserModelBean user)
	{
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> requestMap = externalContext.getSessionMap();
		requestMap.put("userDetails", user);
	}
	
	public void displayUserCreation()
	{
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> requestMap = externalContext.getSessionMap();
		UserModelBean user = new UserModelBean(null, null, 0, null, null, null);
		requestMap.put("userDetails", user);
	}

}

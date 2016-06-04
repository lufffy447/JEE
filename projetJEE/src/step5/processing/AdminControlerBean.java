package step5.processing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
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
		UserModelBean user = this.userDao.checkUser(loginBean.getLogin(),
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
}

package step2.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step2.db.DB;
import step2.model.UserModelBean;

/**
 * Servlet implementation class Servlet3
 */
@WebServlet("/Servlet3")
public class Servlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DB db;
       
    public Servlet3() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	
    	//Vérifie si DB existe dans l'espace de mémoire partagé entre les Servlets
    	//si oui on les récupère, si non  on le crée et on l'ajoute dans l'espace de mémoire
    	//partagé entre les servlets
    	if(getServletContext().getAttribute("DB" )!=null) {
    		db = (DB)getServletContext().getAttribute("DB");
    	}else {
    		db = new DB();
    		getServletContext().setAttribute("DB", db);
    	}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//nothing to do
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserModelBean user = (UserModelBean)request.getSession().getAttribute("myUser");
		db.addUser(user);
		response.sendRedirect(request.getContextPath() + "/step2/display.jsp");
	}

}

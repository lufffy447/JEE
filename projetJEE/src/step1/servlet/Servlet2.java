package step1.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step1.db.DB;
import step1.model.UserModel;

/**
 * Servlet implementation class Servlet2
 */
@WebServlet("/Servlet2")
public class Servlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DB db;
       
    public Servlet2() {
        super();
        db = new DB();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Crée un utilisateur depuis les informations transmises
		//Attention ERREUR SI LES INFORMATIONS TRANSMISES SONT INEXACTES
		UserModel user = new UserModel(request.getParameter("lastname"), request.getParameter("surname"), Integer.valueOf(request.getParameter("age")), request.getParameter("login"), request.getParameter("pwd"));
		//Demande à DB d'ajouter l'utilisateur
		db.addUser(user);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//redirection sur le doGet car même action
		doGet(request, response);
	}

}

package step5.model;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CommentBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userName;
	private String date;
	private String comment;
	private int idR;

	public CommentBean() {
		
	}
	
	public CommentBean(String userName, String date, String comment, int idR) {
		this.userName = userName;
		this.date = date;
		this.comment = comment;
		this.idR = idR;
	}
	
	public int getIdR() {
		return idR;
	}

	public void setIdR(int idR) {
		this.idR = idR;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String string) {
		this.date = string;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}

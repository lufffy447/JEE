package step5.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class UserListModelBean {
	private List<UserModelBean> userList;

	public UserListModelBean() {
		userList = new ArrayList<UserModelBean>();
	}

	public void addUserList(UserModelBean user) {
		this.userList.add(user);
	}

	public List<UserModelBean> getUserList() {
		return userList;
	}
}

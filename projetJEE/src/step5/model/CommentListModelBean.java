package step5.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CommentListModelBean {
	private List<CommentBean> commentList;

	public CommentListModelBean() {
		commentList = new ArrayList<CommentBean>();
	}

	public void addCommentList(CommentBean comment) {
		this.commentList.add(comment);
	}

	public List<CommentBean> getCommentList() {
		return commentList;
	}
}

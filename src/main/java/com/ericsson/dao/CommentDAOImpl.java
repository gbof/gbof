package com.ericsson.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.ericsson.model.Comment;
import com.ericsson.model.User;
import com.ericsson.service.UserService;

@Repository
public class CommentDAOImpl implements CommentDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserService us;
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	public List<Comment> getAllComments() {
		List<Comment> commentsList = new ArrayList<Comment>();
		
			
		List<User> listt = us.getAllUsers();
		String sql = "FROM Comment c where (";
		for(int i=0;i<listt.size();i++){
				Integer user_id = listt.get(i).getId();
				sql = sql+"c.creator_id = '"+user_id+"'";
				if(i!=listt.size()-1)
					sql = sql + " or ";
				else
					sql = sql + ") and c.confirmed=false";
			}
		sql = sql+" order by user_id";
		Query query = openSession().createQuery(sql);
		commentsList = query.list();
		
		if (commentsList.size() > 0)
			return commentsList;
		else
			return null;	
	}
	
	public List<Comment> getYourComments(Integer id)
	{
		List<Comment> commentList = new ArrayList<Comment>();
		

		Query query = openSession().createQuery("from Comment where creator_id = :id and confirmed=false");


		query.setParameter("id", id);
		commentList = query.list();
		if (commentList.size() > 0)
			return commentList;
		else
			return null;	
		
	}
	
	public List<Comment> getCommentsYouGave(Integer id)
	{
		List<Comment> commentList = new ArrayList<Comment>();
		
		Query query = openSession().createQuery("from Comment where user_id = :id");
		query.setParameter("id", id);
		commentList = query.list();
		if (commentList.size() > 0)
			return commentList;
		else
			return null;	
		
	}
	
	public List<Comment> getConfirmedComments() {
		List<Comment> commentsConfirmedList = new ArrayList<Comment>();
	
	List<User> listt = us.getAllUsers();
	String sql = "FROM Comment c where (";
	for(int i=0;i<listt.size();i++){
			Integer user_id = listt.get(i).getId();
			sql = sql+"c.creator_id = '"+user_id+"'";
			if(i!=listt.size()-1)
				sql = sql + " or ";
			else
				sql = sql + ") and c.confirmed=true";
		}
		
	Query query = openSession().createQuery(sql);
	commentsConfirmedList = query.list();
	if (commentsConfirmedList.size() > 0)
		return commentsConfirmedList;
	else
		return null;		
	}
    
	public void addComment(String message1, String message2, Integer ballsNumber, Integer user_id, Integer commentToUserId){
		String query = "INSERT INTO comments (user_id, first_com, second_com, confirmed, creator_id, balls_per_com) VALUES ('"+ commentToUserId +"', '"+ message1 +"', '"+ message2 +"', '"+ 0 +"', '"+ user_id +"', '"+ ballsNumber +"')";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}
	
	public void editComment(String message1, String message2, Integer ballsNumber, Integer com_id){
		String query = "UPDATE comments SET first_com='"+message1+"', second_com='"+message2+"', balls_per_com='"+ballsNumber+"'  WHERE com_id ='"+com_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}
	
	public void editCommentBalls(Integer com_id){
		String query = "UPDATE comments SET balls_per_com=0  WHERE com_id ='"+com_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}
	
	public List<Double> getBallValue(Double money){
		List<Double> allBalls = new ArrayList<Double>();
		String sql = "select '"+ money +"'/(sum(balls_to_give)+sum(received_balls)) from Ball";
		Query query = openSession().createQuery(sql);
		allBalls = query.list();
		if (allBalls.size() > 0)
			return allBalls;
		else
			return null;	
	}
	
	public List<Double> getCash(){
		List<Double> allBalls = new ArrayList<Double>();
		String sql = "select sum(cash) from Ball";
		Query query = openSession().createQuery(sql);
		allBalls = query.list();
		if (allBalls.size() > 0)
			return allBalls;
		else
			return null;	
	}
	
	
	public List<Long> getBallValue2(){
		List<Long> allBalls = new ArrayList<Long>();
		List<User> listt = us.getAllUsers();
		String sql = "select (sum(b.balls_to_give)+sum(b.received_balls)) from Ball b where(";
		for(int i=0;i<listt.size();i++){
				Integer ball_id = listt.get(i).getBall().getBallsId();
				sql = sql+"b.balls_id = '"+ball_id+"'";
				if(i!=listt.size()-1)
					sql = sql + " or ";
				else
					sql = sql + ")";
			}
		Query query = openSession().createQuery(sql);
		allBalls = query.list();
		if (allBalls.size() > 0)
			return allBalls;
		else
			return null;	
	}
	
	public List<Integer> getAllBallsGivenTo(Integer id, Integer id2){
		List<Integer> ballsList = new ArrayList<Integer>();
		String sql = "select sum(c.balls_per_com) from Comment c where c.creator_id = '"+ id +"' and c.user = '"+ id2 +"'";
		Query query = openSession().createQuery(sql);
		ballsList = query.list();
		if (ballsList.size() > 0)
			return ballsList;
		else
			return null;
		
	}

	@Override
	public void removeComment(Integer com_id) {
		String query = "delete from comments where com_id="+com_id;
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}
	
	public boolean checkNull(String string){
		if(string=="")return true;
		else
			return false;
	}
	
	public void setConfirm(Integer id){
		String query = "UPDATE comments SET confirmed = '1' WHERE com_id = '"+ id + "'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}
	
	public void setConfirmAll()
	{
		String query = "UPDATE comments SET confirmed = 1";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}

	public Comment getCommentId(Integer id) {
		List<Comment> commentList = new ArrayList<Comment>();
		Query query = openSession().createQuery("from Comment where com_id = :id");
		query.setParameter("id", id);
		commentList = query.list();
		if (commentList.size() > 0)
			return commentList.get(0);
		else
			return null;	

	}
	
	

}

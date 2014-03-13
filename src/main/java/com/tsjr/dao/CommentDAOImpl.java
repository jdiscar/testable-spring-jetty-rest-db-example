package com.tsjr.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tsjr.entities.Comment;

/**
 * CommentDAOImpl
 */
public class CommentDAOImpl extends HibernateDaoSupport implements CommentDAO  {
	public Comment get(int id) {
		return (Comment)getHibernateTemplate().get(Comment.class, id);
	}

	public int create(Comment Comment) {
		return (Integer)getHibernateTemplate().save(Comment);
	}
}

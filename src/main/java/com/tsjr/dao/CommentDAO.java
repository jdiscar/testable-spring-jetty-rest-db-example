package com.tsjr.dao;

import com.tsjr.entities.Comment;

/**
 * CommentDAO
 */
public interface CommentDAO {
	public Comment get(int id);

	public int create(Comment comment);
}

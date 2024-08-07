package com.koreaIT.JAM.service;

import java.sql.Connection;

import com.koreaIT.JAM.dao.ArticleDao;

public class ArticleService {
	Connection conn;
	private ArticleDao articleDao;

	public ArticleService(Connection conn) {
		this.conn = conn;
		this.articleDao = new ArticleDao(conn);
	}

	public int doWrite(String title, String body) {
		return articleDao.doWrite(title, body);
	}
}

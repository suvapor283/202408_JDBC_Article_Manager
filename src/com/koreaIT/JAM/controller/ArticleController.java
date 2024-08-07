package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.service.ArticleService;

public class ArticleController {
	private Scanner sc;
	private Connection conn;
	private ArticleService articleService;
	
	public ArticleController(Scanner sc, Connection conn) {
		this.sc = sc;
		this.conn = conn;
		this.articleService = new ArticleService(conn);	
	}

	public void doWrite() {
		System.out.printf("제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("내용 : ");
		String body = sc.nextLine().trim();

		int id = articleService.doWrite(title, body);

		System.out.printf("%d번 게시물이 작성되었습니다.\n", id);	
	}

	public void showList() {
		List<Article> articles = new ArrayList<>();

		List<Map<String, Object>> articleListMap = articleDao.showList();

		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}

		if (articles.isEmpty()) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}

		System.out.println("번호	|	제목	|	작성일");
		for (Article article : articles) {
			System.out.printf("%d	|	%s	|	%s\n", article.getId(), article.getTitle(),article.getUpdateDate());
		}
	}

	public void showDetail(String cmd) {
		int id = -1;

		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (NumberFormatException e) {
			System.out.println("명령어를 올바르게 입력해주세요.");
			return;
		}

		Map<String, Object> articleMap = articleDao.showDetail(id);

		if (articleMap.isEmpty()) {
			System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("번호 : %d\n", articleMap.get("id"));
		System.out.printf("작성일 : %s\n", articleMap.get("regDate"));
		System.out.printf("수정일 : %s\n", articleMap.get("updateDate"));
		System.out.printf("제목 : %s\n", articleMap.get("title"));
		System.out.printf("내용 : %s\n", articleMap.get("body"));
	}

	public void doModify(String cmd) {
		int id = -1;

		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (NumberFormatException e) {
			System.out.println("명령어를 올바르게 입력해주세요.");
		}

		int isExists = articleDao.isExists(id);

		if (isExists == 0) {
			System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine().trim();

		articleDao.doModify(id, title, body);

		System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
	}

	public void doDelete(String cmd) {
		int id = -1;

		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (NumberFormatException e) {
			System.out.println("명령어를 올바르게 입력해주세요.");
		}

		int isExists = articleDao.isExists(id);

		if (isExists == 0) {
			System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}

		articleDao.doDelete(id);

		System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
	}
}
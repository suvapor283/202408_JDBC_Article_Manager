package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class Main {

	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

		String url = "jdbc:mysql://localhost:3306/2024_08_JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
		String username = "root";
		String password = "";

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);

			while (true) {
				System.out.printf("명령어 ) ");
				String cmd = sc.nextLine().trim();

				if (cmd.equals("exit")) {
					break;
				}

				if (cmd.isEmpty()) {
					System.out.println("명령어를 입력해주세요.");
					continue;
				}

				if (cmd.equals("article write")) {
					System.out.printf("제목 : ");
					String title = sc.nextLine().trim();
					System.out.printf("내용 : ");
					String body = sc.nextLine().trim();

					SecSql sql = new SecSql();
					sql.append("INSERT INTO article");
					sql.append("SET regDate = NOW()");
					sql.append(", updateDate = NOW()");
					sql.append(", title = ?", title);
					sql.append(", `body` = ?", body);

					int id = DBUtil.insert(conn, sql);

					System.out.printf("%d번 게시물이 작성되었습니다.\n", id);
				}

				else if (cmd.equals("article list")) {
					List<Article> articles = new ArrayList<>();

					SecSql sql = new SecSql();
					sql.append("SELECT * FROM article");
					sql.append("ORDER BY id DESC");

					List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

					for (Map<String, Object> articleMap : articleListMap) {
						articles.add(new Article(articleMap));
					}

					if (articles.isEmpty()) {
						System.out.println("게시물이 존재하지 않습니다.");
						continue;
					}

					System.out.println("번호	|	제목	|	작성일");
					for (Article article : articles) {
						System.out.printf("%d	|	%s	|	%s\n", article.getId(), article.getTitle(),article.getUpdateDate());
					}
				}

				else if (cmd.startsWith("article detail ")) {
					int id = -1;
							
					try {
						id = Integer.parseInt(cmd.split(" ")[2]);
					} catch (NumberFormatException e) {
						System.out.println("명령어를 올바르게 입력해주세요.");
						continue;
					}

					SecSql sql = new SecSql();
					sql.append("SELECT * FROM article");
					sql.append("WHERE id = ?", id);

					Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

					if (articleMap.isEmpty()) {
						System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
						continue;
					}

					System.out.printf("번호 : %d\n", articleMap.get("id"));
					System.out.printf("작성일 : %s\n", articleMap.get("regDate"));
					System.out.printf("수정일 : %s\n", articleMap.get("updateDate"));
					System.out.printf("제목 : %s\n", articleMap.get("title"));
					System.out.printf("내용 : %s\n", articleMap.get("body"));

				}
				
				else if (cmd.startsWith("article modify ")) {
					int id = -1;
					
					try {
						id = Integer.parseInt(cmd.split(" ")[2]);
					} catch (NumberFormatException e) {
						System.out.println("명령어를 올바르게 입력해주세요.");
					}

						SecSql sql = new SecSql();
						sql.append("SELECT COUNT(id) FROM article");
						sql.append("WHERE id = ?", id);
						
						int articleCnt = DBUtil.selectRowIntValue(conn, sql);

						if (articleCnt == 0) {
							System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
							continue;
						}

						System.out.printf("수정할 제목 : ");
						String title = sc.nextLine().trim();
						System.out.printf("수정할 내용 : ");
						String body = sc.nextLine().trim();

						sql = new SecSql();
						sql.append("UPDATE article");
						sql.append("SET title = ?", title);
						sql.append(", `body` = ?", body);
						sql.append("WHERE id = ?", id);

						DBUtil.update(conn, sql);

						System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
				}

				else if (cmd.startsWith("article delete ")) {
					try {
						int id = Integer.parseInt(cmd.split(" ")[2]);

						SecSql sql = new SecSql();
						sql.append("SELECT * FROM article");
						sql.append("WHERE id = ?", id);

						Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

						if (articleMap.size() == 0) {
							System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
							continue;
						}

						sql = new SecSql();
						sql.append("DELETE FROM article");
						sql.append("WHERE id = ?", id);

						DBUtil.delete(conn, sql);

						System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

					} catch (NumberFormatException e) {
						System.out.println("명령어를 올바르게 입력해주세요.");
					}
				}

				else {
					System.out.println("존재하지 않는 명령어입니다.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		sc.close();

		System.out.println("== 프로그램 종료 ==");
	}
}
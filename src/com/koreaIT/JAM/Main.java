package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;

public class Main {

	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 0;
		List<Article> articles = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/2024_08_JAM";
        String username = "root";
        String password = "";
		
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
				
				Connection conn = null;
		        PreparedStatement pstmt = null;
		        
				System.out.printf("제목 : ");
				String title = sc.nextLine().trim();
				System.out.printf("내용 : ");
				String body = sc.nextLine().trim();
				
				try {
		            conn = DriverManager.getConnection(url, username, password);
		            
		            String sql = "INSERT INTO article";
		            sql += " regDate = NOW()";
		            sql += ", updateDate = NOW()";
		            sql += ", title = '" + title + "'";
		            sql += ", `body` = '" + body + "';";
		            
		            pstmt = conn.prepareStatement(sql);
		            pstmt.executeUpdate();

		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            if (pstmt != null) {
		                try {
		                	pstmt.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (conn != null) {
		                try {
		                    conn.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		        }
				
				articles.add(new Article(++lastArticleId, title, body));
				System.out.printf("%d번 게시물이 작성되었습니다.\n", lastArticleId);
			}
			
			else if (cmd.equals("article list")) {
				if (articles.isEmpty()) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}
				
				System.out.println("번호	|	제목");
				for (int i = articles.size() - 1; i >= 0; i--) {
					System.out.printf("%d	|	%s\n", articles.get(i).getId(), articles.get(i).getTitle());
				}
			}
			
			else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}
		
		sc.close();
		
		System.out.println("== 프로그램 종료 ==");
	}
}
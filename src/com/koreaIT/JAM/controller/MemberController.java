package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.JAM.dao.MemberDao;

public class MemberController {
	private Scanner sc;
	private MemberDao memberDao;

	public MemberController(Scanner sc, Connection conn) {
		this.sc = sc;
		this.memberDao = new MemberDao(conn);
	}

	public void doJoin() {
		String loginId = null;
		String loginPw = null;
		String name = null;
		
		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if (loginId.isEmpty()) {
				System.out.println("아이디는 필수 입력 정보입니다.");
				continue;
			}

			int isLoginDup = memberDao.isLoginDup(loginId);

			if (isLoginDup == 1) {
				System.out.println("이미 존재하는 아이디입니다.");
				continue;
			}
			
			System.out.printf("[ %s ] 는 사용가능한 아이디입니다.\n", loginId);
	
			break;
		}
		
		while (true) {
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginPw.isEmpty()) {
				System.out.println("비밀번호는 필수 입력 정보입니다.");
				continue;
			}
			
			System.out.printf("비밀번호 확인 : ");
			String loginPwChk = sc.nextLine().trim();

			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("비밀번호를 확인해주세요.");
				continue;
			}
			
			break;
		}
		
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			
			if (name.isEmpty()) {
				System.out.println("이름은 필수 입력 정보입니다.");
				continue;
			}
			
			break;
		}	
		
		memberDao.doJoin(loginId, loginPw, name);
		
		System.out.printf("[ %s ] 님 회원가입을 축하합니다.\n", name);
	}

//	public void doLogin() {
//		System.out.printf("아이디 : ");
//		String loginId = sc.nextLine().trim();
//		System.out.printf("비밀번호 : ");
//		String loginPw = sc.nextLine().trim();
//		
//		SecSql sql = new SecSql();
//		sql.append("SELECT * FROM `member`");
//		sql.append("WHERE loginId = ?", loginId);
//		
//		Map<String, Object> memberMap = DBUtil.selectRow(conn, sql);
//		
//		if (memberMap.isEmpty()) {
//			System.out.println("아이디가 일치하지 않습니다.");
//			continue;
//		}
//		
//		if (loginPw.equals(memberMap.get("loginPw"))) {
//			System.out.println("비밀번호가 일치하지 않습니다.");
//			continue;
//		}
//		
//		System.out.printf("%s님 환영합니다.\n", loginId);	
//	}
}
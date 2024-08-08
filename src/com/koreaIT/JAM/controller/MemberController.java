package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Member;
import com.koreaIT.JAM.service.MemberService;

public class MemberController {
	private Scanner sc;
	private MemberService memberService;

	public MemberController(Scanner sc, Connection conn) {
		this.sc = sc;
		this.memberService = new MemberService(conn);
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

			int isLoginDup = memberService.isLoginIdDup(loginId);

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

		memberService.joinMember(loginId, loginPw, name);

		System.out.printf("[ %s ] 님 회원가입을 축하합니다.\n", name);
	}

	public void doLogin() {
		String loginId = null;
		String loginPw = null;

		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginId.isEmpty()) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}

			if (loginPw.isEmpty()) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}

			Member member = memberService.getMemberByLoginId(loginId);

			if (member == null) {
				System.out.println("아이디를 확인해주세요.");
				continue;
			}

			if (member.getLoginPw().equals(loginPw) == false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			
			memberService.loginMember(member);
			break;
		}
		
		System.out.printf("%s님 환영합니다.\n", loginId);
	}
}
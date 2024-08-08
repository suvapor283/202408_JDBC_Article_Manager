package com.koreaIT.JAM.service;

import java.sql.Connection;
import java.util.Map;

import com.koreaIT.JAM.dao.MemberDao;
import com.koreaIT.JAM.dao.session.Session;
import com.koreaIT.JAM.dto.Member;

public class MemberService {
	private MemberDao memberDao;

	public MemberService(Connection conn) {
		memberDao = new MemberDao(conn);
	}

	public void joinMember(String loginId, String loginPw, String name) {
		memberDao.joinMember(loginId, loginPw, name);
	}
	
	public void loginMember(Member member) {
		Session.loginedMember = member;
	}
	
	public int isLoginIdDup(String loginId) {
		return memberDao.isLoginIdDup(loginId);
	}

	public Member getMemberByLoginId(String loginId) {
		Map<String, Object> memberMap = memberDao.getMember(loginId);
		
		if (memberMap.isEmpty()) {
			return null;
		}
		 
		return new Member(memberMap);
	}
}
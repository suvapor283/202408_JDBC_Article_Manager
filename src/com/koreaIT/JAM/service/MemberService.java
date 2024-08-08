package com.koreaIT.JAM.service;

import java.sql.Connection;

import com.koreaIT.JAM.dao.MemberDao;

public class MemberService {
	private MemberDao memberDao;

	public MemberService(Connection conn) {
		memberDao = new MemberDao(conn);
	}

	public void joinMember(String loginId, String loginPw, String name) {
		memberDao.joinMember(loginId, loginPw, name);
	}
	
	public int isLoginIdDup(String loginId) {
		return memberDao.isLoginIdDup(loginId);
	}
}
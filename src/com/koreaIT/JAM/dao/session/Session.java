package com.koreaIT.JAM.dao.session;

import com.koreaIT.JAM.dto.Member;

public class Session {

	public static Member loginedMember;
	
	public Session() {
		loginedMember = null;
	}
}

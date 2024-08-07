package com.koreaIT.JAM.dao;

import java.sql.Connection;

import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class MemberDao {
	Connection conn;

	public MemberDao(Connection conn) {
		this.conn = conn;
	}
	
	public void doJoin(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);
		
		DBUtil.insert(conn, sql);
	}

	public int isLoginDup(String loginId) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(id) FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRowIntValue(conn, sql);
	}
}

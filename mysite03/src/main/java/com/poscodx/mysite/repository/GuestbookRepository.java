package com.poscodx.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestBookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;

	public List<GuestBookVo> findAll() {

		return sqlSession.selectList("guestbook.findAll");

	}

	public Boolean insert(GuestBookVo vo) {

		int count = sqlSession.insert("guestbook.insert", vo);
		return count == 1;

	}

	public Boolean deleteByNoAndPassword(Long no, String password) {

		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		int count = sqlSession.delete("guestbook.deleteByNoAndPassword", map);
		return count == 1;

	}

//	public String findPasswordByNo(Long no) {
//		String result = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			conn = dataSource.getConnection();
//
//			String sql = "select password " + "from guestbook " + "where no = ?";
//			pstmt = conn.prepareStatement(sql);
//
//			pstmt.setLong(1, no);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				String password = rs.getString(1);
//
//				result = password;
//			}
//
//		} catch (SQLException e) {
//			System.out.println("Guestbook Select error: " + e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return result;
//	}

}
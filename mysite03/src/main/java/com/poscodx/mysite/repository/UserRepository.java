package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.UserVo;


@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;


	public UserVo findByEmailAndPassword(String email, String password) {
		Map<String,Object> map = new HashMap<>();
		map.put("email",email);
		map.put("password",password);
		
		return sqlSession.selectOne("user.findByEmailAndPassword",map);
	
		
	}

	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo", no);
	}

	public boolean insert(UserVo vo) {
		System.out.println(vo);
		int count = sqlSession.insert("user.insert",vo);
		
		return count == 1;
		
	}

	public void update(UserVo vo) {
		sqlSession.update("user.update",vo);		
	}

	public void deleteByNo(String no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "delete from guestbook " + "where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, no);

			rs = pstmt.executeQuery();

		} catch (SQLException e) {
			System.out.println("Guestbook Delete error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
		private Connection getConnection() throws SQLException {
			Connection conn = null;

			try {
				Class.forName("org.mariadb.jdbc.Driver");
				
				String url = "jdbc:mariadb://192.168.64.2:3307/webdb?charset=utf8";
				conn = DriverManager.getConnection(url, "webdb", "webdb");
			} catch (ClassNotFoundException e) {
				System.out.println("드라이버 로딩 실패:" + e);
			} 
			
			return conn;
		}
		public List<UserVo> findAll() {
			List<UserVo> result = new ArrayList<UserVo>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = getConnection();

				String sql = "select no, name, contents, reg_date " + "from guestbook " + "order by reg_date desc";
				pstmt = conn.prepareStatement(sql);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					int no = rs.getInt(1);
					String name = rs.getString(2);
					String contents = rs.getString(3);
					String regDate = rs.getString(4);

					UserVo vo = new UserVo();
					vo.setName(name);

					result.add(vo);
				}

			} catch (SQLException e) {
				System.out.println("Guestbook Select error: " + e);
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			return result;
		}


	
}
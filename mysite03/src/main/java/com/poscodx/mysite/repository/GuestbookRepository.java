package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestBookVo;

@Repository
public class GuestbookRepository {

	public List<GuestBookVo> findAll() {
		List<GuestBookVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "select no, name, password, contents, date_format(reg_date, '%Y/%m/%d %H:%i:%s') from guestbook order by reg_date desc";
			pstmt = conn.prepareStatement(sql);

			// 4. binding

			// 5. SQL 실행
			rs = pstmt.executeQuery();

			// 6. 결과 처리
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String contents = rs.getString(4);
				String regDate = rs.getString(5);

				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setContents(contents);
				vo.setRegDate(regDate);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 7. 자원정리
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

	public Boolean insert(GuestBookVo vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "insert into guestbook values(null, ?, ?, ?, now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			
			int count = pstmt.executeUpdate();
			
			//5. 결과 처리
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	public Boolean deleteByNoAndPassword(Long no, String password) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "delete from guestbook where no = ? and password = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.setString(2, password);
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
	}
	public String findPasswordByNo(Long no) {
		String result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql =
					"select password "
					+ "from guestbook "
					+ "where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String password = rs.getString(1);
				
				result = password;
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

}
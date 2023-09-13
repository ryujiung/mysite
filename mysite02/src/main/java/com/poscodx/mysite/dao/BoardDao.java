package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;

public class BoardDao {

	public List<BoardVo> findAll(int page) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int page1 = 0+(page-1)*5;
	
		

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "select a.no, title, contents, hit, date_format(reg_date, '%Y/%m/%d %H:%i:%s'),g_no,o_no,depth,user_no,b.name, (select ceil(count(*)/5) from board) as total from board a ,user b where a.user_no=b.no  order by g_no desc, o_no asc limit  ?,5";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setInt(1, page1);
			// 5. SQL 실행
			rs = pstmt.executeQuery();

			// 6. 결과 처리
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				String name = rs.getString(10);
				int total = rs.getInt(11);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				vo.setName(name);
				vo.setPage(page);
				vo.setTotal(total);

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
	public BoardVo findByNo(Long no1) {
		BoardVo vo1 = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =  null;
		try {
			conn = getConnection();
			
			String sql = "select *"
					+ " from board"
					+ " where no = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no1);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				
				vo1 = new BoardVo();
				vo1.setNo(no);
				vo1.setTitle(title);
				vo1.setContents(contents);
				vo1.setHit(hit);
				vo1.setRegDate(reg_date);
				vo1.setG_no(g_no);
				vo1.setO_no(o_no);
				vo1.setDepth(depth);
				vo1.setUser_no(user_no);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vo1;
	
	}
	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into board select null, ?, ?, 0, now(), MAX(g_no) + 1, 1, 1, ? from board";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUser_no());

			pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
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
	public void deleteByNo(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "delete from board where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
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
	public void updateTitleAndContentsByNo(String title, String contents, String no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int no1 = Integer.parseInt(no);

		try {
			conn = getConnection();
			String sql = "update board set title=?, contents=? where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setInt(3, no1);

			pstmt.executeQuery();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
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
	public void UpdateHit(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =  null;
		try {
			conn = getConnection();
			
			String sql = "update board"
					+ " set hit = hit + 1"
					+ " where no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
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
	public void insertreply(BoardVo vo) {
		Connection conn = null;
		PreparedStatement update_pstmt = null;
		PreparedStatement insert_pstmt = null;

		try {
			conn = getConnection();
			
			String update_sql = "update board set o_no = o_no + 1 where g_no = ? and o_no >= ?";
			
			update_pstmt = conn.prepareStatement(update_sql);
			update_pstmt.setInt(1, vo.getG_no());
			update_pstmt.setInt(2, vo.getO_no()+1);
			
			update_pstmt.executeQuery();

			String insert_sql = "insert into board values(null, ?, ?, 0, now(),?, ?, ?, ?)";
			
			insert_pstmt = conn.prepareStatement(insert_sql);
			insert_pstmt.setString(1, vo.getTitle());
			insert_pstmt.setString(2, vo.getContents());
			insert_pstmt.setInt(3, vo.getG_no());
			insert_pstmt.setInt(4, vo.getO_no()+1);			
			insert_pstmt.setInt(5, vo.getDepth()+1);
			insert_pstmt.setLong(6, vo.getUser_no());

			insert_pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (update_pstmt != null) {
					update_pstmt.close();
				}
				if (insert_pstmt != null) {
					insert_pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}


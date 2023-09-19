package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> findAll(int page) {
		
		return sqlSession.selectList("board.findAll",page);
		
	}
	 public BoardVo findByNo(Long no) {
	        return sqlSession.selectOne("board.findByNo", no);
	    }
	 public int insert(BoardVo boardVo) {
	        return sqlSession.insert("board.insert", boardVo);
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


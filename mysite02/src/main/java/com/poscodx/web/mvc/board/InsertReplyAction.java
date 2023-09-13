package com.poscodx.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;

public class InsertReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String g_no1 = request.getParameter("g_no");
		String depth1 = request.getParameter("depth");
		String o_no1 = request.getParameter("o_no");
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		int groupNo = Integer.parseInt(g_no1);
		int depth = Integer.parseInt(depth1);
		int orderNo = Integer.parseInt(o_no1);
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setG_no(groupNo);
		vo.setDepth(depth);
		vo.setO_no(orderNo);
		vo.setUser_no(authUser.getNo());


		new BoardDao().insertreply(vo);

		response.sendRedirect(request.getContextPath() + "/board");

	}

}

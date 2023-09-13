package com.poscodx.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class DetailForm implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String g_no = request.getParameter("g_no");
		String depth = request.getParameter("depth");
		String o_no = request.getParameter("o_no");
		request.setAttribute("no", no);
		request.setAttribute("title", title);
		request.setAttribute("contents", contents);
		request.setAttribute("g_no", g_no);
		request.setAttribute("depth", depth);
		request.setAttribute("o_no", o_no);
		
		Long no1 = Long.parseLong(request.getParameter("no"));
		BoardVo vo = new BoardDao().findByNo(no1);
		new BoardDao().UpdateHit(no1);
		request.setAttribute("vo", vo);
		
		System.out.println(vo);
		
		WebUtil.forward("board/view",request, response);
	}

}

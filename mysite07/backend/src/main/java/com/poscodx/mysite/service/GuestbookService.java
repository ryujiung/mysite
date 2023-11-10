package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestbookRepository;
import com.poscodx.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	final GuestbookRepository guestbookRepository;

	public GuestbookService(GuestbookRepository guestbookRepository) {
		this.guestbookRepository = guestbookRepository;
	}

	public List<GuestbookVo> getContentList(Long no) {
		return guestbookRepository.findAll(no);
	}
	
	public boolean deleteContent(Long no, String password) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		
		return guestbookRepository.delete(vo);

// Test.		
//		vo.setNo(null);
//		vo.setName("안대혁");
//		vo.setMessage("테스트");
//		vo.setPassword("1234");
//		guestbookRepository.insert(vo);
//		return true;
	}

	public boolean addMessage(GuestbookVo vo) {
		return guestbookRepository.insert(vo);
	}
}

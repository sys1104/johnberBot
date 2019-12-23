package com.song.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.song.dao.JohnBerDao;
import com.song.service.JohnBerBotService;

@RestController
public class CrawlingController {
	@Autowired
	JohnBerDao dao;
	@Autowired
	JohnBerBotService service;
	Logger logger = LoggerFactory.getLogger("com.song.controller.CrawlingController");
	
	@RequestMapping("/getChatId")
	public String getChatId(HttpServletRequest request) {
		String paramChatId = request.getParameter("chatId");
		String chatId = "";
		try {
			chatId = service.getChatId(paramChatId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chatId;
	}
	@RequestMapping("/regUser")
	public void regChatId(HttpServletRequest request) {
		String paramChatId = request.getParameter("chatId");
		int result = -1;
		try {
			result = service.regUser(paramChatId);
			logger.debug(result +  "건 등록완료" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/delUser")
	public void delUser(HttpServletRequest request) {
		String paramChatId = request.getParameter("chatId");
		int result = -1;
		try {
			result = service.delUser(paramChatId);
			logger.debug(result +  "건 삭제완료" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/regItem")
	public void regItem(@RequestParam HashMap<String, String> paramMap) {
		int result = -1;
		try {
			result = service.regItem(paramMap);
			logger.debug(result +  "건 등록완료" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/getWishListAll")
	public List<HashMap<String, Object>> getWishListAll() {
		
		List<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
		try {
			itemList = service.getWishListAll();
			logger.debug(itemList.size() +  "건 " );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemList;
	} 
	
	@RequestMapping("/updatePrice")
	//매 시간 12분마다
	@Scheduled(cron = "0 12 * * * *")
	public void updatePrice() {
		
		List<HashMap<String, Object>> updateList = new ArrayList<HashMap<String, Object>>();
		try {
			updateList = service.getUpdateList();
			logger.debug( "업데이트 대상 리스트 : " + updateList.size() +  "건 " );
			if (updateList.size() > 0) {
				for (HashMap<String, Object> map : updateList) {
					logger.debug(service.updatePrice(map) + "건 업데이트 완료");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}

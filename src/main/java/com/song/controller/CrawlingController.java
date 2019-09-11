package com.song.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
	@RequestMapping("/price")
	public ModelAndView getPrice() {
		
		try {
			logger.debug(service.getChatId("77000118"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("Result");
		Map<String, String> map = new HashMap<>();
		
		map.put("price", "500");
		mav.addObject("map", map);
		return mav;
	}
	
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
	public void regItem(@RequestParam Map<String, String> paramMap) {
		
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
	public List<HashMap<String, String>> getWishListAll() {
		
		List<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
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
	public void updatePrice() {
		
		List<HashMap<String, String>> updateList = new ArrayList<HashMap<String, String>>();
		try {
			updateList = service.getUpdateList();
			logger.debug( "업데이트 대상 리스트 : " + updateList.size() +  "건 " );
			if (updateList.size() > 0) {
				for (HashMap<String, String> map : updateList) {
					logger.debug(service.updatePrice(map) + "건 업데이트 완료");
				}
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}

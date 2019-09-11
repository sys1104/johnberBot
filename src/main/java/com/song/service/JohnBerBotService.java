package com.song.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.song.crawler.NaverCrawler;
import com.song.dao.JohnBerDao;


@Transactional
@Service
public class JohnBerBotService {
	@Autowired
	private JohnBerDao dao;
	private NaverCrawler crawler = new NaverCrawler();
	
	public String getChatId(String chatId) throws Exception {
		System.out.println("Service.getChatId");
		return dao.getChatId(chatId);
	}
	
	public int regUser(String chatId) throws Exception {
		return dao.regUser(chatId);
	}
	
	public int delUser(String chatId) throws Exception {
		return dao.delUser(chatId);
	}	
	
	public int regItem(Map<String, String> map) throws Exception {
		return dao.regItem(map);
	}	
	
	public int getPrice(HashMap<String, String> map) throws Exception {
		return dao.getPrice(map);
	}
	
	public List<HashMap<String, String>> getWishListAll() throws Exception {
		return dao.getWishListAll();
	}
	
	//가격변동 시 Update
	public List<HashMap<String, String>> getUpdateList() throws Exception {
		List<HashMap<String, String>> wishList = getWishListAll();
		List<HashMap<String, String>> updateList = new ArrayList<HashMap<String, String>>();
		for (HashMap<String, String> map : wishList) {
			String url = map.get("URL");
			String oldPrice = map.get("ITEM_PRICE");
			String chatId = map.get("CHAT_ID");
			HashMap<String, String> itemInfo = crawler.getItemInfoMap(url);
			itemInfo.put("chatId", chatId);
			itemInfo.put("oldPrice", oldPrice);
			String newPrice = itemInfo.get("itemPrice").replace(",", "");
			itemInfo.remove("itemPrice");
			itemInfo.put("itemPrice", newPrice);
			if (oldPrice.equals(newPrice)) {
				continue;
			} else {
				System.out.println("updateListAdded : " + itemInfo.toString());
				updateList.add(itemInfo);
			}
		}
		return updateList;
	}

	//가격변동 시 Update 및 메시지 발송
	public int updatePrice(HashMap<String, String> map) throws Exception {
		String itemPrice = map.get("itemPrice");
		String oldPrice = map.get("oldPrice");
		String itemName = map.get("itemName");
		String message = "@@가격변동알림@@" + "\n상품명 : " + itemName + "\n이전가격 : " + oldPrice + "\n현재가격 : " + itemPrice;
		sendMessage(message, map.get("chatId"));
		return dao.updatePrice(map);
	}
	
    public String sendMessage(String message, String chatId) {
    	TelegramBot bot = new TelegramBot("952633662:AAGDJOld3g9M691pvvMM8ULmF7oRzYhkvR4"); 
    	SendMessage request = new SendMessage(chatId, message)
    	        .parseMode(ParseMode.HTML)
    	        .disableWebPagePreview(true)
    	        .disableNotification(false);

    	SendResponse sendResponse = bot.execute(request);
    	boolean ok = sendResponse.isOk();
    	Message responseMessage = sendResponse.message();
    	//System.out.println( "responseMessage : " + responseMessage);
    	return String.valueOf(ok);
    }	
}

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
import com.song.utils.CommonUtils;

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

	public int regItem(HashMap<String, String> map) throws Exception {
		return dao.regItem(map);
	}

	public int getPrice(HashMap<String, String> map) throws Exception {
		return dao.getPrice(map);
	}

	public List<HashMap<String, Object>> getWishListAll() throws Exception {
		return dao.getWishListAll();
	}

	public List<HashMap<String, Object>> getWishListByID(String chatID) throws Exception {
		return dao.getWishListByID(chatID);
	}
	
	// 가격변동 시 Update
	public List<HashMap<String, Object>> getUpdateList() throws Exception {
		List<HashMap<String, Object>> wishList = getWishListAll();
		List<HashMap<String, Object>> updateList = new ArrayList<HashMap<String, Object>>();

		for (HashMap<String, Object> map : wishList) {
			String url = map.get("URL").toString();
			String oldPrice = map.get("ITEM_PRICE").toString();
			String chatId = map.get("CHAT_ID").toString();
			HashMap<String, Object> itemInfo = crawler.getItemInfoMap(url);
			itemInfo.put("chatId", chatId);
			itemInfo.put("oldPrice", oldPrice);
			String newPrice = itemInfo.get("itemPrice").toString().replace(",", "");
			itemInfo.remove("itemPrice");
			itemInfo.put("itemPrice", newPrice);
			itemInfo.put("url", url);

			if (oldPrice.equals(newPrice)) {
				continue;
			} else {
				System.out.println("updateListAdded : " + itemInfo.toString());
				updateList.add(itemInfo);
			}
		}
		return updateList;
	}

	// 가격변동 시 Update 및 메시지 발송
	public int updatePrice(HashMap<String, Object> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		String itemPrice = map.get("itemPrice").toString().replace(",", "");
		String oldPrice = map.get("oldPrice").toString();
		String itemName = map.get("itemName").toString();
		String url = map.get("url").toString();
		sb.append("@@가격변동알림@@" + "\n상품명 : ").append(itemName)
		   .append("\n이전가격 : ").append(CommonUtils.moneyCommaUtil(oldPrice))
		   .append("\n현재가격 : ").append(CommonUtils.moneyCommaUtil(itemPrice))
		   .append( "\n링크이동 : ").append(url);
		String message = sb.toString();
		sendMessage(message, map.get("chatId").toString());
		return dao.updatePrice(map);
	}

	public String sendMessage(String message, String chatId) {
		TelegramBot bot = new TelegramBot("952633662:AAGDJOld3g9M691pvvMM8ULmF7oRzYhkvR4");
		SendMessage request = new SendMessage(chatId, message).parseMode(ParseMode.HTML).disableWebPagePreview(true)
				.disableNotification(false);
		SendResponse sendResponse = bot.execute(request);
		boolean ok = sendResponse.isOk();
		Message responseMessage = sendResponse.message();
		// System.out.println( "responseMessage : " + responseMessage);
		return String.valueOf(ok);
	}
}
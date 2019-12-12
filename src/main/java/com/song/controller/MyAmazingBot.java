package com.song.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.song.crawler.NaverCrawler;
import com.song.utils.CommonUtils;

@Service
public class MyAmazingBot extends TelegramLongPollingBot {
	private String chatId;
    @Override
    public void onUpdateReceived(Update arg0) {
        // TODO
//        System.out.println(arg0.getMessage().getFrom().getId()); //get ID 는 suer id
    	System.out.println(arg0.getMessage().getFrom().getLastName()); //get ID 는 suer id
        System.out.println(arg0.getMessage().getFrom().getFirstName()); //get ID 는 suer id
        System.out.println(arg0.getMessage().getChatId());  // 채팅방의 ID
        System.out.println(arg0.getMessage().getText());  // 받은 TEXT
        this.chatId = String.valueOf(arg0.getMessage().getChatId());
        
        if ("/start".equals(arg0.getMessage().getText())) {
        	sendMessage("안녕하세요 존버봇입니다.\n @@@명령어 목록@@@ \n/등록 : 자동으로 사용자 등록\n/누구 : 나는누구? \n/찜하기 : 상품등록방법 안내\n/탈퇴 : 탈퇴");
        }
        if ("/찜하기".equals(arg0.getMessage().getText())) {
        	sendMessage("사용자 등록 후 네이버 쇼핑의 특정상품 URL을 입력하면 자동으로 원하는 상품이 등록됩니다.");
        }
        if ("/누구".equals(arg0.getMessage().getText())) {
        	sendMessage("@존버봇");
        }
        if ("/등록".equals(arg0.getMessage().getText())) {
    		
    			if (isRegistered()) {
    				sendMessage("이미 등록된 사용자입니다.");
    			} else {
    				try {
    					sendPost(chatId, "/regUser");
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				
    				sendMessage("사용자 등록완료되었습니다.");
    			}
        }
        if ("/탈퇴".equals(arg0.getMessage().getText())) {
    		try {
    			if ( !isRegistered() ) {
    				sendMessage("사용자 정보가 존재하지 않습니다.");
    			} else {
    				sendPost(chatId, "/delUser");
    				sendMessage("탈퇴완료되었습니다.");    				
    			}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        if (arg0.getMessage().getText().startsWith("http")) {
        	if ( !isRegistered() ) {
        		sendMessage("사용자 등록 후 상품 등록 가능합니다.");
        	} else {
            	Map<String, String> itemInfoMap = new HashMap<>();
            	
            	NaverCrawler naverCrawler = new NaverCrawler();
            	
            	String url = arg0.getMessage().getText();
            	itemInfoMap = naverCrawler.getItemInfoMap(url);
            	if (itemInfoMap.size() > 0) {
                	String itemPrice = itemInfoMap.get("itemPrice");
                	itemPrice = itemPrice.replace(",","");
                	itemInfoMap.remove("itemPrice");
                	itemInfoMap.put("itemPrice", itemPrice);
                	itemInfoMap.put("chatId", this.chatId);
                	itemInfoMap.put("url", url);
                	String itemName = itemInfoMap.get("itemName");
                	
                	try {
    					sendPostMap(itemInfoMap, "/regItem");
    					sendMessage("다음과 같은 상품이 등록되었습니다. \n" + "상품명 : " + itemName + "\n최저가 : " + CommonUtils.moneyCommaUtil(itemPrice));
    				} catch (Exception e) {
    					e.printStackTrace();
    				}           		
            	} else {
            		sendMessage("상품정보 조회에 실패했습니다.");
            	}

            	
        	}

        }
        
        //System.out.println(arg0.getMessage().getReplyToMessage().getText());  // bot이 물어 본 받은 TEXT 사용자    	
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "johnberBot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "952633662:AAGDJOld3g9M691pvvMM8ULmF7oRzYhkvR4";
    }
    
    public String sendPost(String parameter, String method) throws Exception {
    	String rtnData = "";
    	String baseURL = "http://localhost:8080";
    	String URLMethod = method;
        URL url = new URL(baseURL.concat(URLMethod)); // 호출할 url
        Map<String,Object> params = new LinkedHashMap<>(); // 파라미터 세팅
        params.put("chatId", parameter);
 
        StringBuilder postData = new StringBuilder();
        for(Map.Entry<String,Object> param : params.entrySet()) {
            if(postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
 
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes); // POST 호출
 
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
 
        String inputLine;
        while((inputLine = in.readLine()) != null) { // response 출력
        	rtnData = inputLine;
            System.out.println(inputLine);
        }
 
        in.close();
        return rtnData;
    }
    public String sendPostMap(Map<String, String> itemInfoMap, String method) throws Exception {
    	String rtnData = "";
    	String baseURL = "http://localhost:8080";
    	String URLMethod = method;
        URL url = new URL(baseURL.concat(URLMethod)); // API 호출 url + method
        Map<String,Object> params = new LinkedHashMap<>(); // 파라미터 세팅
        params.put("chatId", itemInfoMap.get("chatId"));
        params.put("itemName", itemInfoMap.get("itemName"));
        params.put("itemPrice", itemInfoMap.get("itemPrice"));
        params.put("url", itemInfoMap.get("url"));
 
        StringBuilder postData = new StringBuilder();
        for(Map.Entry<String,Object> param : params.entrySet()) {
            if(postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
 
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes); // POST 호출
 
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
 
        String inputLine;
        while((inputLine = in.readLine()) != null) { // response 출력
        	rtnData = inputLine;
            System.out.println(inputLine);
        }
 
        in.close();
        return rtnData;
    }    
    public String sendMessage(String message) {
    	TelegramBot bot = new TelegramBot(getBotToken()); 
    	SendMessage request = new SendMessage(this.chatId, message)
    	        .parseMode(ParseMode.HTML)
    	        .disableWebPagePreview(true)
    	        .disableNotification(false);

    	SendResponse sendResponse = bot.execute(request);
    	boolean ok = sendResponse.isOk();
    	Message responseMessage = sendResponse.message();
    	//System.out.println( "responseMessage : " + responseMessage);
    	return String.valueOf(ok);
    }

    public boolean isRegistered() {
    	try {
    		String rtnChatId = sendPost(this.chatId, "/getChatId");
    		if (rtnChatId.length() > 1) {
    			return true;
    		} else {
    			return false;
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return true;
    }
}

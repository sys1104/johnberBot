package com.song.crawler;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverCrawler {
	
	private String pcPriceSelector = "#content > div > div.summary_cet > div.price_area > span > em";
	private String mPriceSelector = "#root > div > div.nxKO8SM2-b > div._1gv7l8EHt9 > em";
	private String pcItemNameSelector = "#container > div.summary_area > div.summary_info._itemSection > div > div.h_area > h2";
	private String mItemNameSelector = "#root > div > div.nxKO8SM2-b > h2";
	
	public HashMap<String, String> getItemInfoMap(String url) {
		HashMap<String, String> itemInfo = new HashMap<>();
		try {
			boolean isMobile = isMobile(url);
			String priceSelector = isMobile ? mPriceSelector : pcPriceSelector;
			String itemNameSelector = isMobile ? mItemNameSelector : pcItemNameSelector;
			Document doc = Jsoup.connect(url).get();
			Elements itemPrices = doc.select(priceSelector);
			Elements itemNames = doc.select(itemNameSelector);
			
			for (Element itemName : itemNames) {
				System.out.println("상품명 : " + itemName.html());
				itemInfo.put("itemName", itemName.html());
			}
			for (Element itemPrice : itemPrices) {
				System.out.println("가격 : " + itemPrice.html());
				itemInfo.put("itemPrice", itemPrice.html());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return itemInfo;
	}
	public boolean isMobile(String url) {
		if (url.contains("msearch")) {
			return true;
		} else {
			return false;
		}
	}
	
}

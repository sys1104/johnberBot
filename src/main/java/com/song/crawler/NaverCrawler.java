package com.song.crawler;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.song.factory.SelectorFactory;
import com.song.selector.Selector;

public class NaverCrawler {
	
	public HashMap<String, String> getItemInfoMap(String url) {
		HashMap<String, String> itemInfo = new HashMap<>();
		try {
			SelectorFactory factory = new SelectorFactory();
			Selector selector = factory.createSelector(url);
			System.out.println("selectorName : " + selector.getClass().getName());
			String priceSelector = selector.getPriceSelector();
			String itemNameSelector = selector.getItemNameSelector();
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
	
}

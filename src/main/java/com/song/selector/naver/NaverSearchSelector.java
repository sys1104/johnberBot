package com.song.selector.naver;

import com.song.selector.Selector;

public class NaverSearchSelector implements Selector{
	
	
	private String priceSelector = "#content > div > div.summary_cet > div.price_area > span > em";
	private String itemNameSelector = "#container > div.summary_area > div.summary_info._itemSection > div > div.h_area > h2";
	
	public String getPriceSelector() {
		return priceSelector;
	}
	public void setPriceSelector(String priceSelector) {
		this.priceSelector = priceSelector;
	}
	public String getItemNameSelector() {
		return itemNameSelector;
	}
	public void setItemNameSelector(String itemNameSelector) {
		this.itemNameSelector = itemNameSelector;
	}
	
	@Override
	public String toString() {
		return "NaverSearchSelector [priceSelector=" + priceSelector + ", itemNameSelector=" + itemNameSelector + "]";
	}	
	
}

package com.song.selector;

public class MobileNaverShoppingSelector implements Selector{
	
	private String priceSelector = "#content > div._2JTkTTsRVa > div._23mgnXGWxk > div._1zRK6QU7Ph > div._2Dc3SNpokv > div._1Q_DAKx199 > div > div > strong > span.HMqIXR5wc3";
	private String itemNameSelector = "#content > div._2JTkTTsRVa > div._23mgnXGWxk > div._1zRK6QU7Ph > h3";
	
	@Override
	public String getPriceSelector() {
		return priceSelector;
	}
	
	public void setPriceSelector(String priceSelector) {
		this.priceSelector = priceSelector;
	}
	
	@Override
	public String getItemNameSelector() {
		return itemNameSelector;
	}
	
	public void setItemNameSelector(String itemNameSelector) {
		this.itemNameSelector = itemNameSelector;
	}

	@Override
	public String toString() {
		return "MobileNaverShoppingSelector [priceSelector=" + priceSelector + ", itemNameSelector=" + itemNameSelector
				+ "]";
	}
	
}

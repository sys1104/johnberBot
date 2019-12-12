package com.song.factory;

import com.song.selector.MobileNaverSearchSelector;
import com.song.selector.MobileNaverShoppingSelector;
import com.song.selector.NaverSearchSelector;
import com.song.selector.NaverShoppingSelector;
import com.song.selector.Selector;

public class SelectorFactory extends Factory {

	@Override
	public Selector createSelector(String url) {
		
		if (url.contains("msearch.naver")) return new MobileNaverSearchSelector();
		
		if (url.contains("m.shopping.naver")) return new MobileNaverShoppingSelector();
		
		if (url.contains("search.naver")) return new NaverSearchSelector();
		
		if (url.contains("shopping.naver")) return new NaverShoppingSelector();
		
		return null;
	}
	
}

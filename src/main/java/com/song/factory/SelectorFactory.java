package com.song.factory;

import com.song.selector.MobileNaverSearchSelector;
import com.song.selector.NaverSearchSelector;
import com.song.selector.NaverShoppingSelector;
import com.song.selector.Selector;

public class SelectorFactory extends Factory {

	@Override
	public Selector createSelector(String url) {
		
		if (url.contains("msearch.naver")) return new MobileNaverSearchSelector();
				
		if (url.contains("search.naver")) return new NaverSearchSelector();
		
		//Mobile도 가능(Mobile URL접속 -> PC URL 자동 리다이렉트)
		if (url.contains("shopping.naver")) return new NaverShoppingSelector();
		
		return null;
	}
	
}

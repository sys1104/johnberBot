package com.song.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface JohnBerDao {
	String getChatId(String chatId);
	
	int getPrice(HashMap<String, String> map);
	
	List<HashMap<String, String>> getWishListAll();
	
	int regItem(Map<String, String> map);
	
	int regUser(String chatId);
	
	int delUser(String chatId);
	
	int updatePrice(HashMap<String, String> map);
}

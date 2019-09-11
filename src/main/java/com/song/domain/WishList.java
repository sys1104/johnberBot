package com.song.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishList implements Serializable {
	private Long seq;
	private String chatId;
	private String itemName;
	private int itemPrice;
	private String url;
}
package com.wyj.tea.entity.tea;

import java.io.Serializable;

public class Tea implements Serializable{
	private static final long serialVersionUID = 8818435606061332445L;
	private String id;
	private String title;
	private String create_time;
	private String nickname;
	private String wap_thumb;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getWap_thumb() {
		return wap_thumb;
	}

	public void setWap_thumb(String wap_thumb) {
		this.wap_thumb = wap_thumb;
	}
	
}

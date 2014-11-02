package com.wyj.tea.entity.main;

import java.io.Serializable;
import java.util.List;

public class HeadLines implements Serializable {
	private static final long serialVersionUID = -7152123021447678905L;
	private String errorMessage;
	private List<Data> data;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

}

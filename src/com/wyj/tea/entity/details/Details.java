package com.wyj.tea.entity.details;

import java.io.Serializable;
public class Details implements Serializable {
	private static final long serialVersionUID = -234323921958614314L;
	private String errorMessage;
	private Data data;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}

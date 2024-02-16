package org.formation.service.saga;

import lombok.Data;

@Data
public class CommandResponse {

	private long orderId;

	// 0 Ok et -1 KO
	private int status;

	private String command;

	public boolean isOk() {
		return status == 0;
	}
}

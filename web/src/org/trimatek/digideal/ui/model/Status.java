package org.trimatek.digideal.ui.model;

public class Status {

	private String id;
	private String multisigAddress;
	private String[] unspentTx;
	private String spentTx;
	private String status;
	private String running;
	private String comments;
	private int result;
	// 400 BAD REQUEST
	// 404 NOT FOUND

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRunning() {
		return running;
	}

	public void setRunning(String running) {
		this.running = running;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMultisigAddress() {
		return multisigAddress;
	}

	public void setMultisigAddress(String multisigAddress) {
		this.multisigAddress = multisigAddress;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String[] getUnspentTx() {
		return unspentTx;
	}

	public void setUnspentTx(String[] unspentTx) {
		this.unspentTx = unspentTx;
	}

	public String getSpentTx() {
		return spentTx;
	}

	public void setSpentTx(String spentTx) {
		this.spentTx = spentTx;
	}

}
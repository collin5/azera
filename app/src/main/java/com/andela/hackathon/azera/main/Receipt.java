package com.andela.hackathon.azera.main;

class Receipt {
	public String category;
	public String tags;
	public String imageUrl;
	public String status;
	public String user_id;
	public String description;
	public String updatedAt;
	public String createdAt;

	public Receipt() {

	}

	public Receipt(String category, String tags, String imgUrl, String status, String user_id, String description, String updatedAt, String createdAt) {
		this.category = category;
		this.tags = tags;
		this.imageUrl = imgUrl;
		this.status = status;
		this.user_id = user_id;
		this.description = description;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;

	}
}

package no.ntnu.stud.ubilearn.models;

import java.util.Date;

public class ExerciseImage {
	
	private String objectId;
	private byte[] bytes;
	private String ownerId;
	private Date createdAt;
	
	public ExerciseImage(String objectId, byte[] bytes,String ownerId, Date createdAt) {
		super();
		this.objectId = objectId;
		this.bytes = bytes;
		this.createdAt = createdAt;
		this.ownerId = ownerId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
}

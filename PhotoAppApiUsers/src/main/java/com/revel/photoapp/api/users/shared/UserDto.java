package com.revel.photoapp.api.users.shared;

import java.io.Serializable;
import java.util.List;

import com.revel.photoapp.api.users.ui.model.response.AlbumResponse;

public class UserDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5291563424573314027L;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String userId;
	private String encryptedPassword;
	private List<AlbumResponse> albums;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public List<AlbumResponse> getAlbums() {
		return albums;
	}
	public void setAlbums(List<AlbumResponse> albums) {
		this.albums = albums;
	}
}

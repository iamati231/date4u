package com.tutego.date4u.dto;

import com.tutego.date4u.entity.Photo;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ProfileDTO {

	private long id;
	private String nickname;
	@DateTimeFormat( pattern = "yyyy-MM-dd" )
	private LocalDate birthdate;
	private int hornlength;
	private int gender;
	private Integer attractedToGender;
	private String description;
	private LocalDateTime lastseen;
	private List<Photo> photos;


	public ProfileDTO() {
	}

	public ProfileDTO( long id, String nickname,
	                   LocalDate birthdate, int hornlength, int gender,
	                   Integer attractedToGender, String description, LocalDateTime lastseen ) {
		this.id = id;
		this.nickname = nickname;
		this.birthdate = birthdate;
		this.hornlength = hornlength;
		this.gender = gender;
		this.attractedToGender = attractedToGender;
		this.description = description;
		this.lastseen = lastseen;
	}

	public ProfileDTO( Long id, String nickname, LocalDate birthdate, int hornlength, int gender,
	                   Integer attractedToGender, String description, LocalDateTime lastseen,
	                   List<Photo> photos ) {
		this.id = id;
		this.nickname = nickname;
		this.birthdate = birthdate;
		this.hornlength = hornlength;
		this.gender = gender;
		this.attractedToGender = attractedToGender;
		this.description = description;
		this.lastseen = lastseen;
		this.photos = photos;
	}

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname( String nickname ) {
		this.nickname = nickname;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate( LocalDate birthdate ) {
		this.birthdate = birthdate;
	}

	public int getHornlength() {
		return hornlength;
	}

	public void setHornlength( int hornlength ) {
		this.hornlength = hornlength;
	}

	public int getGender() {
		return gender;
	}

	public void setGender( int gender ) {
		this.gender = gender;
	}

	public Integer getAttractedToGender() {
		return attractedToGender;
	}

	public void setAttractedToGender( Integer attractedToGender ) {
		this.attractedToGender = attractedToGender;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public LocalDateTime getLastseen() {
		return lastseen;
	}

	public void setLastseen( LocalDateTime lastseen ) {
		this.lastseen = lastseen;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos( List<Photo> photos ) {
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "ProfileFormData{" +
		       "id=" + id +
		       ", nickname='" + nickname + '\'' +
		       ", birthdate=" + birthdate +
		       ", hornlength=" + hornlength +
		       ", gender=" + gender +
		       ", attractedToGender=" + attractedToGender +
		       ", description='" + description + '\'' +
		       ", lastseen=" + lastseen +
		       '}';
	}
}
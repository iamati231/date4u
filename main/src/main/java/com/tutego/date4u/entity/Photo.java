package com.tutego.date4u.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Access( AccessType.FIELD )
public class Photo {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	@ManyToOne
	@JoinColumn( name = "profile_fk" )
	private Profile profile;
	@NotNull
	@Pattern( regexp = "[\\w__-]{1,200}" )
	private String name;
	@Column( name = "is_profile_photo" )
	private boolean isProfilePhoto;
	@NotNull
	@Past
	private LocalDateTime created;

	public Photo() {
	}

	public Photo( Long id, Profile profile, String name, boolean isProfilePhoto, LocalDateTime created ) {
		this.id = id;
		this.profile = profile;
		this.name = name;
		this.isProfilePhoto = isProfilePhoto;
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	public Profile getProfile() {
		return profile;
	}

	public String getName() {
		return name;
	}

	public void setProfile( Profile profile ) {
		this.profile = profile;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public boolean isProfilePhoto() {
		return isProfilePhoto;
	}

	public void setProfilePhoto( boolean profilePhoto ) {
		isProfilePhoto = profilePhoto;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated( LocalDateTime created ) {
		this.created = created;
	}


	@Override
	public String toString() {
		return "Photo[" + id + "]";
	}
}

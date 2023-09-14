package com.website.blogging.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Role {
	
	@Id
	private int id;
	private String role;
	@ManyToMany
	private List<User> user= new ArrayList<User>();

}

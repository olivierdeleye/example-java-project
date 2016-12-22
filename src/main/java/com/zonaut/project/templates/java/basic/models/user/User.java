package com.zonaut.project.templates.java.basic.models.user;

import com.zonaut.project.templates.java.basic.models.Entry;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * User entity class.
 *
 * @author David Debuck
 */
@Entity
@Table(name = "users")
public class User extends Entry {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Length(min = 4, max = 50)
	@Column(name = "username", length = 50, unique = true, nullable = false)
	private String username;

	@NotEmpty
	@Email
	@Length(max = 100)
	@Column(name = "email", length = 100, unique = true, nullable = false)
	private String email;

	@NotEmpty
	@Column(name = "password", length = 80, nullable = false)
	private String password;

	@NotEmpty
	@Length(max = 50)
	@Column(name = "first_name")
	private String firstName;

	@NotEmpty
	@Length(max = 50)
	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}

}
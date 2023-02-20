package classs;

import java.util.List;

public class comment {
	private String name_user;
	private String email;
	private String text_comment;
	private String time_comment;
	
	private String title_film;
	private int id_film;
	
	public comment() {}
	
	public comment(String name_user, String email, String text_comment, String time_comment) {
		this.setName_user(name_user);
		this.setEmail(email);
		this.setText_comment(text_comment);
		this.setTime_comment(time_comment);
	}
	
	public comment(String name_user, String text_comment, String time_comment, String title_film, int id_film) {
		this.setName_user(name_user);
		this.setText_comment(text_comment);
		this.setTime_comment(time_comment);
		this.setTitle_film(title_film);
		this.setId_film(id_film);
	}

	public String getName_user() {
		return name_user;
	}

	public void setName_user(String name_user) {
		this.name_user = name_user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getText_comment() {
		return text_comment;
	}

	public void setText_comment(String text_comment) {
		this.text_comment = text_comment;
	}

	public String getTime_comment() {
		return time_comment;
	}

	public void setTime_comment(String time_comment) {
		this.time_comment = time_comment;
	}

	public String getTitle_film() {
		return title_film;
	}

	public void setTitle_film(String title_film) {
		this.title_film = title_film;
	}

	public int getId_film() {
		return id_film;
	}

	public void setId_film(int id_film) {
		this.id_film = id_film;
	}

}

package classs;

import java.util.List;

public class list_film {
	
	private int id;
	private String title;
	private String yearr;
	private int rating;
	private String description;
	private String img_cover;
	private String src_video;
	private List<String> genre;
	
	public list_film() {}
	
	public list_film(int id, String title, String yearr, String img_cover, int rating, List<String> genre) {
		this.setId(id);
		this.setTitle(title);
		this.yearr = yearr;
		this.setImg_cover(img_cover);
		this.rating = rating;
		this.genre = genre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYearr() {
		return yearr;
	}

	public void setYearr(String yearr) {
		this.yearr = yearr;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg_cover() {
		return img_cover;
	}

	public void setImg_cover(String img_cover) {
		this.img_cover = img_cover;
	}

	public String getSrc_video() {
		return src_video;
	}

	public void setSrc_video(String src_video) {
		this.src_video = src_video;
	}

	public List<String> getGenre() {
		return genre;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}

}

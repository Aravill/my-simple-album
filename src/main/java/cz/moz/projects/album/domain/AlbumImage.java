package cz.moz.projects.album.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

public class AlbumImage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tags")
	private int id;
	@Column(length = 40)
	private String uuid;
	@Column(length = 100)
	private String originalName;
	@Column(length = 100)
	private String mimeType;
	@Temporal (TemporalType.TIMESTAMP)
	private Date dateTaken;
	@ManyToMany
	private List<Tag> tags;
	@Column(length = 40)
	private String title;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public Date getDateTaken() {
		return dateTaken;
	}
	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}

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
}

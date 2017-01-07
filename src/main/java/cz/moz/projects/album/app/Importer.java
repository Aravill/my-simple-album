package cz.moz.projects.album.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.moz.projects.album.domain.AlbumImage;
import cz.moz.projects.album.domain.Tag;
import cz.moz.projects.album.repos.ImagesRepository;
import cz.moz.projects.album.repos.TagsRepository;
import cz.moz.projects.album.services.StorageService;

@Component
public class Importer {

	@Autowired
	private ImagesRepository imagesRepo;
	@Autowired
	private TagsRepository tagsRepo;
	@Autowired
	private StorageService storageService;
	
	public void saveImage (String filepath, String[] tags){
		AlbumImage ai = storageService.storeFile(new File(filepath), tags);
		
		List<Tag> tagList = new ArrayList<Tag>();
		for (String tagStr : tags){
			Tag tag = tagsRepo.findByName(tagStr);
			if (tag == null){
				tag = new Tag();
				tag.setName(tagStr);
				tagsRepo.save(tag);
			}
			tagList.add(tag);
		}
		ai.setTags(tagList);
		imagesRepo.save(ai);
	}
}

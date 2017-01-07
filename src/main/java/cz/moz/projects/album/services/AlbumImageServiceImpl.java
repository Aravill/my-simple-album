package cz.moz.projects.album.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.moz.projects.album.domain.AlbumImage;
import cz.moz.projects.album.repos.ImagesRepository;

@Service
@Transactional
public class AlbumImageServiceImpl implements AlbumImageService {

	@Autowired
	private ImagesRepository entriesRepo;
	
	public AlbumImage findAlbumEntryByUuid(String uuid) {
		return entriesRepo.findByUuid(uuid);
	}
		
}
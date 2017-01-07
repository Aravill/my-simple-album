package cz.moz.projects.album.services;

import java.io.File;

import cz.moz.projects.album.domain.AlbumImage;

public interface StorageService {
	
	AlbumImage storeFile(File file, String[] tags);
	AlbumImage storeFile(File file, String originalFileName);
	File findFile (AlbumImage albumImage);
	
}

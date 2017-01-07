package cz.moz.projects.album.services;

import cz.moz.projects.album.domain.AlbumImage;

public interface AlbumImageService {
	AlbumImage findAlbumEntryByUuid(String uuid);
}

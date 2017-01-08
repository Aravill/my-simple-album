package cz.moz.projects.album.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import cz.moz.projects.album.domain.AlbumImage;
import cz.moz.projects.album.services.StorageService;

public abstract class AlbumImageUploader implements Receiver, SucceededListener {
	private static final long serialVersionUID = -6165149741632163143L;
	
	
	private File image;
	private String originalFileName;
	
	private AlbumImage ai;
	
	private StorageService storageService;
	
	public AlbumImage getAi(){
		return ai;
	}
	
	public void setStorageService (StorageService storageService){
		this.storageService = storageService;
	}
	
	@Override
	public void uploadSucceeded(SucceededEvent event){
//		ai = storageService.storeFile(image, originalFileName);
		afterUploadSucceeded(this.originalFileName);
	}
	
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream fos = null;
		try{
			String ext = filename.substring(filename.indexOf("."));
			Path path = Files.createTempFile("tmp", ext);
			image = new File(path.toString());
			originalFileName = filename;
			System.out.println("Created image " + image.getAbsolutePath());
			fos = new FileOutputStream(image);
		}catch (final IOException e){
			new Notification("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
		}
		return fos;
	}
	
	public String getFileName() {
		return originalFileName;
	}
	
	public abstract void afterUploadSucceeded(String fileName);

}

package cz.moz.projects.album.ui;

import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.ui.Upload.Receiver;

public class AlbumImageUploader implements Receiver{

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream fos = null;
		
		return null;
	}

}

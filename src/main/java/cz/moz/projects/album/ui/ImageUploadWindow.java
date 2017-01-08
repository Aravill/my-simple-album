package cz.moz.projects.album.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;

public class ImageUploadWindow extends AbstractWindow {
	private static final long serialVersionUID = 6035211044731379270L;

	private Label lblSelectedFile;
	
	public ImageUploadWindow() {
		super("Upload obrázku");
		
		setWidth("400px");
		setHeight("200px");
		
		AlbumImageUploader reciever = new AlbumImageUploader() {
			private static final long serialVersionUID = 3278560898584103902L;

			@Override
			public void afterUploadSucceeded(String fileName) {
				lblSelectedFile.setValue(fileName);
			}
			
		};
		Upload selectButton = new Upload("Select image", reciever);
		selectButton.setImmediate(true);
		selectButton.addSucceededListener(reciever);
		
		lblSelectedFile = new Label("");
		lblSelectedFile.setCaption("Vybraný soubor:");
		
		layout.addComponent(selectButton);
		layout.addComponent(lblSelectedFile);
		setContent(layout);
	}

	@Override
	public void destroy() {
	}

}

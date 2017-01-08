package cz.moz.projects.album.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cz.moz.projects.album.app.SpringContextHelper;
import cz.moz.projects.album.repos.ImagesRepository;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@Theme("simple-album")
public class AlbumUI extends UI{

	private VerticalLayout content;
	
	private VaadinRequest vaadinRequest;
	
	public VaadinRequest getVaadinRequest() {
		return vaadinRequest;
	}
	
	@Autowired
	ImagesRepository imagesRepo;

	@Override
	protected void init(VaadinRequest request) {
		
		SpringContextHelper.inject(this);
		this.vaadinRequest = request;

		Page page = Page.getCurrent();
		page.setTitle("Album");

		VerticalLayout pageContainer = new VerticalLayout();
		pageContainer.setSizeFull();

		VerticalLayout iContainer = new VerticalLayout();
		iContainer.setWidth("1024px");
		iContainer.setHeight("800px");

		VerticalLayout iiContainer = new VerticalLayout();
		iiContainer.setWidth("100%");
		iiContainer.setStyleName("bordered-container");

		HorizontalLayout header = new HorizontalLayout();
		header.setWidth("100%");
		header.setHeight("140px");
		header.setStyleName("bordered-container");

		HorizontalLayout mccontainer = new HorizontalLayout();
		mccontainer.setWidth("100%");
		mccontainer.setHeight("660px");

		HorizontalLayout imccontainer = new HorizontalLayout();
		imccontainer.setHeight("100%");
		imccontainer.setStyleName("bordered-container");

		VerticalLayout leftMenu = new VerticalLayout();
		leftMenu.setWidth("200px");
		leftMenu.setHeight("660px");
		leftMenu.setSpacing(true);
		leftMenu.setMargin(true);
		leftMenu.setStyleName("bordered-container");

		content = new VerticalLayout();
		content.setWidth("821px");
		content.setHeight("660px");
		content.setMargin(true);
		content.setStyleName("bordered-container");

		//Componenty str�nky
		imccontainer.addComponent(leftMenu);
		imccontainer.addComponent(content);

		mccontainer.addComponent(imccontainer);

		iiContainer.addComponent(header);
		iiContainer.addComponent(mccontainer);

		iContainer.addComponent(iiContainer);

		pageContainer.addComponent(iContainer);
		pageContainer.setComponentAlignment(iContainer, Alignment.TOP_CENTER);
		
		ListSelect imageList = new ListSelect("Image List");
		imageList.addItem("Image1");
		imageList.setNullSelectionAllowed(true);
		
		
//		Tla��tko pro v�b�r fotky
//		final AlbumImageUploader reciever = new AlbumImageUploader();
//		Upload selectButton = new Upload("Select image", reciever);
//		selectButton.setImmediate(true);
//		selectButton.setButtonCaption(null);
		
//		//Component pod-okna
//		final Window uploadImageWindow = new Window("Upload Image");
//		VerticalLayout uploadVerticalContent = new VerticalLayout();
//		uploadVerticalContent.setWidth("400px");
//		uploadVerticalContent.setHeight("200px");
//		uploadVerticalContent.setMargin(true);
//		uploadImageWindow.setContent(uploadVerticalContent);
//		//P�id�n� komponent� do pod-okna
//		uploadVerticalContent.addComponent(selectButton);
//		uploadImageWindow.center();
//		//uploadImageContent.addComponent();
		
		Button uploadButton = new Button("Upload image", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
//				addWindow(uploadImageWindow);
				UI.getCurrent().addWindow(new ImageUploadWindow());
			}

		});
		leftMenu.addComponent(uploadButton);
		leftMenu.addComponent(imageList);
		
		setContent(pageContainer);

	}
}

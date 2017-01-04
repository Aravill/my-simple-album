package cz.moz.projects.album.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@Theme("simple-album")
public class AlbumUI extends UI{

	private VerticalLayout content;
	
	@Override
	protected void init(VaadinRequest request) {
		
		Page page = Page.getCurrent();
		page.setTitle("Simple Album");
		
		VerticalLayout pageContainer = new VerticalLayout();
		pageContainer.setSizeFull();
		
		VerticalLayout iContainer = new VerticalLayout();
		iContainer.setWidth("1024px");
		iContainer.setHeight("800px");
		
		HorizontalLayout header = new HorizontalLayout();
		header.setWidth("100%");
		header.setHeight("140px");
		header.setStyleName("header");
		
		HorizontalLayout mcContainer = new HorizontalLayout();
		mcContainer.setWidth("100%");
		mcContainer.setHeight("660px");
		
		HorizontalLayout imcContainer = new HorizontalLayout();
		imcContainer.setHeight("100%");
		imcContainer.setStyleName("menu-content");
		
		VerticalLayout leftMenu = new VerticalLayout();
		leftMenu.setWidth("200px");
		leftMenu.setHeight("660px");
		leftMenu.setSpacing(true);
		leftMenu.setMargin(true);
		leftMenu.setStyleName("left-menu");
		
		content = new VerticalLayout();
		content.setWidth("824px");
		content.setHeight("660px");
		content.setMargin(true);
		content.setStyleName("content");
		
		imcContainer.addComponent(leftMenu);
		imcContainer.addComponent(content);
		
		mcContainer.addComponent(imcContainer);
		
		Button uploadPhotoButton = new Button("Upload photo", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				
			}
		});
		
	}

}

package cz.moz.projects.album.ui;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cz.moz.projects.album.app.SpringContextHelper;

public abstract class AbstractWindow extends Window {
	private static final long serialVersionUID = 2379053152038707925L;
	
	protected VerticalLayout layout;

	public AbstractWindow(String title) {
		SpringContextHelper.inject(this);
		center();
		setCaption(title);
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
	}

	@Override
	public void attach() {
		focus();
		super.attach();
	}

	@Override
	public void detach() {
		destroy();
		super.detach();
	}

	protected void destroyTable(Table table) {
		if (table != null) {
			try {
				table.removeAllItems();
			} catch (UnsupportedOperationException e) {
				// ok, co se dá dělat -- některé kontejnery (LQC) tohle
				// nepodporují
			}
			table.setContainerDataSource(null);
			table.detach();
		}
	}

	public abstract void destroy();
}

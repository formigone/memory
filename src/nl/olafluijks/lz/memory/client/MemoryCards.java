package nl.olafluijks.lz.memory.client;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public class MemoryCards extends Composite {

	Image image = new Image();
	String backgroundUrl = "background.png", foregroundUrl;
	Boolean showImage = false;

	public MemoryCards(String imageUrl) {
		foregroundUrl = imageUrl;
		showBackground();
		initWidget(image);
	}

	public void showBackground() {
		image.setUrl(backgroundUrl);
		showImage = false;
	}

	public void showForeground() {
		image.setUrl(foregroundUrl);
		showImage = true;
	}

	public String getForeground() {
		return foregroundUrl;
	}

	public boolean isShowingImage() {
		return showImage;
	}

	public boolean sameAs(MemoryCards memoryCard) {
		if (memoryCard.getForeground().equals(this.getForeground())) {
			return true;
		} else {
			return false;
		}
	}

	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return addDomHandler(handler, MouseDownEvent.getType());
	}
}

package nl.olafluijks.lz.memory.client;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MemoryGame extends Composite {

	FlowPanel flowPanel = new FlowPanel();
	int numberOfCards = 6, pairs = 0;
	MemoryCards firstCard = null;

	public MemoryGame() {
		flowPanel.setPixelSize(400, 300);
		initWidget(flowPanel);

		// Let the game begin!
		startNewGame();
	}

	private void startNewGame() {
		
	    SoundController soundController = new SoundController();
	    Sound sound = soundController.createSound(Sound.MIME_TYPE_AUDIO_OGG_FLAC, "http://www.olafluijks.nl/gwt/memory/noname2.ogg", true, false);
	    sound.setVolume(100);
	    sound.play();		
		
		flowPanel.clear();
		pairs = 0;
		int field[] = new int[numberOfCards * 2];
		for (int i = 0; i < numberOfCards; i++) {
			field[i] = i;
			field[i + numberOfCards] = i;
		}
		// Shuffle cards
		for (int i = 0; i < 100; i++) {
			int a = (int) Math.round(Math.random() * ((numberOfCards * 2) - 1));
			int b = (int) Math.round(Math.random() * ((numberOfCards * 2) - 1));
			GWT.log("Card " + a + " with " + b, null);
			int c = field[a];
			field[a] = field[b];
			field[b] = c;
		}
		// Grab cards
		for (int i = 0; i < numberOfCards * 2; i++) {
			MemoryCards memoryCard = new MemoryCards("card" + (field[i] + 1)
					+ ".png");
			flowPanel.add(memoryCard);
			memoryCard.addMouseDownHandler(onCardClick(memoryCard));
		}
	}

	private class MyDialog extends DialogBox {
		public MyDialog() {
			// Set the dialog box's caption.
			setText("You are tha best!");

			// Enable animation.
			setAnimationEnabled(true);

			// Enable glass background.
			setGlassEnabled(true);

			// DialogBox is a SimplePanel, so you have to set its widget
			// property to whatever you want its contents to be.
			Button ok = new Button("OK");
			ok.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					MyDialog.this.hide();
					startNewGame();
				}
			});

			Label label = new Label("Click OK to start a new awesome game!");

			VerticalPanel panel = new VerticalPanel();
			panel.setHeight("400");
			panel.setWidth("300");
			panel.setSpacing(10);
			panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			panel.add(label);
			panel.add(ok);

			setWidget(panel);
		}
	}

	private MouseDownHandler onCardClick(final MemoryCards memoryCard) {
		return new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				if (!memoryCard.isShowingImage()) {
					memoryCard.showForeground();
					if (firstCard != null) {
						if (memoryCard.sameAs(firstCard)) {
							pairs++;
							if (pairs == numberOfCards) {
								Winner();
							}
						} else {
							flipCard(memoryCard, firstCard);
						}
						firstCard = null;
					} else {
						firstCard = memoryCard;
					}
				}
			}

			private void Winner() {
				MyDialog myDialog = new MyDialog();
				myDialog.center();
				myDialog.show();
			}

			private void flipCard(final MemoryCards memoryCard,
					final MemoryCards firstCard) {
				Timer wait = new Timer() {
					@Override
					public void run() {
						memoryCard.showBackground();
						firstCard.showBackground();
					}
				};
				wait.schedule(1000);
			}
		};
	}
}

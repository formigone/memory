package nl.olafluijks.lz.memory.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Memory implements EntryPoint {

	Button buttonStartNewGame = new Button("Start New Game");
	DialogBox memoryGameDialogBox;

	@Override
	public void onModuleLoad() {
		RootPanel.get("gameArea").add(buttonStartNewGame);
		buttonStartNewGame.addClickHandler(new ButtonStartNewGameHandler());
	}

	private class ButtonStartNewGameHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			MemoryGame memoryGame = new MemoryGame();
			memoryGameDialogBox = new DialogBox();
			memoryGameDialogBox.setText("GWT Memory Game");
			memoryGameDialogBox.setModal(false);
			memoryGameDialogBox.center();
			memoryGameDialogBox.show();
			memoryGameDialogBox.add(memoryGame);
		}
	}
}

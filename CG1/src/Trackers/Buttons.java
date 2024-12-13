package Trackers;

import CardsBase.GameParams;
import ProcessGame.Actions;
import ProcessGame.Player;
import ProcessGame.Stages;

import javax.swing.text.View;

public class Buttons {
    private final int INDENT = 10;
    private int buttonsWight = 220;
    private int buttonsHeight = 35;
    private boolean visible;
    private Button[] buttons = new Button[3];

    public Buttons() {
        String[] nameOfActions = new String[]{"Raise", "Call", "Fold"};
        Runnable[] actions = new Runnable[] {()->Actions.doRise(Stages.players.get(0)),
                                        ()->Actions.doCall(Stages.players.get(0)),
                                        ()->Actions.doFold(Stages.players.get(0))};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(GameParams.BUTTONS_X, GameParams.BUTTONS_Y + (buttonsHeight + INDENT) * i,
                    buttonsWight, buttonsHeight, nameOfActions[i], actions[i]);
        }
    }

    public Button[] getButtons() {
        return buttons;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}


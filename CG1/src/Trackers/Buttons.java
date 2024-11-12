package Trackers;

import CardsBase.GameParams;

public class Buttons {
    private final int INDENT = 10;
    private int buttonsWight = 220;
    private int buttonsHeight = 35;
    private boolean visible;
    private Button[] buttons = new Button[3];

    public Buttons() {
        String[] actions = new String[]{"Raise", "Call", "Fold"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(GameParams.BUTTONS_X,  GameParams.BUTTONS_Y + (buttonsHeight + INDENT) * i, buttonsWight, buttonsHeight, actions[i]);
        }
    }

    public Button[] getButtons() {
        return buttons;
    }

}


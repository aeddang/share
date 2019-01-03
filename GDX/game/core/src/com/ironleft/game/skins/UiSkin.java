package com.ironleft.game.skins;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class UiSkin extends Skin
{
    private static UiSkin instance;
    public static UiSkin getInstance()
    {
        if (instance == null) instance = new UiSkin(new UiSkinElement());
        return instance;
    }

    public UiSkin (UiSkinElement element) {
        super();

        add(DEFAULT, element.texture);
        add(DEFAULT, element.font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = getFont(DEFAULT);
        textButtonStyle.up = newDrawable(DEFAULT, Color.DARK_GRAY);
        textButtonStyle.down = newDrawable(DEFAULT, Color.DARK_GRAY);
        textButtonStyle.checked = newDrawable(DEFAULT, Color.BLUE);
        textButtonStyle.over = newDrawable(DEFAULT, Color.LIGHT_GRAY);
        add(DEFAULT, textButtonStyle);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = newDrawable(DEFAULT, Color.DARK_GRAY);
        buttonStyle.down = newDrawable(DEFAULT, Color.DARK_GRAY);
        buttonStyle.checked = newDrawable(DEFAULT, Color.BLUE);
        buttonStyle.over = newDrawable(DEFAULT, Color.LIGHT_GRAY);
        add(DEFAULT, buttonStyle);

        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.background = newDrawable(DEFAULT, Color.DARK_GRAY);
        windowStyle.stageBackground = newDrawable(DEFAULT, Color.YELLOW);
        windowStyle.titleFont = getFont(DEFAULT);
        windowStyle.titleFontColor = Color.BLACK;
        add(DEFAULT, windowStyle);


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.background = newDrawable(DEFAULT, Color.YELLOW);
        labelStyle.font = getFont(DEFAULT);
        labelStyle.fontColor = Color.GRAY;
        add(DEFAULT, labelStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = newDrawable(DEFAULT, Color.YELLOW);
        textFieldStyle.font = getFont(DEFAULT);
        textFieldStyle.fontColor = Color.GRAY;
        add(DEFAULT, textFieldStyle);

    }
    public static final String DEFAULT = "default";
}

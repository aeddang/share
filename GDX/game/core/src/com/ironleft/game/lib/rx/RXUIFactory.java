package com.ironleft.game.lib.rx;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.ironleft.game.lib.page.PageEvent;
import com.ironleft.game.lib.page.PageScreen;

import io.reactivex.*;


public class RXUIFactory
{
    public static Flowable<ChangeListener.ChangeEvent> bindButton(Actor button)
    {
        return Flowable.create(emitter -> {
            ChangeListener listener = new ChangeListener() {
                public void changed (ChangeEvent event, Actor actor) {
                    emitter.onNext(event);
                }
            };
            emitter.setCancellable(() -> {
                button.removeListener(listener);
                emitter.onComplete();
            });
            button.addListener(listener);
        }, BackpressureStrategy.DROP);
    }

    public static Flowable<String> bindTextField(TextField textField)
    {
        return Flowable.create(emitter -> {
            TextField.TextFieldListener listener = new TextField.TextFieldListener() {
                public void keyTyped (TextField textField, char c) {
                    emitter.onNext(String.valueOf(c));
                }
            };
            emitter.setCancellable(() -> {
                textField.setTextFieldListener(null);
                emitter.onComplete();
            });
            textField.setTextFieldListener(listener);
        }, BackpressureStrategy.DROP);
    }

    public static Flowable<PageEvent> bindPageScreen(PageScreen pageScreen)
    {
        return Flowable.create(emitter -> {
            PageScreen.PageScreenListener listener = new PageScreen.PageScreenListener() {
                public void onBack (PageScreen pageScreen) {
                    emitter.onNext(new PageEvent(PageEvent.Type.onBack));
                }
            };
            emitter.setCancellable(() -> {
                pageScreen.setPageScreenListener(null);
                emitter.onComplete();
            });
            pageScreen.setPageScreenListener(listener);
        }, BackpressureStrategy.DROP);
    }


}

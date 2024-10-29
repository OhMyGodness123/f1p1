package com.example.f1p1;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition tt;

    public Shake(Node node) {
        tt = new TranslateTransition(Duration.millis(40), node);
        tt.setFromX(0f);
        tt.setByX(5f);
        tt.setCycleCount(3);
        tt.setAutoReverse(true);
    }

    public void playanim() {
        tt.playFromStart();
    }
}

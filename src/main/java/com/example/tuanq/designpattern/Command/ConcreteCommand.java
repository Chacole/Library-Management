package com.example.tuanq.designpattern.Command;

import javafx.scene.Scene;

public class ConcreteCommand implements Command {
    private Navigate navigate;
    private Scene targetScene;

    public ConcreteCommand(Navigate navigate, Scene targetScene) {
        this.navigate = navigate;
        this.targetScene = targetScene;
    }

    @Override
    public void execute() {
        navigate.switchToScene(targetScene);
    }
}
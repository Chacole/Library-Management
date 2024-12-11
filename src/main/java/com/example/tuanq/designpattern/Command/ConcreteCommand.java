package com.example.tuanq.designpattern.Command;

import javafx.scene.Scene;

public class ConcreteCommand implements Command {
    private NavigationSystem navigationSystem;
    private Scene targetScene;

    public ConcreteCommand(NavigationSystem navigationSystem, Scene targetScene) {
        this.navigationSystem = navigationSystem;
        this.targetScene = targetScene;
    }

    @Override
    public void execute() {
        navigationSystem.switchToScene(targetScene);
    }
}
package presentation.controllers;

import domain.entities.Player;

public class EntityController {
    // Take input from user, turn into Input Data Object and pass through the Input Boundary
    //(i.e., calls a method defined in the Input Boundary interface that takes an Input Data
    // object as a parameter).
    Player player;

    public EntityController(Player player) {
        this.player = player;
    }
}

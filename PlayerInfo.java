package com.example.hossein.chess;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfo {
    private String name;
    private boolean play_turn;
    List<Object> beads;

    public PlayerInfo(String name) {
        this.name = name;
        play_turn = false;
        beads = new ArrayList<>();

    }


    void fiil_list ()
    {

    }
}

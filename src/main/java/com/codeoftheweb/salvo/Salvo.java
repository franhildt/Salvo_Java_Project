package com.codeoftheweb.salvo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")

    private long id;

    @ElementCollection
    @Column(name="locations")
    private List<String> locations;

    private long turn;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="gameplayer_id")
    private GamePlayer gamePlayer;

    public Salvo(long turn,GamePlayer gamePlayer,List<String> locations)
    {
        this.turn=turn;
        this.gamePlayer=gamePlayer;
        this.locations=locations;
    }

    public Salvo(){}

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public long getTurn() {
        return turn;
    }

    public void setTurn(long turn) {
        this.turn = turn;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }


}

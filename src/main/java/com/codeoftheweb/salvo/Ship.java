package com.codeoftheweb.salvo;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")

    private long id;
    private String shipType;

    @ElementCollection
    @Column(name="locations")
    private List<String> locations;

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="gameplayer_id")
    private GamePlayer gamePlayer;

    public Ship (List<String> locations)
    {
        this.locations=locations;
    }

    public Ship(){}

    public Ship (String shipType, GamePlayer gamePlayer, List<String> locations)
    {
        this.shipType=shipType;
        this.gamePlayer=gamePlayer;
        this.locations=locations;
    }

    public long getId() {
        return id;
    }

    public String getShipType() {
        return shipType;
    }

    public List<String> getLocations() {
        return locations;
    }
}

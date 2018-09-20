package com.codeoftheweb.salvo;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name ="native", strategy ="native")
    private long id;

    private LocalDateTime date;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="player_id")
    private Player player;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer")
    @Fetch(value= FetchMode.SUBSELECT)
    private List<Ship> ships= new ArrayList<>();

    @OneToMany(mappedBy ="gamePlayer")
    @Fetch(value=FetchMode.SUBSELECT)
    private List<Salvo> salvoes=new ArrayList<>();

    public GamePlayer() {
        date = LocalDateTime.now();    }

    public GamePlayer(LocalDateTime date,Game game,Player player)
    {
        this.date=date;
        this.player=player;
        this.game=game;
    }

    public void addShip(Ship ship)
    {
        ship.setGamePlayer(this);
        this.ships.add(ship);
    }

    public List<Ship> getShips() {
        return ships;
    }

    public GamePlayer(LocalDateTime date){
        this.date=date;
    }

    public List<Salvo> getSalvoes() {
        return salvoes;
    }

    public Score getScore()
    {
        return this.game.getScores().stream().filter(score -> score.getGame().equals(game)).findFirst().orElse(null);
    }

    public long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }
}

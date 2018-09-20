package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    private long id;

    private LocalDateTime finishDate;

    private float score;

    public Score(){}

    public float getScore() {
        return score;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public Score(LocalDateTime finish, Game game, Player player , float score)
    {
        this.score=score;
        this.finishDate=finish;
        this.player=player;
        this.game=game;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name="player_id")
    private Player player;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="game_id")
    private Game game;
}

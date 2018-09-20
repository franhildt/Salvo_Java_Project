package com.codeoftheweb.salvo;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Game
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name ="native", strategy ="native")
    private long id;

    private LocalDateTime date;

    public Game() {
     this.date = LocalDateTime.now();
    }

    public Game(LocalDateTime date){
        this.date=date;
    }

    @OneToMany(mappedBy = "game")
    private List<GamePlayer> gamePlayers= new ArrayList<>();

    @OneToMany(mappedBy = "game")
    private List<Score> scores= new ArrayList<>();

    public List<Score> getScores() {
        return scores;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate(){
        return this.date;
    }

    public long getId() {
        return id;
    }

    public List<GamePlayer> getGamePlayers()
    {
        return gamePlayers;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setGamePlayers(List<GamePlayer> gamePlayers) {
        gamePlayers = gamePlayers;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}
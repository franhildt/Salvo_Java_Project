package com.codeoftheweb.salvo;

import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name ="native", strategy ="native")
    private long id;


    private String userName;

    private String password;

    @OneToMany(mappedBy = "player")
    private List<GamePlayer> games= new ArrayList<>();

    @OneToMany(mappedBy = "player")
    private List<Score> scores= new ArrayList<>();

    public Player() { }

    public Player(String username, String pwd){
        this.userName=username;
        this.password=pwd;
    }

    public List<GamePlayer> getGamePlayers() {
        return games;
    }

    public List<Score> getScores() {
        return scores;
    }

    public Score getScore(Game game) {
        return game.getScores().stream().filter(score -> score.getGame().equals(game)).findFirst().orElse(null);

    }

    public Score getScore(GamePlayer gamePlayer)
    {
        return gamePlayer.getGame().getScores().stream().filter(score -> score.getGame().equals(gamePlayer.getGame())).filter(score -> score.getPlayer().equals(gamePlayer.getPlayer())).findFirst().orElse(null);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username1){
        this.userName=username1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<GamePlayer> getGames() {
        return games;
    }

    public long getLost(){
        return getScores().stream().filter(p->p.getScore()==0).count();
    }

    public long getWon(){
        return getScores().stream().filter(p->p.getScore()==1).count();
    }

    public long getTied(){
        return getScores().stream().filter(p->p.getScore()==0.5).count();
    }

    public double getTotal()
    {
        return getTied()*0.5+getWon();
    }

    public void setGames(List<GamePlayer> games) {
        this.games = games;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

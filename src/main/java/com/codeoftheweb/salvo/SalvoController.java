package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createUser(@RequestParam String userName,@RequestParam String pwd) {
        if (userName.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "Name in use"), HttpStatus.FORBIDDEN);
        }
        Player player = playerRepository.findByUserName(userName);
        if (player != null) {
            return new ResponseEntity<>(makeMap("error", "Username already exists"), HttpStatus.CONFLICT);
        }
        Player newPlayer = playerRepository.save(new Player("Player 1",passwordEncoder.encode("123")));
        return new ResponseEntity<>(makeMap("id", newPlayer.getId()), HttpStatus.CREATED);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }

    @RequestMapping (path="/games",method=RequestMethod.POST)
    public ResponseEntity<Object> newGame(Authentication authentication)
    {
        if(authentication==null)
        {
            return new ResponseEntity<>(makeMap("error","usuario no identificado"),HttpStatus.UNAUTHORIZED);
        } else
            {
                Game game=gameRepository.save(new Game());
                GamePlayer gamePlayer=gamePlayerRepository.save(new GamePlayer(LocalDateTime.now(),game,playerRepository.findByUserName(authentication.getName())));
                return new ResponseEntity<>(makeMap("gpid",gamePlayer.getId()),HttpStatus.CREATED);
        }
    }


    @RequestMapping ("/games")
    public Map<String,Object> gameListWithCurrentUserDTO(Authentication authentication)
    {
        Map<String,Object> dto= new LinkedHashMap<String,Object>();
        Map<String,Object> dto2= new LinkedHashMap<String,Object>();
        if(authentication==null || authentication instanceof AnonymousAuthenticationToken)
        {
            dto.put("player","Guest");
        }
        else
        {
            dto2.put("id",playerRepository.findByUserName(authentication.getName()).getId());
            dto2.put("name",playerRepository.findByUserName(authentication.getName()).getUserName());
            dto.put("player",dto2);
        }

        dto.put("games",this.getGames());
        return dto;
    }

    @RequestMapping(path="/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
    public ResponseEntity<Object> getPlacedShips(@PathVariable Long gamePlayerId, Authentication authentication)
    {
        if(authentication==null)
        {
            return new ResponseEntity<>(makeMap("error","usuario no identificado"),HttpStatus.UNAUTHORIZED);
        }
        else if(gamePlayerRepository.getOne(gamePlayerId)==null)
        {
            return new ResponseEntity<>(makeMap("error","gameplayer invalido"),HttpStatus.UNAUTHORIZED);
        }
        else if(gamePlayerRepository.getOne(gamePlayerId).getPlayer().getId()!=playerRepository.findByUserName(authentication.getName()).getId())
        {
            return new ResponseEntity<>(makeMap("error","player no coincide con gameplayer"),HttpStatus.UNAUTHORIZED);
        }
        return null;

        //COMPLETARRRRRRRRRRRRR
    }


    public List<Object> getGames(){
        return gameRepository
                .findAll()
                .stream()
                .map(game -> gameDTO(game))
                .collect (Collectors.toList());
    }

    @RequestMapping ("/leaderBoard")
    public List<Object> getLeaderBoard()
    {
        return playerRepository
                .findAll()
                .stream()
                .map(player -> leaderDTO(player))
                .collect(Collectors.toList());
    }

    @RequestMapping("/game_view/{nn}")
    public ResponseEntity<Object> getGameView(@PathVariable Long nn, Authentication authentication){
        if(playerRepository.findByUserName(authentication.getName()).getId()==gamePlayerRepository.findById(nn).orElse(null).getPlayer().getId())
        {
            return new ResponseEntity<>(viewDTO(gamePlayerRepository.getOne(nn)),HttpStatus.ACCEPTED);
        }
        else
        {
            return new ResponseEntity<>(makeMap("error","unauthorized"),HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(path="/game/{gameId}/players",method=RequestMethod.POST)
    public ResponseEntity<Object> joinGame (@PathVariable Long gameId,Authentication authentication)
    {
        if(authentication==null)
        {
            return new ResponseEntity<>(makeMap("error","usuario no identificado"),HttpStatus.UNAUTHORIZED);
        }
        else if (gameRepository.findById(gameId).orElse(null) == null)
        {
            return new ResponseEntity<>(makeMap("error","no se encuentra el juego"),HttpStatus.FORBIDDEN);
        }
        else if (getPlayers(gameId).size()==2 )
        {
            return new ResponseEntity<>(makeMap("error","juego completo"),HttpStatus.FORBIDDEN);
        }
        else
        {
            GamePlayer gamePlayer=gamePlayerRepository.save(new GamePlayer(LocalDateTime.now(),gameRepository.findById(gameId).orElse(null),playerRepository.findByUserName(authentication.getName())));
            return new ResponseEntity<>(makeMap("gpid",gamePlayer.getId()),HttpStatus.CREATED);
        }
    }


    public Set<Player> getPlayers (Long gameId)
    {
        Game game=gameRepository.findById(gameId).orElse(null);
        Set<GamePlayer> gamePlayers = new HashSet<>();
        gamePlayers.addAll(game.getGamePlayers());
        
        Set<Player> players = new HashSet<>();
        gamePlayers.forEach(gamePlayer -> players.add(gamePlayer.getPlayer()));

        return players;
    }

    private Map<String,Object> leaderDTO(Player player)
    {
        Map<String,Object> dto=new LinkedHashMap<String,Object>();
        Map<String,Object> score=new LinkedHashMap<String,Object>();

        dto.put("id",player.getId());
        dto.put("email",player.getUserName());
        dto.put("scores",score);

        score.put("total",player.getTotal());
        score.put("won",player.getWon());
        score.put("lost",player.getLost());
        score.put("tied",player.getTied());

        return dto;
    }

    private Map<String,Object> viewDTO (GamePlayer gamePlayer)
    {
        Map<String,Object> dto=new LinkedHashMap<String,Object>();
        dto.put("id",gamePlayer.getGame().getId());
        dto.put("created",gamePlayer.getGame().getDate());
        dto.put("gamePlayers",gamePlayerList(gamePlayer.getGame().getGamePlayers()));
        dto.put("ships",shipList(gamePlayer.getShips()));
        dto.put("salvoes",getSalvosFromAllGamePlayers(gamePlayer.getGame()));


        return dto;
    }

    private Map<String, Object> gameDTO (Game game)
    {
        Map<String,Object> dto = new LinkedHashMap<String, Object>();
        //dto.put("player", playersLogged());
        dto.put("id",game.getId());
        dto.put("created",game.getDate());
        dto.put("gamePlayers", gamePlayerList(game.getGamePlayers()));
        dto.put("scores",scoreList(game.getGamePlayers()));

        return dto;
    }

    private List<Map<String,Object>> scoreList(List<GamePlayer> gamePlayerSet)
    {
        return gamePlayerSet
                .stream()
                .map(gamePlayer->makeScoreDTO(gamePlayer))
                .collect(Collectors.toList());
    }

    private Map<String,Object> makeScoreDTO(GamePlayer gamePlayer)
    {
        Map<String,Object> dto= new LinkedHashMap<String,Object>();
        dto.put("playerID",gamePlayer.getPlayer().getId());
        if(gamePlayer.getPlayer().getScore(gamePlayer.getGame())!=null)
        {
            dto.put("score",gamePlayer.getPlayer().getScore(gamePlayer).getScore());
        }
        else {
            dto.put("score", null);
        }

        return dto;
    }

    private List<Map> shipList(List<Ship> ships)
    {
        return ships.stream()
                .map(ship -> shipDTO(ship))
                .collect(Collectors.toList());
    }

    private List<Map> getSalvosFromAllGamePlayers(Game game)
    {
        List<Map> finalList=new ArrayList<>();
        game.getGamePlayers().forEach(gamePlayer -> finalList.addAll(salvoesList(gamePlayer.getSalvoes())));

        return finalList;
    }

    private List<Map> salvoesList(List<Salvo> salvoes)
    {
        return salvoes.stream()
                .map(salvo->salvoDTO(salvo))
                .collect(Collectors.toList());
    }

    private Map<String,Object> shipDTO(Ship ship)
    {
        Map<String,Object> dto=new LinkedHashMap<String,Object>();
        dto.put("shipType",ship.getShipType());
        dto.put("location",ship.getLocations());

        return dto;
    }

    private Map<String,Object> salvoDTO(Salvo salvo)
    {
        Map<String,Object> dto=new LinkedHashMap<String,Object>();
        dto.put("turn",salvo.getTurn());
        dto.put("player",salvo.getGamePlayer().getPlayer().getId());
        dto.put("location",salvo.getLocations());

        return dto;
    }

    private List<Map> gamePlayerList(List<GamePlayer> gamePlayers)
    {
        return gamePlayers.stream()
                .map(gamePlayer -> gamePlayersDTO(gamePlayer))
                .collect(Collectors.toList());
    }

    private Map <String,Object> gamePlayersDTO(GamePlayer gamePlayer)
    {
        Map<String,Object> dto= new LinkedHashMap<String,Object>();
        dto.put("gpid",gamePlayer.getId());
        dto.put("id",gamePlayer.getPlayer().getId());
        dto.put("player",gamePlayer.getPlayer().getUserName());

        return dto;
    }


    private Map <String,Object> playerDTO (Player player)
    {
        Map<String,Object> dto= new LinkedHashMap<String,Object>();
        dto.put("id",player.getId());
        dto.put("player",player.getUserName());

        return dto;
    }
}

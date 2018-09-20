package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hibernate.criterion.Restrictions.and;

@SpringBootApplication
public class SalvoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

    @Bean
    public CommandLineRunner initData(GamePlayerRepository gamePlayerRepository, GameRepository gameRepository, PlayerRepository playerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository)
    {
        return (args) -> {
                // save a couple of players as the testbed wants
                Player player1 = new Player("j.bauer@ctu.gov",        passwordEncoder().encode("123"));
                playerRepository.save(player1); // create player 1 with name pepe
                Player player2 = new Player("c.obrian@ctu.gov",   passwordEncoder().encode("123"));
                playerRepository.save(player2); // create player 2 with name tito
                Player player3 = new Player("kim_bauer@gmail.com",passwordEncoder().encode("123"));
                playerRepository.save(player3); // create player 1 with name jose
                Player player4 = new Player("t.almeida@ctu.gov",  passwordEncoder().encode("123"));
                playerRepository.save(player4); // create player 2 with name fred
                //save a pair of games as the testbed wants
                Game game1 = new Game(LocalDateTime.now());
                gameRepository.save(game1); // create game 1
                Game game2 = new Game(LocalDateTime.now().plusHours(1));
                gameRepository.save(game2); // create game 2
                Game game3 = new Game(LocalDateTime.now().plusHours(2));
                gameRepository.save(game3); // create game 3
                Game game4 = new Game(LocalDateTime.now().plusHours(3));
                gameRepository.save(game4); // create game 4
                Game game5 = new Game(LocalDateTime.now().plusHours(4));
                gameRepository.save(game5); // create game 5
                Game game6 = new Game(LocalDateTime.now().plusHours(5));
                gameRepository.save(game6); // create game 6
                Game game7 = new Game(LocalDateTime.now().plusHours(6));
                gameRepository.save(game7); // create game 7
                Game game8 = new Game(LocalDateTime.now().plusHours(7));
                gameRepository.save(game8); // create game 8
                //save a couple of game players as the testbed wants
                GamePlayer gamePlayer1 = new GamePlayer( LocalDateTime.now(), game1, player1);
                gamePlayerRepository.save( gamePlayer1); // crate a new game player with game 1 and player 1
                GamePlayer gamePlayer2 = new GamePlayer( LocalDateTime.now(), game1, player2);
                gamePlayerRepository.save( gamePlayer2); // create a new game playe rwith game 1 and player 2 (a.k.a the opponent
                GamePlayer gamePlayer3 = new GamePlayer( LocalDateTime.now().plusHours(1), game2, player1);
                gamePlayerRepository.save( gamePlayer3);
                GamePlayer gamePlayer4 = new GamePlayer( LocalDateTime.now().plusHours(1), game2, player2);
                gamePlayerRepository.save( gamePlayer4);
                GamePlayer gamePlayer5 = new GamePlayer( LocalDateTime.now().plusHours(2), game3, player2);
                gamePlayerRepository.save( gamePlayer5);
                GamePlayer gamePlayer6 = new GamePlayer( LocalDateTime.now().plusHours(2), game3, player4);
                gamePlayerRepository.save( gamePlayer6);
                GamePlayer gamePlayer7 = new GamePlayer( LocalDateTime.now().plusHours(3), game4, player2);
                gamePlayerRepository.save( gamePlayer7); // crate a new game player with game 1 and player 1
                GamePlayer gamePlayer8 = new GamePlayer( LocalDateTime.now().plusHours(3), game4, player1);
                gamePlayerRepository.save( gamePlayer8); // create a new game playe rwith game 1 and player 2 (a.k.a the opponent
                GamePlayer gamePlayer9 = new GamePlayer( LocalDateTime.now().plusHours(4), game5, player4);
                gamePlayerRepository.save( gamePlayer9);
                GamePlayer gamePlayer10 = new GamePlayer( LocalDateTime.now().plusHours(4), game5, player1);
                gamePlayerRepository.save( gamePlayer10);
                GamePlayer gamePlayer11 = new GamePlayer( LocalDateTime.now().plusHours(5), game6, player3);
                gamePlayerRepository.save( gamePlayer11);
                GamePlayer gamePlayer12 = new GamePlayer( LocalDateTime.now().plusHours(6), game7, player4);
                gamePlayerRepository.save( gamePlayer12);
                GamePlayer gamePlayer13 = new GamePlayer( LocalDateTime.now().plusHours(7), game8, player3);
                gamePlayerRepository.save( gamePlayer13);
                GamePlayer gamePlayer14 = new GamePlayer( LocalDateTime.now().plusHours(7), game8, player4);
                gamePlayerRepository.save( gamePlayer14);
                //save a couple of ships as the testbed wants
                Ship ship1 = new Ship("Destroyer", gamePlayer1, Arrays.asList(new String[]{"H2","H3","H4","F4","F5"}));
                shipRepository.save(ship1);
                Ship ship2 = new Ship("Submarine", gamePlayer1, Arrays.asList(new String[]{"E1","F1","G1"}));
                shipRepository.save(ship2);
                Ship ship3 = new Ship("Patrol Boat", gamePlayer1, Arrays.asList(new String[]{"B4","B5",}));
                shipRepository.save(ship3);
                Ship ship4 = new Ship("Destroyer", gamePlayer2, Arrays.asList(new String[]{"B5","C5","D5"}));
                shipRepository.save(ship4);
                Ship ship5 = new Ship("Patrol Boat", gamePlayer2, Arrays.asList(new String[]{"F1","F2"}));
                shipRepository.save(ship5);
                Ship ship6 = new Ship("Destroyer", gamePlayer3, Arrays.asList(new String[]{"B5","C5","D5"}));
                shipRepository.save(ship6);
                Ship ship7 = new Ship("Patrol Boat", gamePlayer3, Arrays.asList(new String[]{"C6","C7"}));
                shipRepository.save(ship7);
                Ship ship8 = new Ship("Submarine", gamePlayer4, Arrays.asList(new String[]{"A2","A3","A4"}));
                shipRepository.save(ship8);
                Ship ship9 = new Ship("Patrol Boat", gamePlayer4, Arrays.asList(new String[]{"G6","H6",}));
                shipRepository.save(ship9);
                Ship ship10 = new Ship("Destroyer", gamePlayer5, Arrays.asList(new String[]{"B5","C5","D5"}));
                shipRepository.save(ship10);
                Ship ship11 = new Ship("Patrol Boat", gamePlayer5, Arrays.asList(new String[]{"C6","C7"}));
                shipRepository.save(ship11);
                Ship ship12 = new Ship("Submarine", gamePlayer6, Arrays.asList(new String[]{"A2","A3","A4"}));
                shipRepository.save(ship12);
                Ship ship13 = new Ship("Patrol Boat", gamePlayer6, Arrays.asList(new String[]{"G6","H6"}));
                shipRepository.save(ship13);
                Ship ship14 = new Ship("Destroyer", gamePlayer7, Arrays.asList(new String[]{"B5","C5","D5"}));
                shipRepository.save(ship14);
                Ship ship15 = new Ship("Patrol Boat", gamePlayer7, Arrays.asList(new String[]{"C6","C7"}));
                shipRepository.save(ship15);
                Ship ship16 = new Ship("Submarine", gamePlayer8, Arrays.asList(new String[]{"A2","A3","A4"}));
                shipRepository.save(ship16);
                Ship ship17 = new Ship("Patrol Boat", gamePlayer8, Arrays.asList(new String[]{"G6","H6"}));
                shipRepository.save(ship17);
                Ship ship18 = new Ship("Destroyer", gamePlayer9, Arrays.asList(new String[]{"B5","C5","D5"}));
                shipRepository.save(ship18);
                Ship ship19 = new Ship("Patrol Boat", gamePlayer9, Arrays.asList(new String[]{"C6","C7"}));
                shipRepository.save(ship19);
                Ship ship20 = new Ship("Submarine", gamePlayer10, Arrays.asList(new String[]{"A2","A3","A4"}));
                shipRepository.save(ship20);
                Ship ship21 = new Ship("Patrol Boat", gamePlayer10, Arrays.asList(new String[]{"G6","H6"}));
                shipRepository.save(ship21);
                Ship ship22 = new Ship("Destroyer", gamePlayer11, Arrays.asList(new String[]{"B5","C5","D5"}));
                shipRepository.save(ship22);
                Ship ship23 = new Ship("Patrol Boat", gamePlayer11, Arrays.asList(new String[]{"C6","C7"}));
                shipRepository.save(ship23);
                Ship ship24 = new Ship("Destroyer", gamePlayer12, Arrays.asList(new String[]{"B5","C5","D5"}));
                shipRepository.save(ship24);
                Ship ship25 = new Ship("Patrol Boat", gamePlayer12, Arrays.asList(new String[]{"C6","C7"}));
                shipRepository.save(ship25);
                Ship ship26 = new Ship("Submarine", gamePlayer14, Arrays.asList(new String[]{"A2","A3","A4"}));
                shipRepository.save(ship26);
                Ship ship27 = new Ship("Patrol Boat", gamePlayer14, Arrays.asList(new String[]{"G6","H6","C3"}));
                shipRepository.save(ship27);
                //save a couple of salvoes as the testbed
                Salvo salvo1 = new Salvo((long) 1, gamePlayer1, Arrays.asList(new String[]{"B5","C5","F1"}));
                salvoRepository.save(salvo1);
                Salvo salvo2 = new Salvo((long) 2, gamePlayer1, Arrays.asList(new String[]{"F2","D5"}));
                salvoRepository.save(salvo2);
                Salvo salvo3 = new Salvo((long) 1, gamePlayer2, Arrays.asList(new String[]{"B4","B5","B6"}));
                salvoRepository.save(salvo3);
                Salvo salvo4 = new Salvo((long) 2, gamePlayer2, Arrays.asList(new String[]{"E1","H3","A2"}));
                salvoRepository.save(salvo4);
                Salvo salvo5 = new Salvo((long) 1, gamePlayer3, Arrays.asList(new String[]{"A2","A4","G6"}));
                salvoRepository.save(salvo5);
                Salvo salvo6 = new Salvo((long) 2, gamePlayer3, Arrays.asList(new String[]{"A3","H6"}));
                salvoRepository.save(salvo6);
                Salvo salvo7 = new Salvo((long) 1, gamePlayer4, Arrays.asList(new String[]{"B5","D5","C7"}));
                salvoRepository.save(salvo7);
                Salvo salvo8 = new Salvo((long) 2, gamePlayer4, Arrays.asList(new String[]{"C5","C6"}));
                salvoRepository.save(salvo8);
                Salvo salvo9 = new Salvo((long) 1, gamePlayer5, Arrays.asList(new String[]{"G6","H6","A4"}));
                salvoRepository.save(salvo9);
                Salvo salvo10 = new Salvo((long) 2, gamePlayer5, Arrays.asList(new String[]{"A2","A3","D8"}));
                salvoRepository.save(salvo10);
                Salvo salvo11 = new Salvo((long) 1, gamePlayer6, Arrays.asList(new String[]{"H1","H2","H3"}));
                salvoRepository.save(salvo11);
                Salvo salvo12 = new Salvo((long) 2, gamePlayer6, Arrays.asList(new String[]{"E1","F2","G3"}));
                salvoRepository.save(salvo12);
                Salvo salvo13 = new Salvo((long) 1, gamePlayer7, Arrays.asList(new String[]{"A3","A4","F7"}));
                salvoRepository.save(salvo13);
                Salvo salvo14 = new Salvo((long) 2, gamePlayer7, Arrays.asList(new String[]{"A2","G6","H6"}));
                salvoRepository.save(salvo14);
                Salvo salvo15 = new Salvo((long) 1, gamePlayer8, Arrays.asList(new String[]{"B5","C6","H1"}));
                salvoRepository.save(salvo15);
                Salvo salvo16 = new Salvo((long) 2, gamePlayer8, Arrays.asList(new String[]{"C5","C7","D5"}));
                salvoRepository.save(salvo16);
                Salvo salvo17 = new Salvo((long) 1, gamePlayer9, Arrays.asList(new String[]{"A1","A2","A3"}));
                salvoRepository.save(salvo17);
                Salvo salvo18 = new Salvo((long) 2, gamePlayer9, Arrays.asList(new String[]{"G6","G7","G8"}));
                salvoRepository.save(salvo18);
                Salvo salvo19 = new Salvo((long) 1, gamePlayer10, Arrays.asList(new String[]{"B5","B6","C7"}));
                salvoRepository.save(salvo19);
                Salvo salvo20 = new Salvo((long) 2, gamePlayer10, Arrays.asList(new String[]{"C6","D6","E6"}));
                salvoRepository.save(salvo20);
                Salvo salvo21 = new Salvo((long) 3, gamePlayer10, Arrays.asList(new String[]{"H1","H8"}));
                salvoRepository.save(salvo21);
                //save a couple of scores as the testbed
                Score score1 = new Score(LocalDateTime.now().plusMinutes(30),game1, player1, 1);
                scoreRepository.save(score1);
                Score score2 = new Score(LocalDateTime.now().plusMinutes(30),game1, player2, 0);
                scoreRepository.save(score2);
                Score score3 = new Score(LocalDateTime.now().plusHours(1).plusMinutes(30),game2, player1,(float) 0.5 );
                scoreRepository.save(score3);
                Score score4 = new Score(LocalDateTime.now().plusHours(1).plusMinutes(30),game2, player2,(float) 0.5);
                scoreRepository.save(score4);
                Score score5 = new Score(LocalDateTime.now().plusHours(2).plusMinutes(30),game3, player2, 1);
                scoreRepository.save(score5);
                Score score6 = new Score(LocalDateTime.now().plusHours(2).plusMinutes(30),game3, player4, 0);
                scoreRepository.save(score6);
                Score score7 = new Score(LocalDateTime.now().plusHours(3).plusMinutes(30),game4, player2,(float) 0.5 );
                scoreRepository.save(score7);
                Score score8 = new Score(LocalDateTime.now().plusHours(3).plusMinutes(30),game4, player1,(float) 0.5);
                scoreRepository.save(score8);


                /*
                Player  player1 = new Player("fran@gmail.com",passwordEncoder().encode("123"));
                playerRepository.save(player1);
                Player player2 = new Player("player2@gmail.com",passwordEncoder().encode("123"));
                playerRepository.save(player2);
                Player player3 = new Player("player3@gmail.com",passwordEncoder().encode("123"));
                playerRepository.save(player3);
                Player player4 = new Player("player4@gmail.com",passwordEncoder().encode("123"));
                playerRepository.save(player4);
                Player player5 = new Player("player5@gmail.com",passwordEncoder().encode("123"));
                playerRepository.save(player5);
                Player player6 = new Player("player6@gmail.com",passwordEncoder().encode("123"));
                playerRepository.save(player6);

                Game game1 = new Game();
                gameRepository.save(game1);
                Game game2 = new Game();
                gameRepository.save(game2);
                Game game3 = new Game();
                gameRepository.save(game3);

                GamePlayer gamePlayer1=new GamePlayer(player1,game1);
                GamePlayer gamePlayer2=new GamePlayer(player2,game1);
                GamePlayer gamePlayer3=new GamePlayer(player3,game2);
                GamePlayer gamePlayer4=new GamePlayer(player4,game2);
                GamePlayer gamePlayer5=new GamePlayer(player5,game3);
                GamePlayer gamePlayer6=new GamePlayer(player6,game3);


                scoreRepository.save(new Score(1,new Date(),player1,game1));
                scoreRepository.save(new Score(0,new Date(),player2,game1));
                scoreRepository.save(new Score((float)0.5,new Date(),player3,game2));
                scoreRepository.save(new Score((float)0.5,new Date(),player4,game2));

                gamePlayerRepository.save(gamePlayer1);
                gamePlayerRepository.save(gamePlayer2);
                gamePlayerRepository.save(gamePlayer3);
                gamePlayerRepository.save(gamePlayer4);
                gamePlayerRepository.save(gamePlayer5);
                gamePlayerRepository.save(gamePlayer6);


                shipRepository.save(new Ship("submarine",gamePlayer1,Arrays.asList(new String [] {"A1","A2","A3"})));
                shipRepository.save(new Ship("conqueror",gamePlayer1,Arrays.asList(new String [] {"E5","E6","E7"})));
                shipRepository.save(new Ship("destroyer",gamePlayer1,Arrays.asList(new String [] {"B10","C10","D10","E10"})));
                shipRepository.save(new Ship("submarine",gamePlayer2,Arrays.asList(new String [] {"A1","A2","A3"})));
                shipRepository.save(new Ship("conqueror",gamePlayer2,Arrays.asList(new String [] {"H4","H2","H3"})));

                salvoRepository.save(new Salvo((long) 1,gamePlayer1,Arrays.asList(new String [] {"B2","A2"})));
                salvoRepository.save(new Salvo((long) 2,gamePlayer1,Arrays.asList(new String [] {"C1","D1"})));
                salvoRepository.save(new Salvo((long) 1,gamePlayer2,Arrays.asList(new String [] {"A1","H6"})));*/

    };
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName-> {
            Player player = playerRepository.findByUserName(inputName);
            if (player != null) {
                return new User(player.getUserName(), player.getPassword(),
                        AuthorityUtils.createAuthorityList("USER"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Who can see what
        http.authorizeRequests()
                .antMatchers("/web/*").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/web/styles/games.css").permitAll()
                .antMatchers("/api/games").permitAll()
                .antMatchers("/api/game_view/*").hasAuthority("USER")
                .antMatchers("/rest/*").denyAll();

        http.formLogin()
                .usernameParameter("name")
                .passwordParameter("pwd")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        // turn off checking for CSRF tokens
        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request)
    {
        HttpSession session=request.getSession(false);
        if(session!=null)
        {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}

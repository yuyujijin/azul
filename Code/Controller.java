import java.util.ArrayList;

import javax.swing.JPanel;

public class Controller {
	public Game game;
	public View view;

	public Controller(int players,int length, int height, boolean joker) {
		game = new Game(players, this, joker);
		view = new View(this, length,  height);
	}

    /* Our round method
    *  if game isn't won, we call the phase1, put the 1st player Tile,
    *  set the factories, and call move();
    *  else, we count every points and close the game
    */

	public void round() {
		if (!game.hasWon()) {
			game.phase1();
			game.putFirstPlayerTile();
			view.setFactories(game.getFactoriesNbr());
			view.fillFactories(game.getFactories());
			move();
		} else {
			game.finalCount();
			updateScores();
			view.disableBoards();
			view.disableTiles();
			view.disableCenter();
			view.won(game.getWinner(), game.getScore(game.getWinner()));
		}
	}

	/* We disable everyboards (not allowing you to deposit)
	* then check if we cant proceed to phase 3, if so enable Tiles in factories and Center
	* else, we checks if the game isnt won and if we're in phase 3,
	* we disable every Tiles, update round and call another time the round
	*/

	public void move() {
		view.updateBorderActivePlayer(getPlayer());
		view.update();
		view.disableBoards();
		if (!game.canPhase3()) {
			view.enableTiles();
			view.enableCenter();
		} else if (!game.hasWon()) {
			view.disableTiles();
			view.disableCenter();
			game.phase3();
			updateBoards();
			view.update();
			game.round++;
			round();
		}
	}

	public void nextPlayer() {
		game.nextPlayer();
	}

	/* Picking from a factory */

	public void pickTileFactory(char c, int i) {
		game.pickPlayer(game.pickFromFactory(c, i));

		/* deactivate every tiles */

		view.disableTiles();
		view.disableCenter();
		view.update();
		updateHand();
		deposit(getPlayer());

	}

	/* pick from center */

	public void pickTileCenter(char c) {

	    /* if Tile is first player tile */

		if (c == 'f') {
			game.tileFirstPicked();
			view.update();
			view.updateBoard(getPlayer(),game.getBoard(getPlayer()));
			nextPlayer();
			move();

			/* else */

		} else {
			game.pickPlayer(game.pickFromCenter(c));
			view.disableTiles();
			view.disableCenter();
			view.update();
			view.updateBoard(getPlayer(),game.getBoard(getPlayer()));
			updateHand();
			deposit(getPlayer());
		}
	}

	/* enabling players[i] board, so he can deposit */

	public void deposit(int i) {
		view.enableBoard(i);
		view.update();
	}

	/* depositing on floor */

	public void depositFloor() {
		game.depositFloor(getPlayer());
		view.updateBoard(getPlayer(), game.getBoard(getPlayer()));
		view.disableBoards();
		updateHand();
		nextPlayer();
		move();
	}

	/* depositing on Pattern */

	public void depositPattern(int i) {
		if (game.canAddPattern(i)) {
			game.depositPattern(getPlayer(), i);
			view.updateBoard(getPlayer(), game.getBoard(getPlayer()));
			view.disableBoards();
			updateHand();
			nextPlayer();
			move();
		}
	}

	/* Getters */

	public ArrayList<Tile> getCenter() {
		return game.center;
	}

	public void test() {
		System.out.println("ouiçamarche");
	}

	public int getPlayer() {
		return game.activePlayer;
	}

	public int getPlayersNbr() {
		return game.players.length;
	}

	public int getFactoriesNbr() {
		return game.factories.length;
	}

	public Factory[] getFactories() {
		return game.factories;
	}

	/* Updaters */

	public void updateBoards() {
		updateScores();
		for (int i = 0; i < getPlayersNbr(); i++) {
			view.resetPattern(i);
			view.resetFloor(i);
			view.factory.removeAll();
			view.updateBoard(i, game.players[i].getBoard());
		}
	}

	public void updateScores() {
		for (int i = 0; i < getPlayersNbr(); i++) {
			view.updateBoardScore(i, game.players[i].getBoard().getScore());
		}
	}

	public void updateHand() {
		view.updateHand(game.getHand());
	}
}

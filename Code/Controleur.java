import java.util.ArrayList;

import javax.swing.JPanel;

public class Controleur {
	public Game game;
	public View view;

	public Controleur(int players,int length, int height, boolean joker) {
		game = new Game(players, this, joker);
		view = new View(this, length,  height);
	}

	public void Start() {
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

	public void move() {
		view.update();
		System.out.println("Bag : " + game.getBagSize());
		System.out.println("Discards : " + game.getDiscardSize());
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
			Start();
		}
	}

	public Factory[] getFactories() {
		return game.factories;
	}

	public void nextPlayer() {
		game.nextPlayer();
	}

	public void pickTile(char c, int i) {
		game.pickPlayer(game.pickFromFactory(c, i));
		/* deactivate every tiles */
		view.disableTiles();
		view.disableCenter();
		view.update();
		System.out.println("player " + getPlayer() + " has picked");
		updateHand();
		deposite(getPlayer());

	}

	public void pickTileCenter(char c) {
		if (c == 'f') {
			game.tileFirstPicked();
			view.update();
			nextPlayer();
			move();
		} else {
			game.pickPlayer(game.pickFromCenter(c));
			view.disableTiles();
			view.disableCenter();
			view.update();
			System.out.println("player " + getPlayer() + " has picked");
			updateHand();
			deposite(getPlayer());
		}
	}

	public void deposite(int i) {
		System.out.println("deposite");
		view.enableBoard(i);
		view.update();
	}

	public void depositeFloor() {
		game.depositeFloor(getPlayer());
		view.updateBoard(getPlayer(), game.getBoard(getPlayer()));
		view.disableBoards();
		updateHand();
		nextPlayer();
		move();
	}

	public void depositeDeco(int i) {
		if (game.canAddPattern(i)) {
			game.depositeDeco(getPlayer(), i);
			view.updateBoard(getPlayer(), game.getBoard(getPlayer()));
			view.disableBoards();
			updateHand();
			nextPlayer();
			move();
		}
	}

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

	public void updateBoards() {
		updateScores();
		for (int i = 0; i < getPlayersNbr(); i++) {
			view.resetDeco(i);
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

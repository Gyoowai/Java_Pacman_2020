package logic;

import java.util.Random;


import application.Sound;
import entity.Ghost1;
import entity.Ghost2;
import entity.Pacman;
import entity.base.Entity;
import entity.base.Ghost;
import entity.base.MovingEntity;

public class GameController {

	private static GameMap gameMap;

	private static Pacman pacman;
	private static Ghost1 ghost1;
	private static Ghost2 ghost2;

	private static int score;

	private static int scorePoint;

	private static boolean win;

	private static boolean lose;

	private static boolean gamePause;

	private static boolean pacmanMouthOpen;

	private static boolean PowerUp;
	private static int powerUpTimeCount;
	private static int powerupCount;

	private static boolean start;

	public static void IntializeMap(String[][] map, int px, int py, int g1x, int g1y, int g2x, int g2y) {
		pacman = new Pacman();
		ghost1 = new Ghost1();
		ghost2 = new Ghost2();

		setStart(true);
		setGameWin(false);
		setPowerUp(false);
		setPowerUpTimeCount(0);
		setPowerupCount(0);
		setGameLose(false);
		setScorePoint(0);
		setGamePause(false);
		setPacmanMouthOpen(false);

		gameMap = new GameMap(map);
		setScore(gameMap.getAllPoint());

		gameMap.addEntity(pacman, px, py);
		gameMap.addEntity(ghost1, g1x, g1y);
		gameMap.addEntity(ghost2, g2x, g2y);
	}

	public static GameMap getCurrentMap() {
		return gameMap;
	}

	public static void movePacman() {
		if ((GameController.getPacmanDirection() != Direction.NONE) && (isStart())) {
			Sound.playWalkSound();
			setStart(false);
		}
		setPacmanMouthOpen(!isPacmanMouthOpen());
		pacman.move();

	}

	public static boolean killcheck(Object object) {
		if(object instanceof Ghost) {
		Ghost ghost = (Ghost) object;
		if (powerUpTimeCount == 50 * getPowerupCount()) {
			setPowerUp(false);
			setPowerUpTimeCount(0);
			setPowerupCount(0);
		}
		if (isPowerUp()) {
			setPowerUpTimeCount(getPowerUpTimeCount() + 1);
			if ((getPacmanX() == ghost.getX()) && (getPacmanY() == ghost.getY())) {
				ghost.dead();
				GameController.setScorePoint(GameController.getScorePoint() + 500);
				return true;
			}
		} else {
			if ((getPacmanX() == ghost.getX()) && (getPacmanY() == ghost.getY())) {
				setGameLose(true);
				return true;
			}
		}
		}
		return false;
		
	}

	public static void moveGhost(Object object) {
		if(object instanceof MovingEntity) {
		MovingEntity ghost = (MovingEntity)object;
		if (isPowerUp() == false) {
			if (ghost.getX() == pacman.getX()) {
				if (((MovingEntity) ghost).getY() > pacman.getY()) {
					setDirection(ghost, Direction.UP);
				} else {
					setDirection(ghost, Direction.DOWN);
				}
			} else if (ghost.getY() == pacman.getY()) {
				if (((MovingEntity) ghost).getX() > pacman.getX()) {
					setDirection(ghost, Direction.LEFT);
				} else {
					setDirection(ghost, Direction.RIGHT);
				}
			}
		} else {
			if ( ghost.getX() == pacman.getX()) {
				if (ghost1.getY() > pacman.getY()) {
					setDirection(ghost, Direction.DOWN);
				} else {
					setDirection(ghost, Direction.UP);
				}
			} else if (ghost.getY() == pacman.getY()) {
				if (ghost.getX() > pacman.getX()) {
					setDirection(ghost, Direction.RIGHT);
				} else {
					setDirection(ghost, Direction.LEFT);
				}
			}
		}
		randomMove(ghost);
		}
	}
	public static void randomMove(MovingEntity ghost) {
		Random r = new Random();
		while (!ghost.movePossible()) {
			if (r.nextInt(4) == 0) {
				setDirection(ghost, Direction.UP);
			} else if (r.nextInt(4) == 1) {
				setDirection(ghost, Direction.DOWN);
			} else if (r.nextInt(4) == 2) {
				setDirection(ghost, Direction.LEFT);
			} else if (r.nextInt(4) == 3) {
				setDirection(ghost, Direction.RIGHT);
			}
		}
		ghost.move();

	}
	public static void setDirection(MovingEntity movingEntity, Direction direction) {
		movingEntity.setDirection(direction);
	}
	public static Direction getPacmanDirection() {
		return pacman.getDirection();
	}

	public static void setPacmanDirection(Direction dir) throws samePacmanDirectionException {
		if(dir == pacman.getDirection()) {
			throw new samePacmanDirectionException();
		}else {
			pacman.setDirection(dir);
		}
	}

	public static int getPacmanX() {
		return pacman.getX();
	}

	public static int getPacmanY() {
		return pacman.getY();
	}
	
	public static int getGhost1X() {
		return ghost1.getX();
	}
	
	public static int getGhost1Y() {
		return ghost1.getY();
	}
	
	public static int getGhost2X() {
		return ghost2.getX();
	}
	
	public static int getGhost2Y() {
		return ghost2.getY();
	}
	
	public static Object getGhost1() {
		return ghost1;
	}
	
	public static Object getGhost2() {
		return ghost2;
	}

	public static int getGhost1Sprite() {
		return ghost1.getSymbol();
	}

	public static int getGhost2Sprite() {
		return ghost2.getSymbol();
	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int score) {
		GameController.score = score;
	}

	public static boolean isGameWin() {
		return win;
	}

	public static void setGameWin(boolean is_win) {
		GameController.win = is_win;
	}

	public static boolean isGameLose() {
		return lose;
	}

	public static void setGameLose(boolean is_lose) {
		GameController.lose = is_lose;
	}

	public static boolean isPowerUp() {
		return PowerUp;
	}

	public static void setPowerUp(boolean PowerUp) {
		GameController.PowerUp = PowerUp;
	}

	public static int getPowerUpTimeCount() {
		return powerUpTimeCount;
	}

	public static void setPowerUpTimeCount(int powerUpTimeCount) {
		GameController.powerUpTimeCount = powerUpTimeCount;
	}

	public static int getPowerupCount() {
		return powerupCount;
	}

	public static void setPowerupCount(int powerupCount) {
		GameController.powerupCount = powerupCount;
	}

	public static boolean isStart() {
		return start;
	}

	public static void setStart(boolean start) {
		GameController.start = start;
	}

	public static int getScorePoint() {
		return scorePoint;
	}

	public static void setScorePoint(int scorePoint) {
		GameController.scorePoint = scorePoint;
	}

	public static String getScorePointText() {
		return Integer.toString(scorePoint);
	}

	public static boolean isGamePause() {
		return gamePause;
	}

	public static void setGamePause(boolean gamePause) {
		GameController.gamePause = gamePause;
	}

	public static boolean isPacmanMouthOpen() {
		return pacmanMouthOpen;
	}

	public static void setPacmanMouthOpen(boolean pacmanMouthOpen) {
		GameController.pacmanMouthOpen = pacmanMouthOpen;
	}

}

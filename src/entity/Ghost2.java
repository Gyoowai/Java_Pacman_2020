package entity;

import entity.base.Ghost;
import entity.base.MovingEntity;
import logic.Direction;
import logic.GameController;
import logic.Sprites;

public class Ghost2 extends MovingEntity implements Ghost
{
	public boolean dead()
	{
		this.setX(10);
		this.setY(9);
		setDirection(Direction.UP);
		return true;
	}

	public Ghost2() {
		setDirection(Direction.UP);
	}

	@Override
	public int getSymbol()
	{
		if (GameController.isPowerUp())
		{
			if (GameController.getPowerUpTimeCount() >= 50 * GameController.getPowerupCount() - 15)
			{
				return Sprites.BLINKGHOST;
			}
			return Sprites.BLUEGHOST;
		}
		return Sprites.GHOST2;

	}

}

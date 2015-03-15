package com.ThirtyNineEighty.Game.Gameplay;

import com.ThirtyNineEighty.Game.Gameplay.Characteristics.Characteristic;
import com.ThirtyNineEighty.Game.Gameplay.Characteristics.CharacteristicFactory;
import com.ThirtyNineEighty.Game.Gameplay.Subprograms.MoveSubprogram;
import com.ThirtyNineEighty.Game.IEngineObject;
import com.ThirtyNineEighty.Game.Worlds.IWorld;
import com.ThirtyNineEighty.System.GameContext;
import com.ThirtyNineEighty.System.IContent;
import com.ThirtyNineEighty.System.ISubprogram;

public class Bullet extends GameObject
{
  private ISubprogram subprogram;

  protected Bullet(String type)
  {
    super(CharacteristicFactory.get(type));

    IContent content = GameContext.getContent();
    content.bindProgram(subprogram = new MoveSubprogram(this).setLifeTime(150));
  }

  @Override
  public void onCollide(IEngineObject object)
  {
    super.onCollide(object);

    if (!(object instanceof GameObject))
      return;

    GameObject gameObject = (GameObject)object;
    Characteristic c = gameObject.getCharacteristics();

    c.addHealth(c.getDamage());

    if (c.getHealth() <= 0)
    {
      IWorld world = GameContext.getContent().getWorld();
      world.remove(this);
    }
  }

  @Override
  public void onRemoved()
  {
    super.onRemoved();

    IContent content = GameContext.getContent();
    content.unbindProgram(subprogram);
  }
}
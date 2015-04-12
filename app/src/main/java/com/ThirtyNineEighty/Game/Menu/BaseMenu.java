package com.ThirtyNineEighty.Game.Menu;

import android.view.MotionEvent;

import com.ThirtyNineEighty.Game.Menu.Controls.IControl;
import com.ThirtyNineEighty.Renderable.Renderable2D.I2DRenderable;
import com.ThirtyNineEighty.System.GameContext;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMenu
  implements IMenu
{
  private ArrayList<IControl> controls;
  private ArrayList<I2DRenderable> renderables;

  protected BaseMenu()
  {
    controls = new ArrayList<IControl>();
    renderables = new ArrayList<I2DRenderable>();
  }

  protected void addControl(IControl control) { controls.add(control); }
  protected void removeControl(IControl control) { controls.remove(control); }
  protected Iterable<IControl> getControls() { return controls; }

  protected void addRenderable(I2DRenderable control) { renderables.add(control); }
  protected void removeRenderable(I2DRenderable control) { renderables.remove(control); }

  @Override
  public void uninitialize()
  {
    for(IControl control : controls)
      control.dispose();
  }

  @Override
  public final void fillRenderable(List<I2DRenderable> filled)
  {
    for(I2DRenderable renderable : controls)
      filled.add(renderable);

    for(I2DRenderable renderable : renderables)
      filled.add(renderable);
  }

  @Override
  public final void processEvent(MotionEvent event)
  {
    int action = event.getActionMasked();
    int pointerIndex = event.getActionIndex();

    float x = event.getX(pointerIndex) - GameContext.EtalonWidth / 2;
    float y = - (event.getY(pointerIndex) - GameContext.EtalonHeight / 2);
    int id = event.getPointerId(pointerIndex);

    switch (action)
    {
    case MotionEvent.ACTION_DOWN:
    case MotionEvent.ACTION_POINTER_DOWN:
      for(IControl control : controls)
        control.processDown(id, x, y);
      break;

    case MotionEvent.ACTION_MOVE:
      for(IControl control : controls)
        control.processMove(id, x, y);
      break;

    case MotionEvent.ACTION_UP:
    case MotionEvent.ACTION_POINTER_UP:
    case MotionEvent.ACTION_CANCEL:
      for(IControl control : controls)
        control.processUp(id, x, y);
      break;
    }
  }
}

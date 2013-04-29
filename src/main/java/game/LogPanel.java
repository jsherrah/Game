package game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.vecmath.Vector3d;

import com.google.inject.Inject;

public class LogPanel implements HasInit, HasRender {

  @Inject View view;
  @Inject LookAt lookAt;

  MutableTexture texture;
  int x, y, width, height;
  
  String text;

  public void init() {
    x = 10;
    y = 10;
    width = (int) view.getWidth();
    height = (int) view.getHeight();
    texture = new MutableTexture(width, height);
  }

  public String format(Vector3d v) {
    return String.format("(%2.2f,%2.2f,%2.2f)", v.x, v.y, v.z);
  }

  public void render() {
    text = "f=" + format(lookAt.forward()) + " s=" + format(lookAt.sideways());
    renderFlat();
    renderGL();
  }

  private void renderGL() {
    view.orthoView();
    texture.update();
    texture.bind();

    glColor3f(1.0f, 1.0f, 1.0f);
    glBegin(GL_QUADS);  
    glTexCoord2f(0, 0);
    glVertex3f(0, 0, 0);

    glTexCoord2f(0, 1);
    glVertex3f(0, texture.getHeight(), 0);

    glTexCoord2f(1, 1);
    glVertex3f(texture.getWidth(), texture.getHeight(), 0);

    glTexCoord2f(1, 0);
    glVertex3f(texture.getWidth(), 0, 0);
    glEnd();
  }

  private void renderFlat() {
    Graphics2D g = texture.getGraphics();
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0));
    g.clearRect(0, 0, width, height);
    g.setColor(Color.white);
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1));
    g.drawString(text, x, y);
    g.dispose();
  }

}
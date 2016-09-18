package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bola extends Sprite {
	private int dy = 1;
	private int dx = 1;

	public Bola() {
		super(5,5,Color.BLACK);
	}

	public void move() {
		super.move(dx, dy);
	}

	public void invertHorizontal() {
		dx *= -1;
	}

	public void invertVertical() {
		dy *= -1;
	}
	
}

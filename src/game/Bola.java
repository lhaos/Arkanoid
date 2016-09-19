package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bola extends Sprite {
	private int dy = 0;
	private int dx = 0;

	public Bola(int fase) {
		super(5,5,Color.BLACK);
		dy = fase;
		dx = fase;
	}

	public void bolaFase(int fase){
		dx = fase;
		dy = fase;
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

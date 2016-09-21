package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bola extends Sprite {
	private int dy = -1;
	private int dx = 1;

	public Bola(int fase) {
		super(5,5,Color.BLACK);
		bolaInicio();
		
	}
	
	public void bolaInicio(){
		super.setPosition(Resolution.MSX.width / 2 + 5, Resolution.MSX.height - 16);
		dy = -1;
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

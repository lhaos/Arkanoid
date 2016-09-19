package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bola extends Sprite {
	private int dy = 0;
	private int dx = 0;

	public Bola(int fase) {
		super(5,5,Color.BLACK);
		super.setPosition(Resolution.MSX.width / 2 + 5, Resolution.MSX.height - 16);
		dy = fase;
		dx = fase;
		if(fase == 2){
			dy = fase+1;
			dx = fase+1;
		}
	}
	
	public void bolaInicio(){
		super.setPosition(Resolution.MSX.width / 2 + 5, Resolution.MSX.height - 16);
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

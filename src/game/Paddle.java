package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;

public class Paddle extends Sprite{

	public Paddle(){
		super(25, 5, Color.BLACK);
		super.setPosition(Resolution.MSX.width / 2 - 5, Resolution.MSX.height - 10);
	}
	
	public void paddleInicio(){
		super.setPosition(Resolution.MSX.width / 2 - 5, Resolution.MSX.height - 10);
	}
	
}//fecha classe

package game;

import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bloco extends Sprite {
	private boolean bloco = true;
	private int cor = 0 + (int) Math.random()*10;
	
	public Bloco(int fase) {
		
		super(24, 8, Color.RED);
		
	}// fecha construtor
	
	public void draw(Canvas canvas) {
		if(bloco == true)
		super.draw(canvas);
	}

	public boolean bateu(Bola bola) {
		if (bloco == false) {
			return false;
		}
		Point pBola = bola.getPosition();
		double tamanhoX = super.getPosition().x + super.getWidth();
		double tamanhoY = super.getPosition().y + super.getHeight();
		if (super.getPosition().x <= pBola.x+5 && tamanhoX >= pBola.x+5) {
			if (super.getPosition().y <= pBola.y && tamanhoY >= pBola.y) {
				bloco = false;
				return true;
			}
			
		}//fecha if
		return false;
	}// fecha metodo para verificar se a bola bateu no bloco

}// fecha classe

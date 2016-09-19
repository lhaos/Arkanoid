package game;

import javax.swing.JOptionPane;

import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {

	private Bola bola;
	private int blocoArray = 10;
	private Bloco[] linhaUm = new Bloco[blocoArray];
	private Bloco[] linhaDois = new Bloco[blocoArray];
	private Bloco[] linhaTres = new Bloco[blocoArray];
	private Bloco[] linhaQuatro = new Bloco[blocoArray];
	private Bloco[] linhaCinco = new Bloco[blocoArray];
	private Bloco[] linhaSeis = new Bloco[blocoArray];
	private Sprite paddle;
	private boolean move = false;
	private int vida = 10 , score = 0, recorde = 0, fase = 1;

	// =============Inicia canvas============\\

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();

		bola.draw(canvas);

		for (int i = 0; i < 10; i++) {
			linhaUm[i].draw(canvas);
			linhaDois[i].draw(canvas);
			linhaTres[i].draw(canvas);
			linhaQuatro[i].draw(canvas);
			linhaCinco[i].draw(canvas);
			linhaSeis[i].draw(canvas);

		} // fecha for para blocos

		paddle.draw(canvas);

		canvas.putText(2, 2, 10, "RECORDE:");
		canvas.putText(60, 2, 10, String.format("%05d", recorde));
		canvas.putText(100, 2, 10, "SCORE:");
		canvas.putText(143, 2, 10, String.format("%05d", score));
		canvas.putText(183, 2, 10, "VIDAS:");
		canvas.putText(223, 2, 10, String.valueOf(vida));

	}// fecha canvas

	// =============Inicia setup============\\

	@Override
	protected void setup() {
		setResolution(Resolution.MSX);
		setFramesPerSecond(100);

		bola = new Bola(fase);

		bola.setPosition(Resolution.MSX.width / 2 + 5, Resolution.MSX.height - 16);

		bindKeyPressed("SPACE", new KeyboardAction() {
			@Override
			public void handleEvent() {
				move = true;
			}
		});
		
		criaBlocos();

		paddle = new Sprite(25, 5, Color.BLACK);

		paddle.setPosition(Resolution.MSX.width / 2 - 5, Resolution.MSX.height - 10);

		bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				Point posicaoP = paddle.getPosition();

				if (Resolution.MSX.width - 254 < posicaoP.x && move == true) {
					paddle.move(-5, 0);
				} else {
					paddle.move(0, 0);
				} // fecha else
			}
		});
		bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				Point posicaoP = paddle.getPosition();

				if (Resolution.MSX.width > posicaoP.x + 25 && move == true) {
					paddle.move(5, 0);
				} else {
					paddle.move(0, 0);
				} // fecha else
			}
		});
	}// fecha setup

	// =============Inicia loop============\\

	@Override
	protected void loop() {
		colidiuParede(bola, paddle);
		colidiuPaddle(bola, paddle);
		colidiuBloco(linhaUm);
		colidiuBloco(linhaDois);
		colidiuBloco(linhaTres);
		colidiuBloco(linhaQuatro);
		colidiuBloco(linhaCinco);
		colidiuBloco(linhaSeis);
		
		
		if(move){
			bola.move();
		}
		
		if(recorde < score){
			recorde = score;
		}
				
		redraw();
	}// fecha loop

	// =============Inicia funções do jogo============\\

	private void colidiuParede(Bola bola, Sprite paddle) {
		Point posicao = bola.getPosition();
		if (posicao.x < 0 || posicao.x >= Resolution.MSX.width - 5) {
			bola.invertHorizontal();
		}
		if (posicao.y < 15) {
			bola.invertVertical();
		}
		if (posicao.y >= Resolution.MSX.height - 5) {
			reiniciar(bola, paddle);
		}
	}// fecha colisão da bola

	private void colidiuPaddle(Bola bola, Sprite paddle) {
		Point posicaoB = bola.getPosition();
		Point posicaoP = paddle.getPosition();
		if (posicaoB.y + 5 == posicaoP.y && posicaoP.x + 27 >= posicaoB.x + 5 && posicaoP.x <= posicaoB.x + 5) {
			bola.invertVertical();
		}
	}// fecha colisão com paddle
	
	private void colidiuBloco(Bloco[] linhaBloco){
			
		for (int i = 0; i < blocoArray; i++) {
			if(linhaBloco[i].bateu(bola)){
				bola.invertVertical();
				score = score +100;
			}//fecha if
		}//fecha for
			
	}//fecha colisão com os blocos
	
	private void criaBlocos() {
		for (int i = 0; i < blocoArray; i++) {
			int x = (i % 10) * 25 + 2;
			linhaUm[i] = new Bloco(fase);
			linhaUm[i].setPosition(new Point(x, 27));
			linhaDois[i] = new Bloco(fase);
			linhaDois[i].setPosition(new Point(x, 36));
			linhaTres[i] = new Bloco(fase);
			linhaTres[i].setPosition(new Point(x, 45));
			linhaQuatro[i] = new Bloco(fase);
			linhaQuatro[i].setPosition(new Point(x, 54));
			linhaCinco[i] = new Bloco(fase);
			linhaCinco[i].setPosition(new Point(x, 63));
			linhaSeis[i] = new Bloco(fase);
			linhaSeis[i].setPosition(new Point(x, 72));

		}
	}//fecha metodo para criar os blocos

	private void reiniciar(Bola bola, Sprite paddle) {
		int continuar;
		paddle.setPosition(Resolution.MSX.width / 2 - 5, Resolution.MSX.height - 10);
		bola.setPosition(Resolution.MSX.width / 2 + 5, Resolution.MSX.height - 16);
		move = false;
		vida--;
		if (vida == 0) {
			continuar = JOptionPane.showConfirmDialog(null, "Deseja jogar novamente?", "Game Over",
					JOptionPane.YES_NO_OPTION);
			if (continuar == 0) {
				vida = 3;
				score = 0;
				criaBlocos();
			} else {
				System.exit(0);
			} // fecha else interno
		} // fecha if para continuar

	}// fecha metodo de reiniciar o jogo


	// =============Encerra funções============\\

}// fecha classe

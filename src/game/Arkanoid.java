package game;

import java.io.IOException;

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
	private Paddle paddle;
	private Image fundo;
	private boolean move = false, fase2 = false, fase3 = false, faseFinal = false, mudarFase = false;
	private int vida = 10 , score = 0, recorde = 0, fase = 1, valorDeFase = 6000;

	// =============Inicia canvas============\\

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();

		canvas.drawImage(fundo, 0, 0);
		
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
		carImg();
		setResolution(Resolution.MSX);
		setFramesPerSecond(100);

		bola = new Bola(fase);

		bindKeyPressed("SPACE", new KeyboardAction() {
			@Override
			public void handleEvent() {
				move = true;
			}
		});
		
		criaBlocos();

		paddle = new Paddle();

		bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				Point posicaoP = paddle.getPosition();

				if (Resolution.MSX.width - 254 < posicaoP.x && move == true) {
					paddle.move(-5, 0);
				}else if(Resolution.MSX.width - 254 < posicaoP.x && move == false){
					paddle.move(-5, 0);
					bola.move(-5, 0);
				}else{
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
				}else if(Resolution.MSX.width > posicaoP.x + 25 && move == false){
					paddle.move(5, 0);
					bola.move(5, 0); 
				}else{
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
		trocaFase();
		
		
		if(move){
			bola.move();
		}
		
		if(recorde < score){
			recorde = score;
		}
				
		redraw();
	}// fecha loop

	// =============Inicia funções do jogo============\\

	private void carImg(){
		try {
			fundo = new Image("imagens/fundo.png");
			fundo.resize(Resolution.MSX.width,Resolution.MSX.height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void colidiuParede(Bola bola, Paddle paddle) {
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
		if (posicaoB.y + 5 == posicaoP.y && posicaoP.x + 27 >= posicaoB.x && posicaoP.x <= posicaoB.x + 5) {
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
			linhaUm[i] = new Bloco(Color.GRAY);
			linhaUm[i].setPosition(new Point(x, 27));
			linhaDois[i] = new Bloco(Color.RED);
			linhaDois[i].setPosition(new Point(x, 36));
			linhaTres[i] = new Bloco(Color.BLUE);
			linhaTres[i].setPosition(new Point(x, 45));
			linhaQuatro[i] = new Bloco(Color.YELLOW);
			linhaQuatro[i].setPosition(new Point(x, 54));
			linhaCinco[i] = new Bloco(Color.MAGENTA);
			linhaCinco[i].setPosition(new Point(x, 63));
			linhaSeis[i] = new Bloco(Color.GREEN);
			linhaSeis[i].setPosition(new Point(x, 72));

		}
		
	}//fecha metodo para criar os blocos
	
	
	private void trocaFase(){
		
		if(score == valorDeFase && fase == 1){
			fase++;
			valorDeFase += valorDeFase;
			criaBlocos();
			paddle.paddleInicio();
			bola.bolaInicio();
			JOptionPane.showMessageDialog(null, "Round "+(fase+1));
			fase2 = true;
			mudarFase = false;
		}else if(score == valorDeFase && fase == 2){
			fase++;
			valorDeFase += valorDeFase;
			criaBlocos();
			paddle.paddleInicio();
			bola.bolaInicio();
			JOptionPane.showMessageDialog(null, "Round "+(fase+1));
			fase3 = true;
			mudarFase = false;
		}else if(score == valorDeFase && fase == 3){
			JOptionPane.showMessageDialog(null, "VOCÊ VENCEU, PARABÉNS!");
			int continuar;
			continuar = JOptionPane.showConfirmDialog(null, "Deseja jogar novamente?", "Game Over",
					JOptionPane.YES_NO_OPTION);
			if (continuar == 0) {
				fase = 1;
				valorDeFase += valorDeFase;
				mudarFase = false;
				criaBlocos();
			} else {
				System.exit(0);
			}
		}//fecha else if
	}//fecha metodo para trocar de fase

	private void reiniciar(Bola bola, Paddle paddle) {
		int continuar;
		bola.bolaInicio();
		paddle.paddleInicio();
		move = false;
		vida--;
		if (vida == 0) {
			continuar = JOptionPane.showConfirmDialog(null, "Deseja jogar novamente?", "Game Over",
					JOptionPane.YES_NO_OPTION);
			if (continuar == 0) {
				fase = 1;
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

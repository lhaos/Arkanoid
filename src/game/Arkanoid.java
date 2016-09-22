package game;

import java.io.IOException;

import javax.swing.JOptionPane;

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
	private boolean move = false, pause = false;
	private int vida = 5, ganhaVida = 10000, score = 0, recorde = 0, fase = 1, valorDeFase = 6000, 
			mudaFase = valorDeFase;

	// ========================================Inicia canvas==================================================\\

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

		if (pause) {
			canvas.putText(103, 80, 20, "PAUSE");
		}

	}// fecha canvas

	// ====================================Inicia setup========================================\\

	@Override
	protected void setup() {
		carImg();
		setResolution(Resolution.MSX);
		setFramesPerSecond(100);
		
		bola = new Bola();

		botoes();

		criaBlocos();

		paddle = new Paddle();

		

	}// fecha setup

	// ==============================================Inicia loop=====================================\\

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
		carImg();
		
		if (move) {
			bola.move();
		}

		if (recorde < score) {
			recorde = score;
		}
		
		if(score == ganhaVida){
			vida++;
			ganhaVida += ganhaVida;
		}

		redraw();
	}// fecha loop

	// =====================================Inicia funçoes do jogo=====================================\\

	private void carImg() {
		try {
			if (fase == 1) {
				fundo = new Image("imagens/fundo.png");
			} else if (fase == 2) {
				fundo = new Image("imagens/fundo2.png");
			} else {
				fundo = new Image("imagens/fundo3.png");
			}
			fundo.resize(Resolution.MSX.width, Resolution.MSX.height);
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
			caiu(bola, paddle);
		}
	}// fecha colisao da bola

	private void colidiuPaddle(Bola bola, Sprite paddle) {
		Point posicaoB = bola.getPosition();
		Point posicaoP = paddle.getPosition();
		if (posicaoB.y + 5 == posicaoP.y && posicaoP.x + 27 >= posicaoB.x && posicaoP.x <= posicaoB.x + 5) {
			bola.invertVertical();
		}
	}// fecha colisao com paddle

	private void colidiuBloco(Bloco[] linhaBloco) {

		for (int i = 0; i < blocoArray; i++) {
			if (linhaBloco[i].bateu(bola)) {
				bola.invertVertical();
				score = score + 100;
			} // fecha if
		} // fecha for

	}// fecha colisao com os blocos

	private void criaBlocos() {
		if (fase == 1) {
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
		} else if (fase == 2) {
			int y = 8;
			for (int i = 0; i < blocoArray; i++) {
				y += 9;
				
				linhaUm[i] = new Bloco(Color.GRAY);
				linhaUm[i].setPosition(new Point(2, y));
				linhaDois[i] = new Bloco(Color.RED);
				linhaDois[i].setPosition(new Point(46, y));
				linhaTres[i] = new Bloco(Color.BLUE);
				linhaTres[i].setPosition(new Point(92, y));
				linhaQuatro[i] = new Bloco(Color.YELLOW);
				linhaQuatro[i].setPosition(new Point(138, y));
				linhaCinco[i] = new Bloco(Color.MAGENTA);
				linhaCinco[i].setPosition(new Point(184, y));
				linhaSeis[i] = new Bloco(Color.GREEN);
				linhaSeis[i].setPosition(new Point(230, y));
			}
		}else{
			int contFalPon = 0;
			for (int i = 0; i < blocoArray; i++) {
				int x = (i % 10) * 25 + 2;
				
				linhaUm[i] = new Bloco(Color.RED);
				if(i >= 2 && i <= 7){
					linhaUm[i].setPosition(new Point(x, 20));
				}else{linhaUm[i].naoDesenha(); contFalPon++;}
				
				linhaDois[i] = new Bloco(Color.RED);
				if(i >= 2 && i <= 7){
					linhaDois[i].setPosition(new Point(x, 29));
				}else{linhaDois[i].naoDesenha(); contFalPon++;}
				
				if(i == 3 || i == 6){
					linhaTres[i] = new Bloco(Color.BLACK);
				}else{linhaTres[i] = new Bloco(Color.RED);}
				if(i >= 1 && i <= 8){
					linhaTres[i].setPosition(new Point(x, 38));
				}else{linhaTres[i].naoDesenha(); contFalPon++;}
				
				if(i == 3 || i == 6){
					linhaQuatro[i] = new Bloco(Color.BLACK);
				}else{linhaQuatro[i] = new Bloco(Color.RED);}
				if(i >= 1 && i <= 8){
					linhaQuatro[i].setPosition(new Point(x, 47));
				}else{linhaQuatro[i].naoDesenha(); contFalPon++;}
				
				linhaCinco[i] = new Bloco(Color.RED);
				linhaCinco[i].setPosition(new Point(x, 56));
				linhaSeis[i] = new Bloco(Color.RED);
				linhaSeis[i].setPosition(new Point(x, 65));

			}
			mudaFase -= contFalPon * 100;
		}//fecha else para formar a fase 3

	}// fecha metodo para criar os blocos

	private void trocaFase() {

		if (score == mudaFase && fase == 1) {
			fase++;
			mudaFase += valorDeFase;
			criaBlocos();
			paddle.paddleInicio();
			bola.bolaInicio();
			move = false;
			JOptionPane.showMessageDialog(null, "Round " + (fase));
		} else if (score == mudaFase && fase == 2) {
			fase++;
			mudaFase += valorDeFase;
			criaBlocos();
			paddle.paddleInicio();
			bola.bolaInicio();
			move = false;
			JOptionPane.showMessageDialog(null, "Round " + (fase));
		} else if (score == mudaFase && fase == 3) {
			JOptionPane.showMessageDialog(null, "VOCÊ VENCEU, PARABÉNS!");
			int continuar;
			continuar = JOptionPane.showConfirmDialog(null, "Deseja jogar novamente?", "Game Over",
					JOptionPane.YES_NO_OPTION);
			if (continuar == 0) {
				fase = 1;
				vida += 3;
				mudaFase += valorDeFase;
				paddle.paddleInicio();
				bola.bolaInicio();
				move = false;
				criaBlocos();
			} else {
				System.exit(0);
			}
		} // fecha else if
	}// fecha metodo para trocar de fase

	private void caiu(Bola bola, Paddle paddle) {
		int continuar;
		bola.bolaInicio();
		paddle.paddleInicio();
		move = false;
		vida--;
		if (vida == 0) {
			continuar = JOptionPane.showConfirmDialog(null, "Deseja jogar novamente?", "Game Over",
					JOptionPane.YES_NO_OPTION);
			if (continuar == 0) {
				reiniciar();
			} else {
				System.exit(0);
			} // fecha else interno
		} // fecha if para continuar

	}// fecha metodo de reiniciar o jogo
	
	private void reiniciar(){
		bola.bolaInicio();
		paddle.paddleInicio();
		move = false;
		fase = 1;
		vida = 5;
		score = 0;
		criaBlocos();
	}//fecha metodo para reiniciar jogo
	
	private void botoes(){
		bindKeyPressed("SPACE", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if (!move) {
					move = true;
					pause = false;
				} else {
					move = false;
					pause = true;
				}
			}
		});
		
		bindKeyPressed("I", new KeyboardAction() {
			@Override
			public void handleEvent() {
				pause = true;
				move = false;
				tutorial();
			}
		});
		
		bindKeyPressed("R", new KeyboardAction() {
			@Override
			public void handleEvent() {
				reiniciar();
			}
		});
		
		bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				Point posicaoP = paddle.getPosition();

				if (Resolution.MSX.width - 254 < posicaoP.x && move == true && pause == false) {
					paddle.move(-5, 0);
				} else if (Resolution.MSX.width - 254 < posicaoP.x && move == false && pause == false) {
					paddle.move(-5, 0);
					bola.move(-5, 0);
				} else {
					paddle.move(0, 0);
				} // fecha else
			}
		});
		bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				Point posicaoP = paddle.getPosition();

				if (Resolution.MSX.width > posicaoP.x + 25 && move == true && pause == false) {
					paddle.move(5, 0);
				} else if (Resolution.MSX.width > posicaoP.x + 25 && move == false && pause == false) {
					paddle.move(5, 0);
					bola.move(5, 0);
				} else {
					paddle.move(0, 0);
				} // fecha else
			}
		});
		
	}//fecha metodo para usar os botoes
	
	private void tutorial(){
		String msg = "[Space] = Inicia e pausa o jogo depois de iniciado.";
		msg += "\n[Seta esquerda] = Move o paddle para a esquerda.";
		msg += "\n[Seta direita] = Move o paddle para a direita.";
		msg += "\n[I] = Abre a janela de informaçoes.";
		msg += "\n[R] = Reinicia o jogo.";
		JOptionPane.showMessageDialog(null, ""+msg, "Informaçoes", JOptionPane.INFORMATION_MESSAGE);
	}

	// ===========================================Encerra funçoes===============================================\\

}// fecha classe

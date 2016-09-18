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
	private Bloco bloco;
	private Sprite paddle;
	private boolean desenhaBloco=true, move = false;
	private int vida = 3;

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		bola.draw(canvas);
		
		if(desenhaBloco){
			bloco.draw(canvas);
		}
		
		paddle.draw(canvas);
		
	}

	@Override
	protected void setup() {
		setResolution(Resolution.MSX);
		setFramesPerSecond(60);
		
		bola = new Bola();
		
		bola.setPosition(
				Resolution.MSX.width/2+5,
				Resolution.MSX.height-16
				);
		
		bindKeyPressed("SPACE", new KeyboardAction() {
			@Override
			public void handleEvent() {
				move = true;		
			}
		});
		
		bloco = new Bloco();
		
		bloco.setPosition(
				Resolution.MSX.width-255,
				Resolution.MSX.height-180
				);
		
		paddle = new Sprite(25,5,Color.BLACK);
		
		paddle.setPosition(
				Resolution.MSX.width/2-5,
				Resolution.MSX.height-10
				);
		
		bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				Point posicaoP = paddle.getPosition();
				
				if(Resolution.MSX.width-254 < posicaoP.x && move == true){
					paddle.move(-4,0);		
				}else{
					paddle.move(0,0);	
				}//fecha else
			}
		});
		bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				Point posicaoP = paddle.getPosition();
				
				if(Resolution.MSX.width > posicaoP.x+25 && move == true){
					paddle.move(4,0);		
				}else{
					paddle.move(0,0);	
				}//fecha else				
			}
		});
	}//fecha setup

	@Override
	protected void loop() {
		colidiuParede(bola, paddle);
		colidiuPaddle(bola, paddle);
		
		if(move){
			bola.move();
		}
		
		Point position = bola.getPosition();
		Point blocoPosition = bloco.getPosition();
		
		if(position.x+bola.getWidth() < blocoPosition.x){
			desenhaBloco=true;
			// não bateu
		}
		
		else if(position.x > blocoPosition.x+bloco.getWidth() ){
			desenhaBloco=true;
			// não bateu
		}
		else if(position.y+bola.getHeight() < blocoPosition.y){
			desenhaBloco=true;
			// não bateu
		}
		else if(position.y > blocoPosition.y+bloco.getHeight()){
			desenhaBloco=true;
			// não bateu
		}
		else{
			desenhaBloco = false;
		}//fecha else
		
		redraw();
	}//fecha loop
	
	private void colidiuParede(Bola bola, Sprite paddle) {
		Point posicao = bola.getPosition();
		if (posicao.x < 0 || posicao.x >= Resolution.MSX.width-5){
			bola.invertHorizontal();
		}
		if (posicao.y < 0) {
			bola.invertVertical();
		}	
		if(posicao.y >= Resolution.MSX.height-5){
			reiniciar(bola, paddle);
		}
	}//fecha colisão da bola
	
	private void colidiuPaddle(Bola bola, Sprite paddle){
		Point posicaoB = bola.getPosition();
		Point posicaoP = paddle.getPosition();
		if(posicaoB.y+5 == posicaoP.y && posicaoP.x+25 >= posicaoB.x+5 && posicaoP.x <= posicaoB.x+5){
			bola.invertVertical();
		}
	}//fecha colisão com paddle
	
	private void reiniciar(Bola bola, Sprite paddle){
		String continuar = "";
			paddle.setPosition(
					Resolution.MSX.width/2-5,
					Resolution.MSX.height-10
					);
			bola.setPosition(
					Resolution.MSX.width/2+5,
					Resolution.MSX.height-16
					);
			move = false;
			vida--;
			JOptionPane.showMessageDialog(null, "Você tem ainda tem "+vida+" Vidas");
			if(vida == 0){
				continuar = JOptionPane.showInputDialog(null, "Continuar?\n"+"[s] ou [n]", 
						JOptionPane.QUESTION_MESSAGE);
				if(continuar.equalsIgnoreCase("s")){
					vida = 3;
				}else if(continuar.equalsIgnoreCase("n")){
					System.exit(0);
				}else{
					JOptionPane.showMessageDialog(null, "Erro, opção inválida!\n"+"Escolha uma opção válida");
				}//fecha else interno
			}//fecha if para continuar
			
	}//fecha metodo de reiniciar
	
}//fecha classe





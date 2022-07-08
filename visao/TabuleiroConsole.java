package pcjunior.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import pcjunior.cm.Aplicacao;
import pcjunior.cm.excecao.ExplosaoException;
import pcjunior.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro; 
		executarJogo();
	}

	private void executarJogo() {

			cicloDoJogo();
			System.out.println("Jogar novamente? (S/n)");
			String resposta = entrada.nextLine();
			
			if(resposta.equalsIgnoreCase("n")) {				
				System.out.println("Saindo...");
				// ENCERRA A APLICA��O
				System.exit(0);
			} else {
				// REINICIA A APLICA��O
				Aplicacao.main(null);					
			}									
		entrada.close();
	}

	//  CICLO DO FUNCIONAMENTO DO JOGO - SO ACABA COM OBJETIVO ALAN�ADO
	//  EXPLOSAOEXCEPTION OU DIGITAR "SAIR"
	private void cicloDoJogo() {
		try {
			while (!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				String digitado = capturarValorDigitado("Digite (x,y): ");
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
						.map(e -> Integer.parseInt(e.trim())).iterator();
				
				digitado = capturarValorDigitado("1- Abrir / 2- Marcar/Desmarcar: ");
				if("1".equals(digitado)) {
					try {
						// CORRE��O DE -1 PARA ENCAIXAR NOS �NDICES QUE COME�AM COM 0
						tabuleiro.abrir((xy.next() - 1), (xy.next() -1 ));
					} catch (NoSuchElementException e) {						
						System.out.println("Digite um campo v�lido!");
					} catch (NumberFormatException e) {
						System.out.println("Digite um campo v�lido!");
					}
				} else if("2".equals(digitado)) {
					try {
						// CORRE��O DE -1 PARA ENCAIXAR NOS �NDICES QUE COME�AM COM 0
						tabuleiro.marcar((xy.next() - 1), (xy.next() -1 ));
					} catch (NoSuchElementException e) {
						System.out.println("Digite um campo v�lido!");
						
					} catch (NumberFormatException e) {
						System.out.println("Digite um campo v�lido!");
					} 
				}				
			}			
			// OBJETIVO FOI ALCAN�ADO
			System.out.println(tabuleiro);
			System.out.println("PARAB�NS! VIT�RIA!!");
			// CAPTURA A EXPLOSAOEXCEPTION
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("BOOOM!!! FIM DE JOGO!!");
		}		
	}
	private String capturarValorDigitado(String texto) {
		// IMPRIME A INTERA��O DE COMANDO COM USU�RIO
		System.out.print(texto);
		String digitado = entrada.nextLine();
		// SAIR DO JOGO 
		if("sair".equalsIgnoreCase(digitado)) {				
			System.out.println("Saindo...");
			System.exit(0);			
		}		
		return digitado;		
	}	
	
}
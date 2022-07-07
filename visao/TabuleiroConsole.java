package pcjunior.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import pcjunior.cm.excecao.ExplosaoException;
import pcjunior.cm.excecao.SairException;
import pcjunior.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro; 
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean controle = true;
			
			while(controle) {
				cicloDoJogo();
				System.out.println("Jogar novamente? (S/n)");
				String resposta = entrada.nextLine();
				if(resposta.equalsIgnoreCase("n")) {
					controle = false;
					System.out.println("Saindo...");
				} else {
					tabuleiro.reiniciar();
				}				
			}			
		} catch(SairException e) {
			System.out.println("Saindo...");
		} finally {
			entrada.close();
		}
		
	}

	private void cicloDoJogo() {
		try {
			// CICLO DO FUNCIONAMENTO DO JOGO - SO ACABA COM OBJETIVO ALANÇADO
			// OU SAIREXCEPTION  OU EXPLOSAOEXCEPTION
			while (!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				String digitado = capturarValorDigitado("Digite (x,y): ");
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
						.map(e -> Integer.parseInt(e.trim())).iterator();
				
				digitado = capturarValorDigitado("1- Abrir / 2- Marcar/Desmarcar: ");
				if("1".equals(digitado)) {
					try {
						// CORREÇÃO DE -1 PARA ENCAIXAR NOS ÍNDICES QUE COMEÇAM COM 0
						tabuleiro.abrir((xy.next() - 1), (xy.next() -1 ));
					} catch (NoSuchElementException e) {						
						System.out.println("Digite um campo válido!");
					} catch (NumberFormatException e) {
						System.out.println("Digite um campo válido!");
					}
				} else if("2".equals(digitado)) {
					try {
						// CORREÇÃO DE -1 PARA ENCAIXAR NOS ÍNDICES QUE COMEÇAM COM 0
						tabuleiro.marcar((xy.next() - 1), (xy.next() -1 ));
					} catch (NoSuchElementException e) {
						System.out.println("Digite um campo válido!");
						
					} catch (NumberFormatException e) {
						System.out.println("Digite um campo válido!");
					}
				}				
			}
			// OBJETIVO ALCANÇADO
			System.out.println(tabuleiro);
			System.out.println("PARABÉNS! FIM DE JOGO!!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("FIM DE JOGO!!");
		}
		
	}
	
	private String capturarValorDigitado(String texto) {
		// IMPRIME A INTERAÇÃO DE COMANDO COM USUÁRIO
		System.out.print(texto);
		String digitado = entrada.nextLine();
		// SAIR DO JOGO 
		if("sair".equalsIgnoreCase(digitado)) {				
			throw new SairException();
		}		
		return digitado;		
	}
	
	
	
	
}
	
	



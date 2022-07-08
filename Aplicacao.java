package pcjunior.cm;

import java.util.Scanner;

import pcjunior.cm.modelo.Tabuleiro;
import pcjunior.cm.visao.TabuleiroConsole;

public class Aplicacao {
	
	
	public static void main(String[] args) {
	
		// MENU DO JOGO
		
		System.out.print("##------------ CAMPO MINADO -----------##|\n");
	  	System.out.print("|----------------------------------------|\n");
	  	System.out.print("| 1. Principiante (9 x 9 e 10 Minas      |\n");
	  	System.out.print("| 2. Médio (16 x 16 e 40 Minas           |\n");
	  	System.out.print("| 3. Especialista (16 x 30 e 99 Minas)   |\n");
	  	System.out.print("| 4. Sair                                |\n");
	  	System.out.print("|----------------------------------------|\n");
		
		int opcao = 0;
		
				while(opcao != 4){
						
					try {
						// RECEBE STRING E CONVERTE PARA INTEIRO PARA CAIR NO CASE
						opcao = verificaOpcaoDigitada();
					} catch (Exception e) {
						
					}
						switch(opcao) {
						case 1:
							Tabuleiro tabuleiroPrincipiante = new Tabuleiro(9, 9, 10);
							new TabuleiroConsole(tabuleiroPrincipiante);
							break;
						case 2:
							Tabuleiro tabuleiroMedio = new Tabuleiro(16, 16, 40);
							new TabuleiroConsole(tabuleiroMedio);
							break;
						case 3:
							Tabuleiro tabuleiroEspecialista = new Tabuleiro(16, 30, 99);
							new TabuleiroConsole(tabuleiroEspecialista);
							break;		
						case 4:
							System.out.println("Saindo...");
							System.exit(0);						
						default:
							System.out.println("Opção inválida...Digite uma opção válida!");
						}
					
				}			
	}
	
	private static int verificaOpcaoDigitada() {
		final Scanner entrada = new Scanner(System.in);
		String opcao = entrada.next();
		// SAIR AO DIGITAR SAIR
		if("sair".equalsIgnoreCase(opcao)) {
			entrada.close();
			System.out.println("saindo...");
			System.exit(0);
		}
		
		int opcaoInt = Integer.parseInt(opcao);		
		return opcaoInt;
	}
	
	
	

}

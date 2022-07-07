package pcjunior.cm;

import pcjunior.cm.modelo.Tabuleiro;
import pcjunior.cm.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(6 ,6 ,10);
		new TabuleiroConsole(tabuleiro);
	}

}

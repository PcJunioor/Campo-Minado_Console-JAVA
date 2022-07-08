package pcjunior.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import pcjunior.cm.excecao.ExplosaoException;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<Campo>();
	
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		try {
			campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
				.findFirst().ifPresent(c -> c.abrirCampo());
		} catch (ExplosaoException e) {			
			campos.stream().filter(c -> c.getMinado()).forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	public void marcar(int linha, int coluna) {
		
		campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst().ifPresent(c -> c.marcarCampo());
	}


	// INICIAR TABULEIRO	
	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Campo(linha, coluna));
				
			}
		}		
	}
	
	private void associarVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}		
	}
	
	private void sortearMinas() {
		int minasArmadas = 0;
		
		Predicate<Campo> minado = c -> c.getMinado();
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = (int) campos.stream().filter(minado).count();
			
		} while(minasArmadas < minas);		
	}
	
	// JOGO GANHO
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	// REINICIAR JOGO
//	public void reiniciar() {
//		campos.stream().forEach(c -> c.reiniciarJogo());
//		sortearMinas();		
//	}
	
	// EXIBIR O TABULEIRO FOI NA TENTATIVA E ERRO
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		// CONTRU플O DAS COLUNAS
		sb.append("\n");
		sb.append("      ");
		// NUMERA플O DA COLUNA
		for(int c = 1; c <= colunas; c++) {
			String printColuna = String.format("%02d", c);
			sb.append("");
			sb.append(printColuna);
			sb.append(" ");			
		}
		sb.append("\n");
		// BARRAS INFERIORES DAS COLUNAS
		for(int c = 1; c<= colunas; c++) {
			sb.append("___");
		}
		sb.append("_____");		
		sb.append("\n");
		// CONSTRU플O DAS LINHAS
		for(int l = 1; l <= linhas; l++) {
			String printLinha = String.format("%02d", l);
			// NUMERA플O DAS LINHAS
			sb.append(" ");
			sb.append(printLinha + "|");
			sb.append(" ");
			for(int c = 1; c <= colunas; c++) {				
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("|");
			sb.append("\n");
		}
		sb.append("   |");
		for(int c = 1; c<= colunas; c++) {
			sb.append("___");
		}
		sb.append("_|");
		return sb.toString();
	}
	

}

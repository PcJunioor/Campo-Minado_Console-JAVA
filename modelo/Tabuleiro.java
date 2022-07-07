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
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciarJogo());
		sortearMinas();		
	}
	
	// EXIBIR O TABULEIRO
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("     ");
		for(int c = 1; c <= colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		sb.append("\n");
		for(int c = 1; c<= colunas; c++) {
			sb.append("___");
		}
		sb.append("_____");
		
		
		int i = 0;
		sb.append("\n");
		
		for(int l = 1; l <= linhas; l++) {
			sb.append(" ");
			sb.append(l + " |");
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

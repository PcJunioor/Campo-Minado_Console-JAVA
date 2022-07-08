package pcjunior.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import pcjunior.cm.excecao.ExplosaoException;

public class Campo {
	private final int linha;
	private final int coluna;
	
	private boolean minado = false;
	private boolean marcado = false;
	private boolean aberto = false;
	
	private List<Campo> vizinhos = new ArrayList<Campo>();
	
	
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	
	
	void minar() {
		minado = true;
	}

	void marcarCampo() {
		if(!aberto) {
		  marcado = !marcado;
		}
	}
	
	boolean abrirCampo() {
		if(!aberto && !marcado) {
			this.aberto = true;
			// FIM DE JOGO ACHOU MINA
			if(minado == true) {				
				throw new ExplosaoException();
			}
			
			if(vizinhancaSegura()) {
				// FUNÇÃO RECURSIVA
				vizinhos.forEach(v -> v.abrirCampo());
			}
			return true;
		} else {
			return false;
		}
	}
	
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	
	boolean adicionarVizinho(Campo supVizinho) {
		boolean linhaDiferente = this.linha != supVizinho.linha;
		boolean colunaDiferente = this.coluna != supVizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(this.linha - supVizinho.linha);
		int deltaColuna = Math.abs(this.coluna - supVizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(supVizinho);
			return true;			
		} else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(supVizinho);
			return true;
		} else 
			return false;
		
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = marcado && minado;
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
		
	}
	



	// GETTERS AND SETTERS
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}
	
	public boolean getAberto() {
		return aberto;
	}
	
	public boolean getMarcado() {
		return marcado;
	}
	
	public boolean getMinado() {
		return minado;
	}
	
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	// TO STRING
	public String toString() {
		if(marcado) {
			return "x"; // X REPRESENTA CAMPO MARCADO
		} else if(aberto && minado) {
			return "*"; // * REPRESENTA A MINA
		} else if(aberto && minasNaVizinhanca() > 0) {
			return  Long.toString(minasNaVizinhanca()); // QUANTIDADE DE MINAS NA VIZINHANÇA
		} else if(aberto) {
			return " "; // SEM MINAS AO REDOR (ESPAÇO EM BRANCO)
		} else
			return "#"; // CAMPO FECHADO
		
	}
	 
	

}

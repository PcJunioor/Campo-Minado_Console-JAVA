package pcjunior.cm.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pcjunior.cm.excecao.ExplosaoException;

class CampoTeste {
	
	// ATRIBUTOS
	private Campo campo;
	
	// INICIALIZAR O CAMPO
	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3,3);
	}

	// TESTES VIZINHANÇA
	@Test
	void testeIsVizinho() {
		Campo vizinho = new Campo(3,2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);		
	}
	@Test
	void testeIsVizinhoDiagonal() {
		Campo vizinho = new Campo(4,2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);		
	}
	@Test
	void testeNotVizinho() {
		Campo vizinho = new Campo(5,2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);		
	}
	@Test
	void testeNotVizinhoDiagonal() {
		Campo vizinho = new Campo(4,5);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);		
	}
	
	// TESTES MARCAR CAMPO
	@Test
	void testeMarcarCampo() {
		campo.marcarCampo();
		assertTrue(campo.getMarcado());
	}
	@Test
	void testeMarcarCampoDuasChamadas() {
		campo.marcarCampo();
		campo.marcarCampo();
		assertFalse(campo.getMarcado());
	}
	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campo.getMarcado());
	}
	
	// TESTES ABRIR CAMPO
	
	@Test
	void testeAbrirCampoNaoMinadoNaoMarcado() {
		assertTrue(campo.abrirCampo());
	}
	@Test
	void testeAbrirCampoNaoMinadoMarcado() {
		campo.marcarCampo();
		assertFalse(campo.abrirCampo());
	}
	@Test
	void testeAbrirCampoMinadoMarcado() {
		campo.marcarCampo();
		campo.minar();
		assertFalse(campo.abrirCampo());
	}
	@Test
	void testeAbrirCampoMinadoNaoMarcado() {
		campo.minar();
		// TESTAR SE O TIPO DE EXCEÇÃO FOI A ESPERADA
		assertThrows(ExplosaoException.class, () -> {
			campo.abrirCampo();
		});
		
	}	
	@Test
	void testeAbrirComVizinhos1() {
		Campo campo11 = new Campo(1,1);
		Campo campo12 = new Campo(1,2);
		Campo campo22 = new Campo(2,2);		
		
		campo12.minar();		
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		campo.adicionarVizinho(campo22);
		campo.abrirCampo();
		assertTrue(campo22.getAberto() && !campo11.getAberto());
	}
	@Test
	void testeAbrirComVizinhos2() {
		Campo campo22 = new Campo(2,2);
		Campo campo11 = new Campo(1,1);
		campo22.adicionarVizinho(campo11);		
		campo.adicionarVizinho(campo22);
		campo.abrirCampo();
		assertTrue(campo22.getAberto() && campo11.getAberto());
	}
	
	// TESTE GETTERS	
	@Test
	void testeGetLinha() {
		assertTrue(campo.getLinha() == 3);
	}
	@Test
	void testeGetColuna() {
		assertTrue(campo.getColuna() == 3);
	}
	
	@Test
	void testeReiniciarJogo() {
		campo.minar();
		campo.marcarCampo();
		campo.abrirCampo();
		campo.reiniciarJogo();
		assertFalse(campo.getAberto() && campo.getMinado() && campo.getMarcado());
	}
	
	@Test
	void testeObjetivoAlcancadoDesvendado() {
		campo.abrirCampo();
		assertTrue(campo.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivoAlcancadoProtegido() {
		campo.minar();
		campo.marcarCampo();		
		assertTrue(campo.objetivoAlcancado());
	}
	
	@Test
	void testeMinasNaVizinhanca() {
		
		Campo campo11 = new Campo(1,1);
		Campo campo12 = new Campo(1,2);
		Campo campo22 = new Campo(2,2);
		
		campo12.minar();
		campo22.minar();
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		campo.adicionarVizinho(campo22);
		campo.abrirCampo();
		assertTrue(campo.minasNaVizinhanca() > 0);
		
	}
	
	// TESTES DO TOSTRING
	
	@Test
	void testeToStringMarcado() {
		campo.marcarCampo();
		assertEquals("x", campo.toString());
		
	}
	
	@Test
	void testeToStringAbertoMinado() {
		campo.minar();		
		assertThrows(ExplosaoException.class, () -> {
			campo.abrirCampo();			
		});
		assertEquals("*", campo.toString());
	}
	
	@Test
	void testeToStringQuantidadeMinasVizinhanca() {
		Campo campo11 = new Campo(1,1);
		Campo campo12 = new Campo(1,2);
		Campo campo22 = new Campo(2,2);
		Campo campo23 = new Campo(2,3);
		
		campo12.minar();
		campo23.minar();
		campo22.minar();
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		campo.adicionarVizinho(campo22);
		campo.adicionarVizinho(campo23);
		campo.abrirCampo();
		assertEquals("2", campo.toString());
	}
	
	@Test
	void testeToStringAberto() {
		campo.abrirCampo();
		assertEquals(" ", campo.toString());		
	}
	
	@Test
	void testeToStringFechado() {		
		assertEquals("?", campo.toString());		
	}
}

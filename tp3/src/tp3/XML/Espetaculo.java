package tp3.XML;

import java.util.Arrays;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Classe que representa um Evento do tipo Espetaculo.
 * 
 * @version 1.0 
 * @author Docentes da Disciplina de Modelação e Programação, LEIM, Instituto Superior de Engenharia de Lisboa
 *
 */
public class Espetaculo extends Evento {
	
	private static final int MAX_ARTISTAS = 10;
	private String[] artistas = new String[MAX_ARTISTAS];
	private int nArtistas = 0;
	private int numBilhetes;
	private String localidade;
			
	/**
	 * Constroi um novo Espetaculo
	 * @param nome nome do Espetaculo
	 * @param localidade a localidade do Espetaculo
	 * @param numBilhetes o número de bilhetes disponíveis
	 */
	public Espetaculo(String nome, String localidade, int numBilhetes) {
		//TODO
	}

	/**
	 * Informa se um determinado artista irá actuar no Espetaculo. 
	 * @return 1, se actuar e 0 caso contrário.
	 * @Override
	 */
	public int numActuacoes(String artista) {
		//TODO
	}
	
	/**
	 * Permite adicionar un novo artista ao Espetaculo se o artista ainda 
	 * não tem actuações e se o número máximo de artistas ainda não foi ultrapassado.
	 * @param artista representa o novo artista
	 * @return verdadeiro, caso o artista tenha sido adicionado e falso caso contrário.
	 */
	public boolean addArtista(String artista) {
		//TODO
	}
	
	/**
	 * Devolve o número de bilhetes.
	 * @Override
	 */
	public int getNumBilhetes() {
		//TODO
	}

	/**
	 * Devolve uma cópia dos artistas que actuam no Espetaculo.
	 * @Override
	 */
	public String[] getArtistas() {
		//TODO
	}

	/**
	 * Devolve a localidade do Espetaculo
	 * @return a localidade.
	 */
	public String getLocalidade() { 
		//TODO    
	}
	
	/**
	 * Devolve uma String a representar o Espetaculo.
	 * Nota: Ver o ficheiro OutputPretendido.txt
     * @Override
	 */
	public String toString() {
		//TODO
	}
	
	
	/**
	 * Constroi um novo Evento a partir do objecto Node passado como parâmetro.
	 * @param nNode
	 * @return um novo Evento
	 */
	public static Evento build(Node nNode) {
		//TODO
	}
	
	/**
	 *  Constroi um novo Element a partir do Espectaculo actual.
	 *  @param doc - o documento que irá gerar o novo Element
	 */
	public Element createElement(Document doc) {
		//TODO
	}
	
	
}

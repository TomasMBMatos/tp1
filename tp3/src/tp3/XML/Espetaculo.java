package tp3.XML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
		super(nome);
		if( localidade == null || localidade.isBlank() ) throw new IllegalArgumentException("O nome da localidade não pode ser vazio");
		this.localidade = localidade;
		if( numBilhetes <= 0 ) throw new IllegalArgumentException("O número de bilhetes tem que ser superior a zero");
		this.numBilhetes = numBilhetes;
	}

	/**
	 * Informa se um determinado artista irá actuar no Espetaculo. 
	 * @return 1, se actuar e 0 caso contrário.
	 * @Override
	 */
	public int numActuacoes(String artista) {
		for(String art : artistas) {
			if(art != null) {
				if(art.equalsIgnoreCase(artista)) return 1;
			}
		}
		return 0;
	}
	
	/**
	 * Permite adicionar un novo artista ao Espetaculo se o artista ainda 
	 * não tem actuações e se o número máximo de artistas ainda não foi ultrapassado.
	 * @param artista representa o novo artista
	 * @return verdadeiro, caso o artista tenha sido adicionado e falso caso contrário.
	 */
	public boolean addArtista(String artista) {
		if(nArtistas == MAX_ARTISTAS) return false;
		for(int i=0; i<artistas.length;i++) {
			if(artista == null || Objects.equals(artistas[i], artista)) return false;
			if(artistas[i] == null) {
				artistas[i] = artista;
				nArtistas++;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Devolve o número de bilhetes.
	 * @Override
	 */
	public int getNumBilhetes() {
		return this.numBilhetes;
	}

	/**
	 * Devolve uma cópia dos artistas que actuam no Espetaculo.
	 * @Override
	 */
	public String[] getArtistas() {
		List<String> art = new ArrayList<>();
		for(String artista : artistas) {
			if(artista != null)
				art.add(artista);
		}
		return art.toArray(String[]::new);
	}

	/**
	 * Devolve a localidade do Espetaculo
	 * @return a localidade.
	 */
	public String getLocalidade() { 
		return this.localidade;
	}
	
	/**
	 * Devolve uma String a representar o Espetaculo.
	 * Nota: Ver o ficheiro OutputPretendido.txt
     * @Override
	 */
	public String toString() {
		return super.toString() + "em " + localidade;
	}
	
	
	/**
	 * Constroi um novo Evento a partir do objecto Node passado como parâmetro.
	 * @param nNode
	 * @return um novo Evento
	 */
	public static Evento build(Node nNode) {
		String numBilhetes = ((Element) nNode).getAttribute("numBilhetes");
		String nome = "";
		String localidade = "";
		ArrayList<String> artistasList = new ArrayList<String>();
		NodeList list = ((Element) nNode).getChildNodes();
		for(int i=0;i<list.getLength();i++) {
			Node node = list.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			String nomeElement = element.getTagName();
			if(nomeElement.equals("Nome")) {
				nome = element.getTextContent();
			}
			else if(nomeElement.equals("Artistas")) {
				NodeList artistas = element.getChildNodes();
				for(int j=0;j<artistas.getLength();j++) {
					Node artista = artistas.item(j);
					if (artista.getNodeType() == Node.ELEMENT_NODE) {
						artistasList.add(((Element) artista).getTextContent());
					}
				}
			}
			else if(nomeElement.equals("Localidade")) {
				localidade = element.getTextContent();
			}
		}
		Espetaculo espetaculo = new Espetaculo(nome, localidade, Integer.parseInt(numBilhetes));
		for(String artista : artistasList) {
			espetaculo.addArtista(artista);
		}
		return espetaculo;
	}
	
	/**
	 *  Constroi um novo Element a partir do Espectaculo actual.
	 *  @param doc - o documento que irá gerar o novo Element
	 */
	public Element createElement(Document doc) {
		Element espetaculo = doc.createElement("Espetaculo");
		espetaculo.setAttribute("numBilhetes", Integer.toString(numBilhetes));
		Element nome = doc.createElement("Nome");
		nome.appendChild(doc.createTextNode(this.nome));
		espetaculo.appendChild(nome);

		Element elemArtistas = doc.createElement("Artistas");
		for(String art : artistas) {
			Element artista = doc.createElement("Artista");
			artista.appendChild(doc.createTextNode(art));
			elemArtistas.appendChild(artista);
		}
		espetaculo.appendChild(elemArtistas);

		Element localidade = doc.createElement("Localidade");
		localidade.appendChild(doc.createTextNode(this.localidade));
		espetaculo.appendChild(localidade);

		return espetaculo;
	}
}

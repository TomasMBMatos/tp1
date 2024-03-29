package tp3.XML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Classe que representa um Evento do tipo Festival.
 * 
 * @version 1.0 
 * @author Docentes da Disciplina de Modelação e Programação, LEIM, Instituto Superior de Engenharia de Lisboa
 *
 */
public class Festival extends Evento {

	private static final int MAX_EVENTOS = 20;

	private Evento[] eventos = new Evento[MAX_EVENTOS];
	private int numEventos = 0;

	public Festival(String nome) {
		super(nome);
	}

	/**
	 * Devolve todos os bilhetes existentes no Festival (somando e devolvendo todos os bilhetes dos seus Eventos).
	 * @return o número de bilhetes existentes no Festival.
	 */
	public int getNumBilhetes() {
		int cnt = 0;
		for (Evento evento : eventos) {
			if (evento != null)
				cnt += evento.getNumBilhetes();
		}
		return cnt;
	}


	/**
	 * Retorna o número de actuaçõoes de um determinado artista.
	 * @param artista, o nome do artista.
	 * @Override
	 */
	public int numActuacoes(String artista) {
		int cnt = 0;
		for(Evento evento : eventos) {
			if(evento == null) {
				return 0;
			}
			if(evento instanceof Espetaculo) {
				for(String art : evento.getArtistas()) {
					if(art.equalsIgnoreCase(artista)) {
						cnt++;
					}
				}
			} else if(evento instanceof Festival) {
				cnt += evento.numActuacoes(artista);
			}
		}
		return cnt;
	}

	/**
	 *  Devolve uma string representativa do Festival.
	 *  Nota: Ver o ficheiro OutputPretendido/OutputPretendido.txt
	 */
	public String toString() {
		return "Festival " + super.toString();
	}

	/**
	 * Devolve um array contendo todos, de forma não repetida, os nomes de todos os artistas quer irão
	 * actuar no Festival.
	 * @return um array contendo os nomes dos artistas.
	 */
	public String[] getArtistas() {
		List<String> artistas = new ArrayList<>();
		for(Evento evento: eventos) {
			if(evento instanceof Espetaculo) {
				for(String artista: evento.getArtistas()) {
					if(evento.getArtistas() != null && !artistas.contains(artista)) artistas.add(artista);
				}
			} else if(evento instanceof Festival) {
				artistas.addAll(Arrays.asList(evento.getArtistas()));
			}
		}
		return artistas.toArray(String[]::new);
	}

	/**
	 * Retorna a profundidade maxima da "árvore" que contém Festivais dentro de Festivais. O próprio Festival não conta.
	 * @return a profundidade máxima.
	 */
	public int getDeepFestival() {
		int ret = 0;
		for(Evento evento: eventos) {
			int profundidade = 1;
			if(evento instanceof Festival) {
				profundidade += ((Festival) evento).getDeepFestival();
			}
			if(profundidade > ret) {
				ret = profundidade;
			}
		}
		return ret;
	}

	/**
	 * Adiciona um novo Evento ao Festival, caso para nenhum dos artistas do novo evento se verifique que o seu número de atuações no
	 * novo evento (a adicionar) supere em mais de duas o número de atuações no festival corrente.
	 * @param evento, evento a ser recebido
	 * @return verdadeiro, se o novo Evento foi adicionado.
	 */
	public boolean addEvento(Evento evento) {
		if(numEventos == MAX_EVENTOS) return false;
		for(String artista : evento.getArtistas()) {
			if(evento.numActuacoes(artista) > numActuacoes(artista) + 2) return false;
		}
		for(int i=0;i<eventos.length;i++) {
			if(eventos[i] == null) {
				eventos[i] = evento;
				numEventos++;
				break;
			}
		}
		return true;
	}

	/**
	 * Remove um evento em qualquer profundidade do Festival corrente.
	 * @param nomeEvento nome do Evento a remover.
	 * @return verdadeiro, se o Evento foi removido.
	 */
	public boolean delEvento(String nomeEvento) {
		for(int i=0;i<eventos.length;i++) {
			if(eventos[i] == null) continue;
			if(eventos[i].nome.equals(nomeEvento)) {
				eventos[i] = null;
				numEventos--;
				return true;
			} else if (eventos[i] instanceof Festival) {
				if(((Festival) eventos[i]).delEvento(nomeEvento)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Imprime na consola informações sobre o Festival.
	 * Nota: Ver o output pretendido em OutputPretendido/OutputPretendido.txt.
	 * @param prefix, o prefixo para identar o Festival de acordo com a sua profundidade.
	 */
	public void print(String prefix) {
		System.out.println(this);
		for(Evento evento : eventos) {
			if(evento != null) {
				System.out.print("    ");
				evento.print(prefix);
			}
			//System.out.printf("Festival %s com %d bilhetes e com os artistas %s em", super.toString(), getNumBilhetes(), Arrays.toString(getArtistas()));
		}
	}

	/**
	 * Constroi um novo Festival a partir de um nó contendo as infomações lidas do documento XML.
	 * @param nNode o nó associado ao Festival
	 * @return um novo Festival
	 */
	public static Festival build(Node nNode) {
		String nome = "";
		ArrayList<Evento> eventos = new ArrayList<>();
		NodeList list = nNode.getChildNodes();
		for(int i=0;i<list.getLength(); i++) {
			if(list.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) list.item(i);
			String nomeElement = element.getTagName();
			if(nomeElement.equals("Nome")) nome = element.getTextContent();
			else if(nomeElement.equals("Eventos")) {
				for(int j=0;j<element.getChildNodes().getLength();j++) {
					Node event_node = element.getChildNodes().item(j);
					if(event_node.getNodeType() == Node.ELEMENT_NODE) eventos.add(Evento.build(event_node));
				}
			}
		}
		Festival festival = new Festival(nome);
		for(Evento evento : eventos) {
			festival.addEvento(evento);
		}
		return festival;
	}

	/**
	 * Cria um novo Elemento quer irá representar, no documento XML, o Festival associado ao Festival corrente.
	 * @param doc o Documento que irá ser usado para gerar o novo Element.
	 */
	public Element createElement(Document doc) {
		Element festival = doc.createElement("Festival");
		Element nome = doc.createElement("Nome");
		nome.appendChild(doc.createTextNode(this.nome));
		festival.appendChild(nome);

		Element events = doc.createElement("Eventos");
		for(Evento evento : eventos) {
			if(evento != null)
				events.appendChild(evento.createElement(doc));
		}
		festival.appendChild(events);
		return festival;
	}

	/**
	 * Método main que gera no output o que está no ficheiro OutputPretendido/OutputPretendido.txt e cria um novo
	 * documento XML/Eventos.xml, com a mesma estrutura que o documento OutputPretendido/Eventos.xml.
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			File inputFile = new File("tp1/tp3/XML/BaseDados.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			XPath xpath = XPathFactory.newInstance().newXPath();
			String expression = "/BaseDados/Eventos/*";
			NodeList nList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

			Node nNode = nList.item(0);
			Evento evento = Evento.build(nNode);
			if(evento != null) evento.print("");


			Festival fNovo = new Festival("Bollywood Music Festival");

			Espetaculo e1_1 = new Espetaculo("Suna Hai", "Sines", 500);
			e1_1.addArtista("Suna Hai");
			fNovo.addEvento(e1_1);

			Espetaculo e1_2 = new Espetaculo("Rait Zara", "Sines", 400);
			e1_2.addArtista("Rait Zara");
			fNovo.addEvento(e1_2);

			if(evento instanceof Festival) {

				Festival festival = (Festival)evento;
				festival.addEvento(fNovo);

				// root elements
				Document newDoc = dBuilder.newDocument();
				Element rootElement = newDoc.createElement("Eventos");

				rootElement.appendChild(festival.createElement(newDoc));

				newDoc.appendChild(rootElement);

				FileOutputStream output = new FileOutputStream("tp1/tp3/XML/Eventos.xml");
				writeXml(newDoc, output);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Escreve, para o OutputStream, o documento doc.
	 * @param doc o documento contendo os Elementos a gravar on ficheiro output
	 * @param output o ficheiro de saída.
	 */
	private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        // pretty print XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }


}









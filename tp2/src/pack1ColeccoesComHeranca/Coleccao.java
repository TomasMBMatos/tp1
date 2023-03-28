package pack1ColeccoesComHeranca;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Classe Coleccao, deve conter a descrição de uma colecção, com título, os seus
 * livros, colecções e editores. Deve utilizar herança para guardar os livros e
 * as colecções num só array
 */
public class Coleccao extends Obra {
	// prefixo a colocar no início de cada print mais interno que o corrente
	public static final String GENERALPREFIX = "  ";

	// número máximo de obras de uma colecção
	private static int MAXOBRAS = 20;

	// Array de obras, de Livros ou Coleccçõe, em que estas devem encontrar-se
	// sempre nos menores índices e pela ordem de registo
	private IObra[] obras = new IObra[MAXOBRAS];

	// deverá conter sempre o número de obras na colecção
	private int numObras = 0;

	// Editores, tem as mesmas condicionantes que array de autores na classe
	// livro
	private String[] editores;

	/**
	 * Construtor; o título deve ser guardado e validado na clase obra; o array de
	 * editores devem ser pelo menos um e tem as mesmas restrições que os autores
	 * dos livros;
	 */
	public Coleccao(String titulo, String[] editores) {
		super(titulo);
		if(editores.length == 0 || Arrays.stream(editores).anyMatch(Objects::isNull))
			throw new IllegalArgumentException("O array de editores não pode estar vazio");
		this.editores = editores;
	}

	/**
	 * Obtem o número total de páginas da colecção, páginas dos livros e das
	 * colecções
	 */
	public int getNumPaginas() {
		int numPaginas = 0;
		for (IObra obra : obras) {
			if (obra != null) {
				numPaginas += obra.getNumPaginas();
			}
		}
		return numPaginas;
	}

	/**
	 * As colecções com mais de 5000 páginas nos seus livros directos têm um
	 * desconto de 20% nesses livros. As colecções em que o somatório de páginas das
	 * suas subcolecções directas seja igual ou superior ao quádruplo do nº de
	 * páginas da sua subcolecção directa com mais páginas deverão aplicar um
	 * desconto de 10% sobre os preços das suas subcolecções
	 */

	public float getPreco() {
		float precoLivro = 0.0f;
		float preco = 0.0f;
		float pag = 0;
		for(IObra obra : obras) {
			if(obra instanceof Livro) {
				precoLivro += obra.getPreco();
				pag += obra.getNumPaginas();
			}
			if(obra instanceof Coleccao) {
				preco += obra.getPreco();
			}
		}
		if(pag > 5000) {
			preco += precoLivro * 0.8;
		}
		else{
			preco += precoLivro;
		}
		return preco;
	}

	/**
	 * Adiciona uma obra à colecção se puder, se esta não for null e a colecção não
	 * ficar com obras com iguais no seu nível imediato. Deve utilizar o método
	 * getIndexOfLivro e getIndexOfColeccao
	 */
	public boolean addObra(Obra obra) {
		if(obra == null || getIndexOfObra(obra.getTitulo()) >= 0
				|| numObras == MAXOBRAS) return false;

		obras[numObras] = (IObra) obra;
		numObras++;
		return true;
	}

	/**
	 * Devolve o index no array de obras onde estiver a obra com o nome pretendido.
	 * Devolve -1 caso não o encontre
	 */
	private int getIndexOfObra(String titulo) {
		for(int i=0; i<obras.length; i++) {
			if(obras[i] != null && Objects.equals(obras[i].getTitulo(), titulo)) return i;
		}
		return -1;
	}

	/**
	 * Remove do array a obra com o título igual ao título recebido. Devolve a obra
	 * removida ou null caso não tenha encontrado a obra. Deve-se utilizar o método
	 * getIndexOfLivro. Recorda-se que as obras ocupam sempre os menores índices, ou
	 * seja, não pode haver nulls entre elas.
	 */
	public Obra remObra(String titulo) {
		int idx = getIndexOfObra(titulo);
		if(idx == -1) return null;
		ArrayList<IObra> ar = new ArrayList<>();
		Collections.addAll(ar, obras);
		IObra c = ar.get(idx);
		ar.remove(idx);
		obras = ar.toArray(IObra[]::new);
		numObras--;
		return (Obra) c;
	}

	/**
	 * Remove todas as obras (livros ou colecções) dentro da obra corrente, que
	 * tenham um título igual ou título recebido. Devolve true se removeu pelo menos
	 * uma obra, ou false caso não tenha trealizado qualquer remoção. Deve utilizar
	 * os métodos remObra e remAllObra.
	 */
	public boolean remAllObra(String titulo) {
		boolean rem = false;
		for(IObra obra: obras) {
			if(obra instanceof Livro) {
				if(obra.getTitulo().equals(titulo)) {
					this.remObra(titulo);
					rem = true;
				}
			}
			else if(obra instanceof Coleccao) {
				if(obra.getTitulo().equals(titulo)) {
					this.remObra(titulo);
					rem = true;
				}
			}
		}
		return rem;
	}

	/**
	 * Devolve o nº de obras de uma pessoa. Cada colecção deve contabilizar-se como
	 * uma obra para os editores.
	 */
	public int getNumObrasFromPerson(String autorEditor) {
		int num = 0;
		for(String editor: (this).editores) {
			if(editor.equals(autorEditor)) {
				num++;
				break;
			}
		}
		for(IObra obra: obras) {
			if(obra == null) {
				break;
			}
			if(obra instanceof Livro) {
				for(String autor: ((Livro) obra).getAutores())
					if(autor.equals(autorEditor)) {
						num++;
						break;
					}
			}
			if(obra instanceof Coleccao) {
				num += ((Coleccao) obra).getNumObrasFromPerson(autorEditor);
			}
		}
		return num;
	}

	/**
	 * Deve devolver um novo array, sem repetições, com os livros de que o autor
	 * recebido é autor. O array devolvido não deve conter repetições, para excluir
	 * as repetições devem utilizar o método mergeWithoutRepetitions
	 */
	public Livro[] getLivrosComoAutor(String autorNome) {
		Livro[] arrayLivros = new Livro[MAXOBRAS];

		for (int i=0;i<numObras;i++) {
			if (obras[i] instanceof Livro) {
				boolean autorLivro = false;
				for (String autor : ((Livro) obras[i]).getAutores()) {
					if (autor.equalsIgnoreCase(autorNome)) {
						arrayLivros = mergeWithoutRepetitions(arrayLivros, new Livro[]{(Livro) (obras[i])});
						break;
					}
				}
			} else if (obras[i] instanceof Coleccao coleccao) {
				arrayLivros = mergeWithoutRepetitions(arrayLivros, coleccao.getLivrosComoAutor(autorNome));
			}
		}
		int numLivros = 0;
		for (Livro arrayLivro : arrayLivros) {
			if (arrayLivro != null) numLivros++;
		}
		Livro[] finalArray = new Livro[numLivros];
		for( int i = 0, j = 0; i < arrayLivros.length; i++ ) {
			if( arrayLivros[i] != null ) {
				finalArray[j] = arrayLivros[i];
				j++;
			}
		}
		return finalArray;
	}

	/**
	 * Deve devolver um array, sem nulls, com todos os autores e editores existentes
	 * na colecção. O resultado não deve conter repetições. Deve utilizar o método
	 * mergeWithoutRepetitions
	 */
	public String[] getAutoresEditores() {
		String[] editores = this.editores;
		// juntar os editores da coleçao inicial com os autores dos livros
		for (IObra obra : obras) {
			if (obra instanceof Livro livro) {
				editores = mergeWithoutRepetitions(editores, livro.getAutores());
			} else if (obra != null && obra.toString().contains("editores")) {
				editores = mergeWithoutRepetitions(editores, ((Coleccao) obra).getAutoresEditores());
			}
		}
		int cnt = 0;
		assert editores != null;
		for (String editor : editores)
			if (editor != null)
				cnt++;

		String[] editoresFinal = new String[cnt];

		for( int i = 0, j = 0; i < editores.length; i++ ) {
			if( editores[i] != null ) {
				editoresFinal[j] = editores[i];
				j++;
			}
		}
		return editoresFinal;
	}

	/**
	 * Método que recebendo dois arrays sem repetições devolve um novo array com
	 * todos os elementos dos arrays recebidos mas sem repetições
	 */
	private static String[] mergeWithoutRepetitions(String[] a1, String[] a2) {
		return Stream.concat(Stream.of(a1), Stream.of(a2)).
				distinct().toArray(String[]::new);
	}

	/**
	 * Método idêntico ao método anterior mas agora com arrays de livros
	 */
	private static Livro[] mergeWithoutRepetitions(Livro[] a1, Livro[] a2) {
		return Stream.concat(Stream.of(a1), Stream.of(a2)).
				distinct().toArray(Livro[]::new);
	}

	/**
	 * Devolve o nº de livros dentro da colecção
	 */
	public int getNumLivros() {
		int numLivros = 0;
		for(IObra obra: obras) {
			if(obra instanceof Coleccao) {
				numLivros += ((Coleccao) obra).getNumLivros();
			}
			else if(obra instanceof Livro) numLivros++;
		}
		return numLivros;
	}

	/**
	 * Devolve o nº de colecções dentro da colecção
	 */
	public int getNumColeccoes() {
		int numColeccoes = 0;
		for(IObra obra: obras) {
			if(obra instanceof Coleccao) {
				numColeccoes++;
				numColeccoes += ((Coleccao) obra).getNumColeccoes();
			}
		}
		return numColeccoes;
	}

	/**
	 * Devolve a profundidada de máxima de uma colecção em termos de coleccões
	 * dentro de coleccções: uma colecção c1 com uma colecção c2 dentro, c1 deve
	 * devolver 2 e c2 deve devolver 1, independentemente do número do conteúdo de
	 * cada uma.
	 */
	public int getProfundidade() {
		int ret = 1;
		for(IObra obra: obras) {
			int profundidade = 0;
			if(obra instanceof Coleccao) {
				profundidade += ((Coleccao) obra).getProfundidade() + 1;
			}
			if(profundidade > ret) {
				ret = profundidade;
			}
		}
		return ret;
	}

	/**
	 * Duas colecções são iguais se tiverem o mesmo título e a mesma lista de
	 * editores. Deve utilizar o equals da classe Obra. Para verificar verificar se
	 * os editores são os mesmos devem utilizar o método mergeWithoutRepetitions
	 */
	public boolean equals(Object c) {
		return super.equals(c);
	}

	/**
	 * Deve devolver uma string compatível com os outputs desejados
	 */
	public String toString() {
		return String.format("%s, %dp, %2.1f€, editores %s, com %d livros, com %d colecções e com profundidade máxima de %d",
				super.toString(), getNumPaginas(), getPreco(), Arrays.toString(editores),getNumLivros(), getNumColeccoes(), getProfundidade());
	}

	/**
	 * Mostra uma colecção segundo os outputs desejados. Deve utilizar o método
	 * print da classe Obra.
	 */
	public void print(String prefix) {
		System.out.println(prefix + String.format("%s", this));
		for(IObra obra : obras) {
			if(obra instanceof  Livro) {
				System.out.printf("  %s\n",obra);
			}
			else if(obra instanceof Coleccao) {
				obra.print(prefix);
			}
		}
	}

	/**
	 * main
	 */
	public static void main(String[] args) {
		Livro l1 = new Livro("Viagem aos Himalaias", 340, 12.3f, new String[] { "João Mendonça", "Mário Andrade" });
		Livro l2 = new Livro("Viagem aos Pirinéus", 270, 11.5f, new String[] { "João Mendonça", "Júlio Pomar" });

		Coleccao c1 = new Coleccao("Primavera", new String[] { "João Mendonça", "Manuel Alfazema" });

		boolean res;

		res = c1.addObra(l1);
		res = c1.addObra(l2);
		System.out.println("c1 -> " + c1);
		c1.print("");
		System.out.println();

		// adicionar um livro com nome de outro já existente
		res = c1.addObra(l2);
		System.out.println("adição novamente de Viagem aos Pirinéus a c1 -> " + res);
		System.out.println("c1 -> " + c1);
		System.out.println();

		// Outra colecção
		Livro l21 = new Livro("Viagem aos Himalaias 2", 340, 12.3f, new String[] { "João Mendonça", "Mário Andrade" });
		Livro l22 = new Livro("Viagem aos Pirinéus 2", 270, 11.5f, new String[] { "João Mendonça", "Júlio Pomar" });

		Coleccao cx2 = new Coleccao("Outono", new String[] { "João Mendonça", "Manuel Antunes" });
		cx2.addObra(l21);
		cx2.addObra(l22);
		System.out.println("cx2 -> " + cx2);
		cx2.print("");
		System.out.println();

		// adicioná-la a c1
		c1.addObra(cx2);
		System.out.println("c1 após adição da colecção cx2 -> " + c1);
		c1.print("");
		System.out.println();

		// get editores autores
		String[] ae = c1.getAutoresEditores();
		System.out.println("Autores editores of c1 -> " + Arrays.toString(ae));
		System.out.println();

		// getNumObrasFromPerson
		String nome = "João Mendonça";
		int n = c1.getNumObrasFromPerson(nome);
		System.out.println("Nº de obras de " + nome + " -> " + n);
		System.out.println();

		// getLivrosComoAutor
		nome = "João Mendonça";
		Livro[] livros = c1.getLivrosComoAutor(nome);
		System.out.println("Livros de " + nome + " -> " + Arrays.toString(livros));
		System.out.println();
		System.out.println();

		// testes aos métodos getNumLivros, getNumColeccoes e getProfundidade
		c1.print("");
		System.out.println("Nº de livros na colecção " + c1.getTitulo() + " -> " + c1.getNumLivros());

		System.out.println("Nº de colecções dentro da colecção " + c1.getTitulo() + " -> " + c1.getNumColeccoes());

		System.out.println("Profundidade da colecção " + c1.getTitulo() + " -> " + c1.getProfundidade());
		System.out.println("Profundidade da colecção " + cx2.getTitulo() + " -> " + cx2.getProfundidade());
		System.out.println();

		// rem livro
		String nomeLivro = "Viagem aos Himalaias";
		Obra l = c1.remObra(nomeLivro);
		System.out.println("Remoção de " + nomeLivro + " -> " + l);
		c1.print("");

	}
}
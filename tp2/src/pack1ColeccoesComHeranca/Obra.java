package pack1ColeccoesComHeranca;

import java.util.Objects;

public abstract class Obra implements IObra {

	private String titulo;

	/**
	 * Constructor
	 */
	public Obra(String titulo) {
		if (titulo == null || titulo.length() == 0)
			throw new IllegalArgumentException(
					"O titulo tem de ter pelo menos um caracter");
		this.titulo = titulo;
	}

	/**
	 * Devolve o t�tulo da obra
	 */
	public String getTitulo() {
		return this.titulo;
	}

	/**
	 * Deve devolver true se o array conter apenas nomes v�lidos. Cada nome deve ser
	 * validado pelo método validarNome
	 */
	public static boolean validarNomes(String[] nomes) {
		for(String nome: nomes) {
			if(!validarNome(nome)) return false;
		}
		return true;
	}

	/**
	 * Um nome v�lido se n�o for null e conter pelo menos uma letra
	 * (Character.isLetter) e s� conter letras e espa�os (Character.isWhitespace)
	 */
	public static boolean validarNome(String nome) {
		if(nome != null) {
			for(int i=0;i<nome.length();i++) {
				if(!Character.isLetter(nome.charAt(i)) ||
					Character.isWhitespace(nome.charAt(i))) {
					return false;
				}
			}
			return false;
		}
		return false;
	}

	/**
	 * Recebe um nome j� previamente validado, ou seja s� com letras ou espa�os.
	 * Deve devolver o mesmo nome mas sem espa�os (utilizar trim e
	 * Character.isWhitespace) no in�cio nem no fim e s� com um espa�o ' ' entre
	 * cada nome. Deve utilizar um StringBuilder para ir contendo o nome j�
	 * corrigido
	 */
	public static String removeExtraSpaces(String nome) {
		int wsCount = 1;
		StringBuilder sb = new StringBuilder(nome.trim());
		for(int i=0;i<sb.length();i++) {
			if(Character.isWhitespace(sb.charAt(i))) {
				if(wsCount == 0) sb.delete(i,i+1);
				else wsCount--;
			}
		}
		return nome.trim();
	}

	/**
	 * Método que verifica se h� elementos repetidos. O array recebido n�o cont�m
	 * nulls.
	 */
	public static boolean haRepeticoes(String[] elems) {
		for(int i=0;i<elems.length;i++) {
			for(int j=1;j< elems.length;j++) {
				if(i == j) continue;
				if(elems[i].equals(elems[j])) return true;
			}
		}
		return false;
	}

	/**
	 * Devolve uma string com a informa��o da obra (ver outputs desejados e método
	 * toString de Livro)
	 */
	public String toString() {
		return getTitulo();
	}

	/**
	 * Deve mostrar na consola a informa��o da obra (toString) precedida do prefixo
	 * recebido
	 */
	public void print(String prefix) {
		System.out.println(prefix + this);
	}

	/**
	 * O Object recebido � igual, se n�o for null, se for uma obra e se tiver o
	 * mesmo t�tulo que o t�tulo da obra corrente
	 */
	public boolean equals(Object l) {
		if(l instanceof Obra) {
			return Objects.equals(this.titulo, ((Obra) l).titulo);
		}
		return false;
	}

}
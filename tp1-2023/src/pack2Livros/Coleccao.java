package pack2Livros;

import java.util.*;
import java.util.stream.Stream;

/**
 * Classe Colecca, deve conter a descrição de uma colecção, com título, seus
 * livros e editores
 */
public class Coleccao {

    // número máximo de obras de uma colecção
    private static int MAXOBRAS = 20;

    // prefixo usual
    public static final String GENERALPREFIX = "  ";

    // título da colecção
    private String titulo;

    // Array de livros, em que estas encontram-se sempre nos menores índices e
    // pela ordem de registo
    private Livro[] livros = new Livro[MAXOBRAS];

    // deverá conter sempre o número de livros na colecção
    private int numLivros = 0;

    // Editores, tem as mesmas condicionantes que array de autores na classe
    // livro
    private String[] editores;

    /**
     * Construtor; o título tem de ter pelo menos um caracter que não seja um
     * espaço (Character.isWhitespace); o array de editores devem ser pelo menos
     * um e têm as mesmas restrições que os autores dos livros; o array de
     * livros deve conter os mesmos sempre nos menores índices
     */
    public Coleccao(String titulo, String[] editores) {
        // titulo
        if (titulo == null || titulo.length() == 0)
            throw new IllegalArgumentException(
                    "O titulo tem de ter pelo menos um caracter");
        this.titulo = titulo;
        if(editores.length == 0 ||
                editores == null ||
                Arrays.stream(editores).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("O array de editores não pode estar vazio");
        this.editores = editores;

    }

    /**
     *
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Obtem o número total de páginas da colecção
     */
    public int getNumPaginas() {
        int numPaginas = 0;
        for(int i=0; i<numLivros; i++) {
            numPaginas += livros[i].getNumPaginas();
        }
        return numPaginas;
    }

    /**
     * Devolve o preço da colecção tendo em conta que as colecções com 4 ou mais
     * livros têm um desconto de 20% nos livros que custam pelo menos 10 euros e
     * que têm mais de 200 páginas
     */
    public float getPreco() {
        float preco = 0.0f;
        for(int i=0; i<numLivros;i++) {
            if(numLivros >= 4) {
                if(livros[i].getNumPaginas() > 200 && livros[i].getPreco() >= 10.0f) {
                    preco += livros[i].getPreco() * 0.8;
                }
            } else preco += livros[i].getPreco();
        }
        return preco;
    }

    /**
     * Adiciona um livro se puder e este não seja null e a colecção não ficar
     * com livros iguais. Deve utilzar o método getIndexOfLivro.
     */
    public boolean addLivro(Livro livro) {
        int idx = getIndexOfLivro(livro.getTitulo());
        if(idx == -1 && numLivros < MAXOBRAS) {
            livros[numLivros] = livro;
            numLivros++;
            return true;
        }
        return false;
    }

    /**
     * Devolve o index no array de livros onde estiver o livro com o nome
     * pretendido. Devolve -1 caso não o encontre
     */
    private int getIndexOfLivro(String titulo) {
        for(int i=0; i<livros.length; i++) {
            if(livros[i]== null) return -1;
            if(livros[i].getTitulo().equals(titulo)) return i;
        }
        return -1;
    }

    /**
     * Remove do array o livro com o título igual ao título recebido. Devolve o
     * livro removido ou null caso não tenha encontrado o livro. Deve-se
     * utilizar o método getIndexOfLivro. Recorda-se que os livros devem ocupar
     * sempre os menores índices, ou seja, não pode haver nulls entre os livros
     */
    public Livro remLivro(String titulo) {
        int idx = getIndexOfLivro(titulo);
        if(idx == -1) return null;
        ArrayList<Livro> ar = new ArrayList<>();
        Collections.addAll(ar, livros);
        Livro l = ar.get(idx);
        ar.remove(idx);
        livros = ar.toArray(Livro[]::new);
        numLivros--;
        return l;
    }

    /**
     * Devolve o nº de obras de uma pessoa. A colecção deve contabilizar-se como
     * uma obra para os editores.
     */
    public int getNumObrasFromPerson(String autorEditor) {
        int numObras = getLivrosComoAutor(autorEditor).length;
        if(Arrays.asList(editores).contains(autorEditor)) numObras++;
        return numObras;
    }

    /**
     * Devolver um novo array (sem nulls) com os livros de que a pessoa recebida
     * é autor
     */
    public Livro[] getLivrosComoAutor(String autorNome) {
        ArrayList<Livro> ar = new ArrayList<>();
        for(int i=0;i<numLivros;i++) {
            if(livros[i].contemAutor(autorNome)) {
                ar.add(livros[i]);
            }
        }
        return ar.toArray(Livro[]::new);
    }

    /**
     * Deve devolver uma string compatível com os outputs desejados
     */
    public String toString() {
//        return titulo + ", editores " + Arrays.toString(editores) + ", "
//                + numLivros + " livros, " + getNumPaginas() + "p" + getPreco() + "€";
        return String.format("Colecção %s, editores %s, %d livros, %dp %2.1f€",
                titulo, Arrays.toString(editores), numLivros, getNumPaginas(), getPreco());
    }

    /**
     * Deve devolver um array, sem nulls, com todos os autores e editores
     * existentes na colecção. O resultado não deve conter repetições. Deve
     * utilizar o método mergeWithoutRepetitions
     */
    public String[] getAutoresEditores() {
        ArrayList<String> AE = new ArrayList<>();
        String[] ret;
        for(int i=0;i<numLivros;i++) {
            AE.addAll(Arrays.asList(livros[i].getAutores()));
        }
        ret = AE.toArray(String[]::new);
        return mergeWithoutRepetitions(editores, ret);
    }

    /**
     * Método que recebendo dois arrays sem repetições devolve um novo array com
     * todos os elementos dos arrays recebidos mas sem repetições
     */
    private static String[] mergeWithoutRepetitions(String[] a1, String[] a2) {
        // Using concat to merge both arrays, then using distinct to filter out
        // the duplicates and then converting back to a String Array
        return Stream.concat(Stream.of(a1), Stream.of(a2)).
                distinct().toArray(String[]::new);
    }

    /**
     * Devolve true caso a colecção recebida tenha o mesmo título e a mesma
     * lista de editores. Para verificar verificar se os editores são os mesmos
     * devem utilizar o método mergeWithoutRepetitions
     */
    public boolean equals(Coleccao c) {
        int mergeLen = mergeWithoutRepetitions(editores, c.editores).length;
        return titulo.equals(c.titulo) &&
                mergeLen == editores.length && mergeLen ==c.editores.length;
    }

    /**
     * Mostra uma colecção segundo os outputs desejados
     */
    public void print(String prefix) {
        System.out.println(prefix + String.format(" %s", this));
        for(int i=0;i<numLivros;i++) {
            System.out.printf("  %s\n",livros[i]);
        }
    }

    /**
     * main
     */
    public static void main(String[] args) {
        Livro l1 = new Livro("Viagem aos Himalaias", 340, 12.3f,
                new String[]{"João Mendonça", "Mário Andrade"});
        Livro l2 = new Livro("Viagem aos Pirinéus", 270, 11.5f,
                new String[]{"João Mendonça", "Júlio Pomar"});

        Coleccao c1 = new Coleccao("Primavera",
                new String[]{"João Mendonça", "Manuel Alfazema"});

        boolean res;

        res = c1.addLivro(l1);
        res = c1.addLivro(l2);
        System.out.println("c1 -> " + c1);
        c1.print("");
        System.out.println();

        // adicionar um livro com nome de outro já existente
        res = c1.addLivro(l2);
        System.out.println(
                "adição novamente de Viagem aos Pirinéus a c1 -> " + res);
        System.out.println("c1 -> " + c1);
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
        Livro[] obras = c1.getLivrosComoAutor(nome);
        System.out
                .println("Livros de " + nome + " -> " + Arrays.toString(obras));
        System.out.println();

        // rem livro
        String nomeLivro = "Viagem aos Himalaias";
        Livro l = c1.remLivro(nomeLivro);
        System.out.println("Remoção de " + nomeLivro + " -> " + l);
        c1.print("");
        System.out.println();

        // equals
        Coleccao c2 = new Coleccao("Primavera",
                new String[]{"João Mendonça", "Manuel Alfazema"});
        boolean same = c1.equals(c2);
        System.out.println("c2:");
        c2.print("");
        System.out.println("c1.equals(c2) -> " + same);
        System.out.println();

        Coleccao c3 = new Coleccao("Primavera",
                new String[]{"João Mendonça"});
        same = c1.equals(c3);
        System.out.println("c3:");
        c3.print("");
        System.out.println("c1.equals(c3) -> " + same);
    }
}
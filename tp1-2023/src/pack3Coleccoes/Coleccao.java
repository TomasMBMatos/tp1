package pack3Coleccoes;

import pack2Livros.Livro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;


/**
 * Classe Coleccao, deve conter a descrição de uma colecção, com título, os seus
 * livros, colecções e editores
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

    // array de colecções, estas devem ocupar sempre os menores índices
    private Coleccao[] coleccoes = new Coleccao[MAXOBRAS];

    // deverá conter sempre o número de colecções dentro da colecção
    private int numColeccoes = 0;

    // Editores, tem as mesmas condicionantes que array de autores na classe
    // livro
    private String[] editores;

    /**
     * Construtor; o título tem de ter pelo menos um caracter que não seja um
     * espaço (Character.isWhitespace); o array de editores devem ser pelo menos
     * um e têm as mesmas restrições que os autores dos livros;
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
        return titulo;
    }

    /**
     * Obtem o número total de páginas da colecção, páginas dos livros e das
     * colecções
     */
    public int getNumPaginas() {
        int num = 0;
        for(int i=0;i<numLivros;i++) {
            num += livros[i].getNumPaginas();
        }
        for(int i=0;i<numColeccoes;i++) {
            num += coleccoes[i].getNumPaginas();
        }
        return num;
    }

    /**
     * As colecções com mais de 5000 páginas nos seus livros directos têm um
     * desconto de 20% nesses livros. As colecções em que o somatório de páginas
     * das suas subcolecções directas seja igual ou superior ao quádruplo do nº
     * de páginas da sua subcolecção directa com mais páginas deverão aplicar um
     * desconto de 10% sobre os preços das suas subcolecções
     */
    public float getPreco() {
        float preco = 0.0f;
        for(int i=0; i<numLivros;i++) {
            if(getNumPaginas() > 5000) {
            preco += livros[i].getPreco() * 0.8;
            }
            else {
                preco += livros[i].getPreco();
            }
        }
        for(int i = 0; i < numColeccoes;i++) {
                preco += coleccoes[i].getPreco();
        }

        return preco;
    }

    /**
     * Adiciona um livro à colecção se puder e este não seja null e a colecção
     * não ficar com livros iguais ao nível imediato da colecção. Deve utilzar o
     * método getIndexOfLivro e getIndexOfColeccao
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
     * Adiciona uma colecção à colecção se puder, esta não seja null e a
     * colecção não ficar com obras imediatas com títulos repetidos. Deve
     * utilizar o método getIndexOfLivro e getIndexOfColeccao
     */
    public boolean addColeccao(Coleccao col) {
        if(col == null) return false;
        int idx = getIndexOfColeccao(col.getTitulo());
        if(idx == -1 && numColeccoes < MAXOBRAS) {
            coleccoes[numColeccoes] = col;
            numColeccoes++;
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
            if(livros[i] == null) return -1;
            if(livros[i].getTitulo().equals(titulo)) return i;
        }
        return -1;
    }

    /**
     * Devolve o index no array de colecções onde estiver a colecção com o nome
     * pretendido. Devolve -1 caso não o encontre
     */
    private int getIndexOfColeccao(String titulo) {
        for(int i=0; i<coleccoes.length; i++) {
            if(coleccoes[i]== null) return -1;
            if(coleccoes[i].getTitulo().equals(titulo)) return i;
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
     * Remove do array de colecções a colecção com o título igual ao título
     * recebido. Devolve a colecção removida ou null caso não tenha encontrado.
     * Deve-se utilizar o método getIndexOfColeccao. Recorda-se que as colecções
     * devem ocupar sempre os menores índices, ou seja, não pode haver nulls
     * entre elas
     */
    public Coleccao remColeccao(String titulo) {
        int idx = getIndexOfColeccao(titulo);
        if(idx == -1) return null;
        ArrayList<Coleccao> ar = new ArrayList<>();
        Collections.addAll(ar, coleccoes);
        Coleccao c = ar.get(idx);
        ar.remove(idx);
        coleccoes = ar.toArray(Coleccao[]::new);
        numColeccoes--;
        return c;
    }

    /**
     * Devolve o nº de obras de uma pessoa. Cada colecção deve contabilizar-se
     * como uma obra para os editores.
     */
    public int getNumObrasFromPerson(String autorEditor) {
        int cnt = 0;
        // procurar nos livros
        for (Livro livro : livros) {
            if (livro != null) {
                String[] autoresLivro = livro.getAutores();
                for (String s : autoresLivro) {
                    if (autorEditor.equalsIgnoreCase(s)) cnt++;
                }
            }
        }
        // procurar na coleção

        for (String editore : editores) if (autorEditor.equalsIgnoreCase(editore)) cnt++;

        // procurar nas sub coleções
        for( int i = 0; i < numColeccoes; i++ ) {
            cnt += coleccoes[i].getNumObrasFromPerson(autorEditor);
        }
        return cnt;
    }

    /**
     * Devolver um novo array (sem nulls) com os livros de que a pessoa recebida
     * é autor. Não deve conter repetições, para excluir as repetições devem
     * utilizar o método mergeWithoutRepetitions
     */
    public Livro[] getLivrosComoAutor(String autorNome) {
        Livro[] arrayLivros = new Livro[MAXOBRAS];
        // Verficar array de livros
        for (int i=0;i<numLivros;i++) {
            String[] autores = livros[i].getAutores();
            // Comparar cada autor do array de autores com a String recebida
            for (String autor : autores) {
                if (autor.equalsIgnoreCase(autorNome)) {
                    arrayLivros[i] = livros[i];
                    break;
                }
            }
        }
        Livro[] arrayLivrosSec = new Livro[MAXOBRAS];
        // verificar coleções e os seus livros
        for (int i=0;i<numColeccoes;i++) {
            for (int j = 0; j < coleccoes[i].numLivros; j++) {
                String[] autores = coleccoes[i].livros[j].getAutores();
                // Comparar cada autor do array de autores com a String recebida
                for (String autor : autores) {
                    if (autor.equalsIgnoreCase(autorNome)) {
                        arrayLivrosSec[j] = coleccoes[i].livros[j];
                        break;
                    }
                }
            }
        }
        Livro[] resultWNulls = mergeWithoutRepetitions(arrayLivros, arrayLivrosSec);
        int numLivrosAutor = 0;
        for (Livro resultWNull : resultWNulls) {
            if (resultWNull != null) numLivrosAutor++;
        }
        Livro[] arraySemNulls = new Livro[numLivrosAutor];
        for( int i = 0, j = 0; i < resultWNulls.length; i++ ) {
            if( resultWNulls[i] != null ) {
                arraySemNulls[j] = resultWNulls[i];
                j++;
            }
        }
        return arraySemNulls;
    }

    /**
     * Deve devolver uma string compatível com os outputs desejados
     */
    public String toString() {
        return String.format("Colecção %s, editores %s, %d livros, %dp %2.1f€",
                titulo, Arrays.toString(editores), numLivros, getNumPaginas(), getPreco());
    }

    /**
     * Deve devolver um array, sem nulls, com todos os autores e editores
     * existentes na colecção. O resultado não deve conter repetições. Deve
     * utilizar o método mergeWithoutRepetitions
     */
    public String[] getAutoresEditores() {
        String[] editores = this.editores;
        for (Livro livro : livros) {
            if (livro != null)
                editores = mergeWithoutRepetitions(editores, livro.getAutores());
        }
        for (Coleccao coleccoe : coleccoes) {
            if (coleccoe != null)
                editores = mergeWithoutRepetitions(editores, coleccoe.editores);
        }
		return editores;
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
        System.out.println(prefix + String.format("%s", this));
        for(int i=0;i<numLivros;i++) {
            System.out.printf("  %s\n",livros[i]);
        }
        for(int i=0;i<numColeccoes;i++) {
            System.out.printf("  %s\n",coleccoes[i]);
            for(int j=0;j<coleccoes[i].numLivros;j++) {
                System.out.printf("    %s\n",coleccoes[i].livros[j]);
            }
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

        // Outra colecção
        Livro l21 = new Livro("Viagem aos Himalaias 2", 340, 12.3f,
                new String[]{"João Mendonça", "Mário Andrade"});
        Livro l22 = new Livro("Viagem aos Pirinéus 2", 270, 11.5f,
                new String[]{"João Mendonça", "Júlio Pomar"});

        Coleccao cx2 = new Coleccao("Outono",
                new String[]{"João Mendonça", "Manuel Antunes"});
        cx2.addLivro(l21);
        cx2.addLivro(l22);
        System.out.println("cx2 -> " + cx2);
        cx2.print("");
        System.out.println();

        // adicioná-la a c1
        c1.addColeccao(cx2);
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
    }
}
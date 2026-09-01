import java.util.NoSuchElementException;

/**
 * EDL - Estrutura de Dados em Lista.
 *
 * Implementacao propria (na mao) de uma lista generica baseada em array,
 * parecida com um ArrayList simplificado: comeca com uma capacidade
 * inicial e DOBRA de tamanho automaticamente quando fica cheia.
 *
 * Usada pelo PainelDesenho para guardar as figuras desenhadas na tela
 * (cada figura vira um objeto FiguraDesenhada), permitindo:
 * - "Limpar": guarda a lista atual de figuras em uma EDL separada antes de
 *   apagar a tela;
 * - "Redesenhar": recupera as figuras guardadas nessa EDL e as desenha de
 *   volta na tela.
 *
 * @param <T> tipo dos elementos armazenados na lista
 *
 * @author Felipe Estima Correia Urzi
 * @author Igor Dias da Silva
 * @author Pedro Henrique Freire
 * @author Thierry Nadjarian
 *
 * @version 20220815
 */
public class EDL<T> {

    // capacidade inicial do array interno
    private static final int CAPACIDADE_INICIAL = 10;

    // array interno que guarda os elementos (Object[] pois Java nao
    // permite criar array generico "new T[...]" diretamente)
    private Object[] itens;

    // quantidade de elementos realmente guardados (pode ser < itens.length)
    private int quantidade;

    /**
     * Constroi uma EDL vazia, com a capacidade inicial padrao
     */
    public EDL() {
        this.itens = new Object[CAPACIDADE_INICIAL];
        this.quantidade = 0;
    }

    /**
     * Insere um elemento no final da lista.
     * Se o array interno estiver cheio, a capacidade e dobrada
     * automaticamente antes de inserir.
     *
     * @param elemento elemento a ser inserido
     */
    public void inserir(T elemento) {
        if (quantidade == itens.length) {
            aumentarCapacidade();
        }
        itens[quantidade] = elemento;
        quantidade++;
    }

    /**
     * Dobra a capacidade do array interno, copiando os elementos
     * existentes para o novo array (maior)
     */
    private void aumentarCapacidade() {
        Object[] novoArray = new Object[itens.length * 2];
        for (int i = 0; i < itens.length; i++) {
            novoArray[i] = itens[i];
        }
        itens = novoArray;
    }

    /**
     * Retorna o elemento guardado na posicao indicada
     *
     * @param indice posicao do elemento (0 = primeiro elemento)
     * @return elemento guardado na posicao "indice"
     */
    @SuppressWarnings("unchecked")
    public T obter(int indice) {
        if (indice < 0 || indice >= quantidade) {
            throw new NoSuchElementException("Indice invalido: " + indice);
        }
        return (T) itens[indice];
    }

    /**
     * Retorna a quantidade de elementos guardados na lista
     *
     * @return quantidade de elementos
     */
    public int tamanho() {
        return this.quantidade;
    }

    /**
     * Informa se a lista esta vazia
     *
     * @return true se nao ha nenhum elemento guardado
     */
    public boolean estaVazia() {
        return this.quantidade == 0;
    }

    /**
     * Remove todos os elementos da lista (a lista volta a ficar vazia)
     */
    public void limpar() {
        for (int i = 0; i < quantidade; i++) {
            itens[i] = null; // ajuda o garbage collector
        }
        this.quantidade = 0;
    }

    /**
     * Cria e retorna uma NOVA EDL, copia independente desta (mesmos
     * elementos, mas em um array diferente). Usada para "guardar" um
     * "retrato" dos desenhos atuais sem que alteracoes futuras em uma
     * lista afetem a outra.
     *
     * @return copia independente desta EDL
     */
    public EDL<T> copiar() {
        EDL<T> copia = new EDL<>();
        for (int i = 0; i < this.quantidade; i++) {
            copia.inserir(this.obter(i));
        }
        return copia;
    }
}

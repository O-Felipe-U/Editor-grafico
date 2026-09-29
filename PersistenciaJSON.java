import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Persistencia de figuras desenhadas em arquivo, no formato JSON.
 *
 * As coordenadas sao gravadas normalizadas (entre 0 e 1), permitindo que
 * o desenho seja aberto em uma area de desenho com tamanho diferente e seja
 * automaticamente mapeado para o tamanho atual.
 *
 * O formato gravado segue a estrutura:
 * {
 *   "figura": {
 *     "ponto": [...],
 *     "reta": [...],
 *     "triangulo": [...],
 *     "retangulo": [...],
 *     "circulo": [...]
 *   }
 * }
 *
 * @author Felipe Estima Correia Urzi
 * @author Igor Dias da Silva
 * @author Pedro Henrique Freire
 * @author Thierry Nadjarian
 *
 * @version 20260922
 */
public class PersistenciaJSON {

    /**
     * Grava a lista de figuras no formato normalizado utilizado pelo projeto.
     * As coordenadas em pixels sao convertidas para o intervalo de 0 a 1
     * usando as dimensoes atuais da area de desenho.
     *
     * @param lista figuras a serem gravadas
     * @param caminho caminho do arquivo JSON
     * @param larguraCanvas largura atual da area de desenho
     * @param alturaCanvas altura atual da area de desenho
     * @throws IOException se ocorrer erro ao gravar o arquivo
     */
    public static void salvar(EDL<FiguraDesenhada> lista, String caminho,
                              int larguraCanvas, int alturaCanvas) throws IOException {

        if (larguraCanvas <= 0 || alturaCanvas <= 0) {
            throw new IOException("Dimensoes da area de desenho invalidas.");
        }

        StringBuilder ponto = new StringBuilder();
        StringBuilder reta = new StringBuilder();
        StringBuilder triangulo = new StringBuilder();
        StringBuilder retangulo = new StringBuilder();
        StringBuilder circulo = new StringBuilder();

        int idPonto = 1;
        int idReta = 1;
        int idTriangulo = 1;
        int idRetangulo = 1;
        int idCirculo = 1;

        for (int i = 0; i < lista.tamanho(); i++) {
            FiguraDesenhada figura = lista.obter(i);

            switch (figura.getTipo()) {
                case PONTO:
                    adicionarSeparador(ponto);
                    ponto.append(criarPonto(figura, larguraCanvas, alturaCanvas, idPonto++));
                    break;

                case RETA:
                    adicionarSeparador(reta);
                    reta.append(criarReta(figura, larguraCanvas, alturaCanvas, idReta++));
                    break;

                case TRIANGULO:
                    adicionarSeparador(triangulo);
                    triangulo.append(criarTriangulo(figura, larguraCanvas, alturaCanvas, idTriangulo++));
                    break;

                case RETANGULO:
                    adicionarSeparador(retangulo);
                    retangulo.append(criarRetangulo(figura, larguraCanvas, alturaCanvas, idRetangulo++));
                    break;

                case CIRCULO:
                    adicionarSeparador(circulo);
                    circulo.append(criarCirculo(figura, larguraCanvas, alturaCanvas, idCirculo++));
                    break;

                default:
                    break;
            }
        }

        String texto = "{\n" +
                "  \"figura\": {\n" +
                "    \"ponto\": [" + ponto + "\n    ],\n" +
                "    \"reta\": [" + reta + "\n    ],\n" +
                "    \"triangulo\": [" + triangulo + "\n    ],\n" +
                "    \"retangulo\": [" + retangulo + "\n    ],\n" +
                "    \"circulo\": [" + circulo + "\n    ]\n" +
                "  }\n" +
                "}";

        Path arquivo = Paths.get(caminho);
        Files.write(arquivo, texto.getBytes(StandardCharsets.UTF_8));
    }

    private static void adicionarSeparador(StringBuilder array) {
        if (array.length() > 0) {
            array.append(",");
        }
    }

    private static String criarPonto(FiguraDesenhada figura, int largura, int altura, int id) {
        return "\n      {\n" +
                "        \"x\": " + numero(normalizar(figura.getX1(), largura)) + ",\n" +
                "        \"y\": " + numero(normalizar(figura.getY1(), altura)) + ",\n" +
                corJSON(figura.getCor(), 8) + ",\n" +
                "        \"esp\": " + figura.getEsp() + ",\n" +
                "        \"id\": \"ponto_" + id + "\"\n" +
                "      }";
    }

    private static String criarReta(FiguraDesenhada figura, int largura, int altura, int id) {
        return "\n      {\n" +
                pontoJSON("p1", figura.getX1(), figura.getY1(), largura, altura, 8) + ",\n" +
                pontoJSON("p2", figura.getX2(), figura.getY2(), largura, altura, 8) + ",\n" +
                corJSON(figura.getCor(), 8) + ",\n" +
                "        \"esp\": " + figura.getEsp() + ",\n" +
                "        \"id\": \"reta_" + id + "\"\n" +
                "      }";
    }

    private static String criarTriangulo(FiguraDesenhada figura, int largura, int altura, int id) {
        double xa;
        double ya;
        double xb;
        double yb;
        double xc;
        double yc;

        // Triangulos carregados do JSON podem possuir tres vertices livres.
        // Nesse caso, preservamos exatamente os tres pontos ao salvar novamente.
        if (figura.temTerceiroPonto()) {
            xa = figura.getX1();
            ya = figura.getY1();
            xb = figura.getX2();
            yb = figura.getY2();
            xc = figura.getX3();
            yc = figura.getY3();
        } else {
            // Triangulo criado pelo editor: mantem a estrutura original,
            // baseada nos dois pontos do retangulo envolvente.
            xa = figura.getX1();
            ya = figura.getY2();
            xb = figura.getX2();
            yb = figura.getY2();
            xc = (figura.getX1() + figura.getX2()) / 2.0;
            yc = figura.getY1();
        }

        return "\n      {\n" +
                pontoJSON("p1", xa, ya, largura, altura, 8) + ",\n" +
                pontoJSON("p2", xb, yb, largura, altura, 8) + ",\n" +
                pontoJSON("p3", xc, yc, largura, altura, 8) + ",\n" +
                corJSON(figura.getCor(), 8) + ",\n" +
                "        \"esp\": " + figura.getEsp() + ",\n" +
                "        \"id\": \"triangulo_" + id + "\"\n" +
                "      }";
    }

    private static String criarRetangulo(FiguraDesenhada figura, int largura, int altura, int id) {
        return "\n      {\n" +
                pontoJSON("p1", figura.getX1(), figura.getY1(), largura, altura, 8) + ",\n" +
                pontoJSON("p2", figura.getX2(), figura.getY2(), largura, altura, 8) + ",\n" +
                corJSON(figura.getCor(), 8) + ",\n" +
                "        \"esp\": " + figura.getEsp() + ",\n" +
                "        \"id\": \"retangulo_" + id + "\"\n" +
                "      }";
    }

    private static String criarCirculo(FiguraDesenhada figura, int largura, int altura, int id) {
        return "\n      {\n" +
                pontoJSON("centro", figura.getX1(), figura.getY1(), largura, altura, 8) + ",\n" +
                pontoJSON("raio", figura.getX2(), figura.getY2(), largura, altura, 8) + ",\n" +
                corJSON(figura.getCor(), 8) + ",\n" +
                "        \"esp\": " + figura.getEsp() + ",\n" +
                "        \"id\": \"circulo_" + id + "\"\n" +
                "      }";
    }

    private static String pontoJSON(String nome, double x, double y,
                                    int largura, int altura, int indentacao) {
        String espacos = espacos(indentacao);
        return espacos + "\"" + nome + "\": {\n" +
                espacos + "  \"x\": " + numero(normalizar(x, largura)) + ",\n" +
                espacos + "  \"y\": " + numero(normalizar(y, altura)) + "\n" +
                espacos + "}";
    }

    private static String corJSON(Color cor, int indentacao) {
        String espacos = espacos(indentacao);
        return espacos + "\"cor\": {\n" +
                espacos + "  \"r\": " + cor.getRed() + ",\n" +
                espacos + "  \"b\": " + cor.getBlue() + ",\n" +
                espacos + "  \"g\": " + cor.getGreen() + "\n" +
                espacos + "}";
    }

    private static String espacos(int quantidade) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < quantidade; i++) {
            s.append(' ');
        }
        return s.toString();
    }

    /**
     * Le o novo formato normalizado e mapeia as coordenadas para o tamanho
     * atual da area de desenho. Arquivos do formato anterior tambem continuam
     * sendo aceitos para nao quebrar desenhos ja salvos.
     */
    public static EDL<FiguraDesenhada> carregar(String caminho,
                                                int larguraAtual,
                                                int alturaAtual) throws IOException, JSONException {
        Path arquivo = Paths.get(caminho);
        byte[] bytes = Files.readAllBytes(arquivo);
        String texto = new String(bytes, StandardCharsets.UTF_8);
        JSONObject raiz = new JSONObject(texto);

        if (raiz.has("figura")) {
            return carregarFormatoNormalizado(raiz, larguraAtual, alturaAtual);
        }

        return carregarFormatoAntigo(raiz, larguraAtual, alturaAtual);
    }

    private static EDL<FiguraDesenhada> carregarFormatoNormalizado(JSONObject raiz,
                                                                    int larguraAtual,
                                                                    int alturaAtual) {
        EDL<FiguraDesenhada> lista = new EDL<>();
        JSONObject figura = raiz.getJSONObject("figura");

        carregarPontos(figura.optJSONArray("ponto"), lista, larguraAtual, alturaAtual);
        carregarRetas(figura.optJSONArray("reta"), lista, larguraAtual, alturaAtual);
        carregarTriangulos(figura.optJSONArray("triangulo"), lista, larguraAtual, alturaAtual);
        carregarRetangulos(figura.optJSONArray("retangulo"), lista, larguraAtual, alturaAtual);
        carregarCirculos(figura.optJSONArray("circulo"), lista, larguraAtual, alturaAtual);

        return lista;
    }

    private static void carregarPontos(JSONArray array, EDL<FiguraDesenhada> lista,
                                       int largura, int altura) {
        if (array == null) return;

        for (int i = 0; i < array.length(); i++) {
            JSONObject json = array.getJSONObject(i);
            lista.inserir(new FiguraDesenhada(
                    TipoPrimitivo.PONTO,
                    desnormalizar(json.getDouble("x"), largura),
                    desnormalizar(json.getDouble("y"), altura),
                    0, 0, "", json.getInt("esp"), lerCor(json.getJSONObject("cor"))));
        }
    }

    private static void carregarRetas(JSONArray array, EDL<FiguraDesenhada> lista,
                                      int largura, int altura) {
        if (array == null) return;

        for (int i = 0; i < array.length(); i++) {
            JSONObject json = array.getJSONObject(i);
            JSONObject p1 = json.getJSONObject("p1");
            JSONObject p2 = json.getJSONObject("p2");

            lista.inserir(new FiguraDesenhada(
                    TipoPrimitivo.RETA,
                    desnormalizar(p1.getDouble("x"), largura),
                    desnormalizar(p1.getDouble("y"), altura),
                    desnormalizar(p2.getDouble("x"), largura),
                    desnormalizar(p2.getDouble("y"), altura),
                    "", json.getInt("esp"), lerCor(json.getJSONObject("cor"))));
        }
    }

    private static void carregarTriangulos(JSONArray array, EDL<FiguraDesenhada> lista,
                                           int largura, int altura) {
        if (array == null) return;

        for (int i = 0; i < array.length(); i++) {
            JSONObject json = array.getJSONObject(i);
            JSONObject p1 = json.getJSONObject("p1");
            JSONObject p2 = json.getJSONObject("p2");
            JSONObject p3 = json.getJSONObject("p3");

            // O formato JSON possui tres vertices independentes. Eles precisam
            // ser mantidos separadamente; tentar reconstruir o triangulo usando
            // apenas dois pontos faz triangulos arbitrarios virarem linhas ou
            // figuras deformadas ao abrir o arquivo.
            int x1 = desnormalizar(p1.getDouble("x"), largura);
            int y1 = desnormalizar(p1.getDouble("y"), altura);
            int x2 = desnormalizar(p2.getDouble("x"), largura);
            int y2 = desnormalizar(p2.getDouble("y"), altura);
            int x3 = desnormalizar(p3.getDouble("x"), largura);
            int y3 = desnormalizar(p3.getDouble("y"), altura);

            lista.inserir(new FiguraDesenhada(
                    TipoPrimitivo.TRIANGULO,
                    x1, y1, x2, y2, x3, y3,
                    "", json.getInt("esp"), lerCor(json.getJSONObject("cor"))));
        }
    }

    private static void carregarRetangulos(JSONArray array, EDL<FiguraDesenhada> lista,
                                           int largura, int altura) {
        if (array == null) return;

        for (int i = 0; i < array.length(); i++) {
            JSONObject json = array.getJSONObject(i);
            JSONObject p1 = json.getJSONObject("p1");
            JSONObject p2 = json.getJSONObject("p2");

            lista.inserir(new FiguraDesenhada(
                    TipoPrimitivo.RETANGULO,
                    desnormalizar(p1.getDouble("x"), largura),
                    desnormalizar(p1.getDouble("y"), altura),
                    desnormalizar(p2.getDouble("x"), largura),
                    desnormalizar(p2.getDouble("y"), altura),
                    "", json.getInt("esp"), lerCor(json.getJSONObject("cor"))));
        }
    }

    private static void carregarCirculos(JSONArray array, EDL<FiguraDesenhada> lista,
                                         int largura, int altura) {
        if (array == null) return;

        for (int i = 0; i < array.length(); i++) {
            JSONObject json = array.getJSONObject(i);
            JSONObject centro = json.getJSONObject("centro");
            JSONObject raio = json.getJSONObject("raio");

            lista.inserir(new FiguraDesenhada(
                    TipoPrimitivo.CIRCULO,
                    desnormalizar(centro.getDouble("x"), largura),
                    desnormalizar(centro.getDouble("y"), altura),
                    desnormalizar(raio.getDouble("x"), largura),
                    desnormalizar(raio.getDouble("y"), altura),
                    "", json.getInt("esp"), lerCor(json.getJSONObject("cor"))));
        }
    }

    private static Color lerCor(JSONObject cor) {
        return new Color(cor.getInt("r"), cor.getInt("g"), cor.getInt("b"));
    }

    private static double normalizar(double coordenada, int tamanho) {
        return coordenada / tamanho;
    }

    private static int desnormalizar(double coordenada, int tamanho) {
        return (int) Math.round(coordenada * tamanho);
    }

    // Mantem ate 6 casas decimais, como no arquivo de exemplo, retirando
    // zeros desnecessarios no final (0.320000 -> 0.32).
    private static String numero(double valor) {
        double arredondado = Math.round(valor * 1_000_000.0) / 1_000_000.0;
        String texto = String.format(java.util.Locale.US, "%.6f", arredondado);

        while (texto.contains(".") && texto.endsWith("0")) {
            texto = texto.substring(0, texto.length() - 1);
        }
        if (texto.endsWith(".")) {
            texto = texto.substring(0, texto.length() - 1);
        }
        return texto;
    }

    /**
     * Le o formato anterior do projeto para manter compatibilidade com
     * arquivos ja criados antes da mudanca para coordenadas normalizadas.
     */
    private static EDL<FiguraDesenhada> carregarFormatoAntigo(JSONObject raiz,
                                                               int larguraAtual,
                                                               int alturaAtual) {
        JSONArray figuras = raiz.getJSONArray("figuras");
        EDL<FiguraDesenhada> lista = new EDL<>();

        int larguraOriginal = raiz.optInt("larguraCanvas", larguraAtual);
        int alturaOriginal = raiz.optInt("alturaCanvas", alturaAtual);

        for (int i = 0; i < figuras.length(); i++) {
            JSONObject json = figuras.getJSONObject(i);
            TipoPrimitivo tipo = TipoPrimitivo.valueOf(json.getString("tipo"));
            int x1 = json.getInt("x1");
            int y1 = json.getInt("y1");
            int x2 = json.getInt("x2");
            int y2 = json.getInt("y2");

            if (larguraOriginal > 0 && alturaOriginal > 0
                    && larguraAtual > 0 && alturaAtual > 0) {
                x1 = mapearAntigo(x1, larguraOriginal, larguraAtual);
                y1 = mapearAntigo(y1, alturaOriginal, alturaAtual);
                x2 = mapearAntigo(x2, larguraOriginal, larguraAtual);
                y2 = mapearAntigo(y2, alturaOriginal, alturaAtual);
            }

            lista.inserir(new FiguraDesenhada(
                    tipo, x1, y1, x2, y2,
                    json.optString("nome", ""),
                    json.getInt("esp"),
                    new Color(json.getInt("cor"))));
        }

        return lista;
    }

    private static int mapearAntigo(int coordenada, int tamanhoOriginal, int tamanhoAtual) {
        double normalizada = (double) coordenada / tamanhoOriginal;
        return (int) Math.round(normalizada * tamanhoAtual);
    }
}

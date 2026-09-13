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
 * Usa a biblioteca org.json para montar e interpretar o JSON. Cada
 * FiguraDesenhada vira um JSONObject com os campos: tipo, x1, y1, x2, y2,
 * nome, esp e cor. O arquivo gravado tem um JSONObject "raiz" com um campo
 * "versao" (para permitir futuras mudancas no formato sem quebrar arquivos
 * antigos) e um campo "figuras", que e um JSONArray com todas as figuras
 * da EDL recebida.
 *
 * Formato do arquivo gerado (exemplo):
 * {
 *   "versao": 1,
 *   "figuras": [
 *     { "tipo": "RETA", "x1": 10, "y1": 10, "x2": 50, "y2": 50,
 *       "nome": "", "esp": 2, "cor": -16777216 },
 *     ...
 *   ]
 * }
 *
 * @author Felipe Estima Correia Urzi
 * @author Igor Dias da Silva
 * @author Pedro Henrique Freire
 * @author Thierry Nadjarian
 *
 * @version 20220815
 */
public class PersistenciaJSON {

    // versao do formato do arquivo JSON gerado por esta classe
    private static final int VERSAO_FORMATO = 1;

    /**
     * Converte uma unica FiguraDesenhada em um JSONObject.
     *
     * @param figura figura a ser convertida
     * @return JSONObject com os dados da figura
     */
    public static JSONObject figuraParaJSON(FiguraDesenhada figura) {
        JSONObject json = new JSONObject();
        json.put("tipo", figura.getTipo().name());
        json.put("x1", figura.getX1());
        json.put("y1", figura.getY1());
        json.put("x2", figura.getX2());
        json.put("y2", figura.getY2());
        json.put("nome", figura.getNome());
        json.put("esp", figura.getEsp());
        json.put("cor", figura.getCor().getRGB());
        return json;
    }

    /**
     * Converte um JSONObject (gerado por figuraParaJSON) de volta em uma
     * FiguraDesenhada.
     *
     * @param json JSONObject com os dados da figura
     * @return FiguraDesenhada reconstruida a partir do JSON
     * @throws JSONException se algum campo obrigatorio estiver ausente ou
     *         com tipo invalido, ou se "tipo" nao for um valor valido de
     *         TipoPrimitivo
     */
    public static FiguraDesenhada jsonParaFigura(JSONObject json) {
        TipoPrimitivo tipo = TipoPrimitivo.valueOf(json.getString("tipo"));
        int x1 = json.getInt("x1");
        int y1 = json.getInt("y1");
        int x2 = json.getInt("x2");
        int y2 = json.getInt("y2");
        String nome = json.optString("nome", "");
        int esp = json.getInt("esp");
        Color cor = new Color(json.getInt("cor"));
        return new FiguraDesenhada(tipo, x1, y1, x2, y2, nome, esp, cor);
    }

    /**
     * Converte uma EDL inteira de FiguraDesenhada em um JSONArray,
     * percorrendo a lista na ordem em que as figuras foram desenhadas.
     *
     * @param lista EDL com as figuras a serem convertidas
     * @return JSONArray com todas as figuras da lista
     */
    public static JSONArray listaParaJSON(EDL<FiguraDesenhada> lista) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < lista.tamanho(); i++) {
            array.put(figuraParaJSON(lista.obter(i)));
        }
        return array;
    }

    /**
     * Converte um JSONArray (gerado por listaParaJSON) de volta em uma
     * EDL de FiguraDesenhada, preservando a ordem original das figuras.
     *
     * @param array JSONArray com as figuras
     * @return EDL reconstruida a partir do JSON
     * @throws JSONException se algum elemento do array nao for um
     *         JSONObject valido no formato esperado
     */
    public static EDL<FiguraDesenhada> jsonParaLista(JSONArray array) {
        EDL<FiguraDesenhada> lista = new EDL<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = array.getJSONObject(i);
            lista.inserir(jsonParaFigura(json));
        }
        return lista;
    }

    /**
     * Grava a lista de figuras em um arquivo JSON no caminho indicado.
     * O arquivo grava um objeto "raiz" com a versao do formato e o array
     * de figuras (metodo listaParaJSON), formatado com indentacao de 4
     * espacos para facilitar a leitura do arquivo gerado.
     *
     * @param lista EDL com as figuras a serem gravadas
     * @param caminho caminho (nome do arquivo) onde o JSON sera gravado
     * @throws IOException se houver erro ao escrever o arquivo (ex.: sem
     *         permissao, caminho invalido)
     */
    public static void salvar(EDL<FiguraDesenhada> lista, String caminho) throws IOException {
        JSONObject raiz = new JSONObject();
        raiz.put("versao", VERSAO_FORMATO);
        raiz.put("figuras", listaParaJSON(lista));

        String texto = raiz.toString(4); // indentacao de 4 espacos
        Path arquivo = Paths.get(caminho);
        Files.write(arquivo, texto.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Le um arquivo JSON (gravado por "salvar") no caminho indicado e
     * reconstroi a EDL de figuras a partir do campo "figuras" do arquivo.
     *
     * @param caminho caminho (nome do arquivo) de onde o JSON sera lido
     * @return EDL com as figuras lidas do arquivo
     * @throws IOException se houver erro ao ler o arquivo (ex.: arquivo
     *         nao existe, sem permissao de leitura)
     * @throws JSONException se o conteudo do arquivo nao for um JSON
     *         valido ou nao estiver no formato esperado (faltando o
     *         campo "figuras", por exemplo)
     */
    public static EDL<FiguraDesenhada> carregar(String caminho) throws IOException, JSONException {
        Path arquivo = Paths.get(caminho);
        byte[] bytes = Files.readAllBytes(arquivo);
        String texto = new String(bytes, StandardCharsets.UTF_8);

        JSONObject raiz = new JSONObject(texto);
        JSONArray figuras = raiz.getJSONArray("figuras");
        return jsonParaLista(figuras);
    }
}

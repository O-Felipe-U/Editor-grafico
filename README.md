# Editor Grafico - Primitivos

Projeto da disciplina de Computacao Grafica e Processamento de Imagens.

## Requisitos implementados

- Ponto
- Reta
- Circulo
- Retangulo
- Triangulo
- Estrutura de dados (ED) para armazenar todos os primitivos criados
- Redesenho seletivo da ED: somente pontos, retas, circulos, retangulos, triangulos ou todos
- Botao **Limpar tela** que nao remove nenhum elemento da ED
- Selecao de cor
- Controle de espessura
- Barra de status com coordenadas do mouse e quantidade de elementos armazenados

## Como construir cada primitivo

- **Ponto:** 1 clique
- **Reta:** 2 cliques, definindo os extremos
- **Circulo:** 2 cliques; o primeiro e o centro e o segundo define o raio
- **Retangulo:** 2 cliques, definindo cantos opostos
- **Triangulo:** 3 cliques, um para cada vertice

## Algoritmos

- A reta e rasterizada com o algoritmo do ponto medio / Bresenham.
- O circulo e rasterizado com o algoritmo midpoint e simetria dos 8 octantes.
- O retangulo e composto por quatro retas.
- O triangulo e composto por tres retas.

## Estrutura de dados

`RepositorioPrimitivos` e a ED permanente do programa. Cada elemento e um
`PrimitivoArmazenado`, que referencia uma `FiguraDesenhada` e seu
`TipoPrimitivo`.

A tela possui uma lista de visualizacao separada da ED. Portanto:

1. desenhar um primitivo adiciona-o na ED e na tela;
2. **Limpar tela** remove apenas os elementos visiveis;
3. **Redesenhar** consulta a ED novamente e mostra somente o tipo selecionado
   ou todos os elementos.

Essa separacao garante que limpar a tela nunca elimine os dados armazenados.

## Teste da ED pela interface

1. Desenhe varios primitivos de tipos diferentes.
2. Clique em **Limpar tela**.
3. Escolha, por exemplo, `Reta` no campo **Redesenhar da ED**.
4. Clique em **Redesenhar**: somente as retas armazenadas devem aparecer.
5. Repita com os demais tipos e com `Todos`.

## Teste automatico da ED

Execute:

```bash
javac TesteRepositorioPrimitivos.java
java TesteRepositorioPrimitivos
```

A saida esperada e:

```text
OK - ED armazenou e filtrou os 5 tipos corretamente.
```

## Execucao

Compile os arquivos Java e execute `App`.

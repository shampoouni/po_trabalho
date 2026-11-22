Trabalho de Pesquisa e Ordenação — empréstimos de biblioteca (enunciado: "Trabalho PO 2026-2.md").

Como rodar: execute a classe Main a partir da raiz do projeto (a pasta que contém data/ e saida/).
Pelo terminal:
  javac -encoding UTF-8 -d bin $(find src -name "*.java")
  java -cp bin Main

  src/       código-fonte (Main + pacotes emprestimo, metodos.ordenacao e metodos.pesquisa)
  data/      12 arquivos emprestimo*.txt + nomes.txt (400 nomes a pesquisar)
  saida/     72 arquivos gerados (Heap, Quick, QuickIns, ABB, AVL, Hash) + tempos.txt (tabela de tempos médios)

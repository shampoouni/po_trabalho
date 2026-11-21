# Trabalho sobre Métodos de Pesquisa e Ordenação em Java

**Pesquisa e Ordenação** — Profª Cinthia C L Caliari

## Objetivos

- Avaliar competências e conhecimentos desenvolvidos na disciplina de Pesquisa e Ordenação, por meio de um trabalho experimental.
- Oportunizar o desenvolvimento e escrita de uma atividade nos moldes de um trabalho acadêmico, bem como aperfeiçoar a capacidade de criar, planejar, trabalhar e decidir em grupo de forma cooperativa.
- Aprimorar os conhecimentos de programação Java.
- Aplicar diferentes métodos de ordenação e pesquisa em uma situação prática.
- Comparar experimentalmente o desempenho dos diferentes métodos implementados.

## Informações gerais

- **Data limite para entrega: 27/11 (sexta-feira)** pelo Envio de Trabalho do AVA (devem ser postados todos os arquivos fonte, todos os arquivos gerados, texto escrito com a descrição dos métodos utilizados e a conclusão do grupo, além de um relatório descrevendo como cada membro do grupo participou do trabalho).
- O grupo que não cumprir o prazo perderá 1 ponto por dia (contando sábado e domingo) de atraso, sendo no máximo 3 dias.
- Grupos formados por até 5 alunos, sendo que qualquer componente do grupo deve ser capaz de apresentar/defender todas as ideias contidas no trabalho. É fundamental que o trabalho seja feito de forma cooperativa, e não, simplesmente, que cada componente cuide de uma parte do mesmo para depois juntar e formar o todo.
- A parte escrita deverá ser feita de forma bastante sucinta e deverá estar nos padrões adotados pela FAESA para apresentação de relatório de pesquisa.

## Critérios avaliativos

- Organização do documento impresso e formatação segundo as normas de trabalho acadêmico.
- Abordagem do tema central de forma clara e precisa.
- Utilização adequada de estruturas de dados e métodos de pesquisa e ordenação.
- **Não usar estruturas prontas do JAVA** (só pode usar `ArrayList` ou `LinkedList`. Não pode usar nenhum tipo de Sort, `HashMap`, nem algo do tipo).
- Programa correto.
- Legibilidade do código e identação.
- Entrevista individual e/ou coletiva.
- Trabalho em equipe e de forma cooperativa.
- Pontualidade na entrega (a cada dia de atraso, o trabalho valerá 1,0 ponto a menos).

| Item                                      | Pontos  |
|-------------------------------------------|---------|
| Formatação do texto nas normas da FAESA   | 0,5     |
| Descrição dos métodos e conclusões        | 1,0     |
| Implementação dos métodos                 | 4,0     |
| Apresentação                              | 1,5     |
| **TOTAL**                                 | **7,0** |

## Descrição geral do trabalho

O tema da pesquisa é o estudo de métodos de pesquisa e ordenação utilizando **chave secundária**, aplicados a **registros de empréstimos de uma biblioteca**.

Cada registro deverá ser representado por um objeto Java. O objeto deverá possuir, obrigatoriamente, uma **chave primária** que identifique unicamente o registro e pelo menos uma **chave secundária**, que poderá aparecer em vários registros.

Neste trabalho, a pesquisa deverá ser realizada utilizando a chave secundária correspondente ao **nome do usuário** da biblioteca.

Assim, um mesmo nome poderá aparecer em vários registros, pois um usuário poderá realizar diversos empréstimos.

Por exemplo:

```
Empréstimo 101;Ana Souza;Dom Casmurro;Machado de Assis;10/03/2026;20/03/2026
Empréstimo 205;Ana Souza;O Cortiço;Aluísio Azevedo;15/03/2026;25/03/2026
Empréstimo 318;Carlos Lima;Iracema;José de Alencar;12/03/2026;22/03/2026
```

Nesse exemplo:

- código do empréstimo é a chave primária;
- nome do usuário é uma chave secundária;
- título do livro é outra informação que pode ser utilizada na ordenação;
- vários registros podem possuir o mesmo nome de usuário.

Portanto, ao pesquisar pelo nome Ana Souza, o programa deverá encontrar todos os empréstimos associados a esse nome.

## Organização da parte escrita

### Introdução

O capítulo da introdução deve descrever brevemente cada um dos métodos utilizados no trabalho e a metodologia utilizada na implementação destes métodos.

### Desenvolvimento

Apresentar as estratégias utilizadas para manter a estabilidade dos algoritmos de ordenação e fazer a pesquisa em chave secundária nos algoritmos de pesquisa.
Descrever a máquina em que o programa for rodado. Descrever os arquivos usados para teste e colocar as tabelas com os tempos de cada método para cada arquivo, assim como os comentários dizendo qual rodou mais rápido e mais lento em cada caso.

### Conclusão

Apresentar as conclusões geradas, verificando se os resultados encontrados estão de acordo com a teoria estudada em sala de aula, bem como as dificuldades encontradas para realizar o trabalho.

### Bibliografia

Listar os títulos que auxiliaram o desenvolvimento do trabalho, segundo as normas da ABNT.

## Problema a ser implementado

Serão disponibilizados 12 arquivos do tipo texto, contendo 1.000, 5.000, 10.000 e 50.000 registros de empréstimos de uma biblioteca. Os registros estarão dispostos de forma aleatória, invertida e ordenada pelo nome do usuário. Será também fornecido um arquivo com 400 nomes de usuários que serão pesquisados.

O formato de cada registro será:

```
codigoEmprestimo;nomeUsuario;tituloLivro;autor;dataEmprestimo;dataDevolucao
```

Exemplo:

```
10025;Marcos Silva;O Alienista;Machado de Assis;10/03/2026;20/03/2026
```

O `codigoEmprestimo` deverá ser considerado a **chave primária**. O `nomeUsuario` deverá ser considerado a **chave secundária** utilizada nas pesquisas.

### 1) Ordenação utilizando Heapsort

Comece a contar o tempo e faça o procedimento a seguir 5 vezes:

- **1.1)** Carregue um arquivo dado (por exemplo `emprestimo1000alea.txt`) em um vetor ou ArrayList.
- **1.2)** Faça a ordenação **pelo nome, se tiver nome igual, pelo código do empréstimo** usando o método **Heapsort**.
- **1.3)** Grave um arquivo com o resultado da ordenação (por exemplo `heap1000alea.txt`). FIM DO PROCEDIMENTO.
- **1.4)** Após rodar o procedimento acima 5 vezes, termine de contar o tempo.
- **1.5)** Faça a diferença entre o tempo final e o inicial e divida por 5.
- **1.6)** Para cada arquivo disponibilizado, faça todo o procedimento acima, começando do 1.1.

### 2) Ordenação utilizando Quicksort

Comece a contar o tempo e faça o procedimento a seguir 5 vezes:

- **2.1)** Carregue um arquivo dado (por exemplo `reserva1000alea.txt`) em um vetor ou ArrayList.
- **2.2)** Faça a ordenação **pelo nome, se tiver nome igual, pelo código do empréstimo** usando o método **Quicksort**.
- **2.3)** Gere um arquivo com o resultado da ordenação (por exemplo `quick1000alea.txt`). FIM DO PROCEDIMENTO.
- **2.4)** Após rodar o procedimento acima 5 vezes, termine de contar o tempo.
- **2.5)** Faça a diferença entre o tempo final e o inicial e divida por 5.
- **2.6)** Para cada arquivo disponibilizado, faça todo o procedimento acima, começando do 2.1.

### 3) Ordenação utilizando Quicksort com Inserção Direta

Comece a contar o tempo e faça o procedimento a seguir 5 vezes:

- **3.1)** Carregue um arquivo dado (por exemplo `multa1000alea.txt`) em um vetor ou ArrayList.
- **3.2)** Faça a ordenação **pelo nome, se tiver nome igual, pelo código da reserva**, de cada arquivo disponibilizado, usando o método **Quicksort**, porém quando a chamada recursiva tiver **20 elementos ou menos**, chame o **Inserção Direta** para ordenar aquela parte do vetor.
- **3.3)** Gere um arquivo com o resultado da ordenação (por exemplo `QuickIns1000alea.txt`). FIM DO PROCEDIMENTO.
- **3.4)** Após rodar o procedimento acima 5 vezes, termine de contar o tempo.
- **3.5)** Faça a diferença entre o tempo final e o inicial e divida por 5.
- **3.6)** Para cada arquivo disponibilizado, faça todo o procedimento acima, começando do 3.1.

### 4) Pesquisa utilizando ABB

Comece a contar o tempo e faça o procedimento a seguir 5 vezes:

- **4.1)** Carregue um arquivo dado (por exemplo `emprestimo1000alea.txt`) em uma **ABB e a balanceie**. A árvore deverá ser construída considerando o nome do usuário como chave secundária. **Atenção:** como vários empréstimos podem pertencer ao mesmo usuário, o programa deverá tratar corretamente a ocorrência de nomes iguais. **Não será permitido simplesmente descartar um registro quando o nome já existir na árvore.** Uma possibilidade é cada nó armazenar uma coleção de registros que possuem a mesma chave secundária.
- **4.2)** Faça a pesquisa na ABB, usando os 400 nomes fornecidos pela professora, gerando um arquivo com o resultado da pesquisa (por exemplo `ABB1000alea.txt`), conforme observação abaixo. FIM DO PROCEDIMENTO.
- **4.3)** Após rodar o procedimento acima 5 vezes, termine de contar o tempo.
- **4.4)** Faça a diferença entre o tempo final e o inicial e divida por 5.
- **4.5)** Para cada arquivo disponibilizado, faça todo o procedimento acima, começando do 4.1.

### 5) Pesquisa utilizando AVL

Comece a contar o tempo e faça o procedimento a seguir 5 vezes:

- **5.1)** Carregue um arquivo dado (por exemplo `reserva1000alea.txt`) em uma ABB e a balanceie. A árvore deverá ser construída considerando o nome do usuário como chave secundária. Assim como na ABB, o programa deverá tratar corretamente os casos em que vários registros possuem o mesmo nome.
- **5.2)** Faça a pesquisa na AVL, usando os 400 nomes fornecidos pela professora, gerando um arquivo com o resultado da pesquisa (por exemplo `AVL1000alea.txt`), conforme observação abaixo. FIM DO PROCEDIMENTO.
- **5.3)** Após rodar o procedimento acima 5 vezes, termine de contar o tempo.
- **5.4)** Faça a diferença entre o tempo final e o inicial e divida por 5.
- **5.5)** Para cada arquivo disponibilizado, faça todo o procedimento acima, começando do 5.1.

### 6) Pesquisa utilizando Hashing Encadeado

Comece a contar o tempo e faça o procedimento a seguir 5 vezes:

- **6.1)** Carregue um arquivo dado (por exemplo `reserva1000alea.txt`) em um **Hashing Encadeado**. A função de espalhamento deverá ser construída utilizando o nome do usuário, que representa a chave secundária.
- **6.2)** Faça a pesquisa no Hashing, usando os 400 nomes fornecidos pela professora, gerando um arquivo com o resultado da pesquisa (por exemplo `Hash1000alea.txt`), conforme observação abaixo. **Todos os registros associados ao nome pesquisado deverão ser encontrados.** FIM DO PROCEDIMENTO.
- **6.3)** Após rodar o procedimento acima 5 vezes, termine de contar o tempo.
- **6.4)** Faça a diferença entre o tempo final e o inicial e divida por 5.
- **6.5)** Para cada arquivo original, faça todo o procedimento acima, começando do 4.1.

## Observações

### 1) Como imprimir o resultado da pesquisa no arquivo?

Para cada nome pesquisado, deverão ser apresentados todos os empréstimos encontrados.

Por exemplo:

```
NOME: Marcos Silva
Empréstimo: 1025 - Livro: Dom Casmurro - Autor: Machado de Assis
Data do empréstimo: 10/03/2026 - Data de devolução: 20/03/2026

Empréstimo: 3487 - Livro: O Alienista - Autor: Machado de Assis
Data do empréstimo: 15/03/2026 - Data de devolução: 25/03/2026

Empréstimo: 7291 - Livro: Memórias Póstumas de Brás Cubas - Autor: Machado de Assis
Data do empréstimo: 18/03/2026 - Data de devolução: 28/03/2026

TOTAL: 3 empréstimos
```

Caso o nome não seja encontrado:

```
NOME: Marcos Silva
NÃO POSSUI EMPRÉSTIMOS
```

### 2) Estabilidade da ordenação

Nos métodos de ordenação, deverá ser observado o comportamento dos algoritmos quando houver registros com a mesma chave secundária.

O programa deverá utilizar o **código do empréstimo como segundo critério de ordenação**, garantindo uma ordem definida para os registros que possuem o mesmo nome.

Na parte escrita, o grupo deverá explicar como o critério de desempate foi implementado e discutir a estabilidade dos métodos utilizados.

### 3) Medição de tempo

Cada processo deverá ser executado 5 vezes, e o tempo considerado será a média das cinco execuções.

Para computar o tempo, você pode utilizar:

- `System.currentTimeMillis()` → retorna o tempo da máquina em milissegundos
- ou `System.nanoTime()` → retorna o tempo da máquina em nanossegundos.

### 4) Interface

O sistema não deverá possuir menu nem nenhuma interação com o usuário. O programa deverá ser executado em sequência, realizando todos os procedimentos solicitados.

### 5) Arquivos

Todos os arquivos lidos e gerados deverão estar dentro do projeto utilizado no Eclipse, NetBeans ou VS Code.

### 6) Arquivos de Saída

Cada processo de ordenação deverá gerar 12 arquivos de saída, um para cada arquivo de entrada.
Cada processo de pesquisa também deverá gerar 12 arquivos de saída, um para cada arquivo de entrada.

Assim:

- Heapsort → 12 arquivos;
- Quicksort → 12 arquivos;
- Quicksort + Inserção → 12 arquivos;
- ABB → 12 arquivos;
- AVL → 12 arquivos;
- Hashing → 12 arquivos.

Ao final, deverão ser gerados: **12 × 6 = 72 arquivos**.

### 7) Execuções

Cada método deverá ser executado cinco vezes com cada arquivo de entrada. Os arquivos produzidos durante as cinco execuções deverão ser sobrescritos.

Ao final, deverá existir apenas um arquivo de saída para cada combinação de método e arquivo de entrada. Ou seja, um arquivo de saída do Heapsort para o arquivo `emprestimo1000alea.txt`, um arquivo de saída do Heapsort para o arquivo `emprestimo1000ord.txt`, um arquivo de saída do Heapsort para o arquivo `emprestimo1000inv.txt` e assim por diante com todos os tamanhos e todos os algoritmos rodados.

### 8) Arquivos a serem enviados

- códigos-fonte completos;
- arquivos de entrada utilizados;
- os 72 arquivos gerados;
- texto contendo uma introdução, as tabelas com os tempos obtidos, a análise dos resultados e a conclusão do grupo;
- relatório de participação dos integrantes.

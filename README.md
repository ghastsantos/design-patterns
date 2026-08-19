# Exemplo Prático: Análise de Código e Qualidade de Software

Este repositório contém um exemplo didático em Java desenvolvido para a disciplina de **Design Patterns**.

O objetivo deste projeto é servir de base para o estudo prático de manutenibilidade, legibilidade e boas práticas de arquitetura e código.

---

## 🎯 Objetivo da Atividade

A classe `UsuarioPrinter` implementa uma funcionalidade completa e operacional: ela recebe uma lista de usuários e renderiza uma tabela formatada no console.

Apesar de o programa funcionar perfeitamente do ponto de vista funcional, a estrutura interna do código contém diversas fragilidades de design que impactam diretamente a sua evolução, legibilidade e facilidade de testes.

Sua missão como aluno é analisar o código-fonte, identificar os pontos de melhoria estruturais e aplicar as refatorações necessárias para elevar a qualidade do projeto sem alterar a saída gerada no console.

---

## 📋 Proposta de Exercício

1. **Análise Crítica:** Esquadrinhe o método `print` e identifique as violações de boas práticas de programação e orientação a objetos.
2. **Mapeamento:** Liste quais sintomas de código deteriorado (*Code Smells*) estão presentes e quais princípios de design foram violados.
3. **Refatoração:** Aplique técnicas de refatoração para transformar o código em uma solução limpa, bem estruturada e fácil de manter.

---

## 🛠️ Requisitos para Execução

* **Linguagem:** Java 17 ou superior

### Como Executar

```bash
javac UsuarioPrinter.java
java UsuarioPrinter
```

---

## Solução implementada

### Análise crítica

O método `print` concentrava várias responsabilidades ao mesmo tempo: validação da lista, seleção de tema, formatação de campos, mascaramento de CPF, montagem da tabela e escrita no console. Esse acoplamento dificultava a leitura, tornava o método grande e deixava regras de negócio misturadas com detalhes de apresentação.

### Code smells encontrados

- **Long Method:** o método `print` fazia todo o fluxo da funcionalidade.
- **Feature Envy / baixa coesão:** a classe manipulava diretamente detalhes de `Usuario`, CPF, e-mail, tema e tabela.
- **Magic Numbers e Magic Strings:** larguras, espaçamento, nomes de temas e mensagens ficavam espalhados no código.
- **Duplicação conceitual:** regras de formatação e renderização estavam embutidas no mesmo bloco, dificultando o reaproveitamento.
- **Baixa testabilidade:** a saída era escrita diretamente no console, sem uma separação clara das etapas de formatação.

### Princípios violados

- **SRP (Single Responsibility Principle):** uma única classe/método tinha motivos demais para mudar.
- **Open/Closed Principle:** adicionar novos temas ou regras de formatação exigia alterar o fluxo principal.
- **Clean Code:** nomes intermediários pouco expressivos e blocos longos dificultavam a manutenção.

### Refatoração aplicada

A solução preserva a API pública de `UsuarioPrinter.print(...)` e separa o fluxo em componentes internos menores:

- `BorderStyle`: resolve o tema e gera a borda.
- `UsuarioFormatter`: centraliza a formatação de ID, nome, e-mail e CPF.
- `UsuarioRow`: representa uma linha já formatada.
- `UsuarioTable`: monta a tabela no formato original.

Também foi adicionado `UsuarioPrinterTest`, um teste simples sem dependências externas, que captura a saída do console e garante que a refatoração preserve a renderização esperada.

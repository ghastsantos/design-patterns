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

## Solucao Implementada

### Analise critica

O metodo `print` concentrava varias responsabilidades ao mesmo tempo: validacao da lista, selecao de tema, formatacao de campos, mascaramento de CPF, montagem da tabela e escrita no console. Esse acoplamento dificultava a leitura, tornava o metodo grande e deixava regras de negocio misturadas com detalhes de apresentacao.

### Code smells encontrados

- **Long Method:** o metodo `print` fazia todo o fluxo da funcionalidade.
- **Feature Envy / baixa coesao:** a classe manipulava diretamente detalhes de `Usuario`, CPF, email, tema e tabela.
- **Magic Numbers e Magic Strings:** larguras, espacamento, nomes de temas e mensagens ficavam espalhados no codigo.
- **Duplicacao conceitual:** regras de formatacao e renderizacao estavam embutidas no mesmo bloco, dificultando reaproveitamento.
- **Baixa testabilidade:** a saida era escrita diretamente no console sem uma separacao clara das etapas de formatacao.

### Principios violados

- **SRP (Single Responsibility Principle):** uma unica classe/metodo tinha motivos demais para mudar.
- **Open/Closed Principle:** adicionar novos temas ou regras de formatacao exigia alterar o fluxo principal.
- **Clean Code:** nomes intermediarios pouco expressivos e blocos longos dificultavam manutencao.

### Refatoracao aplicada

A solucao preserva a API publica de `UsuarioPrinter.print(...)` e separa o fluxo em componentes internos menores:

- `BorderStyle`: resolve o tema e gera a borda.
- `UsuarioFormatter`: centraliza formatacao de id, nome, email e CPF.
- `UsuarioRow`: representa uma linha ja formatada.
- `UsuarioTable`: monta a tabela no formato original.

Tambem foi adicionado `UsuarioPrinterTest`, um teste simples sem dependencias externas, que captura a saida do console e garante que a refatoracao preserva a renderizacao esperada.
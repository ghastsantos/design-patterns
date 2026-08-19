package br.pucpr.usuario;

import java.util.ArrayList;
import java.util.Objects;

public class UsuarioPrinter {
    private static final int TABLE_WIDTH = 74;
    private static final String RIGHT_INDENT = "                    ";
    private static final String EMPTY_LIST_MESSAGE = "ERRO: Lista de usu\u00e1rios vazia ou nula.";

    public record Usuario(Long id, String nome, String email, String cpf) {
    }

    public void print(ArrayList<Usuario> lista, boolean maskCpf, boolean alignRight, String theme) {
        if (lista == null || lista.isEmpty()) {
            System.out.println(EMPTY_LIST_MESSAGE);
            return;
        }

        var border = BorderStyle.from(theme).line(TABLE_WIDTH);
        var formatter = new UsuarioFormatter(maskCpf);
        var table = new UsuarioTable(border);

        for (var usuario : lista) {
            if (usuario != null) {
                table.append(formatter.format(usuario));
            }
            table.closeSection();
            print(table.content(), alignRight);
        }
    }

    private static void print(String content, boolean alignRight) {
        if (!alignRight) {
            System.out.print(content);
            return;
        }

        var lines = content.split("\n");
        for (var line : lines) {
            System.out.println(RIGHT_INDENT + line);
        }
    }

    public static void main(String[] args) {
        var usuarios = new ArrayList<Usuario>();
        usuarios.add(new Usuario(101L, "Carlos Eduardo de Souza", "carlos.souza@email.com", "12345678901"));
        usuarios.add(new Usuario(102L, "Ana Maria Silva", "ana.silva@email.com", "98765432100"));
        usuarios.add(new Usuario(103L, "Jo\u00e3o Pedro de Alc\u00e2ntara Bragan\u00e7a", "joao.pedro@email.com", "45678912345"));
        usuarios.add(new Usuario(104L, "Mariana Costa", "marianacosta.email.com", "11122233344"));
        usuarios.add(new Usuario(105L, "Lucas Mendes", "lucas@email.com", "12345"));
        usuarios.add(new Usuario(106L, "", "beatriz@email.com", "55566677788"));

        var printer = new UsuarioPrinter();
        printer.print(usuarios, true, true, "LIGHT");
    }

    private enum BorderStyle {
        DARK("#"),
        LIGHT("-"),
        DEFAULT("=");

        private final String character;

        BorderStyle(String character) {
            this.character = character;
        }

        private static BorderStyle from(String theme) {
            if (Objects.equals(theme, "DARK")) {
                return DARK;
            }
            if (Objects.equals(theme, "LIGHT")) {
                return LIGHT;
            }
            return DEFAULT;
        }

        private String line(int width) {
            return character.repeat(width);
        }
    }

    private record UsuarioRow(String id, String nome, String email, String cpf) {
    }

    private static class UsuarioFormatter {
        private final boolean maskCpf;

        private UsuarioFormatter(boolean maskCpf) {
            this.maskCpf = maskCpf;
        }

        private UsuarioRow format(Usuario usuario) {
            return new UsuarioRow(
                    formatId(usuario.id()),
                    formatNome(usuario.nome()),
                    formatEmail(usuario.email()),
                    formatCpf(usuario.cpf())
            );
        }

        private static String formatId(Long id) {
            return id != null ? id.toString() : "0";
        }

        private static String formatNome(String nome) {
            if (nome == null || nome.isEmpty()) {
                return "N\u00c3O INFORMADO";
            }
            if (nome.length() > 20) {
                return nome.substring(0, 17) + "...";
            }
            return nome;
        }

        private static String formatEmail(String email) {
            return email == null || !email.contains("@") ? "INVALIDO" : email;
        }

        private String formatCpf(String cpf) {
            if (cpf == null || cpf.length() != 11) {
                return "CPF INVALIDO";
            }
            if (maskCpf) {
                return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
            }
            return cpf.substring(0, 3)
                    + "."
                    + cpf.substring(3, 6)
                    + "."
                    + cpf.substring(6, 9)
                    + "-"
                    + cpf.substring(9, 11);
        }
    }

    private static class UsuarioTable {
        private final String border;
        private final StringBuilder content = new StringBuilder();

        private UsuarioTable(String border) {
            this.border = border;
            content.append(border).append("\n");
            content.append(String.format("| %-5s | %-20s | %-22s | %-14s |\n", "ID", "NOME", "EMAIL", "CPF"));
            content.append(border).append("\n");
        }

        private void append(UsuarioRow row) {
            content.append(String.format(
                    "| %-5s | %-20s | %-22s | %-14s |\n",
                    row.id(),
                    row.nome(),
                    row.email(),
                    row.cpf()
            ));
        }

        private void closeSection() {
            content.append(border).append("\n");
        }

        private String content() {
            return content.toString();
        }
    }
}


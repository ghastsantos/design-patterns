package br.pucpr.usuario;

import br.pucpr.usuario.UsuarioPrinter.Usuario;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class UsuarioPrinterTest {
    public static void main(String[] args) {
        rendersTheSameTableSectionsAsTheOriginalImplementation();
        formatsInvalidValuesWithAccents();
        printsEmptyListError();
        System.out.println("UsuarioPrinterTest OK");
    }

    private static void rendersTheSameTableSectionsAsTheOriginalImplementation() {
        var usuarios = new ArrayList<Usuario>();
        usuarios.add(new Usuario(101L, "Carlos Eduardo de Souza", "carlos.souza@email.com", "12345678901"));
        usuarios.add(new Usuario(102L, "Ana Maria Silva", "ana.silva@email.com", "98765432100"));

        var nl = System.lineSeparator();
        var expected =
                "                    --------------------------------------------------------------------------" + nl
                + "                    | ID    | NOME                 | EMAIL                  | CPF            |" + nl
                + "                    --------------------------------------------------------------------------" + nl
                + "                    | 101   | Carlos Eduardo de... | carlos.souza@email.com | ***.456.789-** |" + nl
                + "                    --------------------------------------------------------------------------" + nl
                + "                    --------------------------------------------------------------------------" + nl
                + "                    | ID    | NOME                 | EMAIL                  | CPF            |" + nl
                + "                    --------------------------------------------------------------------------" + nl
                + "                    | 101   | Carlos Eduardo de... | carlos.souza@email.com | ***.456.789-** |" + nl
                + "                    --------------------------------------------------------------------------" + nl
                + "                    | 102   | Ana Maria Silva      | ana.silva@email.com    | ***.654.321-** |" + nl
                + "                    --------------------------------------------------------------------------" + nl;

        assertEquals(expected, capturePrint(() -> new UsuarioPrinter().print(usuarios, true, true, "LIGHT")));
    }


    private static void formatsInvalidValuesWithAccents() {
        var usuarios = new ArrayList<Usuario>();
        usuarios.add(new Usuario(105L, "Lucas Mendes", "lucas.email.com", "12345"));

        var nl = "\n";
        var expected =
                "--------------------------------------------------------------------------" + nl
                + "| ID    | NOME                 | EMAIL                  | CPF            |" + nl
                + "--------------------------------------------------------------------------" + nl
                + "| 105   | Lucas Mendes         | INV\u00c1LIDO               | CPF INV\u00c1LIDO   |" + nl
                + "--------------------------------------------------------------------------" + nl;

        assertEquals(expected, capturePrint(() -> new UsuarioPrinter().print(usuarios, true, false, "LIGHT")));
    }
    private static void printsEmptyListError() {
        var expected = "ERRO: Lista de usu\u00e1rios vazia ou nula." + System.lineSeparator();

        assertEquals(expected, capturePrint(() -> new UsuarioPrinter().print(new ArrayList<>(), true, true, "LIGHT")));
    }

    private static String capturePrint(Runnable action) {
        var output = new ByteArrayOutputStream();
        var originalOut = System.out;
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
        try {
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8);
    }

    private static void assertEquals(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected:\n" + expected + "\nActual:\n" + actual);
        }
    }
}
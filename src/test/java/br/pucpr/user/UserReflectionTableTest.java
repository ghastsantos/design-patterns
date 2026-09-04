package br.pucpr.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.pucpr.table.TableBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;

class UserReflectionTableTest {
  @Test
  void inspectBuildsAllUserColumns() {
    var users =
        List.of(
            new User(101L, "Carlos Eduardo de Souza", "carlos.souza@email.com", "12345678901"),
            new User(105L, "Lucas Mendes", "lucas.email.com", "12345"),
            new User(106L, "", "beatriz@email.com", "55566677788"));

    var table = new TableBuilder().light().withData(users, columns -> columns.inspect(User.class));

    var expected =
        """
        ------------------------------------------------------------------------------
        |   ID |        CPF       |        E-MAIL        |           NOME            |
        ------------------------------------------------------------------------------
        |  101 | 123.456.789-01   | carlos.souza@emai... | Carlos Eduardo de Souza   |
        |  105 | CPF INVÁLIDO     | INVÁLIDO             | Lucas Mendes              |
        |  106 | 555.666.777-88   | beatriz@email.com    | NÃO INFORMADO             |
        ------------------------------------------------------------------------------
        """;

    assertEquals(expected, table.toString());
  }
}

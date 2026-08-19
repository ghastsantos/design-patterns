package br.pucpr.user;

import br.pucpr.table.TableData;
import java.util.List;
import java.util.Objects;

public class UsersTableData implements TableData {
  private static final List<String> HEADERS =
      List.of(
          "Identificador",
          "Nome completo do usu\u00e1rio",
          "Endere\u00e7o de e-mail do usu\u00e1rio",
          "CPF do usu\u00e1rio");

  private final List<User> users;
  private final boolean maskCpf;

  public UsersTableData(List<User> users, boolean maskCpf) {
    this.users = users == null ? List.of() : users;
    this.maskCpf = maskCpf;
  }

  @Override
  public List<String> getHeaders() {
    return HEADERS;
  }

  @Override
  public List<List<String>> getRows() {
    return users.stream()
        .filter(Objects::nonNull)
        .map(
            user ->
                List.of(
                    formatId(user.id()),
                    formatName(user.name()),
                    validateAndFormatEmail(user.email()),
                    formatCpf(user.cpf())))
        .toList();
  }

  private static String formatId(Long id) {
    return id != null ? id.toString() : "0";
  }

  private String formatCpf(String cpf) {
    if (cpf == null || cpf.length() != 11) {
      return "CPF INV\u00c1LIDO";
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

  private static String validateAndFormatEmail(String email) {
    return email == null || !email.contains("@") ? "INV\u00c1LIDO" : email;
  }

  private static String formatName(String name) {
    if (name == null || name.isEmpty()) {
      return "N\u00c3O INFORMADO";
    }
    return name;
  }
}

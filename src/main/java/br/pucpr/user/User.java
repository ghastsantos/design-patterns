package br.pucpr.user;

import br.pucpr.table.reflection.Column;

public record User(Long id, String name, String email, String cpf) {
  @Column(header = "  ID", order = 1)
  public String formattedId() {
    return id != null ? "%4d".formatted(id) : "-";
  }

  @Column(header = "       CPF      ", order = 2)
  public String formattedCpf() {
    if (cpf == null || cpf.length() != 11) {
      return "CPF INVÁLIDO";
    }
    return cpf.substring(0, 3)
        + "."
        + cpf.substring(3, 6)
        + "."
        + cpf.substring(6, 9)
        + "-"
        + cpf.substring(9, 11);
  }

  @Column(header = "       E-MAIL       ", order = 3)
  public String formattedEmail() {
    return email == null || !email.contains("@") ? "INVÁLIDO" : email;
  }

  @Column(header = "          NOME           ", order = 4)
  public String formattedName() {
    return name == null || name.isEmpty() ? "NÃO INFORMADO" : name;
  }
}

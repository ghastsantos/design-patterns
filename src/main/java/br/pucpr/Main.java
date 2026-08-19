package br.pucpr;

import static br.pucpr.planet.PlanetType.*;
import static br.pucpr.table.Theme.LIGHT;

import br.pucpr.planet.Planet;
import br.pucpr.planet.PlanetsTableData;
import br.pucpr.table.Table;
import br.pucpr.table.Theme;
import br.pucpr.user.User;
import br.pucpr.user.UsersTableData;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    final var table = new Table();
    final var usuarios = new ArrayList<User>();
    usuarios.add(
        new User(101L, "Carlos Eduardo de Souza", "carlos.souza@email.com", "12345678901"));
    usuarios.add(new User(102L, "Ana Maria Silva", "ana.silva@email.com", "98765432100"));
    usuarios.add(
        new User(
            103L,
            "Jo\u00e3o Pedro de Alc\u00e2ntara Bragan\u00e7a",
            "joao.pedro@email.com",
            "45678912345"));
    usuarios.add(new User(104L, "Mariana Costa", "marianacosta.email.com", "11122233344"));
    usuarios.add(new User(105L, "Lucas Mendes", "lucas@email.com", "12345"));
    usuarios.add(new User(106L, "", "beatriz@email.com", "55566677788"));

    System.out.println("IMPRIMINDO USUARIOS");
    System.out.println("-------------------");
    table.print(new UsersTableData(usuarios, true), true, LIGHT);

    final var planetas = new ArrayList<Planet>();
    planetas.add(new Planet("Merc\u00fario", 4879, 57_910_000L, ROCK));
    planetas.add(new Planet("V\u00eanus", 12104, 108_200_000L, ROCK));
    planetas.add(new Planet("Terra", 12756, 149_600_000L, ROCK));
    planetas.add(new Planet("Marte", 6792, 227_940_000L, ROCK));
    planetas.add(new Planet("J\u00fapiter", 142984, 778_330_000L, GAS));
    planetas.add(new Planet("Saturno", 120536, 1_429_400_000L, GAS));
    planetas.add(new Planet("Urano", 51118, 2_870_990_000L, ICE));
    planetas.add(new Planet("Netuno", 49528, 4_504_300_000L, ICE));
    planetas.add(new Planet("Plut\u00e3o", 2376, 5_906_380_000L, DWARF));
    System.out.println();
    System.out.println("IMPRIMINDO PLANETAS");
    System.out.println("-------------------");
    table.print(new PlanetsTableData(planetas), false, Theme.NORMAL);
  }
}

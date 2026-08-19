package br.pucpr.planet;

import br.pucpr.table.TableData;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PlanetsTableData implements TableData {
  private static final List<String> HEADERS =
      List.of(
          "Nome do planeta",
          "Diametro km",
          "Distancia sol km",
          "Distancia sol ua",
          "Tipo do planeta");

  private final List<Planet> planets;

  public PlanetsTableData(List<Planet> planets) {
    this.planets = planets == null ? List.of() : planets;
  }

  @Override
  public List<String> getHeaders() {
    return HEADERS;
  }

  @Override
  public List<List<String>> getRows() {
    return planets.stream()
        .filter(Objects::nonNull)
        .map(
            planet ->
                List.of(
                    formatName(planet.name()),
                    String.format(Locale.US, "%,.1f", planet.diameterKm()),
                    String.format(Locale.US, "%,d", planet.sunDistanceKm()),
                    String.format(Locale.US, "%.2f", Planet.kmToAu(planet.sunDistanceKm())),
                    formatType(planet.type())))
        .toList();
  }

  @Override
  public boolean isRightAligned(int columnIndex) {
    return columnIndex == 1 || columnIndex == 2 || columnIndex == 3;
  }

  private static String formatName(String name) {
    if (name == null || name.isEmpty()) {
      return "NAO INFORMADO";
    }
    return name;
  }

  private static String formatType(PlanetType type) {
    if (type == null) {
      return "NAO INFORMADO";
    }
    return switch (type) {
      case ROCK -> "Rochoso";
      case GAS -> "Gasoso";
      case ICE -> "Gelado";
      case DWARF -> "Anao";
    };
  }
}

package br.pucpr.planet;

import br.pucpr.table.TableData;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PlanetsTableData implements TableData {
  private static final List<String> HEADERS =
      List.of(
          "Nome do planeta",
          "Di\u00e2metro km",
          "Dist\u00e2ncia do Sol em km",
          "Dist\u00e2ncia do Sol em ua",
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
      return "N\u00c3O INFORMADO";
    }
    return name;
  }

  private static String formatType(PlanetType type) {
    if (type == null) {
      return "N\u00c3O INFORMADO";
    }
    return switch (type) {
      case ROCK -> "Rochoso";
      case GAS -> "Gasoso";
      case ICE -> "Gelado";
      case DWARF -> "An\u00e3o";
    };
  }
}

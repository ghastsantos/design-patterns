package br.pucpr.planet;

import br.pucpr.table.reflection.Column;

public record Planet(
    @Column(header = "      Planeta      ") String name,
    @Column(header = "    Diâmetro    ") double diameterKm,
    long sunDistanceKm,
    @Column PlanetType type) {
  public static final long EARTH_SUN_DISTANCE_KM = 149_600_000L;

  public static double kmToAu(long km) {
    return km / (double) EARTH_SUN_DISTANCE_KM;
  }
}

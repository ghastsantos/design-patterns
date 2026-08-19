package br.pucpr.table;

import java.util.List;

public interface TableData {
  List<String> getHeaders();

  List<List<String>> getRows();

  default boolean isRightAligned(int columnIndex) {
    return false;
  }
}

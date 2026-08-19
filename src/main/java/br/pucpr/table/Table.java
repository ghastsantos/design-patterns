package br.pucpr.table;

import java.util.List;
import java.util.Objects;

public class Table {
  private static final String RIGHT_INDENT = "                    ";
  private static final String EMPTY_TABLE_MESSAGE = "ERRO: Tabela vazia ou nula.";

  public String render(TableData data, Theme theme) {
    if (data == null || data.getHeaders() == null || data.getHeaders().isEmpty()) {
      return EMPTY_TABLE_MESSAGE + "\n";
    }

    var rows = data.getRows();
    if (rows == null || rows.isEmpty()) {
      return EMPTY_TABLE_MESSAGE + "\n";
    }

    var headers = data.getHeaders();
    var border = border(theme, headers);
    var output = new StringBuilder();

    output.append(border).append("\n");
    output.append(formatRow(headers, headers, columnIndex -> false)).append("\n");
    output.append(border).append("\n");

    for (var row : rows) {
      output.append(formatRow(row, headers, data::isRightAligned)).append("\n");
    }

    output.append(border).append("\n");
    return output.toString();
  }

  public void print(TableData data, boolean alignRight, Theme theme) {
    var renderedTable = render(data, theme);
    if (!alignRight) {
      System.out.print(renderedTable);
      return;
    }

    for (var line : renderedTable.split("\n")) {
      System.out.print(RIGHT_INDENT + line + "\n");
    }
  }

  private static String border(Theme theme, List<String> headers) {
    var borderChar = Objects.requireNonNullElse(theme, Theme.NORMAL).getBorderChar();
    return borderChar.repeat(borderWidth(headers));
  }

  private static int borderWidth(List<String> headers) {
    return 1 + headers.stream().mapToInt(header -> Objects.toString(header, "").length() + 3).sum();
  }

  private static String formatRow(
      List<String> row, List<String> headers, ColumnAlignmentResolver alignmentResolver) {
    var output = new StringBuilder("|");

    for (var columnIndex = 0; columnIndex < headers.size(); columnIndex++) {
      var header = Objects.toString(headers.get(columnIndex), "");
      var value = valueAt(row, columnIndex);
      output
          .append(" ")
          .append(formatCell(value, header.length(), alignmentResolver.isRightAligned(columnIndex)))
          .append(" |");
    }

    return output.toString();
  }

  private static String valueAt(List<String> row, int columnIndex) {
    if (row == null || columnIndex >= row.size()) {
      return "";
    }
    return Objects.toString(row.get(columnIndex), "");
  }

  private static String formatCell(String value, int width, boolean rightAligned) {
    var adjustedValue = value.length() > width ? value.substring(0, width) : value;
    var padding = " ".repeat(width - adjustedValue.length());

    if (rightAligned) {
      return padding + adjustedValue;
    }
    return adjustedValue + padding;
  }

  @FunctionalInterface
  private interface ColumnAlignmentResolver {
    boolean isRightAligned(int columnIndex);
  }
}

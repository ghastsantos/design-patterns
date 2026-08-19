package br.pucpr.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

class TableTest {
  @Test
  void renderUsesHeaderWidthsForColumns() {
    var data = new FixedTableData(List.of("ID", "Name"), List.of(List.of("12345", "Ada")));
    var table = new Table();

    var expected =
        """
        =============
        | ID | Name |
        =============
        | 12 | Ada  |
        =============
        """;

    assertEquals(expected, table.render(data, Theme.NORMAL));
  }

  @Test
  void renderRightAlignsColumnsSelectedByTableData() {
    var data =
        new FixedTableData(
            List.of("Qty", "Item"), List.of(List.of("7", "Ink")), List.of(true, false));
    var table = new Table();

    var expected =
        """
        ==============
        | Qty | Item |
        ==============
        |   7 | Ink  |
        ==============
        """;

    assertEquals(expected, table.render(data, Theme.NORMAL));
  }

  @Test
  void printAddsLeftSpacingWhenRequested() {
    var data = new FixedTableData(List.of("ID"), List.of(List.of("7")));
    var output = new ByteArrayOutputStream();
    var originalOut = System.out;

    System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
    try {
      new Table().print(data, true, Theme.LIGHT);
    } finally {
      System.setOut(originalOut);
    }

    var expected =
        """
                            ------
                            | ID |
                            ------
                            | 7  |
                            ------
        """;

    assertEquals(expected, output.toString(StandardCharsets.UTF_8));
  }

  private record FixedTableData(
      List<String> headers, List<List<String>> rows, List<Boolean> rightAlignedColumns)
      implements TableData {
    private FixedTableData {
      headers = List.copyOf(headers);
      rows = rows.stream().map(List::copyOf).toList();
      rightAlignedColumns = List.copyOf(rightAlignedColumns);
    }

    private FixedTableData(List<String> headers, List<List<String>> rows) {
      this(headers, rows, List.of());
    }

    @Override
    public List<String> getHeaders() {
      return headers;
    }

    @Override
    public List<List<String>> getRows() {
      return rows.stream().map(List::copyOf).toList();
    }

    @Override
    public boolean isRightAligned(int columnIndex) {
      return columnIndex < rightAlignedColumns.size() && rightAlignedColumns.get(columnIndex);
    }
  }
}

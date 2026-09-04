package br.pucpr.table.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.pucpr.table.TableBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;

class PaginatedTableDataTest {
  @Test
  void exposesOnlyRowsFromRequestedPage() {
    var data =
        new ColumnTableData<>(
            List.of("Mercury", "Venus", "Earth", "Mars", "Jupiter"),
            new ColumnData<String>() {
              @Override
              public String header() {
                return "Planet";
              }

              @Override
              public String get(String object) {
                return object;
              }
            });

    var page = new PaginatedTableData(data, 2, 2);

    assertEquals(2, page.rowCount());
    assertEquals(1, page.colCount());
    assertEquals("Planet", page.header(0));
    assertEquals("Earth", page.get(0, 0));
    assertEquals("Mars", page.get(1, 0));
  }

  @Test
  void builderAppliesPaginationToAnyTableData() {
    var data =
        new TableData() {
          @Override
          public int rowCount() {
            return 4;
          }

          @Override
          public int colCount() {
            return 1;
          }

          @Override
          public String header(int col) {
            return "Name";
          }

          @Override
          public String get(int row, int col) {
            return List.of("Ana", "Bia", "Caio", "Duda").get(row);
          }
        };

    var table = new TableBuilder().light().page(2, 2).withData(data);

    var expected =
        """
        --------
        | Name |
        --------
        | Caio |
        | Duda |
        --------
        """;

    assertEquals(expected, table.toString());
  }

  @Test
  void builderAppliesPaginationToConfiguredColumns() {
    var table =
        new TableBuilder()
            .light()
            .page(2, 2)
            .withData(List.of("Ana", "Bia", "Caio"), columns -> columns.add("Name", name -> name));

    var expected =
        """
        --------
        | Name |
        --------
        | Caio |
        --------
        """;

    assertEquals(expected, table.toString());
  }
}

package br.pucpr.table.reflection;

import br.pucpr.table.model.ColumnData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Util {
  public static <T> List<ColumnData<T>> inspect(Class<T> clazz) {
    var columns = new ArrayList<ReflectedColumn<T>>();
    for (var method : clazz.getDeclaredMethods()) {
      final var column = method.getAnnotation(Column.class);
      if (column == null) continue;
      columns.add(new ReflectedColumn<>(column.header(), column.order(), method));
    }
    columns.sort(Comparator.comparingInt(ReflectedColumn::order));
    return new ArrayList<>(columns);
  }
}

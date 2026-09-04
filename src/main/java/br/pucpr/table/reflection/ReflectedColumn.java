package br.pucpr.table.reflection;

import br.pucpr.table.model.ColumnData;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectedColumn<T> implements ColumnData<T> {
  private final String header;
  private final int order;
  private final Method getter;

  public ReflectedColumn(String header, int order, Method getter) {
    this.header = header.isEmpty() ? getter.getName() : header;
    this.order = order;
    this.getter = getter;
  }

  public int order() {
    return order;
  }

  @Override
  public String header() {
    return header;
  }

  @Override
  public String get(T object) {
    try {
      getter.setAccessible(true);
      var value = getter.invoke(object);
      return value == null ? "" : value.toString();
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      return "?";
    }
  }
}

package br.pucpr.table.model;

public final class PaginatedTableData implements TableData {
  private final TableData data;
  private final int page;
  private final int pageSize;

  public PaginatedTableData(TableData data, int page, int pageSize) {
    if (data == null) {
      throw new IllegalArgumentException("Data cannot be null");
    }
    if (page < 1) {
      throw new IllegalArgumentException("Page must be greater than zero");
    }
    if (pageSize < 1) {
      throw new IllegalArgumentException("Page size must be greater than zero");
    }
    this.data = data;
    this.page = page;
    this.pageSize = pageSize;
  }

  @Override
  public int rowCount() {
    return Math.max(0, Math.min(pageSize, data.rowCount() - firstRow()));
  }

  @Override
  public int colCount() {
    return data.colCount();
  }

  @Override
  public String header(int col) {
    return data.header(col);
  }

  @Override
  public String get(int row, int col) {
    if (row < 0 || row >= rowCount()) {
      throw new IndexOutOfBoundsException("Row out of page bounds: " + row);
    }
    return data.get(firstRow() + row, col);
  }

  private int firstRow() {
    return (page - 1) * pageSize;
  }
}

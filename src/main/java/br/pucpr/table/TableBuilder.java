package br.pucpr.table;

import br.pucpr.table.model.PaginatedTableData;
import br.pucpr.table.model.TableData;
import java.util.Collection;

public class TableBuilder {
  public interface TableDataConfig<T> {
    ColumnsBuilder<T> setup(ColumnsBuilder<T> b);
  }

  private TableData data;
  private Theme theme = Theme.NORMAL;
  private boolean alignRight = false;
  private Integer page;
  private Integer pageSize;

  public TableBuilder withTheme(Theme theme) {
    this.theme = theme;
    return this;
  }

  public TableBuilder light() {
    return withTheme(Theme.LIGHT);
  }

  public TableBuilder dark() {
    return withTheme(Theme.DARK);
  }

  public TableBuilder rightAligned() {
    this.alignRight = true;
    return this;
  }

  public TableBuilder page(int page, int pageSize) {
    this.page = page;
    this.pageSize = pageSize;
    return this;
  }

  public Table withData(TableData data) {
    this.data = data;
    return build();
  }

  public <T> Table withData(Collection<T> data, TableDataConfig<T> cfg) {
    var builder = new ColumnsBuilder<T>();
    this.data = cfg.setup(builder).build(data);
    return build();
  }

  private Table build() {
    if (this.data == null) {
      throw new IllegalStateException("Call a data setting method first!");
    }
    var tableData = data;
    if (page != null && pageSize != null) {
      tableData = new PaginatedTableData(tableData, page, pageSize);
    }
    return new Table(tableData, theme, alignRight);
  }
}

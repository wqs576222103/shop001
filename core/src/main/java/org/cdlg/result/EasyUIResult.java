package org.cdlg.result;

import java.util.List;

/**
 * @Auther: wqs
 * @Date: 2018/8/14 0014 10:37
 * @Description: I LOVE IT？
 */
public class EasyUIResult<T> {
    private Integer total;
    private List<T> rows;

    public EasyUIResult() {
       // super();
    }
    public EasyUIResult(Integer total,List<T> rows) {
        //super();
        this.total=total;
        this.rows=rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}

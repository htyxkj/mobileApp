package mobile.entity;

/**
 * Created by liujinyan on 2017/3/2.
 */
public class BasePojo {

    //第几页的数据
    private Integer pageIndex;
    //一页有多少行
    private Integer rows;
    //偏移量
    private Integer offset;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}

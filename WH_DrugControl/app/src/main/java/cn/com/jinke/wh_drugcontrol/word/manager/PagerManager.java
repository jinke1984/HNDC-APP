package cn.com.jinke.wh_drugcontrol.word.manager;

import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;

/**
 * PagerManager
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/11
 */

public class PagerManager implements CodeConstants {
    private boolean isRefresh = true;
    private int pageSize;
    private int nextPageStart = 0;
    private int dataCount = 0;

    public PagerManager() {
        pageSize = DATA_PAGE_SIZE;
    }

    public PagerManager(int pageSize) {
        this.pageSize = pageSize;
    }

    public void reset() {
        isRefresh = true;
        nextPageStart = 0;
    }

    public int getNextPageStart() {
        return nextPageStart;
    }

    public void addNextPage() {

        nextPageStart += pageSize;
    }

    public void addNextPage(int count, int total) {
        nextPageStart += count;
        
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getDataCount() {
        return dataCount;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
        if (refresh) reset();
    }
}

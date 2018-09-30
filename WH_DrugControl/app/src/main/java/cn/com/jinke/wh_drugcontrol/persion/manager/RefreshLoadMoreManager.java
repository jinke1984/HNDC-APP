package cn.com.jinke.wh_drugcontrol.persion.manager;

/**
 * Created by Administrator on 2016/6/23.
 */
public class RefreshLoadMoreManager {

    /**
     * 默认的页码的开始页
     */
    int defatultPageStartIndex = 0;

    /**
     * 设置默认的开始页码  （可以设为 0 ，或者1，或者其他）
     * @param defatultPageStartIndex
     */
    public void setDefatultPageStartIndex(int defatultPageStartIndex) {
        this.defatultPageStartIndex = defatultPageStartIndex;
        pageIndex = defatultPageStartIndex;
    }
    public int getDefatultPageStartIndex() {
        return defatultPageStartIndex;
    }

    /**
     * 设置默认开始页码为0
     */
    public void  setDefaultPageStarIndex2Zero(){
        setDefatultPageStartIndex(0);
    }

    /**
     * 页码数
     */
    int pageIndex =defatultPageStartIndex;
    public int getPageIndex(){
        return  pageIndex;
    }

    /**
     * 每页显示的条目数
     */
    int pageItemSize = 15;

    public int getPageItemSize() {
        return pageItemSize;
    }

    public void setPageItemSize(int pageItemSize) {
        this.pageItemSize = pageItemSize;
    }


    /**
     * 重置刷新状态 保证页码归为最初状态
     */
    public synchronized void resetPageIndexInRefreshStatus(){
        pageIndex =defatultPageStartIndex;
    }

    /**
     * 重置加载更多时的状态
     */
    public synchronized void changePageIndexInLoadMoreStatus(){
        //需要将页面加 1
        pageIndex ++;
    }

    /**
     * 重置加载 或者刷新异常时的页码
     *  列： 如果数据有 8 也  当加载到 第4 也的时候出现了异常 （即 该页的数据并没有获取到 ）
     *  如果继续加载  就会直接加载 第 5 页的数据  会导致数据丢失的情况发生  。
     *  所以在异常发生后 需要将页面置为本次失败时的页面
     */
    public synchronized void resetPageIndexInExceptionStatus(){
        //说明有错误存在 需要将页码归位
        if(pageIndex > defatultPageStartIndex){
            pageIndex --;
        }
    }

    /**
     * 是否是刷新模式
     * @return
     */
    public synchronized boolean isRefreshMode(){
        return pageIndex == defatultPageStartIndex;
    }


}

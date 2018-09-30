package cn.com.jinke.wh_drugcontrol.booter;

import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * ProjectParams
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/5
 */

public class ProjectParams extends CommonRequestParams.Builder {


    public ProjectParams(String uri) {
        super(uri);
    }

//    public ProjectParams(String uri, ParamsBuilder builder, String[] signs, String[] cacheKeys) {
//        super(uri, builder, signs, cacheKeys);
//    }

    public ProjectParams with(String name, Object value) {
        if (value == null){
            return this;
        }
        this.putParams(name, value);
        return this;
    }
}

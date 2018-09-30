package cn.com.jinke.wh_drugcontrol.webinterface.core;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.JsonConstans;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;

public class CommonRequestParams extends RequestParams implements JsonConstans {

	public CommonRequestParams(Builder builder) {
		super(builder.uri);
		JSONObject jsonObj = new JSONObject();
		try {
			Iterator iterator = builder.map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				if (key != null && value != null) {
					// addParameter(key.toString(), value.toString());
					addBodyParameter(key.toString(), value.toString());
					jsonObj.put(key.toString(), value.toString());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		AppLogger.e("request url: " + this.toString());
		AppLogger.e("request params: " + jsonObj.toString());
		// addParameter("params", jsonObj.toString());
	}

	public static class Builder {

		private String uri;
		private Map<String, Object> map = new HashMap<>();

		public Builder(String uri) {
			String head = RequestHelper.getInstance().getRequestHeader();
			StringBuffer uriAll = new StringBuffer();
			uriAll.append(head);
			uriAll.append(uri);
			this.uri = uriAll.toString();
		}

		public void putParams(String key, Object value) {
			map.put(key, value);
		}

		public CommonRequestParams build() {
			return new CommonRequestParams(this);
		}
	}
}

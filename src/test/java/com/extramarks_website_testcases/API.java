package com.extramarks_website_testcases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API {
	public void api() throws IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.extramarks.com/weblpt/json/bredcrumbs/2269/0/chapter");
		// httpGet.addHeader("User-Agent", USER_AGENT);
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		// System.out.println("GET Response Status:" +
		// httpResponse.getStatusLine().getStatusCode());
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
		String response2 = httpResponse.getEntity().getContent().toString();
		// HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		String bredcrumbs = "";
		try {
			JSONObject jsonObject = new JSONObject(responseString);
			for (String key : jsonObject.keySet()) {
				// System.out.println(key + "=" + jsonObject.get(key)); // to get the value }
				JSONArray jsonArray = jsonObject.optJSONArray("bredcrumbs");
				if (jsonArray != null) {
					String rack_type = "";
					String rack_name = "";
					for (int i = 1; i < jsonArray.length(); i++) {
						// System.out.println(jsonArray.length());
						JSONObject jsonObjects = jsonArray.optJSONObject(i);
						rack_name = rack_name + "," + jsonObjects.optString("rack_name");
						rack_type = rack_type + "," + jsonObjects.optString("rack_type");
						// System.out.println("Rack Name" + rack_name + " Rack Type : " + rack_type);
					}
					bredcrumbs = rack_name;
					System.out.println("Bredcrumbs" + bredcrumbs);
				}
			}
		} catch (JSONException err) {
			System.out.println("error" + err.getMessage());
		}

		httpClient.close();

	}

}

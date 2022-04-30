package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Bpi;
import com.example.demo.repository.BpiRepository;
import com.example.demo.util.CurrencyUtil;
import com.example.demo.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/")
public class bpiController {
	 @Autowired
	 BpiRepository bpiRepository;
	 
	 String bpiUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";
	 

	 //測試呼叫 coindesk API，並顯示其內容。
	 @GetMapping("coindeskAPI")
	 public String getCoindeskAPI() {
		 RestTemplate restTemplate=new RestTemplate();
		 Map<String,String> params=new HashMap<>();
		 return restTemplate.getForObject(bpiUrl,String.class);
	 }
	 
	 
	 //測試呼叫查詢幣別對應表資料 API，並顯示其內容
	 @GetMapping("bpi/{code}")
	 public String getBpi(@PathVariable String code) {
		 RestTemplate restTemplate=new RestTemplate();
		 JSONObject j = new JSONObject(restTemplate.getForObject(bpiUrl,String.class));
		 Object jsonOb = j.getJSONObject("bpi").getJSONObject(code);
		 return jsonOb.toString();
	 }
	 
	//測試呼叫新增幣別對應表資料 API。
	@PostMapping("bpi/{code}")
	public void insertBpi(@PathVariable String code) {
		
		Bpi bpi = bpiRepository.findById(code).orElse(null);
		
		 if(bpi == null){
	        	RestTemplate restTemplate = new RestTemplate();
	    		JSONObject j = new JSONObject(restTemplate.getForObject(bpiUrl, String.class));
	    		j = j.getJSONObject("bpi").getJSONObject(code) ;
	        	bpi = new Bpi();
	    		bpi.setCode(j.get("code").toString());
	    		bpi.setDescription(j.get("description").toString());
	    		bpi.setRate(j.get("rate").toString());
	    		bpi.setRate_float(j.get("rate_float").toString());;
	    		bpi.setSymbol(j.get("symbol").toString());
	        	bpiRepository.save(bpi);
	    } 
	}
	
	//測試呼叫更新幣別對應表資料 API，並顯示其內容。
	@PutMapping("bpi/{code}")
    public String update(@PathVariable String code){

		Bpi bpi = bpiRepository.findById(code).orElse(null);

        if(bpi != null){
        	RestTemplate restTemplate = new RestTemplate();
    		JSONObject j = new JSONObject(restTemplate.getForObject(bpiUrl, String.class));
    		j = j.getJSONObject("bpi").getJSONObject(code) ;
        	bpi = new Bpi();
    		bpi.setCode(j.get("code").toString());
    		bpi.setDescription(j.get("description").toString());
    		bpi.setRate(j.get("rate").toString());
    		bpi.setRate_float(j.get("rate_float").toString());;
    		bpi.setSymbol(j.get("symbol").toString());
        	bpiRepository.save(bpi);
            return j.toString();
        } else {
            return "update失敗，找不到數據";
        }
    }
	
	//測試呼叫刪除幣別對應表資料 API。
	@DeleteMapping("bpi/{code}")
	public void deleteBpi(@PathVariable String code) {
			bpiRepository.deleteById(code);
	}
	
	//測試呼叫刪除幣別對應表資料 API。
	@GetMapping("transData")
	public String transData() throws JsonMappingException, JsonProcessingException, JSONException {
		RestTemplate restTemplate=new RestTemplate();
		JSONObject request = new JSONObject(restTemplate.getForObject(bpiUrl, String.class));
		JSONObject response = new JSONObject();
		response.put("更新時間",DateUtil.formatStrUTCToDateStr(request.getJSONObject("time").get("updated").toString()));
		
		HashMap<String,Object> jsonMap = new ObjectMapper().readValue(request.getJSONObject("bpi").toString(), HashMap.class);
		HashMap<String, Map<String,String>> bpisMap = new HashMap<String, Map<String,String>>(); 
		
		jsonMap.entrySet().stream().forEach( key -> {
			Map<String,String>  dataMap = (Map<String,String>)key.getValue();
			Map<String,String>  bpiMap = new HashMap<String,String>();
			bpiMap.put("幣別", dataMap.get("code"));
			bpiMap.put("幣別中文名稱", CurrencyUtil.getCurrencyInstance(dataMap.get("code")));
			bpiMap.put("匯率", dataMap.get("rate"));
			bpisMap.put(key.getKey(), bpiMap);
		});
		
		response.put("幣別相關資訊",new JSONObject(bpisMap));
		return response.toString();
	}
	


}
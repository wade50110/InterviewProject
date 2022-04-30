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
import com.example.demo.service.BpiService;
import com.example.demo.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/")
public class BpiController {
	 
	 @Autowired
	 BpiService bpiService;
	 
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
		
		bpiService.insertBpi(code);
		
	}
	
	//測試呼叫更新幣別對應表資料 API，並顯示其內容。
	@PutMapping("bpi/{code}")
    public String update(@PathVariable String code){

		return bpiService.update(code);
    }
	
	//測試呼叫刪除幣別對應表資料 API。
	@DeleteMapping("bpi/{code}")
	public void deleteBpi(@PathVariable String code) {
		
		bpiService.deleteBpi(code);
		
	}
	
	//測試呼叫資料轉換的 API，並顯示其內容。
	@GetMapping("transData")
	public String transData() throws JsonMappingException, JsonProcessingException, JSONException {
		RestTemplate restTemplate=new RestTemplate();
		JSONObject request = new JSONObject(restTemplate.getForObject(bpiUrl, String.class));
		return bpiService.transData(request);
	}
	


}

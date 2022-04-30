package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Bpi;
import com.example.demo.repository.BpiRepository;
import com.example.demo.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BpiService {
	@Autowired
	BpiRepository bpiRepository;
	
	String bpiUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";
	
	public void insertBpi(String code) {
		
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
	
	 public String update(String code){

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
	
	public void deleteBpi(String code) {
		bpiRepository.deleteById(code);
	}
	
	public String transData(JSONObject request) throws JsonMappingException, JsonProcessingException, JSONException {
		JSONObject response = new JSONObject();
		response.put("更新時間",DateUtil.formatStrUTCToDateStr(request.getJSONObject("time").get("updated").toString()));
		
		HashMap<String,Object> jsonMap = new ObjectMapper().readValue(request.getJSONObject("bpi").toString(), HashMap.class);
		HashMap<String, Map<String,String>> bpisMap = new HashMap<String, Map<String,String>>(); 
		
		jsonMap.entrySet().stream().forEach( key -> {
			Map<String,String>  dataMap = (Map<String,String>)key.getValue();
		});
		
		response.put("幣別相關資訊",new JSONObject(bpisMap));
		return response.toString();
	}
}

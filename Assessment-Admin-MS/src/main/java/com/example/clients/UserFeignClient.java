package com.example.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "assessment-authentication-ms", path = "/auth")
public interface UserFeignClient {
	
		@GetMapping("/")
		public String test();

}

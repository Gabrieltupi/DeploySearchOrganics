package com.vemser.dbc.searchorganic.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "imgur-client", url = "https://api.imgur.com/3")
public interface ImgurClient {

    @RequestLine("POST /image")
    @Headers("Authorization: {authorizationHeader}")
    String uploadImage(@Param("authorizationHeader") String authorizationHeader, byte[] image);

}

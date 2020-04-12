package com.revel.photoapp.api.users.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

	private static final String ALBUMS_NF_MESSAGE_KEY = "albums.exceptions.albums-not-found";
	private final Environment appEnvironment;
	
	@Autowired
	public FeignErrorDecoder(Environment appEnvironment) {
		this.appEnvironment = appEnvironment;
	}



	@Override
	public Exception decode(String methodKey, Response response) {
		switch(response.status()) {
		case 400:
			// return new 
			break;
		case 404:{
			if(methodKey.contains("getAlbums")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), appEnvironment.getProperty(ALBUMS_NF_MESSAGE_KEY));
			}
			break;
		}
		default:
			return new Exception(response.reason());
		}
		return null;
	}

}

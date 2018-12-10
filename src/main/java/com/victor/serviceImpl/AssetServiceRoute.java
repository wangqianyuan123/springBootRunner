package com.victor.serviceImpl;

import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.service.AssetService;

@Service
public class AssetServiceRoute {

	 @Resource
	 private AssetService[] assetServices;


	    public AssetService route(Integer assetType) {
	        for (AssetService assetService : assetServices) {
	            if (Objects.equals(assetType, assetService.assetType())) {
	                return assetService;
	            }
	        }
	        return null;
	    }
}

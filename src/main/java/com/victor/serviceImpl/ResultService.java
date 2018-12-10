package com.victor.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.service.AssetService;

@Service
public class ResultService {
	
	@Autowired
	private AssetServiceRoute assetServiceRoute;
	
	public String dealList(Integer assetType, Integer assetId, Integer status, Integer sinceId, Integer count) {
        AssetService assetService = assetServiceRoute.route(assetType);
        if (assetService == null) {
            throw null;
        }
        return assetService.queryDealList(assetId, status, sinceId, count);
    }
}

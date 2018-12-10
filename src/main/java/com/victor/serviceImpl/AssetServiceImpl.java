package com.victor.serviceImpl;



import org.springframework.stereotype.Service;

import com.victor.service.AssetService;


@Service
public class AssetServiceImpl implements AssetService {

	
	@Override
	public Integer assetType() {
		// TODO 自动生成的方法存根
		return 1;
	}

	@Override
	public String queryDealList(Integer assetId, Integer status, Integer sinceId, Integer count) {
		return "详情一";
	}

}

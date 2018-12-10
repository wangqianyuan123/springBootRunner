package com.victor.service;


public interface AssetService {

	Integer assetType();

    String queryDealList(Integer assetId, Integer status, Integer sinceId, Integer count);
}

package com.victor.model;

import java.util.List;
import java.util.Map;

import com.easipass.api.model.dto.dra33dto.Mt2101BillInfo;
import com.easipass.api.model.dto.dra33dto.Mt2101Request;

public interface DataMt201BillDao {

    //MT2101_BILL数据加回执
    public List<Mt2101BillInfo> getExPreNewBillInfo(Mt2101Request mt2101Request);
    //MT2101_BILL数据数据加回执 条数
    public Integer getCountExPreNewBill(Mt2101Request mt2101Request);
    
    //得到Mt2101BlcgInfo
	List<Map<String,Object>> getMt2101BlcgList(Mt2101Request mt2101Request);
	
    //得到Mt2101BlctnrInfo
	List<Map<String,Object>> getMt2101BlctnrList(Mt2101Request mt2101Request);
}

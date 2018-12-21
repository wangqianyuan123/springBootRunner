package com.victor.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




public class maintest {

    private static Log logger = LogFactory.getLog(maintest.class);
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String sql="w#E# ";
		Map map=new HashedMap();
		map.put("USER_CODE", "testcode");
		map.put("USERCODE", "12345");
		map.put("USERCODE1", "567");
		map.put("USERCODE4", "");
//		getRelation(sql,map);
        for (int i =0; i <= sql.lastIndexOf("*"); ++i) {
  System.out.println(i);
        }
		//getSqlParameters(sql);
    }
    
    public static List<String> getSqlParameters(String sql) {
        List<Integer> parameterCount = new ArrayList<Integer>();
        if (sql.contains("#")) {
            for (int i = 0; i <= sql.lastIndexOf("#"); ++i) {
                i = sql.indexOf("#");
                parameterCount.add(i);
            }
        }
//        ListUtils.sortList(parameterCount, ">");
        List<String> parameters = new ArrayList<String>();
        try {
            for (int i = 0; i < parameterCount.size(); i += 2) {
                String parameter = sql.substring(parameterCount.get(i) + 1, parameterCount.get(i + 1));
                parameters.add(parameter);
            }
        } catch (Exception e) {
            logger.info("### errorCode =" + e.getMessage());
            return null;
        }
        return parameters;
    }

    private String cutString(String str, String identifierIndex1, String identifierIndex2) {
        while (str.indexOf(identifierIndex1) != -1) {
            //获得{{{第一个点的位置
            int index1 = str.indexOf(identifierIndex1);
            //获得}}}第一个点的位置
            int index2 = str.indexOf(identifierIndex2, index1 + 1);
            //根据第二个点的位置，得到应该截取的字符串
            String delStr = str.substring(index1, index2 + 3);
            /* 替换目标字符 */
            str = str.replace(delStr, " ");
        }
        return str;
    }
    
//    public static void getRelation(String sql, Map<String, String> map)  {
//        String key = null;
//        Iterator<String> it = map.keySet().iterator();
//        while (it.hasNext()) {
//            key = it.next();
//            if (key != null) {
//                String tempValue = map.get(key);
//                int sqlLen = sql.indexOf("#" + key + "#");
//                if (sqlLen < 0) {
//                	sqlLen=sql.indexOf("@" + key + "@");
//                }
//                if(sqlLen<0){
//                	continue;
//                }
//                char symbolChar = sql.charAt(sqlLen);
//                // 必填字段
//                if (symbolChar == '#') {
//                    if (StringUtils.isNotBlank(tempValue) && !tempValue.equals("null")) {
//                        sql = sql.replace("#"+ key + "#", tempValue);
//                    } else {
//                        // 报错信息
//                        logger.info("### errorCode = " + 202 + " mag" + "data error");
//                        throw new RuntimeException("202");
//                    }
//                } else if (symbolChar == '@') {
//                    while (sql.indexOf("@" + key + "@") != -1) {
//                    	   sqlLen = sql.indexOf("@" + key + "@");
//                        // 满足条件替换 删除边界符
//                        if (StringUtils.isNotBlank(tempValue) && !tempValue.equals("null")) {
//                            sql = sql.replaceFirst("@"+ key + "@", tempValue);
//                            String beginIndexStr = sql.substring(0, sqlLen);
//                            int beginIndex = beginIndexStr.lastIndexOf("{{{");
//                            beginIndexStr = sql.substring(0, beginIndex);
//                            String middleIndexStr = sql.substring(beginIndex + 3, sql.indexOf("}}}", sqlLen));
//                            String endIndexStr = (String) sql.subSequence(sql.indexOf("}}}", sqlLen) + 3, sql.length());
//                            sql = beginIndexStr + middleIndexStr + endIndexStr;
//
//                        } else {
//                            // 否则删除sql 条件 行
//                            String beginIndexStr = sql.substring(0, sqlLen);
//                            int beginIndex = beginIndexStr.lastIndexOf("{{{");
//                            beginIndexStr = sql.substring(0, beginIndex);
//                            String endIndexStr = (String) sql.subSequence(sql.indexOf("}}}", sqlLen) + 3, sql.length());
//                            sql = beginIndexStr + endIndexStr;
//                        }
//                    }
//
//                }
//            }
//        }
//
//        logger.info("### sql = " + sql);
//    }
}

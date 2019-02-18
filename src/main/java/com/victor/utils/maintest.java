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
        DOM4JTest<User> d = new DOM4JTest<User>();
        User user = new User();
        User user1 = new User("姓名1", "12", "男");
        User user2 = new User("姓名2", "1", "女");
        User user3 = new User("石头", "23", "女");

        UserInfo userInfo=new UserInfo();
        userInfo.setFaterName("wangqianyuan");
        userInfo.setSum("123");

        UserInfo2 userInfo2=new UserInfo2();
        userInfo2.setFaterName("wangqianyuan2");
        userInfo2.setSum("userInfo2");

        Detail detail =new Detail(1,"详情");
        List<Detail> details = new ArrayList<Detail>();
        details.add(detail);
        userInfo.setDetailList(details);

        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        userInfos.add(userInfo);

        List<UserInfo2> userInfos2 = new ArrayList<UserInfo2>();
        userInfos2.add(userInfo2);

        user1.setInfos(userInfos);
        user1.setInfo2s(userInfos2);
        user2.setInfos(userInfos);
        user2.setInfo2s(userInfos2);
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
//        XMLUtil.transferXmlToEntityList()
        d.writeXmlDocument(user, users, "UTF-8", "C:\\Users\\wqy\\Desktop\\EXCEL学习\\student.xml");
    }
    

}

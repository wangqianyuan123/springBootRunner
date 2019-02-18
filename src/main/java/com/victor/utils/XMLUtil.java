package com.victor.utils; /**
 *  * Title: XMLUtil.java
 *  * Description: 
 *  * @author baymax
 *  * @time 创建时间：2018年1月9日 下午4:18:13
 *  * 
 *  * 
 *  * public class XMLUtil{ }
 *  * package com.paladin.business.GBLM;
 *  
 */


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


/**
  * Title: XMLUtil.java
  * Description: 
  * @author baymax
  * @time 创建时间：2018年1月6日 下午5:03:06
  * 
  * 
  * public class XMLUtil{ }
  * package com.paladin.business;
  */
public class XMLUtil {

    private static Logger logger = LoggerFactory.getLogger(XMLUtil.class);

    private XMLUtil() {
    }


    public static void entityTransferToXml(Object object, Element root, Class clazz) {
        try {
            // 创建根节点元素
            Element entity = root.addElement(lowerCase(clazz));
            Field[] field = object.getClass().getDeclaredFields(); // 获取实体类b的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) { // 遍历所有有属性
                String name = field[j].getName(); // 获取属属性的名字
                if (!name.equals("serialVersionUID")) {//去除串行化序列属性
                    name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                    Method m = object.getClass().getMethod("get" + name);
System.out.println("属性get" + name + "方法返回值类型:" + m.getReturnType());
                    Object propertievalue = m.invoke(object);// 获取属性值
                    if (null != propertievalue) {
                        name = name.substring(0, 1).toLowerCase() + name.substring(1);
                        if (m.getReturnType().toString().contains("java.lang")) {
                            Element propertie = entity.addElement(name);
                            propertie.setText(propertievalue.toString());
                        } else if (java.util.List.class.equals(m.getReturnType())) {
                            entityTransferToXml((List<Object>) propertievalue, entity, name);
                        } else {
                            Element propertie = entity.addElement(name);
                            entityTransferToXml(propertievalue, propertie, m.getReturnType());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String lowerCase(Class clazz) {
        String objName = clazz.toString();
        objName = objName.substring(objName.lastIndexOf(".") + 1);
        objName = objName.substring(0, 1).toLowerCase() + objName.substring(1);
        return objName;
    }
    private static <T> void entityTransferToXml(List<T> objects,Element root,String name){
         Element entitySet = root.addElement(name);
         for (Object object : objects) {
         entityTransferToXml(object, entitySet,object.getClass());
        }
        }
}
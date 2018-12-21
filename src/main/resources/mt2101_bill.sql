/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : worktool

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2018-12-21 19:53:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mt2101_bill
-- ----------------------------
DROP TABLE IF EXISTS `mt2101_bill`;
CREATE TABLE `mt2101_bill` (
  `FILE_NAME` text COLLATE utf8_unicode_ci COMMENT '报文名',
  `ID` decimal(19,0) NOT NULL COMMENT '主键ID，首次插入时源端表的PK值，ID值不更新',
  `FK_VSL_ID` decimal(19,0) DEFAULT NULL COMMENT '外键，指向VSL的ID',
  `MESSAGEID` varchar(192) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '报文编号',
  `VOYAGE` varchar(51) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '航次',
  `IMO_NO` varchar(75) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '船舶IMO编号',
  `VESSEL` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '船名',
  `MASTER_BLNO` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '总提（运）单号',
  `AMENDMENT_CODE` text COLLATE utf8_unicode_ci COMMENT '更改原因代码',
  `TRANSIT_ITEM_CODE` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更改原因代码',
  `DECONSOLIDATOR` varchar(51) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '拆箱人代码',
  `HOUSE_BLNO` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分提（运）单号',
  `CUBE` decimal(10,0) DEFAULT NULL COMMENT '货物体积',
  `VALUE_AMOUNT` decimal(16,2) DEFAULT NULL COMMENT '货物价值',
  `CURRENCY` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '金额类型代码',
  `LOAD_PORT_CODE` varchar(75) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '装货地代码',
  `LOAD_DATE` varchar(51) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '货物装载运输工具时间',
  `DISCHARGE_PORT_CODE` varchar(75) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '卸货地代码',
  `ARRIVAL_DISCHARGE_DATE` varchar(51) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '到达卸货地日期',
  `DELIVERY_PLACE_CODE` varchar(33) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货地点代码',
  `DELIVERY_PLACE` varchar(210) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货地点名称',
  `TRANSHIPMENT_PLACE_CODE` varchar(33) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '            中转地点代码\r\n',
  `TRANSIT_DEST_PLACE_CODE` varchar(33) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '中转目的地代码',
  `ROUTING_COUNTRY_CODE` text COLLATE utf8_unicode_ci COMMENT '途经国家代码',
  `PLACE_CODE_OF_RECEIPT` varchar(33) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '货物托运的地点或者国家代码',
  `GOODS_CMS_STATUS_CODE` text COLLATE utf8_unicode_ci COMMENT '货物海关状态代码',
  `TRANSPORTSPLITINDICATOR` varchar(3) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '承运人货物分批发货标志',
  `PREPAID_OR_COLLECT` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '运费支付方法代码',
  `PKG_TOTAL_NO` decimal(8,0) DEFAULT NULL COMMENT '货物总件数',
  `PKG_TYPE_CODE` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '包装种类代码',
  `TOTAL_GROSS_WEIGHT` decimal(14,4) DEFAULT NULL COMMENT '货物总毛重',
  `PRECMS_DOCUMENT_NO` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '前一海关单证号',
  `PRECMS_DOCUMENT_TYPE` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '前一海关单证类型代码',
  `DELIVERY_STREET` varchar(210) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '货物交付目的地地址（街道,邮箱）',
  `DELIVERY_CITY_NAME` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '城市名称',
  `DELIVERY_COUNTRY_SUB_ID` varchar(18) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份代码',
  `DELIVERY_COUNTRY_SUB_NAME` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份名称',
  `DELIVERY_POST_CODE` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮政编码',
  `DELIVERY_COUNTRY_CODE` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '国家代码',
  `HANDLING_INSTRUCTION` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '码头作业指令代码',
  `INTERMEDIATE_CARRIER_ID` varchar(51) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '中间承运人标识',
  `INTERMEDIATE_TELEPHONE` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '中间承运人电话',
  `INTERMEDIATE_EMAIL` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '中间承运人电子邮箱',
  `INTERMEDIATE_FAX` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '中间承运人传真',
  `CONSIGNEE_CODE` text COLLATE utf8_unicode_ci COMMENT '收货人代码',
  `CONSIGNEE_NAME` varchar(210) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人名称',
  `CONSIGNEE_STREET` varchar(210) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人地址（街道,邮箱）',
  `CONSIGNEE_CITY_NAME` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '城市名称',
  `CONSIGNEE_COUNTRY_SUB_ID` varchar(18) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份代码',
  `CONSIGNEE_COUNTRY_SUB_NAME` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份名称',
  `CONSIGNEE_POST_CODE` varchar(27) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮政编码',
  `CONSIGNEE_COUNTRY_CODE` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '国家代码',
  `CONSIGNEE_TELEPHONE` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_EMAIL` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人电子邮箱',
  `CONSIGNEE_FAX` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人传真',
  `CONSIGNEE_CONTACT_NAME` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '具体联系人名称',
  `CONSIGNEE_CONTACT_TELEPHONE` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '具体联系人电话',
  `CONSIGNEE_CONTACT_FAX` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '具体联系人电子邮箱',
  `CONSIGNEE_FAX3` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '具体联系人传真',
  `CONSIGNOR_CODE` text COLLATE utf8_unicode_ci COMMENT '发货人代码',
  `CONSIGNOR_NAME` varchar(210) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发货人名称',
  `CONSIGNOR_STREET` varchar(210) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT ' 详细地址（街道,邮箱）',
  `CONSIGNOR_CITY_NAME` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '城市名称',
  `CONSIGNOR_COUNTRY_SUB_ID` varchar(18) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份代码',
  `CONSIGNOR_COUNTRY_SUB_NAME` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份名称',
  `CONSIGNOR_POST_CODE` varchar(27) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮政编码',
  `CONSIGNOR_COUNTRY_CODE` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '国家代码',
  `CONSIGNOR_TELEPHONE` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发货人电话',
  `CONSIGNOR_EMAIL` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发货人电子邮箱',
  `CONSIGNOR_FAX` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发货人传真',
  `NOTIFY_CODE` text COLLATE utf8_unicode_ci COMMENT '通知人代码',
  `NOTIFY_NAME` varchar(210) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '通知人名称',
  `NOTIFY_STREET` varchar(210) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '详细地址（街道,邮箱）',
  `NOTIFY_CITY_NAME` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '城市名称',
  `NOTIFY_COUNTRY_SUB_ID` varchar(18) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份代码',
  `NOTIFY_COUNTRY_SUB_NAME` varchar(105) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份名称',
  `NOTIFY_POST_CODE` varchar(27) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮政编码',
  `NOTIFY_COUNTRY_CODE` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '国家代码',
  `NOTIFY_TELEPHONE` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '通知人电话',
  `NOTIFY_EMAIL` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '通知人电子邮箱',
  `NOTIFY_FAX` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '通知人传真',
  `UNDG_CONTACT_NAME` varchar(210) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '危险品联系人姓名',
  `UNDG_CONTACT_TELEPHONE` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '危险品联系人电话',
  `UNDG_CONTACT_EMAIL` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '危险品联系人电子邮箱',
  `UNDG_CONTACT_FAX` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '危险品联系人传真',
  `FILE_FUNCTION` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '报文功能代码',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '报文建立时间',
  `CONSIGNEE_AEO_CODE` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人经认证的经营者代码',
  `CONSIGNOR_AEO_CODE` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发货人经认证的经营者代码',
  `ISCURRENT` decimal(3,0) NOT NULL COMMENT '是否当前数据',
  `ISDELETED` decimal(3,0) NOT NULL COMMENT '是否（物理）删除',
  `SYSTEMKEY` decimal(19,0) NOT NULL COMMENT '源系统编号',
  `FEED` text COLLATE utf8_unicode_ci NOT NULL COMMENT '源表说明',
  `FEEDKEY` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '源表key列',
  `FEEDVALUE` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '源表key值',
  `LASTUPDATEDDT` datetime NOT NULL COMMENT '记录的最后更新时间',
  `STARTDT` datetime DEFAULT NULL COMMENT '业务数据生效时间',
  `ENDDT` datetime DEFAULT NULL COMMENT '业务数据失效时间',
  `CAPXACTION` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'dml操作标记',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='MT2101提单当前表';

-- ----------------------------
-- Records of mt2101_bill
-- ----------------------------

-- ----------------------------
-- Table structure for mt2101_bill_rsp
-- ----------------------------
DROP TABLE IF EXISTS `mt2101_bill_rsp`;
CREATE TABLE `mt2101_bill_rsp` (
  `FILE_NAME` text COLLATE utf8_unicode_ci COMMENT '报文名',
  `ID` decimal(20,0) NOT NULL COMMENT '主键ID，首次插入时源端表的PK值，ID值不更新',
  `FK_VSL_ID` decimal(20,0) DEFAULT NULL COMMENT '外键，指向VSL的ID',
  `MESSAGEID` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '报文编号',
  `VOYAGE` varchar(17) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '航次',
  `IMO_NO` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '船舶IMO编号',
  `MASTER_BLNO` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '总提（运）单号',
  `HOUSE_BLNO` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分提（运）单号',
  `RSP_CODE` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '回执类型代码',
  `RSP_TEXT` text COLLATE utf8_unicode_ci COMMENT '回执类型详细描述',
  `FILE_CREATE_TIME` varchar(17) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '报文创建时间',
  `ISCURRENT` decimal(3,0) NOT NULL COMMENT '是否当前数据',
  `ISDELETED` decimal(3,0) NOT NULL COMMENT '是否（物理）删除',
  `SYSTEMKEY` decimal(19,0) NOT NULL COMMENT '源系统编号',
  `FEED` text COLLATE utf8_unicode_ci NOT NULL COMMENT '源表说明',
  `FEEDKEY` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '源表key列',
  `FEEDVALUE` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '源表key值',
  `LASTUPDATEDDT` datetime NOT NULL COMMENT '记录的最后更新时间',
  `STARTDT` datetime NOT NULL COMMENT '业务数据生效时间',
  `ENDDT` datetime DEFAULT NULL COMMENT '业务数据失效时间',
  `CAPXACTION` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'dml操作标记',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='MT9999_提单回执';

-- ----------------------------
-- Records of mt2101_bill_rsp
-- ----------------------------
INSERT INTO `mt2101_bill_rsp` VALUES ('1810040950559849005.mft', '105', '105', '105', '1840S', '9324966', 'JJCSHKAB801205', 'N/A', '01', '21301 预配舱单主要数据传输成功。', '20181002095053145', '1', '0', '6', 'MT9999_CONSIGNMENT', 'XPK_CONSIGNMENT', '105.000000000000000', '2018-11-07 16:10:00', '2018-10-23 09:55:03', null, 'U');
INSERT INTO `mt2101_bill_rsp` VALUES ('1810040950559849005.mft', '106', '106', '114', '1840S', '9324966', 'JJCSHKAB801205', 'N/A', '01', '21301 预配舱单主要数据传输成功。', '20181005095053145', '1', '0', '6', 'MT9999_CONSIGNMENT', 'XPK_CONSIGNMENT', '105.000000000000000', '2018-10-25 16:55:03', '2018-10-23 09:55:03', null, 'U');
INSERT INTO `mt2101_bill_rsp` VALUES ('1810040950559849005.mft', '107', '107', '114', '1840S', '9324966', 'JJCSHKAB801205', 'N/A', '01', '21301 预配舱单主要数据传输成功。', '20181004095053145', '1', '0', '6', 'MT9999_CONSIGNMENT', 'XPK_CONSIGNMENT', '105.000000000000000', '2018-10-25 16:55:03', '2018-10-23 09:55:03', null, 'U');
INSERT INTO `mt2101_bill_rsp` VALUES ('1810040950559849005.mft', '108', '108', '108', '1840S', '9324966', 'JJCSHKAB801205', 'N/A', '01', '21301 预配舱单主要数据传输成功。', '20181003095053145', '1', '0', '6', 'MT9999_CONSIGNMENT', 'XPK_CONSIGNMENT', '105.000000000000000', '2018-10-25 16:55:03', '2018-10-23 09:55:03', null, 'U');
INSERT INTO `mt2101_bill_rsp` VALUES ('1810040950559849005.mft', '114', '114', '114', '1840S', '9324966', 'JJCSHKAB801205', 'N/A', '01', '24301?预配舱单删除申请审核通过。', '20181006095053145', '1', '0', '6', 'MT9999_CONSIGNMENT', 'XPK_CONSIGNMENT', '105.000000000000000', '2018-10-25 16:55:03', '2018-10-23 09:55:03', null, 'U');
INSERT INTO `mt2101_bill_rsp` VALUES ('1810040950559849005.mft', '20000001', '20000011', 'SEA_2222222222221_20181004000000001', '1840S', '9324966', 'JJCSHKAB801205', 'N/A', '01', '24301 预配舱单删除申请审核通过。', null, '1', '0', '6', 'MT9999_CONSIGNMENT', 'XPK_CONSIGNMENT', '105.000000000000000', '2018-10-25 16:55:03', '2018-10-23 09:55:03', null, 'U');
INSERT INTO `mt2101_bill_rsp` VALUES ('1810040950559849005.mft', '20000002', '20000022', 'SEA_2222222222221_20181004000000001', '1840S', '9324966', 'JJCSHKAB801205', 'N/A', '01', '24301 预配舱单删除申请审核通过。', '20181009095053145', '1', '0', '6', 'MT9999_CONSIGNMENT', 'XPK_CONSIGNMENT', '105.000000000000000', '2018-10-25 16:55:03', '2018-10-23 09:55:03', null, 'U');
INSERT INTO `mt2101_bill_rsp` VALUES ('1810040950559849005.mft', '20000003', '20000033', 'SEA_2222222222221_20181004000000001', '1840S', '9324966', 'JJCSHKAB801205', 'N/A', '01', '24301 预配舱单删除申请审核通过。', '20181008095053145', '1', '0', '6', 'MT9999_CONSIGNMENT', 'XPK_CONSIGNMENT', '105.000000000000000', '2018-10-25 16:55:03', '2018-10-23 09:55:03', null, 'U');
INSERT INTO `mt2101_bill_rsp` VALUES ('1810040950559849005.mft', '20000004', '20000044', 'SEA_2222222222221_20181004000000002', '1840S', '9324966', 'JJCSHKAB801205', 'N/A', '01', '24301 预配舱单删除申请审核通过。', '20181007095053145', '1', '0', '6', 'MT9999_CONSIGNMENT', 'XPK_CONSIGNMENT', '105.000000000000000', '2018-10-25 16:55:03', '2018-10-23 09:55:03', null, 'U');

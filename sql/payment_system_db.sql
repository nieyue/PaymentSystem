# 数据库 
#创建数据库
DROP DATABASE IF EXISTS payment_system_db;
CREATE DATABASE payment_system_db;

#使用数据库 
use payment_system_db;

#创建支付表
CREATE TABLE payment_tb(
payment_id int(11) NOT NULL AUTO_INCREMENT COMMENT '支付id',
subject varchar(255) COMMENT '主题',
body varchar(255) COMMENT '内容',
notify_url varchar(255) COMMENT '异步通知',
type tinyint(4) COMMENT '支付类型，默认1支付宝支付，2微信支付，3银联支付',
order_number varchar(255) COMMENT '平台订单号',
money decimal(11,2) COMMENT '金额',
status tinyint(4) DEFAULT 1 COMMENT '状态，1已下单-未支付，2支付成功，3支付失败',
business_type tinyint(4) COMMENT '业务类型',
business_id int(11) COMMENT '业务id,外键',
business_notify_url longtext COMMENT '业务回调,外键',
acount_id int(11) COMMENT '账户id,外键',
create_date datetime  COMMENT '创建时间',
update_date datetime  COMMENT '更新时间',
PRIMARY KEY (payment_id),
INDEX INDEX_ORDERNUMBER (order_number) USING BTREE,
INDEX INDEX_TYPE (type) USING BTREE,
INDEX INDEX_BUSINESSID (business_id) USING BTREE,
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='支付表';

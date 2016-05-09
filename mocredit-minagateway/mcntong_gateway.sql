/*
SQLyog Ultimate v8.32 
MySQL - 5.5.20 : Database - mcntong_gateway
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mcntong_gateway` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mcntong_gateway`;

/*Table structure for table `device` */

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `storeid` bigint(20) DEFAULT NULL COMMENT '所属店面id',
  `devcode` varchar(50) DEFAULT NULL COMMENT '设备编号',
  `shopid` bigint(20) DEFAULT NULL COMMENT '商户id',
  `agentid` bigint(20) DEFAULT NULL COMMENT '代理商id',
  `versionid` bigint(20) DEFAULT NULL,
  `createtime` varchar(20) DEFAULT NULL,
  `updatetime` varchar(20) DEFAULT NULL,
  `onlinetime` varchar(20) DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '状态0正常 1:锁死',
  `password` varchar(40) DEFAULT NULL COMMENT '机子登录密码',
  `loginnum` int(1) DEFAULT '0' COMMENT '机子登录次数',
  `updatetype` varchar(10) DEFAULT NULL COMMENT '新更类型',
  `versionno` varchar(5) DEFAULT NULL COMMENT '版本号',
  `adminpassword` varchar(50) DEFAULT NULL COMMENT '机具管理密码',
  `batchno` varchar(6) DEFAULT '000001' COMMENT '机具的批次号',
  `deskey` varchar(20) DEFAULT '3131313131313131' COMMENT '主密钥',
  `mackey` varchar(20) DEFAULT '3131313131313131' COMMENT 'mac密钥',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `device` */

insert  into `device`(`id`,`storeid`,`devcode`,`shopid`,`agentid`,`versionid`,`createtime`,`updatetime`,`onlinetime`,`status`,`password`,`loginnum`,`updatetype`,`versionno`,`adminpassword`,`batchno`,`deskey`,`mackey`) values (1,1,'82393007',1,17,NULL,NULL,NULL,NULL,0,NULL,0,NULL,NULL,NULL,'000082','3131313131313131','3131313131313131'),(2,1,'81105252',1,17,NULL,NULL,NULL,NULL,0,NULL,0,NULL,NULL,NULL,'000022','3131313131313131','3131313131313131');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

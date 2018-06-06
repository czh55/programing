/*
 Navicat Premium Data Transfer

 Source Server         : centos
 Source Server Type    : MySQL
 Source Server Version : 50173
 Source Host           : 10.37.129.3:3306
 Source Schema         : programing

 Target Server Type    : MySQL
 Target Server Version : 50173
 File Encoding         : 65001

 Date: 06/06/2018 15:49:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for programing_cart
-- ----------------------------
DROP TABLE IF EXISTS `programing_cart`;
CREATE TABLE `programing_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `competition_id` int(11) DEFAULT NULL COMMENT '比赛id',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `checked` int(11) DEFAULT NULL COMMENT '是否选择,1=已勾选,0=未勾选',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of programing_cart
-- ----------------------------
BEGIN;
INSERT INTO `programing_cart` VALUES (126, 21, 26, 1, 1, '2017-04-13 21:27:06', '2017-04-13 21:27:06');
INSERT INTO `programing_cart` VALUES (135, 22, 30, 1, 0, '2018-05-24 14:14:57', '2018-06-03 22:43:45');
INSERT INTO `programing_cart` VALUES (144, 22, 27, 1, 1, '2018-06-03 23:05:20', '2018-06-03 23:05:20');
COMMIT;

-- ----------------------------
-- Table structure for programing_category
-- ----------------------------
DROP TABLE IF EXISTS `programing_category`;
CREATE TABLE `programing_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别Id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类别id当id=0时说明是根节点,一级类别',
  `name` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `status` tinyint(1) DEFAULT '1' COMMENT '类别状态1-正常,2-已废弃',
  `sort_order` int(4) DEFAULT NULL COMMENT '排序编号,同类展示顺序,数值相等则自然排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100032 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of programing_category
-- ----------------------------
BEGIN;
INSERT INTO `programing_category` VALUES (100001, 0, '语言类', 1, NULL, '2017-03-25 16:46:00', '2017-03-25 16:46:00');
INSERT INTO `programing_category` VALUES (100002, 0, '工程类', 1, NULL, '2017-03-25 16:46:21', '2017-03-25 16:46:21');
INSERT INTO `programing_category` VALUES (100003, 0, '游戏类', 1, NULL, '2017-03-25 16:49:53', '2017-03-25 16:49:53');
INSERT INTO `programing_category` VALUES (100004, 0, '微信类', 1, NULL, '2017-03-25 16:50:19', '2017-03-25 16:50:19');
INSERT INTO `programing_category` VALUES (100005, 0, '数据库类', 1, NULL, '2017-03-25 16:50:29', '2017-03-25 16:50:29');
INSERT INTO `programing_category` VALUES (100006, 100001, 'C++', 1, NULL, '2017-03-25 16:52:15', '2017-03-25 16:52:15');
INSERT INTO `programing_category` VALUES (100007, 100001, 'Java', 1, NULL, '2017-03-25 16:52:26', '2017-03-25 16:52:26');
INSERT INTO `programing_category` VALUES (100008, 100001, 'PHP', 1, NULL, '2017-03-25 16:52:39', '2017-03-25 16:52:39');
INSERT INTO `programing_category` VALUES (100009, 100001, 'Python', 1, NULL, '2017-03-25 16:52:45', '2017-03-25 16:52:45');
INSERT INTO `programing_category` VALUES (100010, 100001, 'Scala', 1, NULL, '2017-03-25 16:52:54', '2017-03-25 16:52:54');
INSERT INTO `programing_category` VALUES (100011, 100002, '移动开发', 1, NULL, '2017-03-25 16:53:18', '2017-03-25 16:53:18');
INSERT INTO `programing_category` VALUES (100012, 100002, '后台开发', 1, NULL, '2017-03-25 16:53:27', '2017-03-25 16:53:27');
INSERT INTO `programing_category` VALUES (100013, 100002, '前端开发', 1, NULL, '2017-03-25 16:53:35', '2017-03-25 16:53:35');
INSERT INTO `programing_category` VALUES (100014, 100002, '数据清洗', 1, NULL, '2017-03-25 16:53:56', '2017-03-25 16:53:56');
INSERT INTO `programing_category` VALUES (100015, 100002, '人工智能', 1, NULL, '2017-03-25 16:54:07', '2017-03-25 16:54:07');
INSERT INTO `programing_category` VALUES (100016, 100003, '沙箱游戏', 1, NULL, '2017-03-25 16:54:44', '2017-03-25 16:54:44');
INSERT INTO `programing_category` VALUES (100017, 100003, '射击游戏', 1, NULL, '2017-03-25 16:54:51', '2017-03-25 16:54:51');
INSERT INTO `programing_category` VALUES (100018, 100003, '决策游戏', 1, NULL, '2017-03-25 16:55:02', '2017-03-25 16:55:02');
INSERT INTO `programing_category` VALUES (100019, 100003, '模拟游戏', 1, NULL, '2017-03-25 16:55:09', '2017-03-25 16:55:09');
INSERT INTO `programing_category` VALUES (100020, 100003, '动作游戏', 1, NULL, '2017-03-25 16:55:18', '2017-03-25 16:55:18');
INSERT INTO `programing_category` VALUES (100021, 100004, '微信开发', 1, NULL, '2017-03-25 16:55:30', '2017-03-25 16:55:30');
INSERT INTO `programing_category` VALUES (100022, 100004, '微信小程序开发', 1, NULL, '2017-03-25 16:55:37', '2017-03-25 16:55:37');
INSERT INTO `programing_category` VALUES (100023, 100004, '微信公众号开发', 1, NULL, '2017-03-25 16:55:47', '2017-03-25 16:55:47');
INSERT INTO `programing_category` VALUES (100024, 100004, '微信表情开发', 1, NULL, '2017-03-25 16:55:56', '2017-03-25 16:55:56');
INSERT INTO `programing_category` VALUES (100025, 100004, '微信外挂开发', 1, NULL, '2017-03-25 16:56:06', '2017-03-25 16:56:06');
INSERT INTO `programing_category` VALUES (100026, 100005, 'Mysql', 1, NULL, '2017-03-25 16:56:22', '2017-03-25 16:56:22');
INSERT INTO `programing_category` VALUES (100027, 100005, 'Oracle', 1, NULL, '2017-03-25 16:56:30', '2017-03-25 16:56:30');
INSERT INTO `programing_category` VALUES (100028, 100005, 'PostgreSQL', 1, NULL, '2017-03-25 16:56:37', '2017-03-25 16:56:37');
INSERT INTO `programing_category` VALUES (100029, 100005, ' LexisNexis', 1, NULL, '2017-03-25 16:56:45', '2017-03-25 16:56:45');
INSERT INTO `programing_category` VALUES (100030, 100005, 'Redis', 1, NULL, '2017-03-25 16:57:05', '2017-03-25 16:57:05');
INSERT INTO `programing_category` VALUES (100031, 0, '修改品类名称', 1, NULL, '2018-06-06 15:03:11', '2018-06-06 15:03:11');
COMMIT;

-- ----------------------------
-- Table structure for programing_code
-- ----------------------------
DROP TABLE IF EXISTS `programing_code`;
CREATE TABLE `programing_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代码id',
  `competition_id` int(11) NOT NULL COMMENT '产品id',
  `user_id` int(11) NOT NULL COMMENT '提交用户id',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `title` varchar(500) DEFAULT NULL COMMENT '代码题目',
  `src` varbinary(500) DEFAULT NULL COMMENT '上传路径(名称)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of programing_code
-- ----------------------------
BEGIN;
INSERT INTO `programing_code` VALUES (1, 29, 22, 'chen', '陈志恒1', 0x39336664636134362D623230662D343161632D393765342D3164303439646262663964312E646F63, NULL, '2018-05-23 16:29:35');
INSERT INTO `programing_code` VALUES (4, 28, 22, 'chen', 'lxy1', 0x37356265376264322D323536352D346336612D383237382D3732656237343732393765662E646F63, NULL, '2018-05-22 12:12:03');
INSERT INTO `programing_code` VALUES (5, 33, 22, 'chen', '陈志恒1414010831', 0x65363965356430662D633563372D346661392D383031392D3762396431326365396365612E646F63, '2018-05-23 16:25:50', '2018-05-23 16:36:32');
INSERT INTO `programing_code` VALUES (8, 30, 22, 'chen', '陈141401089', 0x32646362326638352D303163322D346330312D396462382D6631323637353837643131392E646F63, '2018-05-23 16:37:54', '2018-05-23 16:42:31');
INSERT INTO `programing_code` VALUES (9, 26, 22, 'chen', '陈1414010831', 0x33643234386133342D333662342D346539362D386236342D3632303238323566326265382E646F63, '2018-05-23 16:43:36', '2018-05-23 16:43:50');
INSERT INTO `programing_code` VALUES (10, 31, 22, 'chen', 'chen124', 0x35623237663738612D376462642D343362372D623633662D3339333561653862326138302E706466, '2018-05-25 11:32:16', '2018-05-31 23:05:23');
INSERT INTO `programing_code` VALUES (11, 35, 1, 'admin', '修改代码的标题', 0xE4BFAEE694B9737263, '2018-06-06 15:38:58', '2018-06-06 15:40:24');
COMMIT;

-- ----------------------------
-- Table structure for programing_competition
-- ----------------------------
DROP TABLE IF EXISTS `programing_competition`;
CREATE TABLE `programing_competition` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `category_id` int(11) NOT NULL COMMENT '分类id,对应programing_category表的主键',
  `sponsor_id` int(11) NOT NULL COMMENT '用户id,对应programing_user表的主键',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `subtitle` varchar(200) DEFAULT NULL COMMENT '商品副标题',
  `main_image` varchar(500) DEFAULT NULL COMMENT '产品主图,url相对地址',
  `sub_images` text COMMENT '图片地址,json格式,扩展用',
  `detail` text COMMENT '商品详情',
  `price` decimal(20,2) NOT NULL COMMENT '价格,单位-元保留两位小数',
  `stock` int(11) NOT NULL COMMENT '库存数量',
  `status` int(6) DEFAULT '1' COMMENT '商品状态.1-在售 2-下架 3-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of programing_competition
-- ----------------------------
BEGIN;
INSERT INTO `programing_competition` VALUES (26, 100006, 1, '蓝桥杯', 'c/c++ B组省赛', 'd8c53aee-5e1c-46d6-bec4-6552403d099d.jpg', 'd8c53aee-5e1c-46d6-bec4-6552403d099d.jpg', '<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; kkkk为促进软件专业技术人才培养，向软件行业输送具有创新能力和实践能力的高端人才，提升高校毕业生的就业竞争力，全面推动行业发展及人才培养进程，<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E5%B7%A5%E4%B8%9A%E5%92%8C%E4%BF%A1%E6%81%AF%E5%8C%96%E9%83%A8%E4%BA%BA%E6%89%8D%E4%BA%A4%E6%B5%81%E4%B8%AD%E5%BF%83\">工业和信息化部人才交流中心</a>特举办“全国软件专业人才设计与创业大赛”，本赛包括个人赛和团队赛两个比赛项目，个人赛设置：1、C/C++程序设计（本科A组、本科B组、高职高专组）2、Java软件开发（本科A组、本科B组、高职高专组）3、嵌入式设计与开发（大学组、研究生组）4、单片机设计与开发（大学组）5、电子设计与开发（大学组），团队赛设置：软件创业赛一个科目组别。并且形成了立足行业，结合实际，实战演练，促进就业的特色。</p>', 300.00, 9996, 1, NULL, '2018-05-29 16:41:21');
INSERT INTO `programing_competition` VALUES (27, 100007, 1, '甲骨文杯', '全国java程序设计竞赛', 'b4c388c0-2dc9-4918-b267-9aadfc2c1425.jpg', 'b4c388c0-2dc9-4918-b267-9aadfc2c1425.jpg', '<p>“全国Java程序设计大赛”是面向全国各大高等院校在校学生和社会技术人士的程序设计竞赛活动。通过竞赛活动为IT相关专业的大学生和技术人士搭建一个展现程序设计能力的平台，提高Java技术人士的实践技能，形成良好的学习和研究氛围，为优秀人才的脱颖而出创造条件。竞赛响应国家加快推进软件产业创新的号召，培养造就高素质IT人才，为社会经济发展作贡献。<br></p>', 200.00, 0, 1, NULL, '2018-06-03 21:44:54');
INSERT INTO `programing_competition` VALUES (28, 100011, 1, ' 大学生挑战赛', 'Google  中国教育合作项目', '3bdd6883-6e5a-4e63-bcd8-d39f6256028d.png', '3bdd6883-6e5a-4e63-bcd8-d39f6256028d.png', '<p>                      </p><h3>              从 2010 年开始，Google 每年举办大学生挑战赛，希望通过竞赛平台，为国内大学生提供动手实践的机会。自 2010 年至 2017 年，Google              大学生竞赛累计参与同学 13477 人次，覆盖全国至少 300 多所院校。            </h3><p>            </p><h3>              从 2018 年开始，作为教育部“中美青年创客大赛”的承办单位之一，Google 欢迎同学们/开发者积极参赛，请访问中美青年创客大赛官方网站（<a href=\"http://www.chinaus-maker.org\" target=\"_blank\">http://www.chinaus-maker.org</a>）了解更多信息，期待大家的参与和支持！            </h3><p>            </p><p>              2018 “中美青年创客大赛” 推荐开源技术和平台:            </p><p>            </p><ul><li>人工智能：              </li><ul><li>TensorFlow：<a href=\"https://tensorflow.google.cn/\" target=\"_blank\">https://tensorflow.google.cn/</a>                </li></ul></ul><p>            </p><ul><li>移动应用和物联网：              </li><ul><li>Android： <a href=\"https://developer.android.google.cn/index.html\" target=\"_blank\">https://developer.android.google.cn/index.html</a>                </li><li>Android Studio：<a href=\"https://developer.android.google.cn/studio/index.html\" target=\"_blank\">https://developer.android.google.cn/studio/index.html</a>                </li><li>Android Things：<a href=\"https://atdocs.cn\" target=\"_blank\">https://atdocs.cn/</a>                </li></ul></ul><p>            </p><ul><li>其它开发技术：              </li><ul><li>Angular：<a href=\"https://www.angular.cn/\" target=\"_blank\">https://www.angular.cn/</a>                </li><li>AMP：<a href=\"https://www.ampproject.org/zh_cn/\" target=\"_blank\">https://www.ampproject.org/zh_cn/</a>                </li><li>Golang：<a href=\"https://golang.google.cn/\" target=\"_blank\">https://golang.google.cn/</a>                </li><li>Flutter：<a href=\"https://flutter.io/\" target=\"_blank\">https://flutter.io/</a>                </li></ul></ul><p>          </p>', 100.00, 9992, 1, NULL, '2018-05-19 09:40:44');
INSERT INTO `programing_competition` VALUES (29, 100014, 1, 'Kaggle', 'The Home of Data Science & Machine Learning', 'ecc3a89b-39c7-491e-b02d-80bf4b223e68.jpg', 'ecc3a89b-39c7-491e-b02d-80bf4b223e68.jpg', '<p>Kaggle是由联合创始人、首席执行官安东尼·高德布卢姆（Anthony Goldbloom）2010年在墨尔本创立的，主要为开发商和数据科学家提供举办机器学习竞赛、托管数据库、编写和分享代码的平台。该平台已经吸引了80万名数据科学家的关注，这些用户资源或许正是吸引谷歌的主要因素。</p><p>Kaggle公司是由联合创始人兼首席执行官AnthonyGoldbloom2010年在墨尔本创立的，主要是为开发商和数据科学家提供举办机器学习竞赛、托管数据库、编写和分享代码的平台。这一平台已经吸引了许多科学家和开发者的关注，他们也纷纷入驻这一平台。这些科学家和开发者资源正是<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E8%B0%B7%E6%AD%8C\">谷歌</a>看中他们的地方[1]<a>&nbsp;</a>。</p>', 20.00, 9991, 1, NULL, '2018-05-19 09:44:52');
INSERT INTO `programing_competition` VALUES (30, 100006, 1, 'ACM国际大学生程序设计竞赛', 'ACM International Collegiate Programming Contes', 'b4c5e2da-4d53-4c45-ac6f-da713f5ca9cc.jpg', 'b4c5e2da-4d53-4c45-ac6f-da713f5ca9cc.jpg', '<p>竞赛规定每支参赛队伍至多由三名在校大学生组成，他们需要在规定的五个小时内解决八个或更多的复杂实际编程问题。每队使用一台电脑，参赛者争分夺秒，与其他参赛队伍拼比逻辑、策略和心理素质。团队成员将在多名专家裁判的严格督察下通力合作，对问题进行难度分级、推断出要求、设计测试平台并构建软件系统，最终成功地解决问题。对于一名精通计算机科学的学生而言，有些问题只是精确度的问题；而有些则需要学生了解并掌握高级算法；还有一些问题是普通学生无法解决的，不过对于那些最优秀的学生而言，这一切都不在话下。</p><p>竞赛的评判过程十分严格。我们分发给学生的是问题陈述，而不是要求须知。他们会收到一个测试数据实例，但无法获得裁判的测试数据和接受标准方面的信息。若每次提交的解决方案出现错误，就会受到加时惩罚。毕竟，在处理顶级计算问题时，谁也不想浪费客户的时间。在最短的累计时间内，提交次数最少、解决问题最多的队伍就是最后的胜利者。</p><p>在IBM开展的众多学术活动中，赞助ACM-ICPC赛事占有十分重要的位置。此举旨在促进开放源代码编程技巧的发展，培养更具竞争力的IT工作人员，从而推动全球创新和经济增长。</p><p>ACM-ICPC大赛是一项旨在展示大学生创新能力、团队精神和在压力下编写程序、分析和解决问题能力的年度竞赛。</p>', 99.00, 9998, 1, NULL, '2018-05-23 16:37:07');
INSERT INTO `programing_competition` VALUES (31, 100006, 22, '山西省第一届ACM竞赛', '小伙伴们快来爆发你的小宇宙吧！', '117ecba1-23f7-48ff-a085-674f32e51731.jpeg', '117ecba1-23f7-48ff-a085-674f32e51731.jpeg,55064614-e144-4740-91da-d868cfaf37c5.jpg', '<p><img alt=\"timg (1).jpeg\" src=\"http://image.programing.com/7ada2c5d-6060-4902-91e6-6e962dec0fa9.jpeg\" width=\"500\" height=\"375\"><br></p>', 20.00, 9995, 1, NULL, '2018-05-28 16:47:00');
INSERT INTO `programing_competition` VALUES (32, 100006, 22, '美女', '美女', 'eb06f76b-c643-4194-b0cb-2c6a42839bfe.jpg', 'eb06f76b-c643-4194-b0cb-2c6a42839bfe.jpg', '<p><img alt=\"b7fd5266d01609247e516aafd80735fae6cd340a.jpg\" src=\"http://image.programing.com/9d99ec9f-dcd9-4a4c-97cb-7ba9f872d5a1.jpg\" width=\"1280\" height=\"1914\"><br></p>', 1.00, 1, 0, '2018-05-12 21:19:30', '2018-05-12 21:19:30');
INSERT INTO `programing_competition` VALUES (33, 100006, 1, '美女1', '美妞', '577f7cda-ebf7-467e-a19b-dd505bbc69e9.jpeg', '577f7cda-ebf7-467e-a19b-dd505bbc69e9.jpeg,929a3fec-e1a0-41c2-90bd-4b2137bd7dc2.jpeg', '<p><img alt=\"20140921101952_Yftyh.thumb.700_0.png\" src=\"http://image.programing.com/404f1088-c4a5-4dbc-8ae6-57ada6da3e19.png\" width=\"700\" height=\"1052\"><br></p>', 1.00, 21, 1, NULL, '2018-05-23 16:24:47');
INSERT INTO `programing_competition` VALUES (34, 100006, 22, '美女2', '美妞2', 'fe26d43e-2e70-4803-8dcf-109899bfe30b.jpeg', 'fe26d43e-2e70-4803-8dcf-109899bfe30b.jpeg', '<p><img alt=\"timg.jpeg\" src=\"http://image.programing.com/b30aa46c-f6b1-4c0b-b76a-3e68bc1e81ea.jpeg\" width=\"2605\" height=\"2400\"><br></p>', 1.00, 23, 0, '2018-05-17 12:04:54', '2018-05-17 12:04:54');
INSERT INTO `programing_competition` VALUES (35, 1, 1, '修改比赛名称', '修改比赛子名称', 'test.jpg', 'test.jpg', 'detailtext', 1.00, 10, 1, NULL, '2018-06-06 14:50:10');
COMMIT;

-- ----------------------------
-- Table structure for programing_order
-- ----------------------------
DROP TABLE IF EXISTS `programing_order`;
CREATE TABLE `programing_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `sponsor_id` int(11) DEFAULT NULL COMMENT '主办方id',
  `shipping_id` int(11) DEFAULT NULL COMMENT '送货id',
  `payment` decimal(20,2) DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
  `payment_type` int(4) DEFAULT NULL COMMENT '支付类型,1-在线支付',
  `postage` int(10) DEFAULT NULL COMMENT '运费,单位是元',
  `status` int(10) DEFAULT NULL COMMENT '订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of programing_order
-- ----------------------------
BEGIN;
INSERT INTO `programing_order` VALUES (129, 1527063887723, 22, 1, 30, 1.00, 1, 0, 20, '2018-05-23 16:25:08', NULL, NULL, NULL, '2018-05-23 16:24:47', '2018-05-23 16:25:09');
INSERT INTO `programing_order` VALUES (130, 1527064628274, 22, 1, 30, 99.00, 1, 0, 20, '2018-05-23 16:37:19', NULL, NULL, NULL, '2018-05-23 16:37:07', '2018-05-23 16:37:20');
INSERT INTO `programing_order` VALUES (131, 1527064980260, 22, 1, 30, 300.00, 1, 0, 20, '2018-05-23 16:43:09', NULL, NULL, NULL, '2018-05-23 16:42:59', '2018-05-23 16:43:10');
INSERT INTO `programing_order` VALUES (132, 1527142954206, 22, 1, 30, 600.00, 1, 0, 0, NULL, NULL, NULL, NULL, '2018-05-24 14:22:34', '2018-05-24 14:22:34');
INSERT INTO `programing_order` VALUES (133, 1527176721079, 22, 1, 30, 200.00, 1, 0, 20, '2018-05-24 23:46:39', NULL, NULL, NULL, '2018-05-24 23:45:20', '2018-05-24 23:46:40');
INSERT INTO `programing_order` VALUES (134, 1527181598655, 22, 22, 30, 20.00, 1, 0, 40, '2018-05-25 01:06:53', '2018-05-25 01:07:10', NULL, NULL, '2018-05-25 01:06:38', '2018-05-25 01:07:09');
INSERT INTO `programing_order` VALUES (135, 1527207018106, 22, 22, 30, 20.00, 1, 0, 0, NULL, NULL, NULL, NULL, '2018-05-25 08:10:17', '2018-05-25 08:10:17');
INSERT INTO `programing_order` VALUES (136, 1527213374765, 22, 22, 30, 20.00, 1, 0, 0, NULL, NULL, NULL, NULL, '2018-05-25 09:56:14', '2018-05-25 09:56:14');
INSERT INTO `programing_order` VALUES (137, 1527219024790, 22, 22, 30, 20.00, 1, 0, 0, NULL, NULL, NULL, NULL, '2018-05-25 11:30:24', '2018-05-25 11:30:24');
INSERT INTO `programing_order` VALUES (138, 1527497220985, 22, 22, 30, 20.00, 1, 0, 40, '2018-05-28 16:47:40', '2018-06-06 14:35:25', NULL, NULL, '2018-05-28 16:47:00', '2018-06-06 14:35:26');
INSERT INTO `programing_order` VALUES (139, 1528033495860, 22, 1, 30, 200.00, 1, 0, 0, NULL, NULL, NULL, NULL, '2018-06-03 21:44:54', '2018-06-03 21:44:54');
COMMIT;

-- ----------------------------
-- Table structure for programing_order_item
-- ----------------------------
DROP TABLE IF EXISTS `programing_order_item`;
CREATE TABLE `programing_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `sponsor_id` int(11) DEFAULT NULL COMMENT '主办方id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `competition_id` int(11) DEFAULT NULL COMMENT '比赛id',
  `competition_name` varchar(100) DEFAULT NULL COMMENT '比赛名称',
  `competition_image` varchar(500) DEFAULT NULL COMMENT '比赛图片地址',
  `current_unit_price` decimal(20,2) DEFAULT NULL COMMENT '生成订单时的比赛报名费，单位是元,保留两位小数',
  `quantity` int(10) DEFAULT NULL COMMENT '比赛数量',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '比赛总价,单位是元,保留两位小数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_user_id_index` (`user_id`,`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of programing_order_item
-- ----------------------------
BEGIN;
INSERT INTO `programing_order_item` VALUES (146, 22, 1, 1527063887723, 33, '美女1', '577f7cda-ebf7-467e-a19b-dd505bbc69e9.jpeg', 1.00, 1, 1.00, '2018-05-23 16:24:47', '2018-05-23 16:24:47');
INSERT INTO `programing_order_item` VALUES (147, 22, 1, 1527064628274, 30, 'ACM国际大学生程序设计竞赛', 'b4c5e2da-4d53-4c45-ac6f-da713f5ca9cc.jpg', 99.00, 1, 99.00, '2018-05-23 16:37:07', '2018-05-23 16:37:07');
INSERT INTO `programing_order_item` VALUES (148, 22, 1, 1527064980260, 26, '蓝桥杯', 'd8c53aee-5e1c-46d6-bec4-6552403d099d.jpg', 300.00, 1, 300.00, '2018-05-23 16:42:59', '2018-05-23 16:42:59');
INSERT INTO `programing_order_item` VALUES (149, 22, 1, 1527142954206, 26, '蓝桥杯', 'd8c53aee-5e1c-46d6-bec4-6552403d099d.jpg', 300.00, 2, 600.00, '2018-05-24 14:22:34', '2018-05-24 14:22:34');
INSERT INTO `programing_order_item` VALUES (150, 22, 1, 1527176721079, 27, '甲骨文杯', 'b4c388c0-2dc9-4918-b267-9aadfc2c1425.jpg', 200.00, 1, 200.00, '2018-05-24 23:45:20', '2018-05-24 23:45:20');
INSERT INTO `programing_order_item` VALUES (151, 22, 22, 1527181598655, 31, '山西省第一届ACM竞赛', '117ecba1-23f7-48ff-a085-674f32e51731.jpeg', 20.00, 1, 20.00, '2018-05-25 01:06:38', '2018-05-25 01:06:38');
INSERT INTO `programing_order_item` VALUES (152, 22, 22, 1527207018106, 31, '山西省第一届ACM竞赛', '117ecba1-23f7-48ff-a085-674f32e51731.jpeg', 20.00, 1, 20.00, '2018-05-25 08:10:18', '2018-05-25 08:10:18');
INSERT INTO `programing_order_item` VALUES (153, 22, 22, 1527213374765, 31, '山西省第一届ACM竞赛', '117ecba1-23f7-48ff-a085-674f32e51731.jpeg', 20.00, 1, 20.00, '2018-05-25 09:56:14', '2018-05-25 09:56:14');
INSERT INTO `programing_order_item` VALUES (154, 22, 22, 1527219024790, 31, '山西省第一届ACM竞赛', '117ecba1-23f7-48ff-a085-674f32e51731.jpeg', 20.00, 1, 20.00, '2018-05-25 11:30:24', '2018-05-25 11:30:24');
INSERT INTO `programing_order_item` VALUES (155, 22, 22, 1527497220985, 31, '山西省第一届ACM竞赛', '117ecba1-23f7-48ff-a085-674f32e51731.jpeg', 20.00, 1, 20.00, '2018-05-28 16:47:00', '2018-05-28 16:47:00');
INSERT INTO `programing_order_item` VALUES (156, 22, 1, 1528033495860, 27, '甲骨文杯', 'b4c388c0-2dc9-4918-b267-9aadfc2c1425.jpg', 200.00, 1, 200.00, '2018-06-03 21:44:54', '2018-06-03 21:44:54');
COMMIT;

-- ----------------------------
-- Table structure for programing_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `programing_pay_info`;
CREATE TABLE `programing_pay_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `pay_platform` int(10) DEFAULT NULL COMMENT '支付平台:1-支付宝,2-微信',
  `platform_number` varchar(200) DEFAULT NULL COMMENT '支付宝支付流水号',
  `platform_status` varchar(20) DEFAULT NULL COMMENT '支付宝支付状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of programing_pay_info
-- ----------------------------
BEGIN;
INSERT INTO `programing_pay_info` VALUES (69, 22, 1527063887723, 1, '2018052321001004300200354706', 'TRADE_SUCCESS', '2018-05-23 16:25:09', '2018-05-23 16:25:09');
INSERT INTO `programing_pay_info` VALUES (70, 22, 1527064628274, 1, '2018052321001004300200354890', 'TRADE_SUCCESS', '2018-05-23 16:37:20', '2018-05-23 16:37:20');
INSERT INTO `programing_pay_info` VALUES (71, 22, 1527064980260, 1, '2018052321001004300200354707', 'TRADE_SUCCESS', '2018-05-23 16:43:10', '2018-05-23 16:43:10');
INSERT INTO `programing_pay_info` VALUES (72, 22, 1527176721079, 1, '2018052421001004300200357425', 'TRADE_SUCCESS', '2018-05-24 23:46:40', '2018-05-24 23:46:40');
INSERT INTO `programing_pay_info` VALUES (73, 22, 1527181598655, 1, '2018052521001004300200357426', 'TRADE_SUCCESS', '2018-05-25 01:06:54', '2018-05-25 01:06:54');
INSERT INTO `programing_pay_info` VALUES (74, 22, 1527497220985, 1, '2018052821001004300200363957', 'TRADE_SUCCESS', '2018-05-28 16:47:42', '2018-05-28 16:47:42');
COMMIT;

-- ----------------------------
-- Table structure for programing_result
-- ----------------------------
DROP TABLE IF EXISTS `programing_result`;
CREATE TABLE `programing_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '结果id',
  `competition_id` int(11) NOT NULL COMMENT '比赛id',
  `sponsor_id` int(11) NOT NULL COMMENT '主办方id',
  `title` varchar(500) DEFAULT NULL COMMENT '结果标题',
  `detail` text COMMENT '结果详情',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of programing_result
-- ----------------------------
BEGIN;
INSERT INTO `programing_result` VALUES (1, 26, 1, '蓝桥杯结果', '<p>xxx 第一</p><p>yyy 第二<br></p>', NULL, '2018-05-19 21:55:18');
INSERT INTO `programing_result` VALUES (2, 35, 1, '修改标题', '修改结果', NULL, '2018-06-06 15:26:40');
COMMIT;

-- ----------------------------
-- Table structure for programing_shipping
-- ----------------------------
DROP TABLE IF EXISTS `programing_shipping`;
CREATE TABLE `programing_shipping` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '收货姓名',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '收货固定电话',
  `receiver_mobile` varchar(20) DEFAULT NULL COMMENT '收货移动电话',
  `receiver_province` varchar(20) DEFAULT NULL COMMENT '省份',
  `receiver_city` varchar(20) DEFAULT NULL COMMENT '城市',
  `receiver_district` varchar(20) DEFAULT NULL COMMENT '区/县',
  `receiver_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` varchar(6) DEFAULT NULL COMMENT '邮编',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of programing_shipping
-- ----------------------------
BEGIN;
INSERT INTO `programing_shipping` VALUES (4, 13, 'geely', '010', '18688888888', '北京', '北京市', '海淀区', '中关村', '100000', '2017-01-22 14:26:25', '2017-01-22 14:26:25');
INSERT INTO `programing_shipping` VALUES (7, 17, 'Rosen', '13800138000', '13800138000', '北京', '北京', NULL, '中关村', '100000', '2017-03-29 12:11:01', '2017-03-29 12:11:01');
INSERT INTO `programing_shipping` VALUES (29, 1, '吉利', '13800138000', '13800138000', '北京', '北京', '海淀区', '海淀区中关村', '100000', '2017-04-09 18:33:32', '2017-04-09 18:33:32');
INSERT INTO `programing_shipping` VALUES (30, 22, '陈志恒', '15135188999', NULL, '山西省', '太原', NULL, 'nuc', '030051', '2018-05-06 22:35:06', '2018-05-06 22:35:06');
INSERT INTO `programing_shipping` VALUES (32, 22, 'AAA', '010', '18688888888', '北京', '北京市', '海淀区', '中关村', '100000', NULL, '2018-06-03 22:16:49');
COMMIT;

-- ----------------------------
-- Table structure for programing_user
-- ----------------------------
DROP TABLE IF EXISTS `programing_user`;
CREATE TABLE `programing_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-普通用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of programing_user
-- ----------------------------
BEGIN;
INSERT INTO `programing_user` VALUES (1, 'admin', '427338237BD929443EC5D48E24FD2B1A', 'admin@programing.com', '13800138000', '问题', '答案', 1, '2016-11-06 16:56:45', '2017-04-04 19:27:36');
INSERT INTO `programing_user` VALUES (22, 'chen', 'D8F80B67499E434EA61ADAF6E6219BF2', 'czh55@qq.com', '15135188999', '问题', '答案', 1, '2018-05-06 14:54:57', '2018-05-06 14:54:57');
INSERT INTO `programing_user` VALUES (23, 'user1', 'D8F80B67499E434EA61ADAF6E6219BF2', 'user1@qq.com', '15135188999', '问题', '答案', 0, '2018-05-19 10:29:12', '2018-05-19 10:29:12');
INSERT INTO `programing_user` VALUES (24, 'user2', 'D8F80B67499E434EA61ADAF6E6219BF2', 'user2@qq.com', '15135188999', '问题', '答案', 1, '2018-05-19 11:35:03', '2018-05-19 11:35:03');
INSERT INTO `programing_user` VALUES (25, 'user3', 'D8F80B67499E434EA61ADAF6E6219BF2', 'user3@qq.com', '15135188999', '问题', '答案', 1, '2018-05-19 11:44:49', '2018-05-19 11:44:49');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

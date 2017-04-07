/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50622
Source Host           : localhost:3306
Source Database       : websocket

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2017-04-07 10:29:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_friend_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_friend_group`;
CREATE TABLE `t_friend_group` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(20) NOT NULL COMMENT '该分组所属的用户ID',
  `group_name` varchar(64) NOT NULL COMMENT '分组名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_friend_group
-- ----------------------------
INSERT INTO `t_friend_group` VALUES ('1', '106', '前端小组');
INSERT INTO `t_friend_group` VALUES ('2', '106', '大数据小组');
INSERT INTO `t_friend_group` VALUES ('3', '106', '策划小组');
INSERT INTO `t_friend_group` VALUES ('4', '106', '产品小组');

-- ----------------------------
-- Table structure for `t_friend_group_friends`
-- ----------------------------
DROP TABLE IF EXISTS `t_friend_group_friends`;
CREATE TABLE `t_friend_group_friends` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `fgid` int(20) NOT NULL COMMENT '分组id',
  `uid` int(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_friend_group_friends
-- ----------------------------
INSERT INTO `t_friend_group_friends` VALUES ('1', '1', '1');
INSERT INTO `t_friend_group_friends` VALUES ('2', '1', '2');
INSERT INTO `t_friend_group_friends` VALUES ('3', '1', '3');
INSERT INTO `t_friend_group_friends` VALUES ('4', '2', '4');
INSERT INTO `t_friend_group_friends` VALUES ('5', '2', '5');
INSERT INTO `t_friend_group_friends` VALUES ('6', '2', '6');
INSERT INTO `t_friend_group_friends` VALUES ('7', '2', '8');
INSERT INTO `t_friend_group_friends` VALUES ('8', '2', '9');
INSERT INTO `t_friend_group_friends` VALUES ('9', '2', '10');
INSERT INTO `t_friend_group_friends` VALUES ('10', '2', '7');
INSERT INTO `t_friend_group_friends` VALUES ('11', '2', '11');
INSERT INTO `t_friend_group_friends` VALUES ('12', '2', '12');
INSERT INTO `t_friend_group_friends` VALUES ('13', '3', '13');
INSERT INTO `t_friend_group_friends` VALUES ('14', '4', '14');
INSERT INTO `t_friend_group_friends` VALUES ('15', '4', '15');
INSERT INTO `t_friend_group_friends` VALUES ('16', '4', '17');
INSERT INTO `t_friend_group_friends` VALUES ('17', '4', '17');

-- ----------------------------
-- Table structure for `t_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(64) NOT NULL COMMENT '群组名称',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '群组图标',
  `create_id` int(20) NOT NULL COMMENT '创建者id',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_group
-- ----------------------------
INSERT INTO `t_group` VALUES ('1', 'Java群', 'http://localhost/static/image/group_default.gif', '106', '2017-04-07 08:51:50');
INSERT INTO `t_group` VALUES ('2', 'Scala群', 'http://localhost/static/image/group_default.gif', '106', '2017-04-07 08:51:50');
INSERT INTO `t_group` VALUES ('3', 'SpringBoot群', 'http://localhost/static/image/group_default.gif', '106', '2017-04-07 08:51:51');
INSERT INTO `t_group` VALUES ('4', 'Redis群', 'http://localhost/static/image/group_default.gif', '1', '2017-04-07 08:52:01');

-- ----------------------------
-- Table structure for `t_group_members`
-- ----------------------------
DROP TABLE IF EXISTS `t_group_members`;
CREATE TABLE `t_group_members` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `gid` int(20) NOT NULL COMMENT '群组ID',
  `uid` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_group_members
-- ----------------------------
INSERT INTO `t_group_members` VALUES ('1', '1', '1');
INSERT INTO `t_group_members` VALUES ('2', '1', '2');
INSERT INTO `t_group_members` VALUES ('3', '1', '3');
INSERT INTO `t_group_members` VALUES ('4', '1', '100');
INSERT INTO `t_group_members` VALUES ('5', '1', '105');
INSERT INTO `t_group_members` VALUES ('6', '2', '1');
INSERT INTO `t_group_members` VALUES ('7', '2', '8');
INSERT INTO `t_group_members` VALUES ('8', '3', '14');
INSERT INTO `t_group_members` VALUES ('9', '3', '18');
INSERT INTO `t_group_members` VALUES ('10', '3', '23');
INSERT INTO `t_group_members` VALUES ('11', '3', '55');
INSERT INTO `t_group_members` VALUES ('12', '4', '106');
INSERT INTO `t_group_members` VALUES ('13', '4', '1');
INSERT INTO `t_group_members` VALUES ('14', '4', '2');
INSERT INTO `t_group_members` VALUES ('15', '4', '76');
INSERT INTO `t_group_members` VALUES ('16', '1', '106');
INSERT INTO `t_group_members` VALUES ('17', '2', '106');
INSERT INTO `t_group_members` VALUES ('18', '3', '106');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `sign` varchar(255) DEFAULT NULL COMMENT '签名',
  `email` varchar(64) NOT NULL COMMENT '邮箱地址',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `sex` int(2) NOT NULL DEFAULT '1' COMMENT '性别',
  `active` varchar(64) NOT NULL COMMENT '激活码',
  `status` varchar(10) NOT NULL DEFAULT 'offline' COMMENT '是否激活',
  `create_date` date NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '皇纯', 'a1adf01f11fb8ee587bdb3c1c85b1db625058cd403caa3bb0fb2556ae36e2638511f3e2faf1ae340', '一辈子只有一个男人，并不丢人。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'c3775dcb9dd343838b52c147f12d253832b42457f9564ca3b480be9b088a9ec9', 'online', '1997-03-21');
INSERT INTO `t_user` VALUES ('2', '羊柔宁', '8b8e45d885ac5942ed30970968e8dc7e64d16f485f7611745a08f95a3715a31ee3d2f858c071aef4', '带着你的贱人、离开我的世界、我成全你们不要脸的幸福。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '19aa65a01ffe48b082dfc99b9c147fc08e0833d101a141048be2488e81e5c631', 'online', '2005-10-05');
INSERT INTO `t_user` VALUES ('3', '华士固', '9823377c697bddbce2c9395c013c28078fab085faeb27e6635a04f452366ddf44649b125264735ed', '以友谊的名义爱着一个人。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '977beeade9c14b64b0b4d6201636627c2a8645fe6d8040b1b117ffa9a95da580', 'online', '1998-05-10');
INSERT INTO `t_user` VALUES ('4', '齐楠友', '731900e455dced18fb17551deb6022205a0e5bc8f9e64c74872d786637c6627509728be94ab83a8a', '藏不住的心情，改不掉的个性。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'fe13a551071149cdbce8b15572e6afa5e47dbc9c50974b22b1f060e95f25af82', 'online', '1996-02-25');
INSERT INTO `t_user` VALUES ('5', '范建胜', 'a656bcd5bfcafcbe27f4ef12e835134e258e609aab82f54767123584347eb89e5b4f87daf5bf4917', '我拿你当人看，你却学狗叫？', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'c4415c533ecd44a88c88670c3c43df7eecfba982356b4336a2c42dc8fd7af14e', 'online', '1992-11-07');
INSERT INTO `t_user` VALUES ('6', '冯海斌', 'b5ff7767228158692a41ba5ac37c84c3f9669cc81e1b5d9a64de6be611979d0a2df1be65aaa71d5a', '最怕在用了心之后得到的是背叛。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'aea9f988feef45bf88f8bebc4768aff67aab6c3661214aaf8486b723e17ea4bb', 'online', '2002-10-11');
INSERT INTO `t_user` VALUES ('7', '祝清', '0363842ad5c79ccc42dfb00e04d05627faa38556f7f571c21b9b9c46cbb417808dcff3ba3f08632b', '你最大的报复就是活的比你幸福。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'c8b4edeac0124a589abae36304e606078a7a93864c6544d498d4a09c7e0eed9b', 'online', '2002-02-22');
INSERT INTO `t_user` VALUES ('8', '季菊娣', '53a9d2dab61d4d8b61c3f692a537544cc4ed2152ca2958e0d02dc553e6abc93b63595183b1615f5c', '有木有人早上不想起，晚上不想睡！', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '0882d23380934b81b2c6cfc3367de424117125f95ee74d99be25c0816c1fd0fc', 'online', '1999-08-17');
INSERT INTO `t_user` VALUES ('9', '平泰飞', '7a7195fc90a6d5568338213f21236b74d11e013bf70c0dd65f76735a1e4c06147784749d7349ee2d', '多少人被一句“我们不合适”打发走了。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '6507cfea107b415c9f606fb224d437b959201e44d2b740e8a7547f59a171c23e', 'online', '2014-10-07');
INSERT INTO `t_user` VALUES ('10', '裴欣', '84bf11b6bf28172660151be56f7ef4221c55f009de46527bfb0e08b163f339948d6090b9b9fac9f7', '不拼-不搏-人生白活，不苦-不累-生活无味。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '705eb077bb8a4dd1af1cc686ba559a0d7626a08c5c5d409785c4ec0ae7ab4973', 'online', '1998-12-13');
INSERT INTO `t_user` VALUES ('11', '井榕', 'e6fd3ef80e6094577f91ccf35e6065ec5919bc946a3277d837abc6f975dc2d6578ef60e0ceac7962', '纸糊的爱情、一撕就破。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '10df14d03b3d4aed9be2d8a0097a1669aa4dd206013e419e8bd82b5d00ddf8fb', 'online', '2006-12-28');
INSERT INTO `t_user` VALUES ('12', '郎晓', '5284cacfc926e21a813d48ad3ff22f282c5ee90e841a8e28e3abb7b286d367e5e5579e425daa5b80', '勉强笑着，只有自己才知道自己有多累。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'cf333880704c47a7b7691dc703dceca1bbef621122ae4861bc7e62fcd1c3193b', 'online', '2014-01-18');
INSERT INTO `t_user` VALUES ('13', '冷泽家', 'c98c318612461ab223ede3bf68b70a7231ec8243328ff0e5ec851fe878c5fadeabaefce53ee5487c', '很多东西不是我不在意、而是我在意又能怎样。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'a901151de1754040b4773710386077ffe8944dc78d9a43a1b3f66deace62cb23', 'online', '2002-08-02');
INSERT INTO `t_user` VALUES ('14', '禄艺蓉', 'a0d2427b7c66433bec36acf3910f3c41075b9f2653b9b07ea9475afefc30c8844ddd9e36d931260e', '男人这辈子最值得自豪的一句话是：我老婆在家等我吃饭。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '0be6a72133fb4f6ca69e41f056afa740fa6922799db4492ba523bd8193778f88', 'online', '2000-03-12');
INSERT INTO `t_user` VALUES ('15', '汲达', '6e96bcea824245086379d41710d677a610fce431f67fd066f133469e088632b36787a42cecd8345b', '我会用我牵强的微笑活出无人能比的高傲。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '3d1af214caaa41b2bc01c45f327d515bc1c13d0b64fc4e43a6110632779bf6d1', 'online', '2001-10-03');
INSERT INTO `t_user` VALUES ('16', '仉友利', '5dbbb9bd5a7598c775043255b850a3a5565c16fc67208e33804e6b9f5f7f38642d18d011d0819575', '中国移动、一个有着营业执照的强盗。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'bb62184f567f41f79135e1e1e1cc1dea1c055af29da04fcfb21270466de67829', 'online', '2004-08-26');
INSERT INTO `t_user` VALUES ('17', '董洁慧', 'fd601962f0313bfdc5361a8ed4bf7ea6cba8d433dfb4c6f1eec27d62404430d7d7cf636a7d69f2b6', '唐僧骑的是神马，悟空腾的是浮云，八戒爱的是小月月，沙僧装的是犀利哥。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'acef798ac4f6467691aac97e83525666db6398541ac344eb8c3eff8b04741231', 'online', '2003-04-26');
INSERT INTO `t_user` VALUES ('18', '司策', 'dcea3a0c29c1e59df4772721a8fe14a46b4d4d3605e8e51dcd4e1976ac46fa94f52d046306eab8a5', '我把10086改成你了名字，每天能收到你的短信，骗自己你还是很关心我。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'a8ae619c87b7448686ca1eaf089c5d4e73566b1197c64e7d92abbf8cc43f603e', 'online', '2002-07-23');
INSERT INTO `t_user` VALUES ('19', '呼龙邦', '44f49696e5d870864d825ddf42d64af0dc15dc6edf7f4e760523bc1de7e15dc8564022fac6ea340d', '小时候认为流血了就是很严重的事，不管疼不疼，先哭再说。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'e047f421785044799c5d494470be16e13cbf4d1f8d9b41508ae321e1d2b33a8b', 'online', '2011-10-20');
INSERT INTO `t_user` VALUES ('20', '颜雅馥', 'f6e6f15ae865027b947bd0a4c0ab1d3031ec2284b57a1a5f15d0ff380022c36a53d155dc3f11379c', '突然发现牛顿好给力，拉力、浮力、推力、摩擦力，重力、压力、阻力、支持力。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'ee816b8aecd449ac80daf28691743e850d213645d91d48b59e178a2766659489', 'online', '2006-11-25');
INSERT INTO `t_user` VALUES ('21', '赫静琦', '0200e4129c724dec85cc5a35506ff63ba0ae0747edee21afa4442b6dd72c27202e3384e2bd469aac', '化妆前，他说请借过、化妆后，他说美女我们是不是见过？', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'a7b1d6a7b9e947c6b4b970035edb14f7a1277ea7cc034c40a5c7180bbc50e25a', 'online', '2002-06-23');
INSERT INTO `t_user` VALUES ('22', '熊成伟', '97032242bf1494d410eaf8b202564b936915efb073673307bc554450113348069729fa0a7c9f8176', '命是爸妈给的，珍惜点、路是自己走的，小心点！', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '4feff554f1b64a3c8fe9ce620458bae1868a0e9d0a0e4585986dedeb68657993', 'online', '2015-03-27');
INSERT INTO `t_user` VALUES ('23', '沃媛', 'e4fd336ae08f7f849ea3b7c54c762c893bb707ceacb782fa81127170f0576d1be3a8e9923c771250', '幸福就是，在没钱的时候，在旧牛仔裤里发现皱皱的100圆。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'aafd1f54ee4a4d49a2e5df55f7a222fd23d2bce666994a2793e0632deaa1e9c6', 'online', '1998-10-03');
INSERT INTO `t_user` VALUES ('24', '訾露', 'cbe1a49a31b4ae66bb645a7d28f1a2491aa8600ba2ee4fb50da508635e93017f731d60acd3091c46', '看上了、追求了、好上了、开心了、不久、腻了、吵了、淡了、散了。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '468e71e1518c44d995ce2df52b4809f27e28732c2b2d4528a17b51f508a61418', 'online', '1996-05-15');
INSERT INTO `t_user` VALUES ('25', '惠菁', '0e6049d7d70618bc275009f66d543550a02c2c01c43f3c033acc43e73cb7950e80c6474cfd896d50', '有些事、无须强求。有些人、无需强留。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '1d3512713fef4e628cf398f528d5e6f16f26f7f187df4bbf9ba051f190bc9adc', 'online', '2010-05-29');
INSERT INTO `t_user` VALUES ('26', '单欢卿', 'e11343ffd0ff5dc56c322018853df7dd74fb005c92c969d44777ec196f3258de8046dabd7cc15d2b', '心烦时，记住三句话：1、算了吧。2、没关系。3、会过去的。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '6774847499cc4ac581edfdc990c3605aec4b289a71bf41abaebd7a8f9366f366', 'online', '1991-08-18');
INSERT INTO `t_user` VALUES ('27', '督娅晓', '0e55a056ba7ff17bc8180e37645361d9faaad30da7339edbbd7d159aa63c95ef8f0545e347b69835', '蓦然回首发现想找个聊天的人都没有。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'fc0f42bfdf5843f08a4c46b8184f915fa4aee1d825c64b3ca17d5e301e1312a1', 'online', '1996-04-02');
INSERT INTO `t_user` VALUES ('28', '裴奇震', '035bce9d67f88b0aefeb132c99b0e1730a6167aadec9c7221781a0bd7ec2e82c269cf67034d085c0', '我小心翼翼维护的朋友、翻过来咬我一口。呵、心寒。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '0044fe87d12e475da9bc7a4731865ea2843bc4953edc4035809db28326881dcc', 'online', '1997-12-02');
INSERT INTO `t_user` VALUES ('29', '廖叶', '02197a3fa27fe8a75ccd19b184f2989b54a7b775df782bb87324c2ef9a9ec2790336421f9a9e3395', '所谓的母校，就是我们一天骂它一千遍，而不准别人侮辱它一句的学校。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'c6a54ffcd6fe49e69f5374cdddce5abe714f4a3cfacc481092ee8ce35715320f', 'online', '1992-04-16');
INSERT INTO `t_user` VALUES ('30', '蒙峰泽', '9d38ec33d57c4744ba604765cd29c4a2b1d0bba4a9313904df6cd6b43027805782c76e8e4459489a', '这社会什么都可以是假的、但我不能容忍钱是假的。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '4fb96fcf8a3b494181e69ad5629ed348a14636356c874c91b5a77392c7b013df', 'online', '2012-07-21');
INSERT INTO `t_user` VALUES ('31', '莫梅琴', 'e4b169b633bf03b03c09f712ae70a51c350ceb9fdbb05f31f3ee7518c1fcdde6e781eca99ca71788', '最体贴你的是鞋子，不要嫌它脏。那是因为你走的路不干净。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'dd4b73e80bfa40ffa029fec50e65a15b9d45ddf201024b9eb5a45a2d8c9c668a', 'online', '1990-05-17');
INSERT INTO `t_user` VALUES ('32', '何莲蓉', '95b8c5681342df0907716542dfd0e7796ea2e934fd2230648d0323e7f2c86e86bb379cef374afe11', '某些人某些事，我感到抱歉、某些情某些爱，我感到无助。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'd835df64bead4e6897024be3d1844600cab42eb75e9c42d486dd7d0a3ad055cf', 'online', '2000-07-26');
INSERT INTO `t_user` VALUES ('33', '戈庆和', 'a491cf3fb2b085b70dbbba04fa97a177cb2a61331be1c3f70e644a50ca94baa77555f842ab254f89', '每天有50%的时间用来鄙视自己，另外50%用来原谅自己，这就叫纠结……', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'a04a8d4dafd34192a68b24c941f64c83145c16007d834cb4b6412226b2663859', 'online', '2012-12-06');
INSERT INTO `t_user` VALUES ('34', '滕顺会', 'c2483a80c0a9289afa273b17c5db2a47e5584036c85560d0771e82cfeee2558c87a7833634ebf6e7', '有没有这么一个人，你无数次说的要放弃，但终究还是舍不得。', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'cf036ba7ce9544b8bc3ecbaa951abb876f4c39d6663b4f08a770f871e4c58e05', 'online', '2007-07-22');
INSERT INTO `t_user` VALUES ('35', '那博善', 'aad409242dfad8365ac79a25b03b014cfdf18460d379d24c870ecc9a89ef967df2b3c2bd7f9b633f', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '1729a92206d0471c9e75c777a94c3fe2a37fbce5ba3f4f70a4de42216c097e35', 'online', '1995-02-16');
INSERT INTO `t_user` VALUES ('36', '欧旭奇', 'a3aa0b6838cbfe81b49bb6acc7d11ad744ad1339c524ecb59651a73034ea7d59f15eccee484ee4bd', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '207414ec2c1c46c48da960a543d53f954e48fcb01ff34335b50ee11c90da48a3', 'online', '1999-04-25');
INSERT INTO `t_user` VALUES ('37', '贡枝', '0d1188fac37409e1d340d8c7a5d76538c2e893959026889dee92dac2f17155dafd5c7465a6e51a08', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '4a7dcd7d437543eeb907ef5c8d1a0dad2f62624eb1fe4e1c9e6948079f604d38', 'online', '1995-03-06');
INSERT INTO `t_user` VALUES ('38', '暨东', '16793b885d2a7eba68f22611cad7464a0fbb5dcae8ddb0987e0778af30731f49c34066081eced406', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '87c624060a074b8d82c8b0a696512f1b0bc8e5d6613d453dbcf1de12950fc949', 'online', '2002-05-01');
INSERT INTO `t_user` VALUES ('39', '红玲', 'b30ee39fbe4f86e933649d4aff48672ce5620671f7c3f4cd2b3de3ec6faea2b454e83a4e528c412d', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '1bc1e9abcb504645ab4ec1bc142c5000bcb6bfd286c644f087d7380b2650cc03', 'online', '2015-02-07');
INSERT INTO `t_user` VALUES ('40', '苍鸣', 'eb04239db68b7abacaa5593c9cac8d7f580cdd680fb9cee7e93756f4efc0ca4decb839751df2de5c', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '645d2dec5111491db32135c49446df02f5edf12a88144259a6ec60c20452598a', 'online', '2003-12-30');
INSERT INTO `t_user` VALUES ('41', '长健行', '363e7caebb9580d55fc028ebe5b9465ea70202d660bfda60979d5a90e0b67f699c50e149c8814c6e', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '8a3fc99deee94173b59c8856ccbfd69f93e4d989855b48efb073682566d035d7', 'online', '2007-07-08');
INSERT INTO `t_user` VALUES ('42', '郦馨桂', '91185c507cc0cf9b3ff701c7e59429bb9b4e1d6df3cc046968755795a7234fefd6af40588b88f7dd', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'f92649eb17a0436e9c178b8222e447c5a24e6125920b410ead44cc14633f1250', 'online', '2012-04-21');
INSERT INTO `t_user` VALUES ('43', '顾维子', '9d2e4a70a15dad9d92b064f3bdc9ed9bacc8d1655d1387eb53ce17491c60317db131c53981c2cbee', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '705bc8cc5272456cb0e821ee72da48bd85beece6572142bcbd77aaa9203d14ff', 'online', '1996-09-09');
INSERT INTO `t_user` VALUES ('44', '充瑗', '74f54ce703c85ae7146555422c11e9cc408160174d51978dcfb1305ca0113ac5190b254417186e13', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'ade5e8a5f38a4715875d6c57c33ab1f79b78ef7816aa431f8cd2e4193066f7d0', 'online', '2009-11-28');
INSERT INTO `t_user` VALUES ('45', '於华', '100b16e89b4293006471e93d6604ab9c7c7c4d0174cf105e5164fb822f46632b537937f73620474d', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'adca6790f3bc44baac7bb4d480f2e28d10c35f0fd83c4e6c9f933a0f700276b8', 'online', '2005-05-30');
INSERT INTO `t_user` VALUES ('46', '明岩安', '7503875a2e156c16cffc7c206b90e9b695cb4474e822fd97ab6da04202acc26acdd641fd73c8177d', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'b0b9b08d238944308399078de266f4df519f9bb10d0b43fda00b3a6b1a225cce', 'online', '1995-02-26');
INSERT INTO `t_user` VALUES ('47', '五宁', 'ddeecc8d1396a9fb209b44926d4fd1663738dec23f96bf119663772bbd0301c36e26f1aa5eba39bc', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '53f09ed5839344a8bb400fd43d64bcf308b48990e0fe4917baeb63b51759bcb9', 'online', '1990-03-06');
INSERT INTO `t_user` VALUES ('48', '冀婉', 'f362f5fed4cd0610020584db60aebc2c0d114b1577d79e2596f15baa01ea0edeb94a8267e89861c6', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'ccba9b508d9f45b58f622153042473fa7bba9743885d483eb56573d5922c8eea', 'online', '2013-05-13');
INSERT INTO `t_user` VALUES ('49', '阚咏', '0e71ae17403346665f00109c281156fc90224d725719b93860d273e6d71601882b5aa8b45326d8e7', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'bbc4d1128da840929616cbe96d2c7821edfc52bed9cf4687b4977c7f6621950d', 'online', '2009-01-06');
INSERT INTO `t_user` VALUES ('50', '缑丽', '8e72494b446037ed3681e2baf8ea48d49909b58a7f94d4d43a143c41ac2711a0f80391488ed1ecad', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'c426213434104bfe95218a745c153e91b42651f2b79746f7b8755e85b1c26ce8', 'online', '2014-03-30');
INSERT INTO `t_user` VALUES ('51', '却蓉', '29925c5268ab65619b7e8883545e07c67dffdf243c35933f134d9250078a7a7f994865254bca4b8e', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '9f3589c8398f447a8570a134ff0ae4c258c120cd35a44d5e9096d1536bb3b901', 'online', '2015-11-11');
INSERT INTO `t_user` VALUES ('52', '蒲蓓', '32defb0b29aea3de7e58ca04a6fb06a468cc9ae2165ba02b2411871f0c07a5c25366c70d4a1c5e33', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '845d19a18a9f4a5c972c3519ba9b82db422eb2986d9f49fab0a96b0727ccf551', 'online', '1991-08-18');
INSERT INTO `t_user` VALUES ('53', '喻思珠', 'c5177ceaaa57b4e071069d6aa1afac6919aeff2c0e8195f863eaf6aac1c1bfe44186e20ce57bc0a9', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '3245cfa347a841edbe12d2d1297b0a8472f972adb78942a78657c7bcb3891350', 'online', '2004-01-30');
INSERT INTO `t_user` VALUES ('54', '侯钧', 'ec0eb6f1748ea260e5c6bbee033fe62202c8c1d1067f01291e4de8828cce444d01fb0f33b0f1f11a', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '956cd9f460d14b3798f743a0ca1b3c987e197e78661e473bbdc539be7ec3e7ac', 'online', '1992-01-23');
INSERT INTO `t_user` VALUES ('55', '乐建', '1ec2a12685fae2b9c13cd6bc9c177b530786baedc7f52b252542684730d1c3e9486e495478918545', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '87b674ec8d044406a0c77728094a464dc3cea04cc4944453b22a689c9e89d554', 'online', '2009-05-09');
INSERT INTO `t_user` VALUES ('56', '欧旭', 'cdbd6d5b0e7ec6c0c468a32ce4da2d6cdd82b00e427bc44503873cdf92d8c7c61fb96cc75456d9be', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '18f08d36978640bbb0b225c5480fc6b4add30cb52697429c99800fa15fb3ec17', 'online', '1992-06-21');
INSERT INTO `t_user` VALUES ('57', '叶巧', '027f11f4fdbae92e35a90297c0150a43d6eb55c3f358a64c9c211b0608721c0bd7e041d3ae04b17c', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '30c996b514c742878335e34573e984c9c3c39fafd61449db8b2aaef611454f4a', 'online', '1992-06-28');
INSERT INTO `t_user` VALUES ('58', '郭山', 'eac35469ebb0e2ee124e752e7a36c633736e6701bcee840b083730cbd1d28d7a88b307fbfdd3f831', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'c72a8c99e89b4f2d96665f27339f1969d0d195b033d44d8db51689b78f0d12b9', 'online', '2007-06-09');
INSERT INTO `t_user` VALUES ('59', '秋元泽', 'e5777355d20fc32db6f206a93b1ae40edf962a87d4f01df49b905ae97f46313bb46b198e7746391d', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '6e4738260ae64bfc916f7d6f5625717a32b7b5ae20b24deb869d556cd3f7f593', 'online', '2001-06-08');
INSERT INTO `t_user` VALUES ('60', '孔妍', '13c843d2315aa0be4710da23ea0ab4b801a4c412c48c7c8526114cf176ddd21f729fb1526a3b052a', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'ceb9bec9806040ab8db86c63fd765ac469fc914218bf4d35a45a04c3e9826bd8', 'online', '1995-08-16');
INSERT INTO `t_user` VALUES ('61', '向若', 'b8432f6eb81c1cda36a8f3d85d2cd8cd26fb53e03049a0244162d3b357da9776c06004c054913b9b', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '5ba2e42d9e384deea7285f0bb2f586170f00c66f08b34216b418bbdbfb2a33eb', 'online', '1993-12-19');
INSERT INTO `t_user` VALUES ('62', '成婕枝', '5d424578528a879aa5eb2a5fea9c5f20526afeb0b657d025ac4d344a3bc616438bcaca9b4fcf2029', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'a080473c787440e2ac46c4355a433065ed83bbf117ff472ebef434e20047f5bd', 'online', '1998-04-29');
INSERT INTO `t_user` VALUES ('63', '空亚滢', '44bd9f26eb11c911ddc58161de5b5fc1f7ed9283c22a5ee7725054a645958f579bcb7c7acf8c0fef', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '30ea2300d4ea4f0c957866865938959162c2c0685857457485ebef298a590f32', 'online', '2013-07-03');
INSERT INTO `t_user` VALUES ('64', '俞时和', '30240cdc00864fd7633e625ed43a640db5071306dbdb9af2b93e0fdb390e6508e06a68ce85bea2d6', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '7528d90429fd4faa82c124b3b1bf2225b33c81c5bdaf4a2e8ae7a3c74f53490a', 'online', '2001-05-13');
INSERT INTO `t_user` VALUES ('65', '于中', '63d56cb387ec2dd9a6bd20084a56b03ee766e534fd7aefcd090f5ce5568912d8c9405e23a5c3742b', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '49b56a6cea624b67a8947966fd8129c18716bd2aa252409d9fe915cf5dcdf505', 'online', '1996-10-01');
INSERT INTO `t_user` VALUES ('66', '云娜', '6162c15e6b535e95796698c0d00c0b7ad9057515652b2060717cdd3ab959379d0deff39b14e7b35e', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '9adbc0f52cd64bef920f07e17009e54b5e815722542842538c11268a47a107c0', 'online', '1994-01-12');
INSERT INTO `t_user` VALUES ('67', '郭新', '71043def72d42d4411646c59f05b84d0e096ce6a8c51c84a8a55c7317ec5a8a2fcae563ca82a6717', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'eaa5f292216e473aa36de6e3fc92dfdeeeb78703aa4140309f0fe4480c7c354a', 'online', '1995-07-31');
INSERT INTO `t_user` VALUES ('68', '严澜', '1ba908c72dd4504ad97fa41cc7e52b958c0bc40a3514c65cb28992b6533433e15631556c2ced55ba', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '8e49d9bdcbbc48628cdc8ed132e888f9bbc7dea32d1a4a9cb2ec23486b70fc88', 'online', '2004-05-11');
INSERT INTO `t_user` VALUES ('69', '狄会诚', '22d3661de2a3cf427992240f406fb56907083973f7423a6cbceeb409f01f7f74b929afab1751ed62', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '35755b32b44140dbb4a65dcb1d1a53e0263d274a3af24f4fac936e3dbc89e118', 'online', '2007-08-10');
INSERT INTO `t_user` VALUES ('70', '秋锦可', '45a94400bf350fc6236e7072c628775dbaa6baa57b7d8c4a5f433f79b0aa2d5a011aee7d19dcd257', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '34d16f6da244489799734519a3003f46287194af8cde432a82d8df487c87f3f8', 'online', '2011-08-08');
INSERT INTO `t_user` VALUES ('71', '竺彪', '7ecf91873a09f61ba35a0a49fa26d493405cd8ae82f586075a39c339fc7d244a22cf4dde60f78c99', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '43bd70dd5d164fddb43431e653a4b3c2c07926ce8d4f4f05af692e6adf543408', 'online', '2003-12-11');
INSERT INTO `t_user` VALUES ('72', '屠明', '43b4902bee215a38d02ac351c81d3900343d7ceb7ce8d06ffa1d5cfcb59f9abab05c3e7bf12f709a', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '5c2adcc5d6354247940a800f6ffc00b90e316876006c47a9ac349a5ecc14ef86', 'online', '2010-08-17');
INSERT INTO `t_user` VALUES ('73', '鲜进', 'fb17f204f7f3e6e7be992dfcf2479d64f517bd018ea4d03e8593bb8ea59b8d249cffb138a69d2138', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '2855c3f581d14fb8bd6f0db4f2609678506ae81203864c4bae1ef7fd4c8feb3c', 'online', '2010-06-05');
INSERT INTO `t_user` VALUES ('74', '梁伦亨', 'b41b005038aca00674f630a786a826c7371c036240d02dd37a1eb34e51286170d3f8df18544b4ce1', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '571a2f612566405bab71bf5917197dc2f1e8309d4f7249afba43d8cd40b7950b', 'online', '1992-04-10');
INSERT INTO `t_user` VALUES ('75', '吕功勇', '05764e6ee3f9e397213d78bc7e10c13fa9b1d9256342d6f411eaa54aea08a1dbfdabb942ee49d6df', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '1f577e259dfd42e5ab826ec3e8f4f87dd17215251526477e832d70ac944b6d91', 'online', '2007-06-02');
INSERT INTO `t_user` VALUES ('76', '马慧勤', '439512e1f1c293b6cfd231a218b7d4fdc396557851c5bc08f7ece92d74b04f3efa42a6999096f66d', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '77e32f6a911b4f0ca501411d6a4857868d506091039b4e63900775c38d0a4e84', 'online', '2008-06-18');
INSERT INTO `t_user` VALUES ('77', '空莉', 'eb9d94fc20af9cc08504b6d3f95ad94763ee4a860119a4ad7e42b1e7c740a8ae5cc71f4744b59dd4', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'd727145fc41347bebe991a49045118a292c5883b4d96448c8b51f6ccb8743ce3', 'online', '1990-11-23');
INSERT INTO `t_user` VALUES ('78', '能玲希', '40471ffa1d3c5f71c807d07593ae92108c85a8d95888de0d16c05eb11627ce82e1bb048d22252c69', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '81bc6caba3194745940c3d9b0bd3a654e38c81eafe514044ae3a1685478f677a', 'online', '1995-12-25');
INSERT INTO `t_user` VALUES ('79', '西巧云', '491c7e97fb5660e0fd162221f2835959e5c7df3d9e8b2699c2c98074aaa77a68b0a988d90266a98f', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'b78fc4fb4ebd4a9fbecc8b58747a3530df1a23d1c3414112b559e613da0cbf05', 'online', '1999-01-30');
INSERT INTO `t_user` VALUES ('80', '姓毅', 'c47e95ff3c17d6c9e0059a8fa09f242ea761417a6e79d6c4bd1af4bde39c1c319152411dffac99f5', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'dbf6e4c99875425e81aa36b9b852bc8e9e18851b800a4b3798eae85c5f752c04', 'online', '2011-11-25');
INSERT INTO `t_user` VALUES ('81', '徐艺枫', '73765b07822107d0731f7a5ffda47eb93a39a25d81ba754e30cb64f28ea3f81621452d9d94e133aa', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '636c13f15d244366a255410a485ed0fd61179a4ec82640d7a86cae43d73b6180', 'online', '2003-06-11');
INSERT INTO `t_user` VALUES ('82', '姚诚旭', 'f10e3c5e7da3d580779d322f8aff4e07d763dd126604b0fc4e750ccb40b47e7220dbb6aac64729cc', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '2c132d1205db4621b16865c060b4621c4893a9f825014360b88fa943c91392ed', 'online', '2000-12-13');
INSERT INTO `t_user` VALUES ('83', '梁璐', 'e916401b4cf9d90f3b82f10e465e670181db80db98ab2e7159d31a3dbf0cfca0620c91f3a825ed1d', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '931f8541b42f45ff902ea7822a606946aab463b66de843698b22177334b0e533', 'online', '1995-07-15');
INSERT INTO `t_user` VALUES ('84', '甫学', '682b872c6d9ce02932dda0b5830aee058f4cd4d99d25fdaa846b7cfea49fbacded309d0c64b78295', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'b3ef1badbc36414ebaea8f73361a805634028776cbe942779d831e998ec1f8be', 'online', '2013-10-05');
INSERT INTO `t_user` VALUES ('85', '严叶思', 'dc87ba0b59439ee7b1145631a571d878f1d493646a46dc0bb7b86335025eb428fddcafe73bf0df61', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'c98b15e649da4ccdbbcbcc8dd5406c348552d89658134caaa198e464cd0a699f', 'online', '2007-04-05');
INSERT INTO `t_user` VALUES ('86', '邵良勇', 'bd4821e7cd4db73fd137ed4223d9673341aede816ee3983340d093142946078d9ecb283544d93019', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '5c4d4bc3179e48a79cbbbbf1d892097df5875c2f64af4b3492bc560109538604', 'online', '1998-12-13');
INSERT INTO `t_user` VALUES ('87', '方钧腾', '039d4ddf76d2c979ec447ec82ead294e1a2ec3b51c24132ce83f40580f597446c33d06cdf68df834', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '53a57518197e40b8a9e28ca985ea4867edec9ac2ab614e348c215dcf48de539c', 'online', '1990-02-10');
INSERT INTO `t_user` VALUES ('88', '汤功康', 'd7af347524267b4d688fae73bc6b9b5dfebdce074c7756cd2fc0ba09b1f3a5672243d8ad19d08ad9', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '91c81ff6962e47eea684b891df94551e4a7e39ea43af410a96557749ed6f849f', 'online', '1997-03-05');
INSERT INTO `t_user` VALUES ('89', '公莉', '02f3ae84ee34972c2d6fbaebed3609f985c07fe7e09a6a262197ed11107c389b67db1fa3f919f542', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '754e55aefb6d4e3da8de2e9be06c4684f3b773daf9e144fdbc09cfad9136e58e', 'online', '1999-02-04');
INSERT INTO `t_user` VALUES ('90', '骆丽 ', 'd2f5690a6c9dfc6255339661c273dcb0534629df0af31be84054dad63f6df6cd276a21cf6f3fdf18', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'f7907b06c4c540a2837ee80cf14af54c114f7993556f4afe963194f9a17e7c28', 'online', '1990-12-25');
INSERT INTO `t_user` VALUES ('91', '连芝霄', '42a42b180c86a1d05035d3452fd5179e99b2200621d68b1688e2bc1e083522307ec43df8478f98b1', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '8bb8b040728c408c82cfa9695ebb0a0cdbc8fb84b20342a991a8712f1609d5a1', 'online', '2013-06-17');
INSERT INTO `t_user` VALUES ('92', '籍欣馥', '4c61b67928e29883ee5ec49fe95bff1ccdab93bbadfa151ec5bcb164fa79b79ad1f92a01f14265ec', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'af22b1f219204e5aaed6a6e9ca94a34fce0a5f7dccc44a64b063b541886eb6ff', 'online', '2006-12-11');
INSERT INTO `t_user` VALUES ('93', '幸鸣', 'a9c1921ac059a82f4b3d1224c6d052c3d8f15f1612ec9c9a935f028f25a90fcdfd16f9d900375ba3', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '33a2336f2cd145cba76a14d051bed2d7f5aae611b8054180bf901b4ca83d100e', 'online', '2007-08-31');
INSERT INTO `t_user` VALUES ('94', '孟真蓓', '8a2982fee0b587bac47d4b198d7df84582fb41f83628aa67c4bd82e099cb276e7946b98ed1df997c', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'ed46d57d2ecb40ecb1bb80ea9dc6f304703cc5b7781d413b93479af988c99ee5', 'online', '2012-11-15');
INSERT INTO `t_user` VALUES ('95', '殳思月', '94ed1d4169722d358215fbb2691ae1a9827925da6ebb923a62218270a498f14b6b72e484019c6239', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '6493f93a84874c40841dd5c939ad9f7594a9dc404a5f4f948a767cfed967ae88', 'online', '2006-12-19');
INSERT INTO `t_user` VALUES ('96', '梁蕊', 'bc416de3c9e3d108fede5cddd0cfe3854ca47d84ff0b56925f5b9fd01b175ead94a3c9ac747e8847', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '80d3c3b60fe74c0a8ada0c1aef2757055c024e9ce9534db882bf848a9aaaa04d', 'online', '1996-06-14');
INSERT INTO `t_user` VALUES ('97', '毋妹娅', '2eba3464ae5e35b8cb70e21a23b6d26ba67eafdb3197fbdf49146dc013c106f7ffeab27e52e29069', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '41c4c357a24f4465b7ca215dea80ae63ad36a2499fcf4f15aba72d241e95a59c', 'online', '2001-05-22');
INSERT INTO `t_user` VALUES ('98', '凤柔毓', 'fbba3411b0deb7914156f13633e4b4a9ac31ad476c683b28215c865310f538f0f0caa0ffa1f2a876', '生活不只是眼前的苟且，还有诗和远方!', '2877319712@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '2537bba144864c14ac548e266eb38c633926659aec4b444786fb249655ec016a', 'online', '2015-05-12');
INSERT INTO `t_user` VALUES ('99', '帅星', 'f255106110cfdff3a046557005359a6a8779ad1f5f2934494793fa115d7f3c5141f9ac71ff4455db', '生活不只是眼前的苟且，还有诗和远方!', '976437146@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', 'd5eef553d1cd456bb54142da9efaafa8280f49e1f478488cb1897bc15d2d2294', 'online', '2012-09-11');
INSERT INTO `t_user` VALUES ('100', '茹佳瑗', 'ea2b3e930b8e4f8ff510c1f889a43f7c9314f2875c3a90a35d6e86bde6198b0ba438fc34838db905', '生活不只是眼前的苟且，还有诗和远方!', 'silence940109@gmail.com', 'http://localhost/static/image/friend_default.jpg', '1', '47141941e8a3451eb7ff0b07027f90c623c4c23fc7f74a56b15f40ca10592d3d', 'online', '2015-01-22');
INSERT INTO `t_user` VALUES ('101', '台力', 'c3dda5be4790d3610650564dd692440bece9bf085feab4a532400aa56d3e1907facd896543a05bbb', '生活不只是眼前的苟且，还有诗和远方!', '905769326@qq.com', 'http://localhost/static/image/friend_default.jpg', '0', '4165fdfa439648db9364fcda08f617f728615881f41d4ac7b98096876b5e512e', 'online', '2005-09-14');
INSERT INTO `t_user` VALUES ('102', 'silence', 'b41b005038aca00674f630a786a826c7371c036240d02dd37a1eb34e51286170d3f8df18544b4ce1', '生活不只是眼前的苟且，还有诗和远方!', '767219759@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'b7c8740882e445e19548dd675191ec0001a38777086c4c609c27c54f7bbe246b', 'online', '2017-04-04');
INSERT INTO `t_user` VALUES ('103', '张三', 'b41b005038aca00674f630a786a826c7371c036240d02dd37a1eb34e51286170d3f8df18544b4ce1', '生活不只是眼前的苟且，还有诗和远方!', '695412269@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', '324ceb8d52d74569aa72e806ff45ab7c2b403a790f694f4295ebe6126fc31f72', 'online', '2017-04-04');
INSERT INTO `t_user` VALUES ('106', '王培坤', 'b41b005038aca00674f630a786a826c7371c036240d02dd37a1eb34e51286170d3f8df18544b4ce1', '生活不只是眼前的苟且，还有诗和远方!', '767219759@qq.com', 'http://localhost/static/image/friend_default.jpg', '1', 'd89e3bca4bb243bda0be25d70b1f85eab81a20420c84405585a1d0bfe237b939', 'online', '2017-04-05');

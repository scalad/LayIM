package com.silence.test

import scala.util.Random
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import com.silence.util.SecurityUtil

object RandomData {
 
    private final val firstName = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福百家姓续"
  	private final val girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 "
  	private final val boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘"
  	private final val email_suffix : Array[String] = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");
  	private final val teleFirst : Array[String] = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
  	
    /**
		 * @@description 随机生成电话号码
	   * @return 电话号码
	   */
	  def getTel(): String = {
    	val index = getNum(0, teleFirst.length - 1);
    	val first = teleFirst(index)
    	val second = String.valueOf(getNum(1, 888) + 10000).substring(1)
    	val thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1)
    	return first + second + thrid;
    }
	  
	  /**
  	 * @@description 随机生成中文名字
  	 * @return 中文名
  	 */
    def getChineseName(): String = {
  		var index = getNum(0, firstName.length() - 1)
  		val first = firstName.substring(index, index + 1)
  		val sex = getNum(0, 1)
  		var str = boy
  		var length = boy.length()
  		if (sex == 0) {
  			str = girl;
  			length = girl.length()
  		} else {
  		}
  		index = getNum(0, length - 1)
  		var second = str.substring(index, index + 1)
  		var hasThird = getNum(0, 1)
  		var third = ""
  		if (hasThird == 1) {
  			index = getNum(0, length - 1)
  			third = str.substring(index, index + 1)
  		}
  		return first + second + third
  	}

	  /**
	   * @description 获取范围内的随机数字
	   * @param start 
	   * @param end
	   * @return
	   */
	  def getNum(start: Int, end: Int): Int = {
  		  (Math.random() * (end - start + 1) + start).toInt
	  }

	  /**
  	 * @description 随机生成时间
  	 * @return 时间
  	 */
  	def getDate(): String = {
  		val rand = new Random()
  		val format = new SimpleDateFormat("yyyy-MM-dd");
  		val cal = Calendar.getInstance()
  		cal.set(1990, 0, 1)
  		val start = cal.getTimeInMillis()
  		cal.set(2016, 0, 1)
  		val end = cal.getTimeInMillis()
  		var d = new Date(start + (rand.nextDouble() * (end - start)).toLong)
  		format.format(d)
  	}
 
  	def main(args: Array[String]): Unit = {
  	    
  	    for(i <- 0 to 100) {
    	    	var start = "insert into t_user(username,password,sign,telephone,avatar,sex,create_date) values ("
  	        start += "'" +getChineseName() + "','" + SecurityUtil.encrypt("123456") + "','" + "生活不只是眼前的苟且，还有诗和远方!" + "','" + getTel() + "','" + "http://localhost/static/image/group_default.jpg" + "'," + getNum(0, 1) + ",'" + getDate() + "');";
  	        println(start)
  	    }
  	  
  	}
}
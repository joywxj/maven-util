package com.wxj.test;

import lombok.extern.slf4j.Slf4j;

/**
* <p>类名: PasswordGenerator  </p>
* <p>描述: TODO 密码生成器</p>
* <p>作者:吴兴军</p>
* <p>电话:18772118541</p>
* <p>邮箱:18772118541@163.com</p>
* <p>日期: 2020-02-04 18:11</p>
*/
@Slf4j
public class PasswordGenerator {

	public static void main(String[] args) {
		generatorPasword(12,false,true,true,true);
	}
	
	/**
	* <p>方法名 getPassword </p>
	* <p>方法描述:  获取密码</p>
	* <p>算法: 生成一个随机数X对应的长度就是密码</p>
	* <p>@param length 长度
	* <p>@return</p>   
	* <p>日期:2020-02-04 18:18</p>
	 */
	public static String getPassword(int length){
		Integer number = getNumber(length);
		double password = Math.random()*number;
		Integer result = (int)password;
		return result.toString();
	}
	/**
	 * <p>@Description:生成电子账号的密码</p>
	 * <p> * @param length
	 * @param upcase
	 * @param lowcase
	 * @param number </p>
	 * <p>@return: java.lang.String</p>
	 * <p>@date:2020/10/21 15:46</p>
	 */
	public static String generatorPasword(int length, boolean upcase, boolean lowcase, boolean number,boolean cha){
		StringBuffer sb = new StringBuffer("");
		log.info("开始生成电子账号密码，传入条件:密码长度==>{}, 是否包含大写字母==>{},是否包含小写字母==>{},是否包含数字==>{},是否包含特殊字符==>{}",length,upcase ? "是": "否",lowcase ? "是": "否",number ? "是": "否",cha ? "是": "否");
		while (true) {
			if (sb.length() > length) {
				log.info("电子账号密码:{}", sb.toString());
				return sb.toString();
			}
			int random = generatorRandom(4);
			if (random == 0 && upcase) { // 随机大写字母
				char upcaseStr = generatorUpcase();
				sb.append(upcaseStr);
			} else if (random == 1 && lowcase) { // 随机小写字母
				char upcaseStr = generatorLowcase();
				sb.append(upcaseStr);
			} else if (random == 2 && number)  {// 随机数字
				int numbers = generatorRandom(10);
				sb.append(numbers);
			}else if (random == 3 && cha)  { // 随机特殊字符
				int numbers = generatorRandom(15);
				String [] strArr = {"!","@","#","$","%","^", "&", "*", "_", "+", "-", "=", "?", ">", "<"};
				sb.append(strArr[numbers]);
			}
		}
	}

	private static char generatorUpcase() {
		int random = generatorRandom(26) + 65;
		char c = (char)random;
		return c;
	}

	private static char generatorLowcase() {
		int random = 'a' + generatorRandom(26);
		char c = (char)random;
		return c;
	}

	/**
	 * 生成以个任意范围内的随机数
	 * @param number
	 * @return
	 */
	public static int generatorRandom (int number) {
		System.out.println("随机数范围:"+ number);
		double random = Math.random();
   		System.out.println("random:"+ random);
		int result = (int)(random * number);
		System.out.println("随机数:"+ result);

		return result;
	}

	/**  
	* <p>方法名 getNumber </p>
	* <p>方法描述: TODO</p>
	* <p>@param length
	* <p>@return</p>   
	* <p>日期:2020-02-04 18:21</p>
	*/
	private static Integer getNumber(int length) {
		Integer result = 0;
		String str = "1";
		for (int i = 0 ; i < length ; i++) {
			str += 0;
		}
		result = Integer.parseInt(str);
		return result;
	}
}

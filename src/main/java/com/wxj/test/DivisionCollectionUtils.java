package com.wxj.test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
/**  
* @ClassName: DivisionCollectionUtils  
* @Description: TODO 将集合分段 目前只支持list和map
* @author: wxj  
* @date: 2019年2月1日
* @Tel:18772118541
* @email:18772118541@163.com
*/
public class DivisionCollectionUtils<D> {
	/**
	* @方法说明：Division list
	* @方法名称：divideList
	* @作者：wxj
	*  @param pamList
	*  @param size
	*  @param threadSize
	*  @return
	* @创建时间: 2018-6-28 上午9:44:05
	*/
	public  List<List<D>> divideList(List<D> pamList, int size, int threadSize) {
		// list里面嵌套list，外层的list将传进来的ip集合分成一几段，当出去了就开启几个线程
		// 内层的list它是装的具体的分段后的ip信息
		List<List<D>> list = new ArrayList<List<D>>();
		int listSize = pamList.size();
		int count = getCount(listSize, size);
		if (count > threadSize) {// 这里最后看求出来的线程数量是不是大于人家要定义的线程数量
			count = threadSize;// 如果大于，那么这时总线程数就是人家线的那么多，不能大于人家给的线程总数
			if (pamList.size() % count == 0) {// 这里就要改变共享线程的ip数，用总的Ip去除以线程数量
				size = pamList.size() / count;
			} else {
				size = pamList.size() / count + 1;
			}
			if (size < size * (count) - listSize) {// 通常经过分组之后，size*count肯定是大于listsize的，那我size*count
													// - listsize
													// 如果它大于size话，那就说明改变count
				count = getCount(listSize, size);
			}
		}
		if(count == 0){
			count =1 ;
		}
		System.out.println(count + "个线程\n" + size + "个对象共享一个线程\n" + "集合的长度：" + listSize);
		for (int i = 1; i <= count; i++) {//
//			List<D> childList = new ArrayList<D>();
			int end = 0;
			if (i == count) {// 当i等于count的时候，这里面的最后一条就是ip集合的size了
				end = pamList.size();
			}else{
				end = i * size;
			}
			// 把之前的ip分分面若干段
			list.add(pamList.subList((i-1) * size, end));// 再把ip集合装到外层的集合里面去
		}
		return list;
	}

	private static int getCount(int listSize, int size) {
		int count = 0;
		if (listSize % size == 0) {//
			count = listSize / size;
		} else {
			if (listSize > (size + 1) * (listSize / size)) {
				count = listSize / size;
				size = size + 1;
			} else {
				count = listSize / size + 1;
			}
		}
		return count;
	}
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		for(int i = 0 ; i < 100 ; i++){
			list.add("ip"+i);
		}
		DivisionCollectionUtils<String> utils = new DivisionCollectionUtils<String>();
		utils.divideList(list,7,5);
	}
	/**
	* @方法说明：Division Map
	* @方法名称：divideMap
	* @作者：wxj
	*  @param pamList
	*  @param size
	*  @param threadSize
	*  @retrn
	* @创建时间: 2018-6-28 上午9:46:10
	*/
	public static List<Map<String, List<String>>> divideMap(Map<String, List<String>> pamList, int size, int threadSize) {
		// list里面嵌套list，外层的list将传进来的ip集合分成一几段，当出去了就开启几个线程
		// 内层的list它是装的具体的分段后的ip信息
		List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
		int listSize = pamList.size();
		int count = getCount(listSize, size);
		if (count > threadSize) {// 这里最后看求出来的线程数量是不是大于人家要定义的线程数量
			count = threadSize;// 如果大于，那么这时总线程数就是人家线的那么多，不能大于人家给的线程总数
			if (pamList.size() % count == 0) {// 这里就要改变共享线程的ip数，用总的Ip去除以线程数量
				size = pamList.size() / count;
			} else {
				size = pamList.size() / count + 1;
			}
			if (size < size * (count) - listSize) {// 通常经过分组之后，size*count肯定是大于listsize的，那我size*count
													// - listsize
													// 如果它大于size话，那就说明改变count
				count = getCount(listSize, size);
			}
		}
		if (count == 0) {
			count = 1;
		}
		System.out.println(count + "个线程\n" + size + "个对象共享一个线程\n" + "集合的长度：" + listSize);
		for (int i = 1; i <= count; i++) {//
			Set<Entry<String, List<String>>> set = pamList.entrySet();
			Iterator<Entry<String, List<String>>> iterator = set.iterator();
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			int j = 0;
			while (iterator.hasNext()) {
				if (j == size || j == pamList.size()-1) {
					break;
				}
				Entry<String, List<String>> next = iterator.next();
				String key = next.getKey();
				List<String> value = next.getValue();
				map.put(key, value);
				iterator.remove();
				j++;
			}
			list.add(map);
		}
		return list;
	}
}

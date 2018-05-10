package downloadfile.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RemoveDuplicateUtils {
	public static List<Integer> removeDuplicateByLoopList(List<Integer> list) {
		for(int i = 0; i < list.size() - 1; i++) {
			for(int j = list.size() - 1; j > i; j--) {
				if(list.get(j).equals(list.get(i))) {
					list.remove(j);
				}
			}
		}
		return list;
	}
	public static List<String> removeDuplicateByHashSet(List<String> list) {
		HashSet<String> h = new HashSet<String>(list);   
		list.clear();   
		list.addAll(h);   
		return list;   
	}
	public static List<Object> removeDuplicate(List<Object> list){  
        List<Object> listTemp = new ArrayList<Object>();  
        for(int i=0;i<list.size();i++){  
            if(!listTemp.contains(list.get(i))){  
                listTemp.add(list.get(i));  
            }  
        }  
        return listTemp;  
    } 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicateWithOrder(List list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for(Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
			}
		list.clear();
		list.addAll(newList);    
	    System.out.println( " remove duplicate " + list);
	    return list;
	}
	//@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		List<Integer> a = new ArrayList<Integer>();
		a.add(3);
		a.add(2);
		a.add(1);
		a.add(1);
		//RemoveDuplicateUtils.removeDuplicateByLoopList(a);
		System.out.println(RemoveDuplicateUtils.removeDuplicateByLoopList(a));
	}
}

package game.level;

import java.util.ArrayList;
import java.util.List;

import game.entity.Entity;

public class EntityListCache {

	private static final List<List<Entity>> cacheMap = new ArrayList<List<Entity>>();
	private static int c;
	
	public static List<Entity> getCache() {
		if(c == cacheMap.size()) cacheMap.add(new ArrayList<>());
		
		List<Entity> result = cacheMap.get(c++);
		result.clear();
		return result;
	}
	
	public static void reset() { c = 0; }
}
package game.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import game.entity.Entity;
import game.entity.hopper.GrayHopper;
import game.entity.hopper.Hopper;
import game.entity.hopper.PinkHopper;
import game.entity.hopper.YellowHopper;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.level.tile.Tile;
import game.particle.Particle;
import game.particle.RockParticle;

public class Level {
	
	public static final Random random = new Random();
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Particle> particles = new ArrayList<Particle>();
	public List<Hopper> hoppers = new ArrayList<Hopper>();
	public List<Entity>[] entitiesInTiles;
	public double gravity;
	public double friction;
	public final int w = 10;
	public final int h = 7;
	public Tile[] tiles;
	public int num;
	private Comparator<Entity> entityComparator = new Comparator<Entity>() {

		public int compare(Entity e0, Entity e1) {
			if(e0.zSort < e1.zSort) return -1;
			if(e0.zSort > e1.zSort) return 1;
			
			if((int)e0.y < (int)e1.y) return -1;
			if((int)e0.y > (int)e1.y) return +1;
			if((int)e0.x < (int)e1.x) return -1;
			if((int)e0.x > (int)e1.x) return +1;
			return 0;
		}
		
	};
	
	@SuppressWarnings("unchecked")
	public Level(int num) {
		if(num >= Art.levels.length) num = 0;
		
		this.num = num;
		this.gravity = 0.09;
		this.friction = 0.99;
		this.tiles = new Tile[this.w * this.h];
		this.entitiesInTiles = new ArrayList[this.w * this.h];
		loadLevel();
	}
	
	public void loadLevel() {
		for(int y = 0; y < this.h; y++) {
			for(int x = 0; x < this.w; x++) {
				this.entitiesInTiles[x + y * this.w] = new ArrayList<Entity>();
			
				int col = Art.levels[this.num].pixels[x + y * this.w];
				
				this.tiles[x + y * this.w] = Tile.air;
				
				if(col == 0xFF404040) this.tiles[x + y * this.w] = Tile.wall;
				else if(col == 0xFFFF6A00) this.tiles[x + y * this.w] = Tile.lava;
				else if(col == 0xFF00FFFF) this.tiles[x + y * this.w] = Tile.portal;
				else if(col == 0xFFA0A0A0) this.tiles[x + y * this.w] = Tile.block;
				else if(col == 0xFFFFD800) {
					YellowHopper hopper = new YellowHopper((x << 5) + 16, (y << 5) + 16);
					addEntity(hopper);
				} else if(col == 0xFFC0C0C0) {
					GrayHopper hopper = new GrayHopper((x << 5) + 16, (y << 5) + 16);
					addEntity(hopper);
				} else if(col == 0xFFFF00FF) {
					PinkHopper hopper = new PinkHopper((x << 5) + 16, (y << 5) + 16);
					addEntity(hopper);
				}
			}			
		}
	}
	
	public void addEntity(Entity e) {
		this.entities.add(e);
		e.init(this);
		
		e.xSlot = (int)(e.x + e.xr) >> 5;
		e.ySlot = (int)(e.y + e.yr) >> 5;
		insertEntity(e, e.xSlot, e.ySlot);
		
		if(e instanceof Hopper) this.hoppers.add((Hopper)e);
	}
	
	public void addParticle(Particle p) {
		this.particles.add(p);
		p.init(this);
	}
	
	public void tick() {
		for(int i = 0; i < this.w * this.h; i++) {
			this.tiles[i].tick(this, i % this.w, i / this.w);
		}
		
		for(int i = 0; i < this.particles.size(); i++) {
			Particle p = this.particles.get(i);
			
			if(!p.removed) {
				p.tick();
				continue;
			}
			
			this.particles.remove(i--);
		}
		
		for(int i = 0; i < this.entities.size(); i++) {
			Entity e = this.entities.get(i);
			
			int xto = e.xSlot;
			int yto = e.ySlot;
			
			e.xSlot = (int)(e.x + e.xr) >> 5;
			e.ySlot = (int)(e.y + e.yr) >> 5;
			
			if(!e.removed) e.tick();
			
			
			if(e.removed) {
				removeEntity(e, xto, yto);
				this.entities.remove(i--);
				continue;
			}
			
			
			int xtn = e.xSlot;
			int ytn = e.ySlot;
			
			if(xtn != xto || ytn != yto) {
				removeEntity(e, xto, yto);
				insertEntity(e, xtn, ytn);
				
				if(e.xSlot < 0 || e.ySlot < 0 || e.xSlot >= this.w || e.ySlot >= this.h) e.die(); 
				
			}
		}
	}
	
	public void render(Bitmap screen, double delta) {
		screen.draw(Art.background, 0, 0);
		
		for(int y = 0; y < this.h; y++) {
			for(int x = 0; x < this.w; x++) {
				Tile tile = getTile(x, y);
				tile.render(screen, this, x << 5, y << 5);
			}			
		}
		
		Collections.sort(this.entities, this.entityComparator);
		for(Entity e : this.entities) {
			e.render(screen, delta);
		}
		
		for(Particle p : this.particles) {
			p.render(screen);
		}
	}
	
	public void insertEntity(Entity e, int x, int y) {
		if(x < 0 || y < 0 || x >= this.w || y >= this.h) return;
		this.entitiesInTiles[x + y * this.w].add(e);
	}
	
	public void removeEntity(Entity e, int x, int y) {
		if(x < 0 || y < 0 || x >= this.w || y >= this.h) return;
		this.entitiesInTiles[x + y * this.w].remove(e);
	}
	
	public List<Entity> getEntities(int x0, int y0, int x1, int y1){
		List<Entity> result = EntityListCache.getCache();
		int xt0 = (x0 >> 5) - 1;
		int yt0 = (y0 >> 5) - 1;
		int xt1 = (x1 >> 5) + 1;
		int yt1 = (y1 >> 5) + 1;
		
		for(int y = yt0; y <= yt1; y++) {
			for(int x = xt0; x <= xt1; x++) {
				if(x < 0 || y < 0 || x >= this.w || y >= this.h) continue;
				
				List<Entity> entities = this.entitiesInTiles[x + y * this.w];
				for(int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					if(!e.intersects(x0, y0, x1, y1)) continue;
					result.add(e);
				}
			}
		}
		
		return result;
	}
	
	public Tile getTile(int x, int y) {
		if(y >= this.h) return Tile.air;
		if(x >= 0 && y >= 0 && x < this.w) return this.tiles[x + y * this.w];
		return Tile.block;
	}
	
	public void setTile(Tile tile, int x, int y) {
		if(x < 0 || y < 0 || x >= this.w || y >= this.h) return;
		this.tiles[x + y * this.w] = tile;
	}
	
	public void destroyTile(Entity e, int xt, int yt) {
		setTile(Tile.air, xt, yt);
		for(int i = 0; i < 10; i++) {
			addParticle(new RockParticle((xt << 5) + 16 + random.nextInt(16) - 8, (yt << 5) + 16 + random.nextInt(16) - 8 + i));
		}
	}
	
}
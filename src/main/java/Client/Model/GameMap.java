package Client.Model;

public class GameMap {
	public Tile[][] map;
	
	public GameMap(Tile[][] map) {
		this.map=map;
	}
	
	public boolean CastleOn(Integer x, Integer y) {
		return map[x][y].Castle;
	}
	
	public void SetCastleOn(Integer x, Integer y) {
		map[x][y].SetCastle(true);
	}
	
	public boolean TreasureOn(Integer x, Integer y) {
		return map[x][y].Treasure;
	}
	
	public void SetWeightOf(Integer x, Integer y, Integer weight) {
		map[x][y].Setweight(weight);
	}
	
	public Integer GetTileTypeOn(Integer x, Integer y) {
		return map[x][y].GetTileType();
	}
}

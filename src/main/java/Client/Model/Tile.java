package Client.Model;

public class Tile {
	public Integer TileType;
	public Boolean Castle;
	public Boolean Treasure;
	
	public Integer weight;
	
	public Tile(Integer TileType, Boolean Castle, Boolean Treasure, Integer weight) {
		this.TileType=TileType;
		this.Castle=false;
		this.Treasure=false;
		this.weight=weight;
	}
	
	public Integer GetTileType() {
		return this.TileType;
	}
	
	public Boolean GetCastle() {
		return this.Castle;
	}
	
	public void SetCastle(Boolean Castle) {
		this.Castle=Castle;
	}
	
	public Boolean GetTreasure() {
		return this.Treasure;
	}
	
	public void SetTreasure(Boolean Treasure) {
		this.Treasure=Treasure;
	}
	
	public Integer Getweight() {
		return this.weight;
	}
	
	public void Setweight(Integer weight) {
		this.weight=weight;
	}
}

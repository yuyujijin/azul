import java.util.ArrayList;

public class Factory {

	ArrayList<Tile> tiles;

	public Factory() {
		tiles = new ArrayList<Tile>();
	}

	public boolean isEmpty() {
		return !(tiles.size() > 0);
	}

	public ArrayList<Tile> getTile() {
		return tiles;
	}

	public void add(Tile t) {
		tiles.add(t);
	}

	public Tile[] pick(char c) {
		if (!isEmpty()) {
			ArrayList<Tile> subList = new ArrayList<Tile>();
			for (int i = 0; i < tiles.size(); i++) {
				if (tiles.get(i).getColor() == c) {
					subList.add(tiles.get(i));
					tiles.remove(i);
					i--;
				}
			}
			Tile[] treturn = listToArray(subList);
			return treturn;
		}
		System.out.println("Error : factory is empty.");
		return null;
	}

	public Tile[] rest() {
		if (!isEmpty()) {
			Tile[] treturn = listToArray(tiles);
			tiles.clear();
			return treturn;
		}
		System.out.println("No rest to get.");
		return null;
	}

	public Tile[] listToArray(ArrayList<Tile> al) {
		Tile[] treturn = new Tile[al.size()];
		int i = 0;
		for (Tile t : al) {
			treturn[i] = t;
			i++;
		}
		return treturn;
	}
}
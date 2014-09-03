import java.util.ArrayList;


public class Vertex {
	
	public Vertex(int position, int x, int y) {
		this.position = position;
		this.x = x;
		this.y = y;
	}
	
	private int position;
	private int x,y;
	private Vertex v1,v2,v3,v4;
	private ArrayList<Edge> listEdge = new ArrayList<Edge>();

	public ArrayList<Edge> getListEdge() {
		return listEdge;
	}

	public void setListEdge(ArrayList<Edge> listEdge) {
		this.listEdge = listEdge;
	}

	public Vertex getV1() {
		return v1;
	}

	public void setV1(Vertex v1) {
		this.v1 = v1;
	}

	public Vertex getV2() {
		return v2;
	}

	public void setV2(Vertex v2) {
		this.v2 = v2;
	}

	public Vertex getV3() {
		return v3;
	}

	public void setV3(Vertex v3) {
		this.v3 = v3;
	}

	public Vertex getV4() {
		return v4;
	}

	public void setV4(Vertex v4) {
		this.v4 = v4;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}

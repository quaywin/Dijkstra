
public class Edge {
	//private Vertex headVertex;
	private int indexTargetVertex;
	private int weight;
	
	public Edge(int indexTargetVertex, int weight){
//		this.headVertex = headVertex;
		this.indexTargetVertex = indexTargetVertex;
		this.weight = weight;
	}
	
	
	public int getIndexTargetVertex() {
		return indexTargetVertex;
	}


	public void setIndexTargetVertex(int indexTargetVertex) {
		this.indexTargetVertex = indexTargetVertex;
	}


	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
}

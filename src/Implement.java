import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Implement {

	private static final int MAX = Integer.MAX_VALUE;
	private int matrix[][], iMatrix[][];
	public int height, width, start, end, nVertex;
	private int shortestPath;
	private StringBuilder strDiagram = new StringBuilder();
	private boolean error = false;
	private String strError = new String(), strShortestPath = new String();
	public ArrayList<Vertex> listShortestPath = new ArrayList<Vertex>();
	public ArrayList<Vertex> listVertex = new ArrayList<Vertex>();
	public ArrayList<Vertex> listOptimzationVertex = new ArrayList<Vertex>();
	public ArrayList<Integer> listLabel = new ArrayList<Integer>();

	public String getStrDiagram() {
		return strDiagram.toString();
	}
	
	
	public String getStringShortestPath() {
		return strShortestPath;
	}

	

	public void ReadFile(String input) {

		BufferedReader br = null;
		try {
			String sCurrentLine;
			int content;
			File file = new File(input);
			br = new BufferedReader(new FileReader(file));
			sCurrentLine = br.readLine().trim();
			height = Integer.parseInt(sCurrentLine);
			sCurrentLine = br.readLine().trim();
			width = Integer.parseInt(sCurrentLine);
			matrix = new int[height][width];
			iMatrix = new int[height][width];
			int i = 0;
			int j = 0;
			while ((content = br.read()) != -1) {
				if (content == 35 || content == 46) {
					if (content == 35)
						matrix[i][j] = 0;
					else
						matrix[i][j] = 1;
					j++;
					if (j == width) {

						j = 0;
						i++;
						if (i == height)
							break;
					}
				}
			}
			while ((content = br.read()) != -1) {
				if (content == 10)
					break;
			}
			sCurrentLine = br.readLine().trim();
			this.start = Integer.parseInt(sCurrentLine);
			sCurrentLine = br.readLine().trim();
			this.end = Integer.parseInt(sCurrentLine);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public int positionVertex(int a, int b) {
		return a * width + b;
	}

	public void findVertex() {
		int p1 = 0, p2 = 0, p3 = 0, p4 = 0, s = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (matrix[i][j] == 1) {
					Vertex vertex = new Vertex(positionVertex(i, j), i, j);
					if (i > 0) {
						p1 = matrix[i - 1][j];
						if (p1 == 1)
							vertex.setV1(new Vertex(positionVertex(i - 1, j),
									i - 1, j));
					}
					if (j > 0) {
						p2 = matrix[i][j - 1];
						if (p2 == 1)
							vertex.setV2(new Vertex(positionVertex(i, j - 1),
									i, j - 1));
					}
					if (i < (height - 1)) {
						p3 = matrix[i + 1][j];
						if (p3 == 1)
							vertex.setV3(new Vertex(positionVertex(i + 1, j),
									i + 1, j));
					}
					if (j < (width - 1)) {
						p4 = matrix[i][j + 1];
						if (p4 == 1)
							vertex.setV4(new Vertex(positionVertex(i, j + 1),
									i, j + 1));
					}
					iMatrix[i][j] = listVertex.size();
					listVertex.add(vertex);
					s = p1 + p2 + p3 + p4;

					if (s != 2) {
						listOptimzationVertex.add(vertex);
					}
					s = 0;
					p1 = 0;
					p2 = 0;
					p3 = 0;
					p4 = 0;
				}
			}
		}
	}

	public int findWay(int previousPosition, int i, int j) {

		if (i > 0) {
			if (matrix[i - 1][j] != 0
					&& positionVertex(i - 1, j) != previousPosition)
				return iMatrix[i - 1][j];
		}
		if (j > 0) {
			if (matrix[i][j - 1] != 0
					&& positionVertex(i, j - 1) != previousPosition)
				return iMatrix[i][j - 1];
		}
		if (i < (height - 1)) {
			if (matrix[i + 1][j] != 0
					&& positionVertex(i + 1, j) != previousPosition)
				return iMatrix[i + 1][j];
		}
		if (j < (width - 1)) {
			if (matrix[i][j + 1] != 0
					&& positionVertex(i, j + 1) != previousPosition)
				return iMatrix[i][j + 1];
		}
		return iMatrix[i][j];
	}

	public void findEdge() {
		nVertex = listOptimzationVertex.size();
		int count = 1;
		Vertex currentVertex, nextVertex, tempVertex;
		for (Vertex v : listOptimzationVertex) {
			ArrayList<Edge> listEdge = new ArrayList<Edge>();
			if (v.getV1() != null) {
				int i = v.getX();
				int j = v.getY();
				currentVertex = v;
				nextVertex = listVertex.get(iMatrix[i - 1][j]);
				while (!listOptimzationVertex.contains(nextVertex)) {
					tempVertex = nextVertex;
					nextVertex = listVertex.get(findWay(
							currentVertex.getPosition(), nextVertex.getX(),
							nextVertex.getY()));
					currentVertex = tempVertex;
					count++;
				}
				listEdge.add(new Edge(
						listOptimzationVertex.indexOf(nextVertex), count));
				count = 1;
			}
			if (v.getV2() != null) {
				int i = v.getX();
				int j = v.getY();
				currentVertex = v;
				nextVertex = listVertex.get(iMatrix[i][j - 1]);
				while (!listOptimzationVertex.contains(nextVertex)) {
					tempVertex = nextVertex;
					nextVertex = listVertex.get(findWay(
							currentVertex.getPosition(), nextVertex.getX(),
							nextVertex.getY()));
					currentVertex = tempVertex;
					count++;
				}
				listEdge.add(new Edge(
						listOptimzationVertex.indexOf(nextVertex), count));
				count = 1;
			}
			if (v.getV3() != null) {
				int i = v.getX();
				int j = v.getY();
				currentVertex = v;
				nextVertex = listVertex.get(iMatrix[i + 1][j]);
				while (!listOptimzationVertex.contains(nextVertex)) {
					tempVertex = nextVertex;
					nextVertex = listVertex.get(findWay(
							currentVertex.getPosition(), nextVertex.getX(),
							nextVertex.getY()));
					currentVertex = tempVertex;
					count++;
				}
				listEdge.add(new Edge(
						listOptimzationVertex.indexOf(nextVertex), count));
				count = 1;
			}
			if (v.getV4() != null) {
				int i = v.getX();
				int j = v.getY();
				currentVertex = v;
				nextVertex = listVertex.get(iMatrix[i][j + 1]);
				while (!listOptimzationVertex.contains(nextVertex)) {
					tempVertex = nextVertex;
					nextVertex = listVertex.get(findWay(
							currentVertex.getPosition(), nextVertex.getX(),
							nextVertex.getY()));
					currentVertex = tempVertex;
					count++;
				}
				listEdge.add(new Edge(
						listOptimzationVertex.indexOf(nextVertex), count));
				count = 1;
			}
			v.setListEdge(listEdge);
		}
	}

	public int Min(ArrayList<Integer> a, ArrayList<Integer> b) {
		int min = MAX;
		int dem = 0;
		for (int i = 0; i < nVertex; i++) {
			if (min > a.get(i) && b.get(i) != -1) {
				min = a.get(i);
				dem = i;
				if (min == Collections.min(a))
					break;
			}
		}
		return dem;
	}

	public void DijkstraPath() {
		int x = -1;
		int y = -1;
		for (Vertex v : listOptimzationVertex) {
			if (v.getPosition() == this.start) {
				x = listOptimzationVertex.indexOf(v);
			}
			if (v.getPosition() == this.end) {
				y = listOptimzationVertex.indexOf(v);
			}
		}
		if (x == -1) {
			strError = strError + "Wrong start position input\n";
			error = true;
		}
		if (y == -1) {
			strError = strError + "Wrong end position input\n";
			error = true;
		}
		if (!error) {

			ArrayList<Integer> Length = new ArrayList<Integer>();
			ArrayList<Integer> Last = new ArrayList<Integer>();
			ArrayList<Integer> T = new ArrayList<Integer>();

			for (int i = 0; i < nVertex; i++) {
				T.add(i);
			}
			for (int i = 0; i < nVertex; i++) {
				if (i == x) {
					Length.add(0);
				} else {
					Length.add(MAX);
				}
			}
			for (int i = 0; i < nVertex; i++) {
				Last.add(-1);
			}
			while (T.get(y) == y) {
				int count = 0;
				int min = Min(Length, T);
				if (T.get(min) == -1)
					break;
				T.set(min, -1);
				for (Edge e : listOptimzationVertex.get(min).getListEdge()) {
					int weight = e.getWeight();
					int targetVertex = e.getIndexTargetVertex();
					if (weight > 0) {
						if (Length.get(targetVertex) > (weight + Length
								.get(min))) {
							Length.set(targetVertex, weight + Length.get(min));
							Last.set(targetVertex, min);
						}
						count++;
					}
				}
				if (count == 0)
					break;
			}
			if (Last.get(y) != -1 && !error) {
				int i = y;
				this.shortestPath = Length.get(i);
				this.listShortestPath.add(listOptimzationVertex.get(i));
				while (Last.get(i) != -1) {
					this.listShortestPath.add(listOptimzationVertex.get(Last
							.get(i)));
					i = Last.get(i);
				}
			} else {
				this.shortestPath = -1;
				error = true;
				strError = "No Path";
			}
		}
	}

	public void WriteFile(String filePath) {
		findVertex();
		findEdge();
		DijkstraPath();
		try {
			File file1 = new File(filePath + "/diagram.txt");
			File file2 = new File(filePath + "/shortest_path.txt");
			if (!file1.exists()) {
				file1.createNewFile();
			}
			if (!file2.exists()) {
				file2.createNewFile();
			}
			PrintWriter out1 = new PrintWriter(file1);
			PrintWriter out2 = new PrintWriter(file2);
			String str, strSP;
			String ls = System.getProperty("line.separator");
			for (Vertex v : listOptimzationVertex) {
				str = String.valueOf(v.getPosition());
				for (Edge e : v.getListEdge()) {
					str = str
							+ " ("
							+ String.valueOf(listOptimzationVertex.get(
									e.getIndexTargetVertex()).getPosition())
							+ ", " + String.valueOf(e.getWeight()) + ")";
				}
				strDiagram.append(str);
				strDiagram.append(ls);
			}
			out1.println(strDiagram);
			//System.out.println(strDiagram);
			out1.close();
			if (!error) {
				strSP = String.valueOf(listShortestPath.get(
						listShortestPath.size() - 1).getPosition());
				for (int i = listShortestPath.size() - 2; i >= 0; i--) {
					strSP = strSP
							+ " "
							+ String.valueOf(listShortestPath.get(i)
									.getPosition());
				}
				//System.out.println(strSP);
				//System.out.println(shortestPath);
				strShortestPath = strSP+System.getProperty("line.separator")+shortestPath;
				out2.println(strSP);
				out2.println(shortestPath);
			} else {
				strShortestPath = strError;
				//System.out.println(strError);
				out2.println(strError);
			}
			out2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

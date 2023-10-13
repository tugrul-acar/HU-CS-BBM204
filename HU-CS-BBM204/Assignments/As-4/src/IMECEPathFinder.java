import java.util.*;
import java.awt.*;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class IMECEPathFinder{
	  public int[][] grid;
	  public int height, width;
	  public int maxFlyingHeight;
	  public double fuelCostPerUnit, climbingCostPerUnit;

	  public IMECEPathFinder(String filename, int rows, int cols, int maxFlyingHeight, double fuelCostPerUnit, double climbingCostPerUnit){

		  grid = new int[rows][cols];
		  this.height = rows;
		  this.width = cols;
		  this.maxFlyingHeight = maxFlyingHeight;
		  this.fuelCostPerUnit = fuelCostPerUnit;
		  this.climbingCostPerUnit = climbingCostPerUnit;

		  try
		  {
			Scanner scanner = new Scanner(new File(filename));
			for(int i = 0; i < rows; i++)
		  	{
				for(int j = 0; j < cols; j++)
				{
					int elevation = scanner.nextInt();
					grid[i][j] = elevation;
				}
		  	}
		  }
		  catch(FileNotFoundException e)
		  {
			e.printStackTrace();
		  }
	  }

	  /**
	   * Draws the grid using the given Graphics object.
	   * Colors should be grayscale values 0-255, scaled based on min/max elevation values in the grid
	   */
	  public void drawGrayscaleMap(Graphics g){

		  int maxElevation = Integer.MIN_VALUE;
		  int minElevation = Integer.MAX_VALUE;
		  for (int[] row : grid) {
			  for (int elevation : row) {
				  if (elevation < minElevation) {
					  minElevation = elevation;
				  }
				  if (elevation > maxElevation) {
					  maxElevation = elevation;
				  }
			  }
		  }

		  double interval = (maxElevation - minElevation) / 255.0;
		  // TODO: draw the grid, delete the sample drawing with random color values given below
		  for (int i = 0; i < height; i++)
		  {
			  for (int j = 0; j < width; j++)
			  {
				  int value = (int)((grid[i][j] - minElevation) / interval);
				  g.setColor(new Color(value, value, value));
				  g.fillRect(j, i, 1, 1);
			  }
		  }
	  }


	public void createGrayscaleMapFile() {
		try {
			int height = grid.length;    // Geçici değer
			int width = grid[0].length;  // Geçici değer

			File file = new File("grayscaleMap.dat");
			FileWriter writer = new FileWriter(file);

			int maxElevation = -1;
			int minElevation = Integer.MAX_VALUE;
			for (int row = 0; row < height; row++) {
				for (int column = 0; column < width; column++) {
					int elevation = grid[row][column];
					if (elevation < minElevation) {
						minElevation = elevation;
					}
					if (elevation > maxElevation) {
						maxElevation = elevation;
					}
				}
			}
			double interval = (maxElevation - minElevation) / 255.0;

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int value = (int) ((grid[i][j] - minElevation) / interval);
					writer.write(String.valueOf(value));
					if (j != width - 1) {
						writer.write(" ");
					}
				}
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the most cost-efficient path from the source Point start to the destination Point end
	 * using Dijkstra's algorithm on pixels.
	 * @return the List of Points on the most cost-efficient path from start to end
	 */
	public List<Point> getMostEfficientPath(Point start, Point end) {

		List<Point> path = new ArrayList<>();
		// TODO: Your code goes here
		// TODO: Implement the Mission 0 algorithm here
		double[][] cost = new double[height][width];
		Point[][] prev = new Point[height][width];
		PriorityQueue<Point> queue = new PriorityQueue<>(height * width, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				return Double.compare(cost[p1.x][p1.y], cost[p2.x][p2.y]);
			}
		});

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cost[i][j] = Double.POSITIVE_INFINITY;
				prev[i][j] = null;
			}
		}

		int temp = start.x;
		start.x = start.y;
		start.y = temp;
		temp = end.x;
		end.x = end.y;
		end.y = temp;

		cost[start.x][start.y] = 0;
		queue.add(start);
		while (!queue.isEmpty()) {
			Point current = queue.poll();
			if (current.equals(end)) {
				break;
			}

			// Diğer işlemler

			int startX = Math.max(0, current.x - 1);
			int endX = Math.min(height - 1, current.x + 1);
			int startY = Math.max(0, current.y - 1);
			int endY = Math.min(width - 1, current.y + 1);

			for (int i = startX; i <= endX; i++) {
				for (int j = startY; j <= endY; j++) {
					if (i == current.x && j == current.y) {
						continue;
					}

					Point neighbor = new Point(i, j);

					if (grid[neighbor.x][neighbor.y] < maxFlyingHeight) {
						double euclidean = (neighbor.x != current.x && neighbor.y != current.y) ? Math.sqrt(2) : 1;
						int heightImpact = Math.max(0, grid[neighbor.x][neighbor.y] - grid[current.x][current.y]);
						double pathCost = euclidean * fuelCostPerUnit + heightImpact * climbingCostPerUnit;

						if (cost[current.x][current.y] + pathCost < cost[neighbor.x][neighbor.y]) {
							cost[neighbor.x][neighbor.y] = cost[current.x][current.y] + pathCost;
							prev[neighbor.x][neighbor.y] = current;
							if (!queue.contains(neighbor)) {
								queue.add(neighbor);
							}
						}
					}
				}
			}
		}

		Deque<Point> pathStack = new ArrayDeque<>();
		Point current = end;
		while (current != null && current != start) {
			pathStack.push(current);
			current = prev[current.x][current.y];
		}

		if (pathStack.size() > 1) {
			path.add(new Point(start.y, start.x));
			while (!pathStack.isEmpty()) {
				Point pTemp = pathStack.pop();
				path.add(new Point(pTemp.y, pTemp.x));
			}
		}
		return path;
	}

	/**
	 * Calculate the most cost-efficient path from source to destination.
	 * @return the total cost of this most cost-efficient path when traveling from source to destination
	 */
	public double getMostEfficientPathCost(List<Point> path) {
		double totalCost = 0.0;

		Iterator<Point> iterator = path.iterator();
		Point previousPoint = iterator.next();

		while (iterator.hasNext()) {
			Point currentPoint = iterator.next();

			double euclideanDistance = calculateEuclideanDistance(previousPoint, currentPoint);
			int heightDifference = calculateHeightDifference(grid, previousPoint, currentPoint);

			double cost = euclideanDistance * fuelCostPerUnit + heightDifference * climbingCostPerUnit;
			totalCost += cost;

			previousPoint = currentPoint;
		}

		return totalCost;
	}

	private double calculateEuclideanDistance(Point p1, Point p2) {
		double dx = Math.abs(p2.x - p1.x);
		double dy = Math.abs(p2.y - p1.y);
		return Math.sqrt(dx * dx + dy * dy);
	}

	private int calculateHeightDifference(int[][] grid, Point p1, Point p2) {
		int height1 = grid[p1.y][p1.x];
		int height2 = grid[p2.y][p2.x];
		return Math.max(0, height2 - height1);
	}


	/**
	 * Draw the most cost-efficient path on top of the grayscale map from source to destination.
	 */
	public void drawMostEfficientPath(Graphics g, List<Point> path){
		
		for(Point p : path) 
		{
			g.setColor(new Color(0, 255, 0));
			g.fillRect(p.x, p.y, 1, 1);
		}
	}

	/**
	 * Find an escape path from source towards East such that it has the lowest elevation change.
	 * Choose a forward step out of 3 possible forward locations, using greedy method described in the assignment instructions.
	 * @return the list of Points on the path
	 */
	public List<Point> getLowestElevationEscapePath(Point start) {
		List<Point> pathPointsList = new ArrayList<>();

		int temp = start.x;
		start.x = start.y;
		start.y = temp;
		int distanceToEast = width - start.y - 1;

		pathPointsList.add(new Point(start.y, start.x));

		for (int i = 0; i < distanceToEast; i++) {
			int baseElevation = grid[start.x][start.y];

			int northeastElevation = Math.abs(grid[start.x - 1][start.y + 1] - baseElevation);
			int eastElevation = Math.abs(grid[start.x][start.y + 1] - baseElevation);
			int southeastElevation = Math.abs(grid[start.x + 1][start.y + 1] - baseElevation);

			if (eastElevation <= northeastElevation && eastElevation <= southeastElevation) {
				start.y += 1;
			} else if (northeastElevation <= southeastElevation) {
				start.x -= 1;
				start.y += 1;
			} else {
				start.x += 1;
				start.y += 1;
			}
			pathPointsList.add(new Point(start.y, start.x));
		}

		return pathPointsList;
	}


	/**
	 * Calculate the escape path from source towards East such that it has the lowest elevation change.
	 * @return the total change in elevation for the entire path
	 */
	public int getLowestElevationEscapePathCost(List<Point> pathPointsList) {
		int totalChange = 0;

		Iterator<Point> iterator = pathPointsList.iterator();
		Point previousPoint = iterator.next();

		while (iterator.hasNext()) {
			Point currentPoint = iterator.next();
			int change = Math.abs(grid[currentPoint.y][currentPoint.x] - grid[previousPoint.y][previousPoint.x]);
			totalChange += change;
			previousPoint = currentPoint;
		}

		return totalChange;
	}


	/**
	 * Draw the escape path from source towards East on top of the grayscale map such that it has the lowest elevation change.
	 */
	public void drawLowestElevationEscapePath(Graphics g, List<Point> pathPointsList){
		for(Point p : pathPointsList) 
		{
			g.setColor(new Color(255, 255, 0));
			g.fillRect(p.x, p.y, 1, 1);
		}
	}


}

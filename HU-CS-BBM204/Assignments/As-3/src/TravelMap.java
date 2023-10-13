import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TravelMap {

    // Maps a single Id to a single Location.
    public Map<Integer, Location> locationMap = new HashMap<>();

    // List of locations, read in the given order
    public List<Location> locations = new ArrayList<>();

    // List of trails, read in the given order
    public List<Trail> trails = new ArrayList<>();

    // TODO: You are free to add more variables if necessary.

    public void initializeMap(String filename){
        // Read the XML file and fill the instance variables locationMap, locations and trails.
        // TODO: Your code here
        File inputFile = new File(filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(inputFile);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        doc.getDocumentElement().normalize();

        NodeList locationList = doc.getElementsByTagName("Location");
        for (int i = 0; i < locationList.getLength(); i++) {
            Node locationNode = locationList.item(i);
            if (locationNode.getNodeType() == Node.ELEMENT_NODE) {
                Element locationElement = (Element) locationNode;
                String name = locationElement.getElementsByTagName("Name").item(0).getTextContent();
                int id = Integer.parseInt(locationElement.getElementsByTagName("Id").item(0).getTextContent());
                Location location = new Location(name, id);
                locations.add(location);
            }
        }

        NodeList trailList = doc.getElementsByTagName("Trail");
        for (int i = 0; i < trailList.getLength(); i++) {
            Node trailNode = trailList.item(i);
            if (trailNode.getNodeType() == Node.ELEMENT_NODE) {
                Element trailElement = (Element) trailNode;
                int sourceId = Integer.parseInt(trailElement.getElementsByTagName("Source").item(0).getTextContent());
                int destId = Integer.parseInt(trailElement.getElementsByTagName("Destination").item(0).getTextContent());
                int danger = Integer.parseInt(trailElement.getElementsByTagName("Danger").item(0).getTextContent());
                Location source = locations.stream().filter(l -> l.id== sourceId).findFirst().get();
                Location dest = locations.stream().filter(l -> l.id == destId).findFirst().get();
                Trail trail = new Trail(source, dest, danger);
                trails.add(trail);
            }
        }
        for (Location location : locations) {
            locationMap.put(location.id, location);
        }

    }

    public List<Trail> getSafestTrails() {
        List<Trail> safestTrails = new ArrayList<>();
        // Fill the safestTrail list and return it.
        // Select the optimal Trails from the Trail list that you have read.
        // TODO: Your code here
        Set<Location> visited = new HashSet<>();


        // Pick any vertex to start with and add its edges to the priority queue.
        Location start = locations.get(0);
        visited.add(start);
        PriorityQueue<Trail> pq = new PriorityQueue<>(Comparator.comparingInt(t -> t.danger));
        pq.addAll(getEdges(start));

        // Repeat until all vertices are visited.
        while (visited.size() < locations.size()) {
            Trail trail = pq.poll();
            Location source = trail.source;
            Location destination = trail.destination;

            // Skip edges that connect two visited vertices.
            if (visited.contains(source) && visited.contains(destination)) {
                continue;
            }

            // Mark the unvisited vertex as visited, add the edge to the MST, and add its edges to the priority queue.
            Location next = visited.contains(source) ? destination : source;
            visited.add(next);
            safestTrails.add(trail);
            pq.addAll(getEdges(next));
        }


        return safestTrails;
    }

    public void printSafestTrails(List<Trail> safestTrails) {
        // Print the given list of safest trails conforming to the given output format.
        // TODO: Your code here
        // Print the MST edges and their total danger.
        System.out.println("Safest trails are:");
        int totalDanger = 0;
        for (Trail trail : safestTrails) {
            System.out.printf("The trail from %s to %s with danger %d\n", trail.source.name, trail.destination.name, trail.danger);
            totalDanger += trail.danger;
        }
        System.out.printf("Total danger: %d\n", totalDanger);

    }

    private List<Trail> getEdges(Location location) {
        List<Trail> edges = new ArrayList<>();
        for (Trail trail : trails) {
            if (trail.source == location || trail.destination == location) {
                edges.add(trail);
            }
        }
        return edges;
    }
}

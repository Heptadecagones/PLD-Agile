package model.algo.Dijkstra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import model.*;

public class CompleterGraph {

    Graph graphe;
    ArrayList<Node> listeDestination;

    public CompleterGraph(Plan plan, Livraison livraison) {

        ArrayList<Intersection> listeIntersection = plan.obtenirListeIntersection();
        ArrayList<Segment> listeSegment = plan.obtenirListeSegment();

        this.graphe = new Graph();
        this.listeDestination = new ArrayList<Node>();

        for(Intersection intersection : listeIntersection) {
            Node tempNode = new Node(intersection.obtenirId());
            this.graphe.ajouterNode(tempNode);
        }

        Node origine = null;
        Node destination = null;
        int c = 0;

        for(Segment segment : listeSegment) {
            c = 0;
            for(Node node : this.graphe.obtenirNodes()) {
                if (node.obtenirNom().equals(segment.obtenirOrigine().obtenirId())) {
                    origine = node;
                    c++;
                }

                if (node.obtenirNom().equals(segment.obtenirDestination().obtenirId())) {
                    destination = node;
                    c++;
                }

                if (c > 1) break;
            }

            if (origine != null && destination != null) origine.ajouterDestination(destination, segment.obtenirLongueur());
        }

        for(Node node : this.graphe.obtenirNodes()) {
            if (node.obtenirNom().equals(plan.obtenirEntrepot().obtenirId())) {
                listeDestination.add(node);
                break;
            }
        }

        for (Livraison dest : livraison.obtenirLivreur().obtenirLivraisons()) {
            for(Node node : this.graphe.obtenirNodes()) {
                if (node.obtenirNom().equals(dest.obtenirLieu().obtenirId())) {
                    listeDestination.add(node);
                    break;
                }
            }
        }
    }

    public ArrayList<Segment> calculerTournee() {

        ArrayList<Segment> tournee = new ArrayList<Segment>();
        Graph grapheTSP = new Graph();
        Node nodeSource = null;

        for (Node node : listeDestination) {
            Node temp = new Node(node.obtenirNom());
            grapheTSP.ajouterNode(temp);
        }
        
        for (Node source : this.listeDestination) {

            //récupérer le node source du graphe TSP
            for (Node n : grapheTSP.obtenirNodes()) {
                if (n.obtenirNom().equals(source.obtenirNom())) {
                    nodeSource = n;
                    break;
                }
            }
            
            // Copie les nodes du graphe dans un nouvel ensemble
            Set<Node> grapheNodes = graphe.obtenirNodes().stream().map(Node::new).collect(Collectors.toSet());
            Graph dijkstraGraph = new Graph(grapheNodes);
            //Calculer Dijkstra avec la source
            dijkstraGraph = calculerPlusCourtCheminDepuisLaSource(dijkstraGraph, source);

            //Ajouter les noeuds/valeurs pour graphe TSP
            for (Node destNode : grapheTSP.obtenirNodes()) {
                for (Node node : dijkstraGraph.obtenirNodes()) {
                    if(destNode.obtenirNom().equals(node.obtenirNom()) && nodeSource != null) {
                        nodeSource.ajouterDestination(destNode, node.obtenirDistance());
                        break;
                    }
                }
            }

            //reset la valeur des noeuds pour l itération suivante
            for (Node node : graphe.obtenirNodes()) {
                node.modifierCheminPlusCourt(new LinkedList<>());
                node.modifierDistance(Integer.MAX_VALUE);
            }
        }

        //ici grapheTSP est bon pour faire la tournée
        return tournee;
    }

    private static void calculerDistanceMinimale(Node evaluationNode, Double edgeWeigh, Node sourceNode) {
        double sourceDistance = sourceNode.obtenirDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.obtenirDistance()) {
            evaluationNode.modifierDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.obtenirCheminPlusCourt());
            shortestPath.add(sourceNode);
            evaluationNode.modifierCheminPlusCourt(shortestPath);
        }
    }

    private static Node obtenirNodeDistanecPlusCourte(Set < Node > unsettledNodes) {
        Node nodeDistanecPlusCourte = null;
        double plusCouteDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            double nodeDistance = node.obtenirDistance();
            if (nodeDistance < plusCouteDistance) {
                plusCouteDistance = nodeDistance;
                nodeDistanecPlusCourte = node;
            }
        }
        return nodeDistanecPlusCourte;
    }

    public static Graph calculerPlusCourtCheminDepuisLaSource(Graph graphe, Node source) {
        source.modifierDistance(0);
    
        Set<Node> nodesResolus = new HashSet<>();
        Set<Node> nodesNonResolus = new HashSet<>();
    
        nodesNonResolus.add(source);
    
        while (nodesNonResolus.size() != 0) {
            Node nodeActuel = obtenirNodeDistanecPlusCourte(nodesNonResolus);
            nodesNonResolus.remove(nodeActuel);
            for (Entry < Node, Double> adjacencyPair: 
            nodeActuel.obtenirNodeAdjacentes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                double edgeWeight = adjacencyPair.getValue();
                if (!nodesResolus.contains(adjacentNode)) {
                    calculerDistanceMinimale(adjacentNode, edgeWeight, nodeActuel);
                    nodesNonResolus.add(adjacentNode);
                }
            }
            nodesResolus.add(nodeActuel);
        }
        return graphe;
    }
}
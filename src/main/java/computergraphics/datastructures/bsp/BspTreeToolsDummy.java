package computergraphics.datastructures.bsp;

import java.util.ArrayList;
import java.util.List;

import computergraphics.datastructures.bsp.BspTreeNode.Orientation;
import computergraphics.math.PrincipalComponentAnalysis;
import computergraphics.math.Vector;

public class BspTreeToolsDummy {
	/**
	 * Recursively create a BSP tree for a given set of points.
	 * 
	 * @param parentNode
	 *            Parent scene graph node
	 * @param allPoints
	 *            List with all point positions in the dataset
	 * @param Set
	 *            if indices used in the current recursive call
	 */
	public BspTreeNode createBspTree(BspTreeNode parentNode, List<Vector> allPoints, List<Integer> pointIndices) {
		BspTreeNode bspTreeNode = new BspTreeNode();
		// As soon as there are only two elements left in a List, we need to
		// compute the BSPTree differently - see else
		if (allPoints.size() > 2) {

			// Prepare PCA for Analysis
			PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis();
			for (Vector v : allPoints) {
				pca.add(v);
			}
			pca.applyPCA();

			// Now we know centroid and normal - apply them to the parentNode
			bspTreeNode.setN(pca.getEigenVector(pca.getHighestEigenValueIndex())); // Normal
			bspTreeNode.setP(pca.getCentroid()); // Centroid

			// Add Points to positive or negative List of BspTreeNode
			// add these vectors to a new List of pos/neg for recursive call
			List<Vector> pos = new ArrayList<>();
			List<Vector> neg = new ArrayList<>();

			for (int i = 0; i < allPoints.size(); i++) {
				Vector v = allPoints.get(i);
				Integer iV = pointIndices.get(i);
				if (bspTreeNode.IsPositive(v)) {
					bspTreeNode.AddElement(Orientation.POSITIVE, iV);
					pos.add(v);
				} else {
					bspTreeNode.AddElement(Orientation.NEGATIVE, iV);
					neg.add(v);
				}
			}

			List<Integer> positive = bspTreeNode.getElementsPos();
			List<Integer> negative = bspTreeNode.getElementsNeg();
			// Recursive call and setting Child
			if (allPoints.size() > 3) {
				// more than 3 Points mean treeNode has two children
				bspTreeNode.SetChild(Orientation.POSITIVE, createBspTree(bspTreeNode, pos, positive));
				bspTreeNode.SetChild(Orientation.NEGATIVE, createBspTree(bspTreeNode, neg, negative));
			} else {
				// 3 points mean that treeNode has only one child!
				if (positive.size() == 1) {
					// positive side is leaf
					bspTreeNode.SetChild(Orientation.NEGATIVE, createBspTree(bspTreeNode, neg, negative));
				} else {
					// negative side is leaf
					bspTreeNode.SetChild(Orientation.POSITIVE, createBspTree(bspTreeNode, pos, positive));
				}
			}
		} else {
			// Case 2: only two Leafs left --> subdivision with
			// connection-vector
			Vector v1 = allPoints.get(0);
			Vector v2 = allPoints.get(1);
			Integer i1 = pointIndices.get(0);
			Integer i2 = pointIndices.get(1);

			// find connection-vector between both points
			Vector connection = v2.subtract(v1);
			// get middle of both points
			Vector normal = connection.multiply(0.5);
			// find exact point between both
			Vector point = v1.add(normal);
			// add information to treeNode: i1 = negative && i2 = positive
			bspTreeNode.AddElement(Orientation.POSITIVE, i2);
			bspTreeNode.AddElement(Orientation.NEGATIVE, i1);
			bspTreeNode.setN(normal);
			bspTreeNode.setP(point);
		}
		return bspTreeNode;
	}

	/**
	 * Compute the back-to-front ordering for all points in 'points' based on
	 * the tree in 'node' and the given eye position
	 * 
	 * I guess there would be a better pattern to avoid these 'recalls' of same functions
	 * but honestly I have no time anymore :(
	 * 
	 * @param node
	 *            Root node of the BSP tree
	 * @param points
	 *            List of points to be considered
	 * @param eye
	 *            Observer position
	 * @return Sorted (back-to-front) list of points
	 */
	public List<Integer> getBackToFront(BspTreeNode node, List<Vector> points, Vector eye) {
		List<Integer> sortedList = new ArrayList<>();

		BspTreeNode positive = node.GetChild(Orientation.POSITIVE);
		BspTreeNode negative = node.GetChild(Orientation.NEGATIVE);
		// Reduce pointsList by pos/neg Elements

		if (positive == null && negative == null) {
			// Case 1: no children available
			Integer iPos = node.getElement(Orientation.POSITIVE, 0);
			Integer iNeg = node.getElement(Orientation.NEGATIVE, 0);
			Integer leaf1;
			Integer leaf2;
			if (node.IsPositive(eye)) {
				// eye on positive part --> negative Index first
				leaf1 = iNeg;
				leaf2 = iPos;
			} else {
				// eye on negative part --> positive Index first
				leaf1 = iPos;
				leaf2 = iNeg;
			}
			// add to List
			sortedList.add(leaf1);
			sortedList.add(leaf2);
		} else if (positive == null || negative == null) {
			// Case 2: only negative child available
			Integer leaf;
			BspTreeNode bsp;
			
			// check which child is null and set variables according to result
			if (positive == null) {
				leaf = node.getElement(Orientation.POSITIVE, 0);
				bsp = negative;
			} else {
				leaf = node.getElement(Orientation.NEGATIVE, 0);
				bsp = positive;
			}
			// check which order needs to be chosen and make recursive call
			if (node.IsPositive(eye)) {
				// leaf is NOT the most distant point
				sortedList.addAll(getBackToFront(bsp, points, eye));
				sortedList.add(leaf);
			} else {
				// leaf IS the most distant point
				sortedList.add(leaf);
				sortedList.addAll(getBackToFront(bsp, points, eye));
			}
		} else {
			// Case 4: both children available
			BspTreeNode bsp1;
			BspTreeNode bsp2;
			if (node.IsPositive(eye)) {
				// eye on positive part --> negative part in recursion first
				bsp1 = negative;
				bsp2 = positive;
			} else {
				// eye on negative part --> positive part in recursion first
				bsp1 = positive;
				bsp2 = negative;
			}
			sortedList.addAll(getBackToFront(bsp1, points, eye));
			sortedList.addAll(getBackToFront(bsp2, points, eye));
		}
		return sortedList;
	}

	public static void main(String[] args) {

		// Test 0 - BspTreeNode Testing
		// Vector x = new Vector(1.0, 1.0, 0);
		// Vector y = new Vector(3.0, 3.0, 0);
		//
		// Vector p = new Vector(2.0, 2.0, 0);
		// Vector n = new Vector(0.5, 0.5, 0);
		//
		// Vector[] ary = { x, y };
		// Integer[] aryI = { 0, 1 };
		// List<Vector> points = Arrays.asList(ary);
		// List<Integer> pointsIndices = Arrays.asList(aryI);
		// BspTreeToolsDummy tools = new BspTreeToolsDummy();
		// BspTreeNode bspTreeNode = tools.createBspTree(null, points,
		// pointsIndices);
		//
		// System.out.println(bspTreeNode.toString());
		// System.out.println("should negative: " + bspTreeNode.IsPositive(x) +
		// "\n");
		// System.out.println("should positive: " + bspTreeNode.IsPositive(y) +
		// "\n");

		// should be true but is false

		// Set 1 - test passed
		// Vector a1 = new Vector(1.0, 1.0, 0); // 0
		// Vector b1 = new Vector(2.0, 2.0, 0); // 1
		// Vector c1 = new Vector(4.0, 4.0, 0); // 2
		// Vector d1 = new Vector(5.5, 5.5, 0); // 3
		//
		// Vector[] ary = { a1, b1, c1, d1 };
		// List<Vector> points1 = Arrays.asList(ary);
		// Integer[] aryI1 = { 0, 1, 2, 3 };
		// List<Integer> pointsIndices1 = Arrays.asList(aryI1);
		// Vector eye1 = new Vector(5.5, 2.0, 0);
		//
		// BspTreeToolsDummy tools1 = new BspTreeToolsDummy();
		// BspTreeNode bspTreeNode1 = tools1.createBspTree(null, points1,
		// pointsIndices1);
		// System.out.println("positives" + bspTreeNode1.getElementsPos());
		// System.out.println("negatives" + bspTreeNode1.getElementsNeg());
		// System.out.println(bspTreeNode1.toString() + " stop\n\n");
		// System.out.println(tools1.getBackToFront(bspTreeNode1, points1,
		// eye1));

		// Set 2 - test passed
		// Vector a2 = new Vector(1.0, 1.0, 0);
		// Vector b2 = new Vector(2.0, 2.0, 0);
		// Vector c2 = new Vector(1.5, 3.0, 0);
		// Vector d2 = new Vector(3.0, 4.5, 0);
		// Vector e2 = new Vector(4.0, 5.5, 0);
		//
		// Vector[] ary2 = { a2, b2, c2, d2, e2 };
		// List<Vector> points2 = Arrays.asList(ary2);
		// Integer[] aryI2 = { 0, 1, 2, 3, 4 };
		// List<Integer> pointsIndices2 = Arrays.asList(aryI2);
		// BspTreeToolsDummy tools2 = new BspTreeToolsDummy();
		// BspTreeNode bspTreeNode2 = tools2.createBspTree(null, points2,
		// pointsIndices2);
		// System.out.println(bspTreeNode2.toString());
		// Vector eye2 = new Vector(2.5, 5.5, 0);
		// System.out.println(tools2.getBackToFront(bspTreeNode2, points2,
		// eye2));

		// Set 3: from BspScene
		// Create data
		// int numberOfPoints = 10;
		// List<Vector> points = new ArrayList<Vector>();
		// List<Integer> pointIndices = new ArrayList<Integer>();
		// for (int i = 0; i < numberOfPoints; i++) {
		// points.add(new Vector((float) (2 * Math.random() - 1), (float) (2 *
		// Math.random() - 1), 0));
		// pointIndices.add(i);
		// }
		// System.out.println("points: " + points);
		// System.out.println("pointIndices: " + pointIndices);
		// // Create tree
		// BspTreeToolsDummy tools = new BspTreeToolsDummy();
		// BspTreeNode rootNode = tools.createBspTree(null, points,
		// pointIndices);
		// System.out.println(rootNode.toString());
		// Vector observer = new Vector(1, 1, 0);
		// List<Integer> back2FrontSorted = tools.getBackToFront(rootNode,
		// points, observer);
		// System.out.println(back2FrontSorted);

	}
}

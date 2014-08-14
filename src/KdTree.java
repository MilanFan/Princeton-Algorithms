/**
 * Princeton Algorithms Assignment 5: Kd-Trees
 * 
 * @author Milan Fan
 *
 */
public class KdTree {

	private static class KdNode {
		private double x;
		private double y;
		private KdNode left;
		private KdNode right;
		private boolean vertical;

		public KdNode(double x, double y, KdNode l, KdNode r, boolean v) {
			this.x = x;
			this.y = y;
			left = l;
			right = r;
			vertical = v;
		}
	}

	private KdNode root;
	private int N;

	// construct an empty set of points
	public KdTree() {
		root = null;
		N = 0;
	}

	// is the set empty?
	public boolean isEmpty() {
		return N == 0;
	}

	// number of points in the set
	public int size() {
		return N;
	}

	private KdNode insert(KdNode n, Point2D p, boolean v) {
		if (n == null) {
			N++;
			return new KdNode(p.x(), p.y(), null, null, v);
		}
		if (p.x() == n.x && p.y() == n.y) return n;
		if (n.vertical && p.x() < n.x
				|| !n.vertical && p.y() < n.y)
			n.left = insert(n.left, p, !n.vertical);
		else
			n.right = insert(n.right, p, !n.vertical);
		return n;
	}

	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p != null) root = insert(root, p, true);
	}
	
	private boolean contains(KdNode n, Point2D query) {
		if (n == null) return false;
		if (n.x == query.x() && n.y == query.y()) return true;
		if (n.vertical && query.x() < n.x
				|| !n.vertical && query.y() < n.y)
			return contains(n.left, query);
		else
			return contains(n.right, query);
	}

	// does the set contain the point p?
	public boolean contains(Point2D p) {
		if (p == null) return false;
		return contains(root, p);
	}
	
	private RectHV subrect(KdNode n, RectHV r, boolean isleft) {
		if (n.vertical) {
			if (isleft) return new RectHV(r.xmin(), r.ymin(), n.x, r.ymax());
			else 		return new RectHV(n.x, r.ymin(), r.xmax(), r.ymax());
		} else {
			if (isleft) return new RectHV(r.xmin(), r.ymin(), r.xmax(), n.y);
			else 		return new RectHV(r.xmin(), n.y, r.xmax(), r.ymax());
		}
	}

	private void draw(KdNode n, RectHV r) {
		if (n == null) return;
		Point2D p = new Point2D(n.x, n.y);
		StdDraw.setPenColor(StdDraw.BLACK);
		p.draw();
		if (n.vertical) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(n.x, r.ymin(), n.x, r.ymax());
			draw(n.left, subrect(n, r, true));
			draw(n.right, subrect(n, r, false));
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(r.xmin(), n.y, r.xmax(), n.y);
			draw(n.left, subrect(n, r, true));
			draw(n.right, subrect(n, r, false));
		}
	}

	// draw all of the points to standard draw
	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
		r.draw();
		draw(root, r);
	}

	private void range(KdNode n, RectHV r, RectHV rect, Queue<Point2D> q) {
		if (n == null) return;
		if (r.intersects(rect)) {
			Point2D p = new Point2D(n.x, n.y);
			if (rect.contains(p)) q.enqueue(p);
			range(n.left, subrect(n, r, true), rect, q);
			range(n.right, subrect(n, r, false), rect, q);
		}
	}

	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if (isEmpty() || rect == null) return null;
		Queue<Point2D> q = new Queue<Point2D>();
		RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
		range(root, r, rect, q);
		return q;
	}

	private Point2D nearest(KdNode n, RectHV r, Point2D query, Point2D champion) {
		if (n == null) return champion;
		Point2D p = new Point2D(n.x, n.y);
		Point2D ret = champion;
		if (champion == null
				|| query.distanceSquaredTo(p) < query.distanceSquaredTo(champion))
			ret = new Point2D(n.x, n.y);
		
		if (r.distanceSquaredTo(query) < ret.distanceSquaredTo(query)) {
			if (n.vertical && query.x() < n.x
					|| !n.vertical && query.y() < n.y) {
				ret = nearest(n.left, subrect(n, r, true), query, ret);
				ret = nearest(n.right, subrect(n, r, false), query, ret);
			} else {
				ret = nearest(n.right, subrect(n, r, false), query, ret);
				ret = nearest(n.left, subrect(n, r, true), query, ret);				
			}
		}

		return ret;
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p) {
		if (p == null) return null;
		RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
		return nearest(root, r, p, null);
	}
}

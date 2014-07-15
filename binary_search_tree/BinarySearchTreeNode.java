/**
 * 
 * @author Teodor Östlund
 * @author Mikael Rosquist
 * 
 */

package alda.tree;

public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	/**
	 * Lagger till en nod i det binara soktradet. Om noden redan existerar sa
	 * lamnas tradet oforandrat.
	 * 
	 * @param data
	 *            datat for noden som ska laggas till.
	 * @return true om en ny nod lades till tradet.
	 */
	public boolean add(T data) {
		if ((data.compareTo(this.data)) < 0)
			if (left == null) {
				left = new BinarySearchTreeNode<T>(data);
				return true;
			} else
				return left.add(data);
		else if ((data.compareTo(this.data)) > 0)
			if (right == null) {
				right = new BinarySearchTreeNode<T>(data);
				return true;
			} else
				return right.add(data);
		else
			return false;
	}

	/**
	 * Privat hjalpmetod som ar till nytta vid borttag. Ni behover inte
	 * skriva/utnyttja denna metod om ni inte vill.
	 * 
	 * @return det minsta elementet i det (sub)trad som noden utgor root i.
	 */
	public T findMin() {
		if (left != null)
			return left.findMin();
		return data;
	}

	/**
	 * Tar bort ett element ur tradet. Om elementet inte existerar s lamnas
	 * tradet oforandrat.
	 * 
	 * @param data
	 *            elementet som ska tas bort ur tradet.
	 * @return en referens till nodens subtrad efter borttaget.
	 */
	public BinarySearchTreeNode<T> remove(T data) {

		if ((data.compareTo(this.data)) < 0) { // Söker till vänster
			if (left != null)
				left = left.remove(data);
			return this;
		} else if ((data.compareTo(this.data)) > 0) { // Söker till höger
			if (right != null)
				right = right.remove(data);
			return this;
		} else if (left != null && right != null) { // Om noden har två barn
			this.data = right.findMin();
			right = right.remove(this.data);
			return this;
		} else if (left == null && right == null) // Om noden är ett löv
			return null;
		else // Om noden har ett barn
			if (left != null && right == null) { // Om noden har ett barn till vänter
				this.data = left.data;
				right = left.right;
				left = left.left;
				return this;
			} else { // Om noden har ett barn till höger
				this.data = right.data;
				left = right.left;
				right = right.right;
				return this;
			}
	}

	/**
	 * Kontrollerar om ett givet element finns i det (sub)trad som noden utgor
	 * root i.
	 * 
	 * @param data
	 *            det sokta elementet.
	 * @return true om det sokta elementet finns i det (sub)trad som noden utgor
	 *         root i.
	 */
	public boolean contains(T data) {
		if (data == null) // Om noden inte finns
			return false;
		else if ((data.compareTo(this.data)) < 0) // Gå ner i vänster ben och leta
			if (left != null)
				return left.contains(data);
			else
				return false;
		else if ((data.compareTo(this.data)) > 0) // Gå ner i höger ben och leta
			if (right != null)
				return right.contains(data);
			else
				return false;
		else // Om noden finns
			return true;
	}

	/**
	 * Storleken pa det (sub)trad som noden utgor root i.
	 * 
	 * @return det totala antalet noder i det (sub)trad som noden utgor root i.
	 */
	public int size() {
		if (left != null && right != null) // Om noden har två barn
			return left.size() + 1 + right.size();
		else if (left != null && right == null) // Om noden har ett barn till vänter
			return left.size() + 1;
		else if (right != null && left == null) // /Om noden har ett barn till höger
			return right.size() + 1;
		else // Om noden är ett löv
			return 1;
	}

	/**
	 * Det hogsta djupet i det (sub)trad som noden utgor root i.
	 * 
	 * @return djupet.
	 */
	public int depth() {
		if (left != null && right != null) // Nod med två barn
			return Math.max(left.depth(), right.depth()) + 1;
		else if (left != null && right == null) // Nod med ett barn till vänster
			return left.depth() + 1;
		else if (right != null && left == null) // Nod med ett barn till höger
			return right.depth() + 1;
		else
			return 0;
	}

	/**
	 * Returnerar en strangrepresentation for det (sub)trad som noden utgor root
	 * i. Denna representation bestar av elementens dataobjekt i sorterad
	 * ordning med ", " mellan elementen.
	 * 
	 * @return strangrepresentationen for det (sub)trad som noden utgor root i.
	 */
	public String toString() {
		if (left != null && right != null) // Nod med två barn
			return left.toString() + ", " + data + ", " + right.toString();
		else if (left != null && right == null) // Nod med ett barn till vänster
			return left.toString() + ", " + data;
		else if (right != null && left == null) // Nod med ett barn till höger
			return data + ", " + right.toString();
		else
			return "" + data;
	}
}
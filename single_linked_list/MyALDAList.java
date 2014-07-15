/**
 * 
 * @author Teodor Östlund
 * @author Mikael Rosquist
 * 
 */

package alda.linear;
import java.util.Iterator;

public class MyALDAList<E> implements ALDAList<E> {

	private Node<E> first;
	private Node<E> current;
	private Node<E> newNode;
	private Node<E> removeNode;
	private String str;
	private int listSize = 0;

	private static class Node<E> {
		E data;
		Node<E> next;

		public Node(E data) {
			this.data = data;
		}
	}

	public void add(E data){
		if (first == null){
			first = new Node<E>(data);
		} else {
			current = first;
			for(int i=0;i<listSize-1;i++){
				current = current.next;
			}
			current.next = new Node<E>(data);
		}
		listSize++;
	}

	public void add(int index, E data){
		if((index < 0) || (index > listSize)){
			throw new IndexOutOfBoundsException();
		}else{
			current = first;
			if (index == 0){
				first = new Node<E>(data);
				first.next = current;
			}else{
				for(int i = 0; i < index-1; i++){
					current = current.next;				
				}
				newNode = new Node<E>(data);
				newNode.next = current.next;
				current.next = newNode;
			}
		}
		listSize++;
	}

	public E remove(int index){

		current = first;
		if ((index < 0) || (index > listSize-1)){
			throw new IndexOutOfBoundsException();
		}else if (index == 0){
			first = first.next;
		}else{
			for(int i = 0; i < index-1; i++){
				current = current.next;
			}
			removeNode = current.next;
			current.next = removeNode.next;
		}
		listSize--;
		return null;
	}

	public boolean remove(E data){
		int index = 0;
		current = first;
		for(Node<E> nod=first; nod!=null; nod=nod.next){
			if(nod.data==data || nod.equals(data)){
				removeNode = nod;
				break;
			}
			index++;
			if(index==listSize){
				return false;
			}
		}
		if(index == 0){
			first = first.next;
		}else{
			for(int i = 0; i < index-1; i++){
				current = current.next;
			}
			current.next = removeNode.next;
		}
		listSize--;
		return true;
	}//remove

	public E get(int index){

		if ((index < 0) || (index >= listSize)){
			throw new IndexOutOfBoundsException();
		}

		Node<E> current = this.first;
		if (index > 0) {
			index--;
			for(int i=-1;i<index;i++){
				current = current.next;
			}
		}
		return current.data;
	}

	public boolean contains(E data){
		for(Node<E> nod=first; nod!=null; nod=nod.next){
			if(nod.data==data || nod.equals(data)){
				return true;
			}
		}
		return false;
	}

	public int indexOf(E data){
		int index = -1;
		for(Node<E> nod=first; nod!=null; nod=nod.next){
			index++;
			if(nod.data==data || nod.equals(data)){
				return index;
			}
		}
		index = -1;
		return index;
	}

	public void clear(){
		listSize = 0;
		first = null;
	}
	
	public int size(){
		return listSize;
	}

	public String toString(){

		Node<E> current = this.first;
		str = "[";
		if(listSize > 0){
			for(int i=0;i<listSize;i++){
				if(i>0){
					current = current.next;
				}
				str += (String)current.data;

				if(i<listSize-1){
					str += ", ";
				}
			}
		}
		str += "]";
		return str;
	}

	public Iterator<E> iterator(){
		return new ListIterator ();
	}

	private class ListIterator implements Iterator<E>{

		private Node<E> current = first;
		private Node<E> temp;
		private boolean remove = false;

		public boolean hasNext(){
			return current != null;
		}
		public E next(){
			if(!hasNext()){
				throw new java.util.NoSuchElementException();
			}
			E data = current.data;
			temp = current;
			current = current.next;
			remove = true;
			return data;
		}
		public void remove(){
			if(remove == false){
				throw new java.lang.IllegalStateException();
			}
			MyALDAList.this.remove(temp.data);
			remove = false;
		}
	}
}



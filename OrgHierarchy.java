import java.io.*; 
import java.util.*; 

// Tree node
class Node {
	Node Boss;
	Vector<Node> children=new Vector<Node>();
	int level;
	int id;
	Node leftchild=null;
	Node rightchild=null;
	int height=-1;
}


class AVLTree{
	Node root=null;

	int size=0;
	int height(Node z){
		if(z==null) return -1;
		return z.height;
	}

	Node rotate_left(Node z){
		Node t=z.rightchild.leftchild;
		Node y=z.rightchild;
		z.rightchild.leftchild=z;
		z.rightchild=t;
		z.height=1+Math.max(height(z.leftchild),height(z.rightchild));
		y.height=1+Math.max(height(y.leftchild),height(y.rightchild));
		return y;
	}

	Node rotate_right(Node z){
		Node t=z.leftchild.rightchild;
		Node y=z.leftchild;
		z.leftchild.rightchild=z;
		z.leftchild=t;
		z.height=1+Math.max(height(z.leftchild),height(z.rightchild));
		y.height=1+Math.max(height(y.leftchild),height(y.rightchild));
		return y;
	}

	boolean is_balanced(Node x){
	
		return Math.abs( height(x.leftchild)- height(x.rightchild))<=1;
	}

	Node minValueNode(Node x){
        while (x.leftchild != null)  
        x = x.leftchild;  
  
        return x;
	}

	Node Search(Node x,int id){
		if (x==null || id==x.id) return x;
		else if(id>x.id) return Search(x.rightchild,id);
		else return Search(x.leftchild,id);
	}

	Node insert(Node x,int id,Node boss, int level){
		if(x==null){
			x=new Node();
			x.id=id;
			x.Boss=boss;
			x.level=level;
			x.height=0;
			size++;
			return x;
		}
		if (id < x.id){
            x.leftchild = insert(x.leftchild, id, boss,level);
		}
		else if(id > x.id){
            x.rightchild = insert(x.rightchild, id,boss,level);
		}
		else{
			return x;
		}
		if(! is_balanced(x)){

			try {
				if (id < x.leftchild.id)
					return rotate_right(x);

				else if(id > x.leftchild.id) {
					x.leftchild = rotate_left(x.leftchild);
					return rotate_right(x);
				}
			} catch (Exception e) {}

			try{
				if(id > x.rightchild.id)
					return rotate_left(x);
				
				else if(id < x.rightchild.id) {
					x.rightchild = rotate_right(x.rightchild);
					return rotate_left(x);
				}
			}
			catch(Exception e){}

		}

		x.height = 1 + Math.max(height(x.leftchild),height(x.rightchild));
		return x;
	}

	Node delete(Node x,int id){
		if (x==null){
			return x;
		}
		else if(x.id>id){
			x.leftchild= delete(x.leftchild,id);
		}
		else if(x.id<id){
			x.rightchild= delete(x.rightchild,id);
		}
		else{
			if(x.rightchild==null || x.leftchild==null){
				size--;
				if(x.rightchild==null) return x.leftchild;
				else return x.rightchild;
			}
			else{
				Node temp=x;
				x=minValueNode(x.rightchild);
				x.rightchild=delete(temp.rightchild,x.id);
				x.leftchild=temp.leftchild;
			}
		}
		x.height=1+Math.max(height(x.leftchild),height(x.rightchild));
		if(!is_balanced(x)){
		
			if(height(x.leftchild)>height(x.rightchild)){
				//Left-Left case
				if(height(x.leftchild.leftchild)>=height(x.leftchild.rightchild))
					return rotate_right(x);
				//Left-Right case
				else{
					x.leftchild=rotate_left(x.leftchild);
					return rotate_right(x);
				}
			}
			else{
				//Right-Right case
				if(height(x.rightchild.rightchild)>=height(x.rightchild.leftchild))
					return rotate_left(x);
				//Right-Left case
				else{
					x.rightchild=rotate_right(x.rightchild);
					return rotate_left(x);
				}
			}
		}
		return x;

	}

}


public class OrgHierarchy implements OrgHierarchyInterface{

	AVLTree tree=new AVLTree();
	//root node
	Node root=tree.root;

	public boolean isEmpty(){
		//your implementation
		return (tree.size==0);
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
	} 

	public int size(){
		//your implementation'
		return tree.size;
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public int level(int id) throws IllegalIDException, EmptyTreeException{
		//your implementation
		if(tree.size==0)
			throw new EmptyTreeException("Organization is empty");
		Node employee=tree.Search(tree.root,id);
		if(employee==null)
			throw new IllegalIDException("ID not found");
		return employee.level;
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	} 

	public void hireOwner(int id) throws NotEmptyException{
		//your implementation
		if(tree.size!=0)
			throw new NotEmptyException("Already Owned By Somebody");
		tree.root=tree.insert(tree.root,id,null,1);
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{
		//your implementation
		if(tree.size==0)
			throw new EmptyTreeException("Organization is Empty");
		Node boss=tree.Search(tree.root,bossid);
		if(boss==null || tree.Search(tree.root,id)!=null)
			throw new IllegalIDException("ID not acceptable");
		tree.root=tree.insert(tree.root,id,boss,boss.level+1);
		Node child=tree.Search(tree.root,id);
		boss.children.add(child);
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	} 

	public void fireEmployee(int id) throws IllegalIDException,EmptyTreeException{
		//your implementation
		if(tree.size==0)
			throw new EmptyTreeException("Organisation is empty");
		Node employee=tree.Search(tree.root,id);
		if( employee==null || employee.level==1)
			throw new IllegalIDException("Cannot remove owner or Id not found");
		
		employee.Boss.children.remove(employee);
		tree.root=tree.delete(tree.root,id);
		// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}
	public void fireEmployee(int id, int manageid) throws IllegalIDException,EmptyTreeException{
		//your implementation
		if (tree.size==0)
			throw new EmptyTreeException("Organisation is empty");
		Node employee=tree.Search(tree.root,id);
		Node substitute=tree.Search(tree.root,manageid);
		if(employee==null || substitute==null || employee.level==1)
			throw new IllegalIDException("Check your IDs");

		for(int i=0;i<employee.children.size();i++){
			employee.children.get(i).Boss=substitute;
			substitute.children.add(employee.children.get(i));
		}

		employee.Boss.children.remove(employee);
		tree.root=tree.delete(tree.root,id);
		//  throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	} 

	public int boss(int id) throws IllegalIDException,EmptyTreeException{
		//your implementation
		if (tree.size==0)
			throw new EmptyTreeException("Organisation is empty");
		Node employee=tree.Search(tree.root,id);
		if(employee==null)
			throw new IllegalIDException("not a valid id");
		if(employee.Boss==null)
			return -1;
		return employee.Boss.id;
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public int lowestCommonBoss(int id1, int id2) throws IllegalIDException,EmptyTreeException{
		//your implementation
		if (tree.size==0)
			throw new EmptyTreeException("Organisation is empty");
		Node employee1=tree.Search(tree.root,id1);
		Node employee2=tree.Search(tree.root,id2);
		if(employee1==null || employee2==null)
			throw new IllegalIDException("not a valid id");
		if(employee1.Boss==null || employee2.Boss==null)
			return -1;
		int gap= Math.abs(employee1.level - employee2.level);
		for (int i=0;i<gap;i++){
			if(employee1.level>employee2.level){
				employee1=employee1.Boss;
			}
			else
				employee2=employee2.Boss;
		}
		employee1=employee1.Boss;
		employee2=employee2.Boss;
		while(employee2!=employee1){
			employee1=employee1.Boss;
			employee2=employee2.Boss;
		}
		return employee1.id;
		// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public String toString(int id) throws IllegalIDException, EmptyTreeException{
		//your implementation
		
		if (tree.size==0)
			throw new EmptyTreeException("Organisation is empty");
		Node employee=tree.Search(tree.root,id);
		//System.out.println(employee);
		if(employee==null)
			throw new IllegalIDException("not a valid id");
		
		StringBuffer s=new StringBuffer();

		int i=0;
		Node initial=employee;
		Vector<Node> queue= new Vector<Node>();
		queue.add(initial);
		queue.add(null);
		Vector<Integer> level=new Vector<Integer>();
		for(int j=0;j<queue.get(i).children.size();j++){
			queue.add(queue.get(i).children.get(j));
		}
		queue.add(null);
		s.append(initial.id);
		i=2;
		if(queue.get(1)==queue.get(2) && queue.get(2)==null) return s.toString();
		
		while(i!=queue.size()){
			if(queue.get(i)!=null){
				level.add(queue.get(i).id);
				for(int j=0;j<queue.get(i).children.size();j++){
					queue.add(queue.get(i).children.get(j));
				}
			}
			else{
				Collections.sort(level);
				s.append(",");
				s.append(level.get(0));
				for(int j=1;j<level.size();j++){
					s.append(" ");
					s.append(level.get(j));
				}
				level.clear();
				queue.add(null);
				if(queue.get(i)==queue.get(i+1))
					break;
			}	
			i++;
		}
		return s.toString();
		//  throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

}

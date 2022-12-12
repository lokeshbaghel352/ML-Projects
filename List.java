
class list_Node {
    Node data;
    list_Node next;
    list_Node(Node d)
    {
        data = d;
        next = null;
    }
}

public class List {
    list_Node head; 
 
    /* Linked list list_Node*/
    // class list_Node {
    //     Node data;
    //     list_Node next;
    //     list_Node(Node d)
    //     {
    //         data = d;
    //         next = null;
    //     }
    // }
 
    /* Given a key, deletes the first
       occurrence of key in
     * linked list */
    void remove(Node key)
    {
        // Store head list_Node
        list_Node temp = head, prev = null;
 
        // If head list_Node itself holds the key to be deleted
        if (temp != null && temp.data == key) {
            head = temp.next; // Changed head
            return;
        }
 
        // Search for the key to be deleted, keep track of
        // the previous list_Node as we need to change temp.next
        while (temp != null && temp.data != key) {
            prev = temp;
            temp = temp.next;
        }
 
        // If key was not present in linked list
        if (temp == null)
            return;
 
        // Unlink the list_Node from linked list
        prev.next = temp.next;
    }
 
    /* Inserts a new list_Node at front of the list. */
    public void add(Node new_data)
    {
        list_Node new_list_Node = new list_Node(new_data);
        new_list_Node.next = head;
        head = new_list_Node;
    }
     
    public int length(){
        list_Node faltu= head;
        int x=0;
        while(faltu!=null){
            faltu=faltu.next;
            x++;
        }
        return x;
    }
}

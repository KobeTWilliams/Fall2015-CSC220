/**
    Do not modify Node class
*/

package PJ2;

class Node<T>
{
      private T    data; // entry in stack
      private Node<T> next; // link to next node

      Node(T dataPortion)
      {
         this(dataPortion, null);
      } // end constructor
      
      Node(T dataPortion, Node<T> nextNode)
      {
         data = dataPortion;
         next = nextNode;
      } // end constructor
      
      T getData()
      {
         return data;
      } // end getData
      
      void setData(T newData)
      {
         data = newData;
      } // end setData
      
      Node<T> getNextNode()
      {
         return next;
      } // end getNextNode
      
      void setNextNode(Node<T> nextNode)
      {
         next = nextNode;
      } // end setNextNode
} // end Node



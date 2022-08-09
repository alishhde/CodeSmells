final class AvlNode<T extends Comparable<T>>
{
 AvlNode<T> parent = null;
 AvlNode<T> left = null;
 AvlNode<T> right = null;


 int height = 0;
 int balance = 0;
 T value = null;




 AvlNode( AvlNode<T> parent, T value )
    {
 this.parent = parent;
 this.value = value;
    }




 public AvlNode<T> reset( AvlNode<T> parent, T value )
    {
 this.parent = parent;
 left = null;
 right = null;


 height = 0;
 this.value = value;


 return this;
    }
}
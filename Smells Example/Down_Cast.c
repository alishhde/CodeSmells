class A
{
    // Nothing here, but if you're a C++ programmer who dislikes object.Equals(object),
    // pretend it's not there and we have abstract bool A.Equals(A) instead.
}
class B : A
{
    private int x;
    public override bool Equals(object other)
    {
        var casted = other as B;  // cautious downcast (dynamic_cast in C++)
        return casted != null && this.x == casted.x;
    }
}
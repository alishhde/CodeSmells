 public abstract class ImageSource
	{


 protected int height;
 protected int width;


 public ImageSource( int width, int height )
		{
 this.width = width;
 this.height = height;
		}


 public int getHeight( )
		{
 return height;
		}


 public int getWidth( )
		{
 return width;
		}


 public abstract int getRGB( int x, int y );
	}
public class KerningRecord implements IDataType
{
 private int code1;
 private int code2;
 private int adjustment;


 /**
     * @return the code1
     */
 public int getCode1()
    {
 return code1;
    }


 /**
     * @param code1 the code1 to set
     */
 public void setCode1(int code1)
    {
 this.code1 = code1;
    }


 /**
     * @return the code2
     */
 public int getCode2()
    {
 return code2;
    }


 /**
     * @param code2 the code2 to set
     */
 public void setCode2(int code2)
    {
 this.code2 = code2;
    }


 /**
     * @return the adjustment
     */
 public int getAdjustment()
    {
 return adjustment;
    }


 /**
     * @param adjustment the adjustment to set
     */
 public void setAdjustment(int adjustment)
    {
 this.adjustment = adjustment;
    }
}
public abstract class FuncLongToString extends VectorExpression {
 private static final long serialVersionUID = 1L;


 private final int inputColumn;


 // Transient members initialized by transientInit method.
 protected byte[] bytes;


 FuncLongToString(int inputColumn, int outputColumnNum) {
 super(outputColumnNum);
 this.inputColumn = inputColumn;
  }


 FuncLongToString() {
 super();


 // Dummy final assignments.
 inputColumn = -1;
  }


 @Override
 public void transientInit() throws HiveException {
 super.transientInit();


 bytes = new byte[64];    // staging area for results, to avoid new() calls
  }


 @Override
 public void evaluate(VectorizedRowBatch batch) throws HiveException {


 if (childExpressions != null) {
 super.evaluateChildren(batch);
    }


 LongColumnVector inputColVector = (LongColumnVector) batch.cols[inputColumn];
 int[] sel = batch.selected;
 int n = batch.size;
 long[] vector = inputColVector.vector;
 BytesColumnVector outputColVector = (BytesColumnVector) batch.cols[outputColumnNum];
 outputColVector.initBuffer();


 boolean[] inputIsNull = inputColVector.isNull;
 boolean[] outputIsNull = outputColVector.isNull;


 if (n == 0) {
 //Nothing to do
 return;
    }


 // We do not need to do a column reset since we are carefully changing the output.
 outputColVector.isRepeating = false;


 if (inputColVector.isRepeating) {
 if (inputColVector.noNulls || !inputIsNull[0]) {
 // Set isNull before call in case it changes it mind.
 outputIsNull[0] = false;
 prepareResult(0, vector, outputColVector);
      } else {
 outputIsNull[0] = true;
 outputColVector.noNulls = false;
      }
 outputColVector.isRepeating = true;
 return;
    }


 if (inputColVector.noNulls) {
 if (batch.selectedInUse) {


 // CONSIDER: For large n, fill n or all of isNull array and use the tighter ELSE loop.


 if (!outputColVector.noNulls) {
 for(int j = 0; j != n; j++) {
 final int i = sel[j];
 // Set isNull before call in case it changes it mind.
 outputIsNull[i] = false;
 prepareResult(i, vector, outputColVector);
         }
        } else {
 for(int j = 0; j != n; j++) {
 final int i = sel[j];
 prepareResult(i, vector, outputColVector);
          }
        }
      } else {
 if (!outputColVector.noNulls) {


 // Assume it is almost always a performance win to fill all of isNull so we can
 // safely reset noNulls.
 Arrays.fill(outputIsNull, false);
 outputColVector.noNulls = true;
        }
 for(int i = 0; i != n; i++) {
 prepareResult(i, vector, outputColVector);
        }
      }
    } else /* there are nulls in the inputColVector */ {


 // Carefully handle NULLs...
 outputColVector.noNulls = false;


 if (batch.selectedInUse) {
 for(int j=0; j != n; j++) {
 int i = sel[j];
 outputColVector.isNull[i] = inputColVector.isNull[i];
 if (!inputColVector.isNull[i]) {
 prepareResult(i, vector, outputColVector);
          }
        }
      } else {
 for(int i = 0; i != n; i++) {
 outputColVector.isNull[i] = inputColVector.isNull[i];
 if (!inputColVector.isNull[i]) {
 prepareResult(i, vector, outputColVector);
          }
        }
      }
    }
  }


 /* Evaluate result for position i (using bytes[] to avoid storage allocation costs)
   * and set position i of the output vector to the result.
   */
 abstract void prepareResult(int i, long[] vector, BytesColumnVector outputColVector);


 @Override
 public String vectorExpressionParameters() {
 return getColumnParamString(0, inputColumn);
  }


 @Override
 public VectorExpressionDescriptor.Descriptor getDescriptor() {
 return (new VectorExpressionDescriptor.Builder()).setMode(
 VectorExpressionDescriptor.Mode.PROJECTION).setNumArguments(1).setInputExpressionTypes(
 VectorExpressionDescriptor.InputExpressionType.COLUMN).setArgumentTypes(
 VectorExpressionDescriptor.ArgumentType.INT_FAMILY).build();
  }
}
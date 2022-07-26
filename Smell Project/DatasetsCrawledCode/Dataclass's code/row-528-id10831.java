public class CovarianceMatricesAggregator implements Serializable {
 /** Serial version uid. */
 private static final long serialVersionUID = 4163253784526780812L;


 /** Mean vector. */
 private final Vector mean;


 /** Weighted by P(c|xi) sum of (xi - mean) * (xi - mean)^T values. */
 private Matrix weightedSum;


 /** Count of rows. */
 private int rowCount;


 /**
     * Creates an instance of CovarianceMatricesAggregator.
     *
     * @param mean Mean vector.
     */
 CovarianceMatricesAggregator(Vector mean) {
 this.mean = mean;
    }


 /**
     * Creates an instance of CovarianceMatricesAggregator.
     *
     * @param mean Mean vector.
     * @param weightedSum Weighted sums for covariace computation.
     * @param rowCount Count of rows.
     */
 CovarianceMatricesAggregator(Vector mean, Matrix weightedSum, int rowCount) {
 this.mean = mean;
 this.weightedSum = weightedSum;
 this.rowCount = rowCount;
    }


 /**
     * Computes covatiation matrices for feature vector for each GMM component.
     *
     * @param dataset Dataset.
     * @param clusterProbs Probabilities of each GMM component.
     * @param means Means for each GMM component.
     */
 static List<Matrix> computeCovariances(Dataset<EmptyContext, GmmPartitionData> dataset,
 Vector clusterProbs, Vector[] means) {


 List<CovarianceMatricesAggregator> aggregators = dataset.compute(
 data -> map(data, means),
 CovarianceMatricesAggregator::reduce
        );


 if (aggregators == null)
 return Collections.emptyList();


 List<Matrix> res = new ArrayList<>();
 for (int i = 0; i < aggregators.size(); i++)
 res.add(aggregators.get(i).covariance(clusterProbs.get(i)));


 return res;
    }


 /**
     * @param x Feature vector (xi).
     * @param pcxi P(c|xi) for GMM component "c" and vector xi.
     */
 void add(Vector x, double pcxi) {
 Matrix deltaCol = x.minus(mean).toMatrix(false);
 Matrix weightedCovComponent = deltaCol.times(deltaCol.transpose()).times(pcxi);
 if (weightedSum == null)
 weightedSum = weightedCovComponent;
 else
 weightedSum = weightedSum.plus(weightedCovComponent);
 rowCount += 1;
    }


 /**
     * @param other Other.
     * @return sum of aggregators.
     */
 CovarianceMatricesAggregator plus(CovarianceMatricesAggregator other) {
 A.ensure(this.mean.equals(other.mean), "this.mean == other.mean");


 return new CovarianceMatricesAggregator(
 mean,
 this.weightedSum.plus(other.weightedSum),
 this.rowCount + other.rowCount
        );
    }


 /**
     * Map stage for covariance computation over dataset.
     *
     * @param data Data partition.
     * @param means Means vector.
     * @return Covariance aggregators.
     */
 static List<CovarianceMatricesAggregator> map(GmmPartitionData data, Vector[] means) {
 int countOfComponents = means.length;


 List<CovarianceMatricesAggregator> aggregators = new ArrayList<>();
 for (int i = 0; i < countOfComponents; i++)
 aggregators.add(new CovarianceMatricesAggregator(means[i]));


 for (int i = 0; i < data.size(); i++) {
 for (int c = 0; c < countOfComponents; c++)
 aggregators.get(c).add(data.getX(i), data.pcxi(c, i));
        }


 return aggregators;
    }


 /**
     * @param clusterProb GMM component probability.
     * @return computed covariance matrix.
     */
 private Matrix covariance(double clusterProb) {
 return weightedSum.divide(rowCount * clusterProb);
    }


 /**
     * Reduce stage for covariance computation over dataset.
     *
     * @param l first partition.
     * @param r second partition.
     */
 static List<CovarianceMatricesAggregator> reduce(List<CovarianceMatricesAggregator> l,
 List<CovarianceMatricesAggregator> r) {


 A.ensure(l != null || r != null, "Both partitions cannot equal to null");


 if (l == null || l.isEmpty())
 return r;
 if (r == null || r.isEmpty())
 return l;


 A.ensure(l.size() == r.size(), "l.size() == r.size()");
 List<CovarianceMatricesAggregator> res = new ArrayList<>();
 for (int i = 0; i < l.size(); i++)
 res.add(l.get(i).plus(r.get(i)));


 return res;
    }


 /**
     * @return mean vector.
     */
 Vector mean() {
 return mean.copy();
    }


 /**
     * @return weighted sum.
     */
 Matrix weightedSum() {
 return weightedSum.copy();
    }


 /**
     * @return rows count.
     */
 public int rowCount() {
 return rowCount;
    }
}
 private void removeAndReconnect(MapReduceOper mr, MapReduceOper newMR) throws VisitorException {
 List<MapReduceOper> mapperSuccs = getPlan().getSuccessors(mr);
 List<MapReduceOper> mapperPreds = getPlan().getPredecessors(mr);


 // make a copy before removing operator
 ArrayList<MapReduceOper> succsCopy = null;
 ArrayList<MapReduceOper> predsCopy = null;
 if (mapperSuccs != null) {
 succsCopy = new ArrayList<MapReduceOper>(mapperSuccs);
        }
 if (mapperPreds != null) {
 predsCopy = new ArrayList<MapReduceOper>(mapperPreds);
        }
 getPlan().remove(mr);


 // reconnect the mapper's successors
 if (succsCopy != null) {
 for (MapReduceOper succ : succsCopy) {
 try {
 getPlan().connect(newMR, succ);
                } catch (PlanException e) {
 int errCode = 2133;
 String msg = "Internal Error. Unable to connect map plan with successors for optimization.";
 throw new OptimizerException(msg, errCode, PigException.BUG, e);
                }
            }
        }


 // reconnect the mapper's predecessors
 if (predsCopy != null) {
 for (MapReduceOper pred : predsCopy) {
 if (newMR.getOperatorKey().equals(pred.getOperatorKey())) {
 continue;
                }
 try {
 getPlan().connect(pred, newMR);
                } catch (PlanException e) {
 int errCode = 2134;
 String msg = "Internal Error. Unable to connect map plan with predecessors for optimization.";
 throw new OptimizerException(msg, errCode, PigException.BUG, e);
                }
            }
        }


 mergeMROperProperties(mr, newMR);
    }
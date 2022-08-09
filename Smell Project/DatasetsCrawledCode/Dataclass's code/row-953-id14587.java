public abstract class AbstractGroupingProperty {
 protected Set<LogicalVariable> columnSet;


 public AbstractGroupingProperty(Set<LogicalVariable> columnSet) {
 this.columnSet = columnSet;
    }


 public Set<LogicalVariable> getColumnSet() {
 return columnSet;
    }


 // Returns normalized and concise columns from an input column set, by considering
 // equivalence classes and functional dependencies.
 protected Set<LogicalVariable> normalizeAndReduceGroupingColumns(Set<LogicalVariable> columns,
 Map<LogicalVariable, EquivalenceClass> equivalenceClasses, List<FunctionalDependency> fds) {
 Set<LogicalVariable> normalizedColumnSet =
 getNormalizedColumnsAccordingToEqClasses(columns, equivalenceClasses);
 reduceGroupingColumns(normalizedColumnSet, fds);
 return normalizedColumnSet;
    }


 // Gets normalized columns, where each column variable is a representative variable of its equivalence class,
 // therefore, the matching of properties will can consider equivalence classes.
 private Set<LogicalVariable> getNormalizedColumnsAccordingToEqClasses(Set<LogicalVariable> columns,
 Map<LogicalVariable, EquivalenceClass> equivalenceClasses) {
 Set<LogicalVariable> normalizedColumns = new ListSet<>();
 if (equivalenceClasses == null || equivalenceClasses.isEmpty()) {
 normalizedColumns.addAll(columns);
 return normalizedColumns;
        }
 for (LogicalVariable v : columns) {
 EquivalenceClass ec = equivalenceClasses.get(v);
 if (ec == null) {
 normalizedColumns.add(v);
            } else {
 if (ec.representativeIsConst()) {
 // trivially satisfied, so the var. can be removed
                } else {
 normalizedColumns.add(ec.getVariableRepresentative());
                }
            }
        }
 return normalizedColumns;
    }


 // Using functional dependencies to eliminate unnecessary columns.
 private void reduceGroupingColumns(Set<LogicalVariable> columnSet, List<FunctionalDependency> fds) {
 // the set of vars. is unordered
 // so we try all FDs on all variables (incomplete algo?)
 if (fds == null || fds.isEmpty()) {
 return;
        }
 Set<LogicalVariable> norm = new ListSet<>();
 for (LogicalVariable v : columnSet) {
 boolean isImpliedByAnFD = false;
 for (FunctionalDependency fdep : fds) {
 if (columnSet.containsAll(fdep.getHead()) && fdep.getTail().contains(v)) {
 isImpliedByAnFD = true;
 norm.addAll(fdep.getHead());
 break;
                }


            }
 if (!isImpliedByAnFD) {
 norm.add(v);
            }
        }
 columnSet.retainAll(norm);
    }


}
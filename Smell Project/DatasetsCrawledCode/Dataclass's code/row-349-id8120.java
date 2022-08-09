@Entity
@NamedQueries({
 @NamedQuery(name = PersistenceConstants.GET_ALL_BACKLOG_INSTANCES, query = "select  OBJECT(a) from BacklogMetricBean a "),
 @NamedQuery(name = PersistenceConstants.DELETE_BACKLOG_METRIC_INSTANCE, query = "delete from BacklogMetricBean a where a.entityName = :entityName and a.clusterName = :clusterName and a.nominalTime = :nominalTime and a.entityType = :entityType"),
 @NamedQuery(name = PersistenceConstants.DELETE_ALL_BACKLOG_ENTITY_INSTANCES, query = "delete from BacklogMetricBean a where a.entityName = :entityName and a.entityType = :entityType")
})
//RESUME CHECKSTYLE CHECK  LineLengthCheck


@Table(name = "BACKLOG_METRIC")
public class BacklogMetricBean {


 @NotNull
 @GeneratedValue(strategy = GenerationType.AUTO)
 @Id
 private String id;


 @Basic
 @NotNull
 @Index
 @Column(name = "entity_name")
 private String entityName;


 @Basic
 @NotNull
 @Column(name = "cluster_name")
 private String clusterName;


 @Basic
 @NotNull
 @Index
 @Column(name = "nominal_time")
 private Date nominalTime;


 @Basic
 @NotNull
 @Index
 @Column(name = "entity_type")
 private String entityType;




 public String getId() {
 return id;
    }


 public String getEntityName() {
 return entityName;
    }


 public String getClusterName() {
 return clusterName;
    }


 public Date getNominalTime() {
 return nominalTime;
    }


 public void setId(String id) {
 this.id = id;
    }


 public void setEntityName(String entityName) {
 this.entityName = entityName;
    }


 public void setClusterName(String clusterName) {
 this.clusterName = clusterName;
    }


 public void setNominalTime(Date nominalTime) {
 this.nominalTime = nominalTime;
    }


 public String getEntityType() {
 return entityType;
    }


 public void setEntityType(String entityType) {
 this.entityType = entityType;
    }
}
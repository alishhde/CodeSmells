@DeferredContextBinding
public class RoutesHealthCheckRepository implements CamelContextAware, HealthCheckRepository {
 private final ConcurrentMap<Route, HealthCheck> checks;
 private Set<String> blacklist;
 private List<PerformanceCounterEvaluator<ManagedRouteMBean>> evaluators;
 private ConcurrentMap<String, Collection<PerformanceCounterEvaluator<ManagedRouteMBean>>> evaluatorMap;
 private volatile CamelContext context;


 public RoutesHealthCheckRepository() {
 this.checks = new ConcurrentHashMap<>();
    }


 @Override
 public void setCamelContext(CamelContext camelContext) {
 this.context = camelContext;
    }


 @Override
 public CamelContext getCamelContext() {
 return context;
    }


 public void setBlacklistedRoutes(Collection<String> blacklistedRoutes) {
 blacklistedRoutes.forEach(this::addBlacklistedRoute);
    }


 public void addBlacklistedRoute(String routeId) {
 if (this.blacklist == null) {
 this.blacklist = new HashSet<>();
        }


 this.blacklist.add(routeId);
    }


 public void setEvaluators(Collection<PerformanceCounterEvaluator<ManagedRouteMBean>> evaluators) {
 evaluators.forEach(this::addEvaluator);
    }


 public void addEvaluator(PerformanceCounterEvaluator<ManagedRouteMBean> evaluator) {
 if (this.evaluators == null) {
 this.evaluators = new CopyOnWriteArrayList<>();
        }


 this.evaluators.add(evaluator);
    }


 public void setRoutesEvaluators(Map<String, Collection<PerformanceCounterEvaluator<ManagedRouteMBean>>> evaluators) {
 evaluators.forEach(this::setRouteEvaluators);
    }


 public void setRouteEvaluators(String routeId, Collection<PerformanceCounterEvaluator<ManagedRouteMBean>> evaluators) {
 evaluators.forEach(evaluator -> addRouteEvaluator(routeId, evaluator));
    }


 public void addRouteEvaluator(String routeId, PerformanceCounterEvaluator<ManagedRouteMBean> evaluator) {
 if (this.evaluatorMap == null) {
 this.evaluatorMap = new ConcurrentHashMap<>();
        }


 this.evaluatorMap.computeIfAbsent(routeId, id -> new CopyOnWriteArrayList<>()).add(evaluator);
    }


 public Stream<PerformanceCounterEvaluator<ManagedRouteMBean>> evaluators() {
 return this.evaluators != null
            ? this.evaluators.stream()
            : Stream.empty();
    }


 public Stream<PerformanceCounterEvaluator<ManagedRouteMBean>> evaluators(String routeId) {
 return this.evaluatorMap != null
            ? evaluatorMap.getOrDefault(routeId, Collections.emptyList()).stream()
            : Stream.empty();
    }


 @Override
 public Stream<HealthCheck> stream() {
 // This is not really efficient as getRoutes() creates a copy of the routes
 // array for each invocation. It would be nice to have more stream oriented
 // operation on CamelContext i.e.
 //
 // interface CamelContext {
 //
 //     Stream<Route> routes();
 //
 //     void forEachRoute(Consumer<Route> consumer);
 // }
 //
 return this.context != null
            ? this.context.getRoutes()
                .stream()
                .filter(route -> route.getId() != null)
                .filter(route -> isNotBlacklisted(route))
                .map(this::toRouteHealthCheck)
            : Stream.empty();
    }


 // *****************************
 // Helpers
 // *****************************


 private boolean isNotBlacklisted(Route route) {
 return this.blacklist != null
            ? !this.blacklist.contains(route.getId())
            : true;
    }


 private HealthCheck toRouteHealthCheck(Route route) {
 return checks.computeIfAbsent(
 route,
 r -> {
 HealthCheck check = new RouteHealthCheck(
 route,
 evaluatorMap != null
                        ? evaluatorMap.getOrDefault(r.getId(), evaluators)
                        : evaluators
                );


 check.getConfiguration().setEnabled(true);


 return check;
            }
        );
    }
}
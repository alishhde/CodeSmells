@SuppressWarnings("serial")
public abstract class StaticMethodMatcherPointcutAdvisor extends StaticMethodMatcherPointcut
 implements PointcutAdvisor, Ordered, Serializable {


 private Advice advice = EMPTY_ADVICE;


 private int order = Ordered.LOWEST_PRECEDENCE;




 /**
	 * Create a new StaticMethodMatcherPointcutAdvisor,
	 * expecting bean-style configuration.
	 * @see #setAdvice
	 */
 public StaticMethodMatcherPointcutAdvisor() {
	}


 /**
	 * Create a new StaticMethodMatcherPointcutAdvisor for the given advice.
	 * @param advice the Advice to use
	 */
 public StaticMethodMatcherPointcutAdvisor(Advice advice) {
 Assert.notNull(advice, "Advice must not be null");
 this.advice = advice;
	}




 public void setOrder(int order) {
 this.order = order;
	}


 @Override
 public int getOrder() {
 return this.order;
	}


 public void setAdvice(Advice advice) {
 this.advice = advice;
	}


 @Override
 public Advice getAdvice() {
 return this.advice;
	}


 @Override
 public boolean isPerInstance() {
 return true;
	}


 @Override
 public Pointcut getPointcut() {
 return this;
	}


}
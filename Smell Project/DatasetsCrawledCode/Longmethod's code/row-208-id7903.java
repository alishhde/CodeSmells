 public void decide(Authentication authentication, Object object,
 Collection<ConfigAttribute> configAttributes) throws AccessDeniedException {
 int deny = 0;


 for (AccessDecisionVoter voter : getDecisionVoters()) {
 int result = voter.vote(authentication, object, configAttributes);


 if (logger.isDebugEnabled()) {
 logger.debug("Voter: " + voter + ", returned: " + result);
			}


 switch (result) {
 case AccessDecisionVoter.ACCESS_GRANTED:
 return;


 case AccessDecisionVoter.ACCESS_DENIED:
 deny++;


 break;


 default:
 break;
			}
		}


 if (deny > 0) {
 throw new AccessDeniedException(messages.getMessage(
 "AbstractAccessDecisionManager.accessDenied", "Access is denied"));
		}


 // To get this far, every AccessDecisionVoter abstained
 checkAllowIfAllAbstainDecisions();
	}
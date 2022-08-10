 static boolean evaluateImpl(Object o1, Object o2) {
 // TODO: maybe we need a comparison "strategy" here, instead of
 // a switch of all possible cases? ... there were other requests for
 // more relaxed type-unsafe comparison (e.g. numbers to strings)


 if (o1 == null && o2 == null) {
 return true;
		} else if (o1 != null) {
 // Per CAY-419 we perform 'in' comparison if one object is a list, and other is not
 if (o2 instanceof Collection) {
 for (Object element : ((Collection<?>) o2)) {
 if (element != null && Evaluator.evaluator(element).eq(element, o1)) {
 return true;
					}
				}
 return false;
			}


 return Evaluator.evaluator(o1).eq(o1, o2);
		}
 return false;
	}
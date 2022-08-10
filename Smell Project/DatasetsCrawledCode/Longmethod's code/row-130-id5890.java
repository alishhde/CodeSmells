 private static boolean handleAspectAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeStruct struct) {
 AnnotationGen aspect = getAnnotation(runtimeAnnotations, AjcMemberMaker.ASPECT_ANNOTATION);
 if (aspect != null) {
 // semantic check for inheritance (only one level up)
 boolean extendsAspect = false;
 if (!"java.lang.Object".equals(struct.enclosingType.getSuperclass().getName())) {
 if (!struct.enclosingType.getSuperclass().isAbstract() && struct.enclosingType.getSuperclass().isAspect()) {
 reportError("cannot extend a concrete aspect", struct);
 return false;
				}
 extendsAspect = struct.enclosingType.getSuperclass().isAspect();
			}


 NameValuePair aspectPerClause = getAnnotationElement(aspect, VALUE);
 final PerClause perClause;
 if (aspectPerClause == null) {
 // empty value means singleton unless inherited
 if (!extendsAspect) {
 perClause = new PerSingleton();
				} else {
 perClause = new PerFromSuper(struct.enclosingType.getSuperclass().getPerClause().getKind());
				}
			} else {
 String perX = aspectPerClause.getValue().stringifyValue();
 if (perX == null || perX.length() <= 0) {
 perClause = new PerSingleton();
				} else {
 perClause = parsePerClausePointcut(perX, struct);
				}
			}
 if (perClause == null) {
 // could not parse it, ignore the aspect
 return false;
			} else {
 perClause.setLocation(struct.context, -1, -1);// struct.context.getOffset(),
 // struct.context.getOffset()+1);//FIXME
 // AVASM
 // Not setting version here
 // struct.ajAttributes.add(new AjAttribute.WeaverVersionInfo());
 AjAttribute.Aspect aspectAttribute = new AjAttribute.Aspect(perClause);
 struct.ajAttributes.add(aspectAttribute);
 FormalBinding[] bindings = new org.aspectj.weaver.patterns.FormalBinding[0];
 final IScope binding;
 binding = new BindingScope(struct.enclosingType, struct.context, bindings);


 // // we can't resolve here since the perclause typically refers
 // to pointcuts
 // // defined in the aspect that we haven't told the
 // BcelObjectType about yet.
 //
 // perClause.resolve(binding);


 // so we prepare to do it later...
 aspectAttribute.setResolutionScope(binding);
 return true;
			}
		}
 return false;
	}
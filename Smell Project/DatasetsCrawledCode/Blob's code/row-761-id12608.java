final class ArrowType extends JSType {
 private static final long serialVersionUID = 1L;


 final Node parameters;
 JSType returnType;


 // Whether the return type is inferred.
 final boolean returnTypeInferred;


 ArrowType(JSTypeRegistry registry, Node parameters, JSType returnType) {
 this(registry, parameters, returnType, false);
  }


 ArrowType(JSTypeRegistry registry, Node parameters,
 JSType returnType, boolean returnTypeInferred) {
 super(registry);


 this.parameters = parameters == null ?
 registry.createParametersWithVarArgs(getNativeType(UNKNOWN_TYPE)) :
 parameters;
 this.returnType = returnType == null ?
 getNativeType(UNKNOWN_TYPE) : returnType;
 this.returnTypeInferred = returnTypeInferred;
  }


 @Override
 public boolean isSubtype(JSType that) {
 return isSubtype(that, ImplCache.create(), SubtypingMode.NORMAL);
  }


 @Override
 protected boolean isSubtype(JSType other,
 ImplCache implicitImplCache, SubtypingMode subtypingMode) {
 if (!(other instanceof ArrowType)) {
 return false;
    }


 ArrowType that = (ArrowType) other;


 // This is described in Draft 2 of the ES4 spec,
 // Section 3.4.7: Subtyping Function Types.


 // this.returnType <: that.returnType (covariant)
 if (!this.returnType.isSubtype(that.returnType, implicitImplCache, subtypingMode)) {
 return false;
    }


 // that.paramType[i] <: this.paramType[i] (contravariant)
 //
 // If this.paramType[i] is required,
 // then that.paramType[i] is required.
 //
 // In theory, the "required-ness" should work in the other direction as
 // well. In other words, if we have
 //
 // function f(number, number) {}
 // function g(number) {}
 //
 // Then f *should* not be a subtype of g, and g *should* not be
 // a subtype of f. But in practice, we do not implement it this way.
 // We want to support the use case where you can pass g where f is
 // expected, and pretend that g ignores the second argument.
 // That way, you can have a single "no-op" function, and you don't have
 // to create a new no-op function for every possible type signature.
 //
 // So, in this case, g < f, but f !< g
 Node thisParam = parameters.getFirstChild();
 Node thatParam = that.parameters.getFirstChild();
 while (thisParam != null && thatParam != null) {
 JSType thisParamType = thisParam.getJSType();
 JSType thatParamType = thatParam.getJSType();
 if (thisParamType != null) {
 if (thatParamType == null ||
            !thatParamType.isSubtype(thisParamType, implicitImplCache, subtypingMode)) {
 return false;
        }
      }


 boolean thisIsVarArgs = thisParam.isVarArgs();
 boolean thatIsVarArgs = thatParam.isVarArgs();
 boolean thisIsOptional = thisIsVarArgs || thisParam.isOptionalArg();
 boolean thatIsOptional = thatIsVarArgs || thatParam.isOptionalArg();


 // "that" can't be a supertype, because it's missing a required argument.
 if (!thisIsOptional && thatIsOptional) {
 // NOTE(nicksantos): In our type system, we use {function(...?)} and
 // {function(...NoType)} to to indicate that arity should not be
 // checked. Strictly speaking, this is not a correct formulation,
 // because now a sub-function can required arguments that are var_args
 // in the super-function. So we special-case this.
 boolean isTopFunction =
 thatIsVarArgs &&
            (thatParamType == null ||
 thatParamType.isUnknownType() ||
 thatParamType.isNoType());
 if (!isTopFunction) {
 return false;
        }
      }


 // don't advance if we have variable arguments
 if (!thisIsVarArgs) {
 thisParam = thisParam.getNext();
      }
 if (!thatIsVarArgs) {
 thatParam = thatParam.getNext();
      }


 // both var_args indicates the end
 if (thisIsVarArgs && thatIsVarArgs) {
 thisParam = null;
 thatParam = null;
      }
    }


 // "that" can't be a supertype, because it's missing a required argument.
 return thisParam == null || thisParam.isOptionalArg() || thisParam.isVarArgs()
        || thatParam != null;
  }


 /**
   * @return True if our parameter spec is equal to {@code that}'s parameter
   *     spec.
   */
 boolean hasEqualParameters(ArrowType that, EquivalenceMethod eqMethod, EqCache eqCache) {
 Node thisParam = parameters.getFirstChild();
 Node otherParam = that.parameters.getFirstChild();
 while (thisParam != null && otherParam != null) {
 JSType thisParamType = thisParam.getJSType();
 JSType otherParamType = otherParam.getJSType();
 if (thisParamType != null) {
 // Both parameter lists give a type for this param, it should be equal
 if (otherParamType != null &&
            !thisParamType.checkEquivalenceHelper(otherParamType, eqMethod, eqCache)) {
 return false;
        }
      } else {
 if (otherParamType != null) {
 return false;
        }
      }


 // Check var_args/optionality
 if (thisParam.isOptionalArg() != otherParam.isOptionalArg()) {
 return false;
      }


 if (thisParam.isVarArgs() != otherParam.isVarArgs()) {
 return false;
      }


 thisParam = thisParam.getNext();
 otherParam = otherParam.getNext();
    }
 // One of the parameters is null, so the types are only equal if both
 // parameter lists are null (they are equal).
 return thisParam == otherParam;
  }


 boolean checkArrowEquivalenceHelper(
 ArrowType that, EquivalenceMethod eqMethod, EqCache eqCache) {
 // Please keep this method in sync with the hashCode() method below.
 if (!returnType.checkEquivalenceHelper(
 that.returnType, eqMethod, eqCache)) {
 return false;
    }
 return hasEqualParameters(that, eqMethod, eqCache);
  }


 @Override
 int recursionUnsafeHashCode() {
 int hashCode = Objects.hashCode(returnType);
 if (parameters != null) {
 Node param = parameters.getFirstChild();
 while (param != null) {
 hashCode = hashCode * 31 + Objects.hashCode(param.getJSType());
 param = param.getNext();
      }
    }
 return hashCode;
  }


 @Override
 public JSType getLeastSupertype(JSType that) {
 throw new UnsupportedOperationException();
  }


 @Override
 public JSType getGreatestSubtype(JSType that) {
 throw new UnsupportedOperationException();
  }


 @Override
 public TernaryValue testForEquality(JSType that) {
 throw new UnsupportedOperationException();
  }


 @Override
 public <T> T visit(Visitor<T> visitor) {
 throw new UnsupportedOperationException();
  }


 @Override <T> T visit(RelationshipVisitor<T> visitor, JSType that) {
 throw new UnsupportedOperationException();
  }


 @Override
 public BooleanLiteralSet getPossibleToBooleanOutcomes() {
 return BooleanLiteralSet.TRUE;
  }


 @Override
 JSType resolveInternal(ErrorReporter reporter) {
 returnType = safeResolve(returnType, reporter);
 if (parameters != null) {
 for (Node paramNode = parameters.getFirstChild();
 paramNode != null; paramNode = paramNode.getNext()) {
 paramNode.setJSType(paramNode.getJSType().resolve(reporter));
      }
    }
 return this;
  }


 boolean hasUnknownParamsOrReturn() {
 if (parameters != null) {
 for (Node paramNode = parameters.getFirstChild();
 paramNode != null; paramNode = paramNode.getNext()) {
 JSType type = paramNode.getJSType();
 if (type == null || type.isUnknownType()) {
 return true;
        }
      }
    }
 return returnType == null || returnType.isUnknownType();
  }


 @Override
 StringBuilder appendTo(StringBuilder sb, boolean forAnnotations) {
 return sb.append("[ArrowType]");
  }


 @Override
 public boolean hasAnyTemplateTypesInternal() {
 return returnType.hasAnyTemplateTypes()
        || hasTemplatedParameterType();
  }


 private boolean hasTemplatedParameterType() {
 if (parameters != null) {
 for (Node paramNode = parameters.getFirstChild();
 paramNode != null; paramNode = paramNode.getNext()) {
 JSType type = paramNode.getJSType();
 if (type != null && type.hasAnyTemplateTypes()) {
 return true;
        }
      }
    }
 return false;
  }
}
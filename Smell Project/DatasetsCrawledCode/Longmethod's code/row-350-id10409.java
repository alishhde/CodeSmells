 public BindStatus(RequestContext requestContext, String path, boolean htmlEscape) throws IllegalStateException {
 this.requestContext = requestContext;
 this.path = path;
 this.htmlEscape = htmlEscape;


 // determine name of the object and property
 String beanName;
 int dotPos = path.indexOf('.');
 if (dotPos == -1) {
 // property not set, only the object itself
 beanName = path;
 this.expression = null;
		}
 else {
 beanName = path.substring(0, dotPos);
 this.expression = path.substring(dotPos + 1);
		}


 this.errors = requestContext.getErrors(beanName, false);


 if (this.errors != null) {
 // Usual case: A BindingResult is available as request attribute.
 // Can determine error codes and messages for the given expression.
 // Can use a custom PropertyEditor, as registered by a form controller.
 if (this.expression != null) {
 if ("*".equals(this.expression)) {
 this.objectErrors = this.errors.getAllErrors();
				}
 else if (this.expression.endsWith("*")) {
 this.objectErrors = this.errors.getFieldErrors(this.expression);
				}
 else {
 this.objectErrors = this.errors.getFieldErrors(this.expression);
 this.value = this.errors.getFieldValue(this.expression);
 this.valueType = this.errors.getFieldType(this.expression);
 if (this.errors instanceof BindingResult) {
 this.bindingResult = (BindingResult) this.errors;
 this.actualValue = this.bindingResult.getRawFieldValue(this.expression);
 this.editor = this.bindingResult.findEditor(this.expression, null);
					}
 else {
 this.actualValue = this.value;
					}
				}
			}
 else {
 this.objectErrors = this.errors.getGlobalErrors();
			}
 this.errorCodes = initErrorCodes(this.objectErrors);
		}


 else {
 // No BindingResult available as request attribute:
 // Probably forwarded directly to a form view.
 // Let's do the best we can: extract a plain target if appropriate.
 Object target = requestContext.getModelObject(beanName);
 if (target == null) {
 throw new IllegalStateException("Neither BindingResult nor plain target object for bean name '" +
 beanName + "' available as request attribute");
			}
 if (this.expression != null && !"*".equals(this.expression) && !this.expression.endsWith("*")) {
 BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(target);
 this.value = bw.getPropertyValue(this.expression);
 this.valueType = bw.getPropertyType(this.expression);
 this.actualValue = this.value;
			}
 this.errorCodes = new String[0];
 this.errorMessages = new String[0];
		}


 if (htmlEscape && this.value instanceof String) {
 this.value = HtmlUtils.htmlEscape((String) this.value);
		}
	}
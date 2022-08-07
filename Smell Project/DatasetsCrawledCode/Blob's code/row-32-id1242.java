@Controller
public class SignupController {


 private final SignupHelper signupHelper;
 
 @Inject
 public SignupController(AccountRepository accountRepository, SignedUpGateway gateway) {
 this.signupHelper = new SignupHelper(accountRepository, gateway);
	}


 /**
	 * Render a signup form to the person as HTML in their web browser.
	 */
 @RequestMapping(value="/signup", method=RequestMethod.GET)
 public SignupForm signupForm(WebRequest request) {
 Connection<?> connection = ProviderSignInUtils.getConnection(request);
 if (connection != null) {
 request.setAttribute("message", new Message(MessageType.INFO, "Your " + StringUtils.capitalize(connection.getKey().getProviderId()) + " account is not associated with a Greenhouse account. If you're new, please sign up."), WebRequest.SCOPE_REQUEST);
 return SignupForm.fromProviderUser(connection.fetchUserProfile());
		} else {
 return new SignupForm();
		}
	}


 /**
	 * Process a signup form submission.
	 * Delegate to a {@link SignupHelper} to actually complete the signin transaction.
	 * Redirects the new member to the application home page on successful sign-in.
	 */
 @RequestMapping(value="/signup", method=RequestMethod.POST)
 public String signup(@Valid SignupForm form, BindingResult formBinding, final WebRequest request) {
 if (formBinding.hasErrors()) {
 return null;
		}
 boolean result = signupHelper.signup(form, formBinding, new SignupCallback() {
 public void postSignup(Account account) {
 ProviderSignInUtils.handlePostSignUp(account.getId().toString(), request);
			}
		});
 return result ? "redirect:/" : null;
	}
 
 @RequestMapping(value="/signup", method=RequestMethod.POST, consumes="application/json")
 public ResponseEntity<Map<String, Object>> signupFromApi(@RequestBody SignupForm form) {
 
 BindingResult formBinding = validate(form); // Temporary manual validation until SPR-9826 is fixed.
 
 if (formBinding.hasErrors()) {
 HashMap<String, Object> errorResponse = new HashMap<String, Object>();
 errorResponse.put("message", "Validation error");
 errorResponse.put("errors", getErrorsMap(formBinding));			
 return new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.BAD_REQUEST);
		}
 boolean result = signupHelper.signup(form, formBinding);
 
 if (result) {
 HashMap<String, Object> errorResponse = new HashMap<String, Object>();
 errorResponse.put("message", "Account created");
 return new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.CREATED);			
		} else {
 HashMap<String, Object> errorResponse = new HashMap<String, Object>();
 errorResponse.put("message", "Account creation error");
 errorResponse.put("errors", getErrorsMap(formBinding));			
 return new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.BAD_REQUEST);			
		}
	}


 private BindException validate(SignupForm form) {
 BindException errors;
 errors = new BindException(form, "signupForm");
 LazyValidatorFactory lvf = new LazyValidatorFactory();
 Validator validator = new SpringValidatorAdapter(lvf.getValidator());
 ValidationUtils.invokeValidator(validator, form, errors);
 return errors;
	}


 private List<Map<String, String>> getErrorsMap(BindingResult formBinding) {
 List<FieldError> fieldErrors = formBinding.getFieldErrors();
 List<Map<String, String>> errors = new ArrayList<Map<String,String>>(fieldErrors.size());						
 for (FieldError fieldError : fieldErrors) {
 Map<String, String> fieldErrorMap = new HashMap<String, String>();
 fieldErrorMap.put("field", fieldError.getField());
 fieldErrorMap.put("code", fieldError.getCode());
 fieldErrorMap.put("message", fieldError.getDefaultMessage());
 errors.add(fieldErrorMap);
		}
 return errors;
	}
 
}
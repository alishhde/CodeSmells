public class FunctionTagsTable extends Composite {


 private final FunctionEditorInput functionEditorInput;
 private final KeyValueSetEditingComposite tagsEditingComposite;
 private final KeyValueSetDataModel tagsDataModel;


 public FunctionTagsTable(Composite parent, FormToolkit toolkit, FunctionEditorInput functionEditorInput) {
 super(parent, SWT.NONE);
 this.functionEditorInput = functionEditorInput;


 this.setLayout(new GridLayout());
 this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));


 tagsDataModel = new KeyValueSetDataModel(MAX_LAMBDA_TAGS, new ArrayList<Pair>());
 tagsEditingComposite = new KeyValueSetEditingCompositeBuilder()
                .addKeyValidator(new StringLengthValidator(1, MAX_LAMBDA_TAG_KEY_LENGTH,
 String.format("This field is too long. Maximum length is %d characters.", MAX_LAMBDA_TAG_KEY_LENGTH)))
                .addValueValidator(new StringLengthValidator(0, MAX_LAMBDA_TAG_VALUE_LENGTH,
 String.format("This field is too long. Maximum length is %d characters.", MAX_LAMBDA_TAG_VALUE_LENGTH)))
                .addKeyValidator(new LambdaTagNameValidator())
                .saveListener(new SelectionAdapter() {
 @Override
 public void widgetSelected(SelectionEvent e) {
 onSaveTags();
                    }
                })
                .build(this, tagsDataModel);


 Composite buttonComposite = new Composite(this, SWT.NONE);
 buttonComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
 buttonComposite.setLayout(new GridLayout(1, false));


 refresh();
    }


 public void refresh() {
 Map<String, String> tagMap = functionEditorInput.getLambdaClient()
                .listTags(new ListTagsRequest()
                        .withResource(functionEditorInput.getFunctionArn()))
                .getTags();
 tagsDataModel.getPairSet().clear();
 for (Entry<String, String> entry : tagMap.entrySet()) {
 tagsDataModel.getPairSet().add(new Pair(entry.getKey(), entry.getValue()));
        }
 tagsEditingComposite.refresh();
    }


 private void onSaveTags() {
 try {
 AWSLambda lambda = functionEditorInput.getLambdaClient();
 Map<String, String> oldTagMap = lambda
                    .listTags(new ListTagsRequest()
                            .withResource(functionEditorInput.getFunctionArn()))
                    .getTags();
 List<String> tagKeysToBeRemoved = new ArrayList<>();
 for (String key : oldTagMap.keySet()) {
 if (!tagsDataModel.getPairSet().contains(key)) {
 tagKeysToBeRemoved.add(key);
                }
            }
 Map<String, String> tagMap = new HashMap<>();
 for (Pair pair : tagsDataModel.getPairSet()) {
 tagMap.put(pair.getKey(), pair.getValue());
            }
 if (!tagKeysToBeRemoved.isEmpty()) {
 lambda.untagResource(new UntagResourceRequest()
                    .withResource(functionEditorInput.getFunctionArn())
                    .withTagKeys(tagKeysToBeRemoved));
            }
 lambda.tagResource(new TagResourceRequest()
                    .withResource(functionEditorInput.getFunctionArn())
                    .withTags(tagMap));
        } catch (AWSLambdaException e) {
 LambdaPlugin.getDefault().reportException(e.getMessage(), e);
        }
    }
}
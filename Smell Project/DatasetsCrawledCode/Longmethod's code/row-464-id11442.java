 private void getSuggestions(final String query) {
 if (query == null || query.isEmpty()) {
 return;
		}


 // Initialize the locatorSugestion parameters
 locatorParams(SUGGEST_PLACE);


 // Attach a listener to the locator task since
 // the LocatorTask may or may not be loaded the
 // the very first time a user types text into the search box.
 // If the Locator is already loaded, the following listener
 // is invoked immediately.


 mLocator.addDoneLoadingListener(new Runnable() {
 @Override public void run() {
 // Does this locator support suggestions?
 if (mLocator.getLoadStatus().name() != LoadStatus.LOADED.name()){
 //Log.i(TAG,"##### " + mLocator.getLoadStatus().name());
				} else if (!mLocator.getLocatorInfo().isSupportsSuggestions()){
 return;
				}
 //og.i(TAG,"****** " + mLocator.getLoadStatus().name());
 final ListenableFuture<List<SuggestResult>> suggestionsFuture = mLocator.suggestAsync(query, suggestParams);
 // Attach a done listener that executes upon completion of the async call
 suggestionsFuture.addDoneListener(new Runnable() {
 @Override
 public void run() {
 try {
 // Get the suggestions returned from the locator task.
 // Store retrieved suggestions for future use (e.g. if the user
 // selects a retrieved suggestion, it can easily be
 // geocoded).
 mSuggestionsList = suggestionsFuture.get();


 showSuggestedPlaceNames(mSuggestionsList);


						} catch (Exception e) {
 Log.e(TAG, "Error on getting suggestions " + e.getMessage());
						}
					}
				});
			}
		});
 // Initiate the asynchronous call
 mLocator.loadAsync();
	}
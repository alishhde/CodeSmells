public class MainActivity extends FragmentActivity {
 private static final String TAG = "MainActivity";
 private static final String INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";


 static final int RPS = 0;
 static final int SETTINGS = 1;
 static final int CONTENT = 2;
 static final int FRAGMENT_COUNT = CONTENT +1;


 private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
 private MenuItem settings;
 private MenuItem challenge;
 private MenuItem share;
 private MenuItem message;
 private boolean isResumed = false;
 private boolean hasNativeLink = false;
 private CallbackManager callbackManager;
 private GameRequestDialog gameRequestDialog;


 private AccessTokenTracker accessTokenTracker;


 @Override
 public void onCreate(Bundle savedInstanceState) {


 FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
 FacebookSdk.setIsDebugEnabled(true);


 super.onCreate(savedInstanceState);
 accessTokenTracker = new AccessTokenTracker() {
 @Override
 protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
 AccessToken currentAccessToken) {
 if (isResumed) {
 if (currentAccessToken == null) {
 showFragment(RPS, false);
                    }
                }
            }
        };


 setContentView(R.layout.main);


 FragmentManager fm = getSupportFragmentManager();
 fragments[RPS] = fm.findFragmentById(R.id.rps_fragment);
 fragments[SETTINGS] = fm.findFragmentById(R.id.settings_fragment);
 fragments[CONTENT] = fm.findFragmentById(R.id.content_fragment);


 FragmentTransaction transaction = fm.beginTransaction();
 for(int i = 0; i < fragments.length; i++) {
 transaction.hide(fragments[i]);
        }
 transaction.commit();


 hasNativeLink = handleNativeLink();


 gameRequestDialog = new GameRequestDialog(this);
 callbackManager = CallbackManager.Factory.create();
 gameRequestDialog.registerCallback(
 callbackManager,
 new FacebookCallback<GameRequestDialog.Result>() {
 @Override
 public void onCancel() {
 Log.d(TAG, "Canceled");
                    }


 @Override
 public void onError(FacebookException error) {
 Log.d(TAG, String.format("Error: %s", error.toString()));
                    }


 @Override
 public void onSuccess(GameRequestDialog.Result result) {
 Log.d(TAG, "Success!");
 Log.d(TAG, "Request id: " + result.getRequestId());
 Log.d(TAG, "Recipients:");
 for (String recipient : result.getRequestRecipients()) {
 Log.d(TAG, recipient);
                        }
                    }
                });
    }


 @Override
 public void onResume() {
 super.onResume();
 isResumed = true;
    }


 @Override
 public void onPause() {
 super.onPause();
 isResumed = false;
    }


 @Override
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
 callbackManager.onActivityResult(requestCode, resultCode, data);
 super.onActivityResult(requestCode, resultCode, data);
 if (requestCode == RpsFragment.IN_APP_PURCHASE_RESULT) {
 String purchaseData = data.getStringExtra(INAPP_PURCHASE_DATA);


 if (resultCode == RESULT_OK) {
 RpsFragment fragment = (RpsFragment) fragments[RPS];
 try {
 JSONObject jo = new JSONObject(purchaseData);
 fragment.onInAppPurchaseSuccess(jo);
                }
 catch (JSONException e) {
 Log.e(TAG, "In app purchase invalid json.", e);
                }
            }
        }
    }


 @Override
 public void onDestroy() {
 super.onDestroy();
 accessTokenTracker.stopTracking();
    }


 @Override
 protected void onResumeFragments() {
 super.onResumeFragments();


 if (hasNativeLink) {
 showFragment(CONTENT, false);
 hasNativeLink = false;
        } else {
 showFragment(RPS, false);
        }
    }


 @Override
 public boolean onPrepareOptionsMenu(Menu menu) {
 // only add the menu when the selection fragment is showing
 if (fragments[RPS].isVisible()) {
 if (menu.size() == 0) {
 share = menu.add(R.string.share_on_facebook);
 message = menu.add(R.string.send_with_messenger);
 challenge = menu.add(R.string.challenge_friends);
 settings = menu.add(R.string.check_settings);
            }
 return true;
        } else {
 menu.clear();
 settings = null;
        }
 return false;
    }


 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
 if (item.equals(settings)) {
 showFragment(SETTINGS, true);
 return true;
        } else if (item.equals(challenge)) {
 GameRequestContent newGameRequestContent = new GameRequestContent.Builder()
                    .setTitle(getString(R.string.challenge_dialog_title))
                    .setMessage(getString(R.string.challenge_dialog_message))
                    .build();


 gameRequestDialog.show(this, newGameRequestContent);


 return true;
        } else if (item.equals(share)) {
 RpsFragment fragment = (RpsFragment) fragments[RPS];
 fragment.shareUsingAutomaticDialog();
 return true;
        } else if (item.equals(message)) {
 RpsFragment fragment = (RpsFragment) fragments[RPS];
 fragment.shareUsingMessengerDialog();
 return true;
        }
 return false;
    }


 private boolean handleNativeLink() {
 if (!AccessToken.isCurrentAccessTokenActive()) {
 AccessToken.createFromNativeLinkingIntent(getIntent(),
 FacebookSdk.getApplicationId(), new AccessToken.AccessTokenCreationCallback(){


 @Override
 public void onSuccess(AccessToken token) {
 AccessToken.setCurrentAccessToken(token);
                        }


 @Override
 public void onError(FacebookException error) {


                        }
                    });
        }
 // See if we have a deep link in addition.
 int appLinkGesture = getAppLinkGesture(getIntent());
 if (appLinkGesture != INVALID_CHOICE) {
 ContentFragment fragment = (ContentFragment) fragments[CONTENT];
 fragment.setContentIndex(appLinkGesture);
 return true;
        }
 return false;
    }


 private int getAppLinkGesture(Intent intent) {
 Uri targetURI = AppLinks.getTargetUrlFromInboundIntent(this, intent);
 if (targetURI == null) {
 return INVALID_CHOICE;
      }
 String gesture = targetURI.getQueryParameter("gesture");
 if (gesture != null) {
 if (gesture.equalsIgnoreCase(getString(R.string.rock))) {
 return RpsGameUtils.ROCK;
          } else if (gesture.equalsIgnoreCase(getString(R.string.paper))) {
 return RpsGameUtils.PAPER;
          } else if (gesture.equalsIgnoreCase(getString(R.string.scissors))) {
 return RpsGameUtils.SCISSORS;
          }
      }
 return INVALID_CHOICE;
    }


 void showFragment(int fragmentIndex, boolean addToBackStack) {
 FragmentManager fm = getSupportFragmentManager();
 FragmentTransaction transaction = fm.beginTransaction();
 if (addToBackStack) {
 transaction.addToBackStack(null);
        } else {
 int backStackSize = fm.getBackStackEntryCount();
 for (int i = 0; i < backStackSize; i++) {
 fm.popBackStack();
            }
        }
 for (int i = 0; i < fragments.length; i++) {
 if (i == fragmentIndex) {
 transaction.show(fragments[i]);
            } else {
 transaction.hide(fragments[i]);
            }
        }
 transaction.commit();
    }
}